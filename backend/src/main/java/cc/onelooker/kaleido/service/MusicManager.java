package cc.onelooker.kaleido.service;

import cc.onelooker.kaleido.dto.ArtistDTO;
import cc.onelooker.kaleido.dto.MusicAlbumArtistDTO;
import cc.onelooker.kaleido.dto.MusicAlbumDTO;
import cc.onelooker.kaleido.dto.MusicTrackDTO;
import cc.onelooker.kaleido.enums.AttributeType;
import cc.onelooker.kaleido.enums.ConfigKey;
import cc.onelooker.kaleido.enums.SubjectType;
import cc.onelooker.kaleido.enums.TaskType;
import cc.onelooker.kaleido.nfo.AlbumNFO;
import cc.onelooker.kaleido.nfo.MovieNFO;
import cc.onelooker.kaleido.nfo.NFOUtil;
import cc.onelooker.kaleido.third.tmm.Album;
import cc.onelooker.kaleido.third.tmm.Artist;
import cc.onelooker.kaleido.third.tmm.Song;
import cc.onelooker.kaleido.third.plex.Metadata;
import cc.onelooker.kaleido.third.plex.PlexUtil;
import cc.onelooker.kaleido.third.tmm.TmmApiService;
import cc.onelooker.kaleido.utils.ConfigUtils;
import cc.onelooker.kaleido.utils.KaleidoConstants;
import cc.onelooker.kaleido.utils.KaleidoUtils;
import cc.onelooker.kaleido.utils.NioFileUtils;
import cn.hutool.core.exceptions.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;

/**
 * @Author cyetstar
 * @Date 2023-10-01 16:09:00
 * @Description TODO
 */
@Slf4j
@Component
public class MusicManager {

    @Autowired
    private MusicAlbumService musicAlbumService;

    @Autowired
    private ArtistService artistService;

    @Autowired
    private MusicAlbumArtistService musicMusicAlbumArtistService;

    @Autowired
    private MusicTrackService musicTrackService;

    @Autowired
    private AttributeService attributeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private TmmApiService tmmApiService;

    @Transactional
    public void saveArtist(ArtistDTO artistDTO) {
        attributeService.updateAttributes(artistDTO.getStyleList(), artistDTO.getId(), AttributeType.Style);
        attributeService.updateAttributes(artistDTO.getGenreList(), artistDTO.getId(), AttributeType.Genre);
        ArtistDTO existArtistDTO = artistService.findById(artistDTO.getId());
        if (existArtistDTO == null) {
            artistService.insert(artistDTO);
        } else {
            artistService.update(artistDTO);
        }
        List<MusicAlbumDTO> musicAlbumDTOList = musicAlbumService.listByArtistId(artistDTO.getId());
        musicAlbumDTOList.forEach(s -> {
            try {
                renameDirIfChanged(s);
                if (ConfigUtils.isEnabled(ConfigKey.writeAudioTag)) {
                    List<MusicTrackDTO> musicTrackDTOList = musicTrackService.listByAlbumId(s.getId());
                    musicTrackDTOList.forEach(t -> taskService.newTask(t.getId(), SubjectType.MusicTrack, TaskType.writeAudioTag));
                }
            } catch (IOException e) {
                ExceptionUtil.wrapAndThrow(e);
            }
        });
    }

