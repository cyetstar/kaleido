package cc.onelooker.kaleido.service.music;

import cc.onelooker.kaleido.dto.music.MusicAlbumDTO;
import cc.onelooker.kaleido.dto.music.MusicArtistAlbumDTO;
import cc.onelooker.kaleido.dto.music.MusicArtistDTO;
import cc.onelooker.kaleido.dto.music.MusicTrackDTO;
import cc.onelooker.kaleido.enums.ConfigKey;
import cc.onelooker.kaleido.third.netease.Album;
import cc.onelooker.kaleido.third.netease.Artist;
import cc.onelooker.kaleido.third.netease.NeteaseApiService;
import cc.onelooker.kaleido.third.netease.Song;
import cc.onelooker.kaleido.third.plex.Metadata;
import cc.onelooker.kaleido.third.plex.PlexApiService;
import cc.onelooker.kaleido.utils.ConfigUtils;
import com.github.houbb.opencc4j.util.ZhConverterUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.flac.FlacTag;
import org.jaudiotagger.tag.id3.ID3v23FieldKey;
import org.jaudiotagger.tag.id3.ID3v23Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

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
    private MusicArtistService musicArtistService;

    @Autowired
    private MusicArtistAlbumService musicArtistAlbumService;

    @Autowired
    private MusicTrackService musicTrackService;

    @Autowired
    private PlexApiService plexApiService;

    @Autowired
    private NeteaseApiService neteaseApiService;

    @Transactional
    public void syncPlexAlbumAndReadAudioTag(String libraryPath, Long albumId) {
        Metadata metadata = plexApiService.findAlbumById(albumId);
        syncPlexAlbum(libraryPath, metadata);
        readAudioTag(metadata.getRatingKey());
    }

    private void syncPlexAlbum(String libraryPath, Metadata metadata) {
        MusicArtistDTO musicArtistDTO = syncPlexArtist(metadata.getParentRatingKey());
        MusicAlbumDTO musicAlbumDTO = musicAlbumService.findById(metadata.getRatingKey());
        if (musicAlbumDTO == null) {
            musicAlbumDTO = new MusicAlbumDTO();
            musicAlbumDTO.setId(metadata.getRatingKey());
            musicAlbumDTO.setTitle(metadata.getTitle());
            musicAlbumDTO.setArtists(metadata.getParentTitle());
            musicAlbumDTO.setSummary(metadata.getSummary());
            musicAlbumDTO.setThumb(metadata.getThumb());
            musicAlbumDTO.setYear(metadata.getYear());
            musicAlbumDTO.setOriginallyAvailableAt(metadata.getOriginallyAvailableAt());
            musicAlbumDTO.setAddedAt(metadata.getAddedAt());
            musicAlbumDTO.setUpdatedAt(metadata.getUpdatedAt());
            musicAlbumDTO = musicAlbumService.insert(musicAlbumDTO);

            musicArtistAlbumService.insertByArtistIdAndAlbumId(musicArtistDTO.getId(), musicAlbumDTO.getId());
        } else {
            musicAlbumDTO.setTitle(metadata.getTitle());
            musicAlbumDTO.setArtists(metadata.getParentTitle());
            musicAlbumDTO.setThumb(metadata.getThumb());
            musicAlbumDTO.setYear(metadata.getYear());
            musicAlbumDTO.setOriginallyAvailableAt(metadata.getOriginallyAvailableAt());
            musicAlbumDTO.setUpdatedAt(metadata.getUpdatedAt());
            musicAlbumService.update(musicAlbumDTO);

            MusicArtistAlbumDTO musicArtistAlbumDTO = musicArtistAlbumService.findByArtistIdAndAlbumId(musicArtistDTO.getId(), musicAlbumDTO.getId());
            if (musicArtistAlbumDTO == null) {
                musicArtistAlbumService.insertByArtistIdAndAlbumId(musicArtistDTO.getId(), musicAlbumDTO.getId());
            }
        }
        // 同步曲目
        syncPlexTrack(libraryPath, musicAlbumDTO.getId());
    }

    private MusicArtistDTO syncPlexArtist(Long artistId) {
        MusicArtistDTO musicArtistDTO = musicArtistService.findById(artistId);
        if (musicArtistDTO == null) {
            Metadata artist = plexApiService.findArtistById(artistId);
            musicArtistDTO = new MusicArtistDTO();
            musicArtistDTO.setId(artist.getRatingKey());
            musicArtistDTO.setTitle(artist.getTitle());
            musicArtistDTO.setTitleSort(artist.getTitleSort());
            musicArtistDTO.setSummary(artist.getSummary());
            musicArtistDTO.setThumb(artist.getThumb());
            musicArtistDTO.setAddedAt(artist.getAddedAt());
            musicArtistDTO.setUpdatedAt(artist.getUpdatedAt());
            musicArtistDTO = musicArtistService.insert(musicArtistDTO);
        }
        return musicArtistDTO;
    }

    private List<MusicTrackDTO> syncPlexTrack(String libraryPath, Long albumId) {
        List<Metadata> metadataList = plexApiService.listTrackByAlbumId(albumId);
        List<MusicTrackDTO> musicTrackDTOList = Lists.newArrayList();
        for (Metadata metadata : metadataList) {
            MusicTrackDTO musicTrackDTO = musicTrackService.findById(metadata.getRatingKey());
            if (musicTrackDTO == null) {
                musicTrackDTO = new MusicTrackDTO();
                musicTrackDTO.setId(metadata.getRatingKey());
                musicTrackDTO.setTitle(metadata.getTitle());
                musicTrackDTO.setDuration(metadata.getDuration());
                musicTrackDTO.setAlbumId(albumId);
                musicTrackDTO.setAddedAt(metadata.getAddedAt());
                musicTrackDTO.setUpdatedAt(metadata.getUpdatedAt());
                musicTrackDTO.setFormat(metadata.getMedia().getContainer());
                musicTrackDTO.setPath(StringUtils.removeStart(metadata.getMedia().getPart().getFile(), libraryPath));
                musicTrackDTO = musicTrackService.insert(musicTrackDTO);
            }
            musicTrackDTOList.add(musicTrackDTO);
        }
        return musicTrackDTOList;
    }

    public int readAudioTag(Long albumId) {
        int error = 0;
        List<MusicTrackDTO> musicTrackDTOList = musicTrackService.listByAlbumId(albumId);
        for (int i = 0; i < musicTrackDTOList.size(); i++) {
            MusicTrackDTO musicTrackDTO = musicTrackDTOList.get(i);
            if (StringUtils.isEmpty(musicTrackDTO.getPath())) {
                continue;
            }
            try {
                String musicLibraryPath = ConfigUtils.getSysConfig(ConfigKey.musicLibraryPath);
                File file = Paths.get(musicLibraryPath, musicTrackDTO.getPath()).toFile();
                AudioFile audioFile = AudioFileIO.read(file);
                Map<String, String> infoMap = getTagInfo(audioFile.getTag());

                musicTrackDTO.setArtists(infoMap.get("ARTIST"));
                musicTrackDTO.setDiscIndex(Integer.valueOf(infoMap.get("DISC_NO")));
                musicTrackDTO.setTrackIndex(Integer.valueOf(infoMap.get("TRACK")));
                musicTrackDTO.setMusicbrainzId(infoMap.get("MUSICBRAINZ_TRACK_ID"));
                musicTrackService.update(musicTrackDTO);
                if (i == 0) {
                    //只更新一次
                    MusicAlbumDTO musicAlbumDTO = musicAlbumService.findById(musicTrackDTO.getAlbumId());
                    musicAlbumDTO.setMusicbrainzId(infoMap.get("MUSICBRAINZ_ALBUMID"));
                    musicAlbumDTO.setArtists(infoMap.get("ALBUMARTIST"));
                    musicAlbumDTO.setType(infoMap.get("RELEASETYPE"));
                    musicAlbumDTO.setGenre(infoMap.get("GENRE"));
                    musicAlbumDTO.setReleaseCountry(infoMap.get("RELEASECOUNTRY"));
                    musicAlbumDTO.setLabel(infoMap.get("RECORD_LABEL"));
                    musicAlbumDTO.setMedia(infoMap.get("MEDIA"));
                    musicAlbumService.update(musicAlbumDTO);
                }
            } catch (Exception e) {
                log.error("读取音频文件失败，曲目名称：{}", musicTrackDTO.getTitle(), e);
                error++;
            }
        }
        return error;
    }

    private Map<String, String> getTagInfo(Tag tag) {
        Map<String, String> resultMap = Maps.newHashMap();
        if (tag instanceof FlacTag) {
            FlacTag flacTag = (FlacTag) tag;
            resultMap.put("DISC_NO", flacTag.getFirst(FieldKey.DISC_NO));
            resultMap.put("ARTIST", flacTag.getFirst(FieldKey.ARTIST));
            resultMap.put("TRACK", flacTag.getFirst(FieldKey.TRACK));
            resultMap.put("MUSICBRAINZ_TRACK_ID", flacTag.getFirst(FieldKey.MUSICBRAINZ_TRACK_ID));
            resultMap.put("MUSICBRAINZ_ALBUMID", flacTag.getFirst(FieldKey.MUSICBRAINZ_RELEASEID));
            resultMap.put("ALBUMARTIST", flacTag.getFirst(FieldKey.ALBUM_ARTIST));
            resultMap.put("RELEASETYPE", flacTag.getFirst("RELEASETYPE"));
            resultMap.put("GENRE", flacTag.getFirst(FieldKey.GENRE));
            resultMap.put("RELEASECOUNTRY", flacTag.getFirst(FieldKey.MUSICBRAINZ_RELEASE_COUNTRY));
            resultMap.put("DATE", flacTag.getFirst(FieldKey.YEAR));
            resultMap.put("RECORD_LABEL", flacTag.getFirst(FieldKey.RECORD_LABEL));
            resultMap.put("ORIGINALDATE", flacTag.getFirst(FieldKey.ORIGINALRELEASEDATE));
            resultMap.put("MEDIA", flacTag.getFirst(FieldKey.MEDIA));
        } else if (tag instanceof ID3v23Tag) {
            ID3v23Tag id3v2Tag = (ID3v23Tag) tag;
            resultMap.put("DISC_NO", id3v2Tag.getFirst(FieldKey.DISC_NO));
            resultMap.put("ARTIST", id3v2Tag.getFirst(FieldKey.ARTIST));
            resultMap.put("TRACK", id3v2Tag.getFirst(FieldKey.TRACK));
            resultMap.put("MUSICBRAINZ_TRACK_ID", id3v2Tag.getFirst(ID3v23FieldKey.MUSICBRAINZ_TRACK_ID));
            resultMap.put("MUSICBRAINZ_ALBUMID", id3v2Tag.getFirst(ID3v23FieldKey.MUSICBRAINZ_RELEASEID));
            resultMap.put("ALBUMARTIST", id3v2Tag.getFirst(ID3v23FieldKey.ALBUM_ARTIST));
            resultMap.put("RELEASETYPE", id3v2Tag.getFirst(ID3v23FieldKey.MUSICBRAINZ_RELEASE_TYPE));
            resultMap.put("GENRE", id3v2Tag.getFirst(ID3v23FieldKey.GENRE));
            resultMap.put("RELEASECOUNTRY", id3v2Tag.getFirst(ID3v23FieldKey.MUSICBRAINZ_RELEASE_COUNTRY));
            resultMap.put("DATE", id3v2Tag.getFirst(ID3v23FieldKey.YEAR));
            resultMap.put("RECORD_LABEL", id3v2Tag.getFirst(ID3v23FieldKey.RECORD_LABEL));
            resultMap.put("ORIGINALDATE", id3v2Tag.getFirst(ID3v23FieldKey.ORIGINALRELEASEDATE));
            resultMap.put("MEDIA", id3v2Tag.getFirst(ID3v23FieldKey.MEDIA));
        }
        return resultMap;
    }

    public int downloadLyric(Long albumId) {
        int error = 0;
        List<MusicTrackDTO> musicTrackDTOList = musicTrackService.listByAlbumId(albumId);
        for (MusicTrackDTO musicTrackDTO : musicTrackDTOList) {
            if (StringUtils.isEmpty(musicTrackDTO.getPath())) {
                continue;
            }
            try {
                downloadTrackLyric(musicTrackDTO);
            } catch (Exception e) {
                log.error("下载歌词失败，曲目名称：{}", musicTrackDTO.getTitle());
                error++;
            }
        }
        return error;
    }

    @Transactional
    public void matchNetease(Long albumId, String neteaseId) {
        Album album = neteaseApiService.getAlbum(neteaseId);
        List<Song> songs = album.getSongs();
        Artist artist = album.getArtist();

        MusicAlbumDTO musicAlbumDTO = musicAlbumService.findById(albumId);
        musicAlbumDTO.setNeteaseId(neteaseId);
        musicAlbumDTO.setSummary(album.getDescription());
        musicAlbumService.update(musicAlbumDTO);

        List<MusicTrackDTO> musicTrackDTOList = musicTrackService.listByAlbumId(albumId);
        for (MusicTrackDTO musicTrackDTO : musicTrackDTOList) {
            Song song = songs.stream().filter(s -> StringUtils.equalsAnyIgnoreCase(s.getSimpleName(), musicTrackDTO.getTitle(), musicTrackDTO.getSimpleTitle())).findFirst().orElse(null);
            if (song != null) {
                musicTrackDTO.setNeteaseId(song.getId());
                musicTrackService.update(musicTrackDTO);
            }
        }

        List<MusicArtistAlbumDTO> musicArtistAlbumDTOList = musicArtistAlbumService.listByAlbumId(musicAlbumDTO.getId());
        for (MusicArtistAlbumDTO musicArtistAlbumDTO : musicArtistAlbumDTOList) {
            MusicArtistDTO musicArtistDTO = musicArtistService.findById(musicArtistAlbumDTO.getArtistId());
            if (StringUtils.equals(artist.getName(), musicArtistDTO.getTitle())) {
                musicArtistDTO.setNeteaseId(artist.getId());
                musicArtistService.update(musicArtistDTO);
            }
        }
    }


    public void downloadTrackLyric(Long trackId, String neteaseId) {
        MusicTrackDTO musicTrackDTO = musicTrackService.findById(trackId);
        try {
            musicTrackDTO.setNeteaseId(neteaseId);
            musicTrackService.update(musicTrackDTO);
            downloadTrackLyric(musicTrackDTO);
        } catch (Exception e) {
            log.error("下载歌词失败，曲目名称：{}", musicTrackDTO.getTitle());
        }
    }

    private void downloadTrackLyric(MusicTrackDTO musicTrackDTO) throws IOException {
        String musicLibraryPath = ConfigUtils.getSysConfig(ConfigKey.musicLibraryPath);
        File file = Paths.get(musicLibraryPath, FilenameUtils.removeExtension(musicTrackDTO.getPath()) + ".lrc").toFile();
        String lyric = neteaseApiService.getLyric(musicTrackDTO.getNeteaseId());
        if (StringUtils.isNotEmpty(lyric)) {
            FileUtils.writeStringToFile(file, lyric, StandardCharsets.UTF_8);
        }
    }
}