    @Transactional
    public void saveAlbum(MusicAlbumDTO musicAlbumDTO) {
        try {
            artistService.updateArtists(musicAlbumDTO.getArtistList(), musicAlbumDTO.getId());
            attributeService.updateAttributes(musicAlbumDTO.getStyleList(), musicAlbumDTO.getId(), AttributeType.Style);
            attributeService.updateAttributes(musicAlbumDTO.getGenreList(), musicAlbumDTO.getId(), AttributeType.Genre);
            attributeService.updateAttributes(musicAlbumDTO.getMoodList(), musicAlbumDTO.getId(), AttributeType.Mood);
            renameDirIfChanged(musicAlbumDTO);
            MusicAlbumDTO existMusicAlbumDTO = musicAlbumService.findById(musicAlbumDTO.getId());
            if (existMusicAlbumDTO == null) {
                musicAlbumService.insert(musicAlbumDTO);
            } else {
                musicAlbumService.update(musicAlbumDTO);
            }
            if (ConfigUtils.isEnabled(ConfigKey.writeAudioTag)) {
                List<MusicTrackDTO> musicTrackDTOList = musicTrackService.listByAlbumId(musicAlbumDTO.getId());
                musicTrackDTOList.forEach(musicTrackDTO -> taskService.newTask(musicTrackDTO.getId(), SubjectType.MusicTrack, TaskType.writeAudioTag));
            }
        } catch (IOException e) {
            ExceptionUtil.wrapAndThrow(e);
        }
    }

    @Transactional
    public void saveTrack(MusicTrackDTO musicTrackDTO) {
        try {
            renameFileIfChanged(musicTrackDTO);
            MusicTrackDTO existMusicTrackDTO = musicTrackService.findById(musicTrackDTO.getId());
            if (existMusicTrackDTO == null) {
                musicTrackService.insert(musicTrackDTO);
            } else {
                musicTrackService.update(musicTrackDTO);
            }
            if (ConfigUtils.isEnabled(ConfigKey.writeAudioTag)) {
                taskService.newTask(musicTrackDTO.getId(), SubjectType.MusicTrack, TaskType.writeAudioTag);
            }
        } catch (Exception e) {
            ExceptionUtil.wrapAndThrow(e);
        }
    }

    @Transactional
    public void syncArtist(Metadata metadata) {
        ArtistDTO artistDTO = artistService.findById(metadata.getRatingKey());
        if (artistDTO == null) {
            artistDTO = new ArtistDTO();
        }
        PlexUtil.toArtistDTO(artistDTO, metadata);
        saveArtist(artistDTO);
    }

    @Transactional
    public void syncAlbum(Metadata metadata, Metadata childMetadata) {
        try {
            MusicAlbumDTO musicAlbumDTO = musicAlbumService.findById(metadata.getRatingKey());
            if (musicAlbumDTO == null) {
                musicAlbumDTO = new MusicAlbumDTO();
            }
            PlexUtil.toMusicAblumDTO(musicAlbumDTO, metadata, childMetadata);
            readAudioTag(musicAlbumDTO);
            saveAlbum(musicAlbumDTO);
        } catch (Exception e) {
            ExceptionUtil.wrapAndThrow(e);
        }
    }

    @Transactional
    public void syncTrack(Metadata metadata) {
        try {
            MusicTrackDTO musicTrackDTO = musicTrackService.findById(metadata.getRatingKey());
            if (musicTrackDTO == null) {
                musicTrackDTO = new MusicTrackDTO();
            }
            PlexUtil.toMusicTrackDTO(musicTrackDTO, metadata);
            readAudioTag(musicTrackDTO);
            saveTrack(musicTrackDTO);
        } catch (Exception e) {
            ExceptionUtil.wrapAndThrow(e);
        }
    }

    private void renameDirIfChanged(MusicAlbumDTO musicAlbumDTO) throws IOException {
        String newPath = KaleidoUtils.genMusicFolder(musicAlbumDTO);
        if (!StringUtils.equals(newPath, musicAlbumDTO.getPath())) {
            Path musicPath = KaleidoUtils.getMusicPath(newPath);
            if (Files.notExists(musicPath)) {
                Files.createDirectories(musicPath);
            }
            NioFileUtils.renameDir(KaleidoUtils.getMusicPath(musicAlbumDTO.getPath()), musicPath);
            musicAlbumDTO.setPath(newPath);
        }
    }

    private void renameFileIfChanged(MusicTrackDTO musicTrackDTO) throws IOException {
        String filename = KaleidoUtils.genMusicFilename(musicTrackDTO);
        if (!StringUtils.equals(filename, musicTrackDTO.getFilename())) {
            MusicAlbumDTO musicAlbumDTO = musicAlbumService.findById(musicTrackDTO.getAlbumId());
            Path path = KaleidoUtils.getMusicFilePath(musicAlbumDTO.getPath(), musicTrackDTO.getFilename());
            Files.move(path, path.resolveSibling(filename));
            String lyricFilename = FilenameUtils.getBaseName(musicTrackDTO.getFilename()) + ".lrc";
            if (Files.exists(path.resolveSibling(lyricFilename))) {
                Files.move(path.resolveSibling(lyricFilename), path.resolveSibling(FilenameUtils.getBaseName(filename) + ".lrc"));
            }
            musicTrackDTO.setFilename(filename);
        }
    }

    private void readAudioTag(MusicTrackDTO musicTrackDTO) throws Exception {
        MusicAlbumDTO musicAlbumDTO = musicAlbumService.findById(musicTrackDTO.getAlbumId());
        Path audioPath = KaleidoUtils.getMusicFilePath(musicAlbumDTO.getPath(), musicTrackDTO.getFilename());
        AudioFile audioFile = AudioFileIO.read(audioPath.toFile());
        Tag tag = audioFile.getTag();
        musicTrackDTO.setArtists(KaleidoUtils.getTagValue(tag, FieldKey.ARTIST));
        musicTrackDTO.setDiscIndex(Integer.parseInt(KaleidoUtils.getTagValue(tag, FieldKey.DISC_NO)));
        musicTrackDTO.setTrackIndex(Integer.parseInt(KaleidoUtils.getTagValue(tag, FieldKey.TRACK)));
        musicTrackDTO.setMusicbrainzId(KaleidoUtils.getTagValue(tag, FieldKey.MUSICBRAINZ_TRACK_ID));
    }

    private void readAudioTag(MusicAlbumDTO musicAlbumDTO) throws Exception {
        Path path = KaleidoUtils.getMusicPath(musicAlbumDTO.getPath());
        Path audioPath = Files.list(path).filter(s -> KaleidoUtils.isAudioFile(s.getFileName().toString())).findFirst().orElse(null);
        if (audioPath == null) {
            return;
        }
        AudioFile audioFile = AudioFileIO.read(audioPath.toFile());
        Tag tag = audioFile.getTag();
        musicAlbumDTO.setMusicbrainzId(KaleidoUtils.getTagValue(tag, FieldKey.MUSICBRAINZ_RELEASEID));
        musicAlbumDTO.setArtists(KaleidoUtils.getTagValue(tag, FieldKey.ALBUM_ARTIST));
        musicAlbumDTO.setType(KaleidoUtils.getTagValue(tag, FieldKey.MUSICBRAINZ_RELEASE_TYPE));
        musicAlbumDTO.setGenre(KaleidoUtils.getTagValue(tag, FieldKey.GENRE));
        musicAlbumDTO.setReleaseCountry(KaleidoUtils.getTagValue(tag, FieldKey.MUSICBRAINZ_RELEASE_COUNTRY));
        musicAlbumDTO.setLabel(KaleidoUtils.getTagValue(tag, FieldKey.RECORD_LABEL));
        musicAlbumDTO.setMedia(KaleidoUtils.getTagValue(tag, FieldKey.MEDIA));
    }

    public void downloadLyric(MusicAlbumDTO musicAlbumDTO) {
        List<MusicTrackDTO> musicTrackDTOList = musicTrackService.listByAlbumId(musicAlbumDTO.getId());
        musicTrackDTOList.forEach(s -> {
            try {
                String lyric = tmmApiService.findLyric(s.getNeteaseId());
                if (StringUtils.isNotEmpty(lyric)) {
                    Path musicFilePath = KaleidoUtils.getMusicFilePath(musicAlbumDTO.getPath(), s.getFilename());
                    File lyricFile = musicFilePath.resolveSibling(FilenameUtils.getBaseName(s.getFilename()) + ".lrc").toFile();
                    FileUtils.writeStringToFile(lyricFile, lyric, StandardCharsets.UTF_8);
                }
            } catch (IOException e) {
                ExceptionUtil.wrapAndThrow(e);
            }
        });
    }

    @Transactional
    public void matchPath(Path path, Album album) {
        try {
            AlbumNFO albumNFO = new AlbumNFO();
            albumNFO.setNeteaseId(album.getNeteaseId());
            albumNFO.setMusicbrainzId(album.getMusicbrainzId());
            Path importPath = KaleidoUtils.getMusicImportPath();
            String filename = FilenameUtils.getBaseName(path.getFileName().toString());
            if (StringUtils.isNotEmpty(album.getTitle()) && !StringUtils.isAllEmpty(album.getNeteaseId(), album.getMusicbrainzId())) {
                filename = album.getTitle() + "(" + StringUtils.defaultIfEmpty(album.getNeteaseId(), album.getMusicbrainzId()) + ")";
            }
            Path newPath = importPath.resolve(filename);
            if (Files.isDirectory(path)) {
                NioFileUtils.deleteByFilter(path, "nfo");
                if (!StringUtils.equals(newPath.toString(), path.toString())) {
                    NioFileUtils.renameDir(path, newPath, StandardCopyOption.REPLACE_EXISTING);
                }
                NFOUtil.write(albumNFO, AlbumNFO.class, newPath, KaleidoConstants.ALBUM_NFO);
            } else {
                if (Files.notExists(newPath)) {
                    Files.createDirectories(newPath);
                }
                NFOUtil.write(albumNFO, AlbumNFO.class, newPath, KaleidoConstants.ALBUM_NFO);
                Files.move(path, newPath.resolve(path.getFileName()), StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (Exception e) {
            ExceptionUtil.wrapAndThrow(e);
        }
    }

    @Transactional
    public void matchInfo(String albumId, Album album) {
        if (album == null) {
            return;
        }
        List<Song> songs = album.getSongs();
        Artist artist = album.getArtist();

        MusicAlbumDTO musicAlbumDTO = musicAlbumService.findById(albumId);
        musicAlbumDTO.setNeteaseId(album.getNeteaseId());
        musicAlbumDTO.setSummary(album.getDescription());
        musicAlbumService.update(musicAlbumDTO);

        List<MusicTrackDTO> musicTrackDTOList = musicTrackService.listByAlbumId(albumId);
        for (MusicTrackDTO musicTrackDTO : musicTrackDTOList) {
            Song song = songs.stream().filter(s -> StringUtils.equalsAnyIgnoreCase(s.getSimpleName(), musicTrackDTO.getTitle(), musicTrackDTO.getSimpleTitle())).findFirst().orElse(null);
            if (song != null) {
                musicTrackDTO.setNeteaseId(song.getNeteaseId());
                musicTrackService.update(musicTrackDTO);
            }
        }

        List<MusicAlbumArtistDTO> musicMusicAlbumArtistDTOList = musicMusicAlbumArtistService.listByAlbumId(musicAlbumDTO.getId());
        for (MusicAlbumArtistDTO musicMusicAlbumArtistDTO : musicMusicAlbumArtistDTOList) {
            ArtistDTO artistDTO = artistService.findById(musicMusicAlbumArtistDTO.getArtistId());
            if (StringUtils.equals(artist.getName(), artistDTO.getTitle())) {
                artistDTO.setNeteaseId(artist.getNeteaseId());
                artistService.update(artistDTO);
            }
        }
    }

//    public void downloadTrackLyric(Long trackId, String neteaseId) {
//        MusicTrackDTO musicTrackDTO = musicTrackService.findById(trackId);
//        try {
//            musicTrackDTO.setNeteaseId(neteaseId);
//            musicTrackService.update(musicTrackDTO);
//            downloadTrackLyric(musicTrackDTO);
//        } catch (Exception e) {
//            log.error("下载歌词失败，曲目名称：{}", musicTrackDTO.getTitle());
//        }
//    }

}
