package cc.onelooker.kaleido.service;

import cc.onelooker.kaleido.dto.ArtistDTO;
import cc.onelooker.kaleido.dto.AttributeDTO;
import cc.onelooker.kaleido.dto.MusicAlbumDTO;
import cc.onelooker.kaleido.dto.MusicTrackDTO;
import cc.onelooker.kaleido.enums.AttributeType;
import cc.onelooker.kaleido.enums.ConfigKey;
import cc.onelooker.kaleido.enums.SubjectType;
import cc.onelooker.kaleido.enums.TaskType;
import cc.onelooker.kaleido.nfo.AlbumNFO;
import cc.onelooker.kaleido.nfo.NFOUtil;
import cc.onelooker.kaleido.third.plex.Metadata;
import cc.onelooker.kaleido.third.plex.PlexUtil;
import cc.onelooker.kaleido.third.tmm.Album;
import cc.onelooker.kaleido.third.tmm.Song;
import cc.onelooker.kaleido.third.tmm.TmmApiService;
import cc.onelooker.kaleido.third.tmm.TmmUtil;
import cc.onelooker.kaleido.utils.*;
import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.http.HttpUtil;
import com.zjjcnt.common.util.constant.Constants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
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
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
    private MusicTrackService musicTrackService;

    @Autowired
    private AttributeService attributeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private TmmApiService tmmApiService;

    @Transactional
    public void saveAlbumTrack(MusicAlbumDTO musicAlbumDTO, List<MusicTrackDTO> musicTrackDTOList) {
        saveAlbum(musicAlbumDTO);
        if (musicTrackDTOList != null) {
            musicTrackDTOList.forEach(this::saveTrack);
        }
    }

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
                    musicTrackDTOList.forEach(
                            t -> taskService.newTask(t.getId(), SubjectType.MusicTrack, TaskType.writeAudioTag));
                }
            } catch (IOException e) {
                ExceptionUtil.wrapAndThrow(e);
            }
        });
    }

    @Transactional
    public void saveAlbum(MusicAlbumDTO musicAlbumDTO) {
        try {
            artistService.updateAlbumArtists(musicAlbumDTO.getArtistList(), musicAlbumDTO.getId());
            attributeService.updateAttributes(musicAlbumDTO.getStyleList(), musicAlbumDTO.getId(), AttributeType.Style);
            attributeService.updateAttributes(musicAlbumDTO.getGenreList(), musicAlbumDTO.getId(),
                    AttributeType.MusicGenre);
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
                musicTrackDTOList.forEach(musicTrackDTO -> taskService.newTask(musicTrackDTO.getId(),
                        SubjectType.MusicTrack, TaskType.writeAudioTag));
            }
        } catch (IOException e) {
            ExceptionUtil.wrapAndThrow(e);
        }
    }

    @Transactional
    public void saveTrack(MusicTrackDTO musicTrackDTO) {
        try {
            artistService.updateTrackArtists(musicTrackDTO.getArtistList(), musicTrackDTO.getId());
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
            String[] artistNames = StringUtils.split(musicTrackDTO.getArtists(), Constants.SEMICOLON);
            if (ArrayUtils.isNotEmpty(artistNames)) {
                Arrays.stream(artistNames).map(s -> {
                    ArtistDTO artistDTO = new ArtistDTO();
                    artistDTO.setTitle(s);
                    return artistDTO;
                });
            }
            saveTrack(musicTrackDTO);
        } catch (Exception e) {
            ExceptionUtil.wrapAndThrow(e);
        }
    }

    @Transactional
    public void updateSource(Path path) {
        boolean isDirectory = Files.isDirectory(path);
        if (!isDirectory) {
            return;
        }
        if (Files.exists(path.resolve(KaleidoConstants.ALBUM_NFO))) {
            try {
                log.info("== 开始更新数据文件: {}", path);
                Album album = findTmmAlbumByNFO(path);
                if (album != null) {
                    MusicAlbumDTO musicAlbumDTO = TmmUtil.toMusicAlbumDTO(album);
                    log.info("== 查询到专辑信息: {}", musicAlbumDTO.getTitle());
                    operationPath(musicAlbumDTO, path);
                } else {
                    log.info("== 未找到匹配信息，直接移动文件");
                    Path musicLibraryPath = KaleidoUtil.getMusicLibraryPath();
                    NioFileUtil.moveDir(path, musicLibraryPath, StandardCopyOption.REPLACE_EXISTING);
                }
            } catch (Exception e) {
                log.error("== 更新音乐源发生错误: {}", path, e);
            } finally {
                log.info("== 完成更新数据文件: {}", path);
            }
        }
    }

    public MusicAlbumDTO findMusicAlbum(String albumId) {
        MusicAlbumDTO musicAlbumDTO = musicAlbumService.findById(albumId);
        List<AttributeDTO> attributeDTOList = attributeService.listBySubjectId(musicAlbumDTO.getId());
        List<ArtistDTO> artistDTOList = artistService.listByAlbumId(musicAlbumDTO.getId());
        musicAlbumDTO.setArtistList(artistDTOList);
        musicAlbumDTO.setStyleList(
                attributeDTOList.stream().filter(s -> StringUtils.equals(s.getType(), AttributeType.Style.name()))
                        .map(AttributeDTO::getValue).collect(Collectors.toList()));
        musicAlbumDTO.setMoodList(
                attributeDTOList.stream().filter(s -> StringUtils.equals(s.getType(), AttributeType.Mood.name()))
                        .map(AttributeDTO::getValue).collect(Collectors.toList()));
        musicAlbumDTO.setGenreList(
                attributeDTOList.stream().filter(s -> StringUtils.equals(s.getType(), AttributeType.MusicGenre.name()))
                        .map(AttributeDTO::getValue).collect(Collectors.toList()));
        return musicAlbumDTO;
    }

    private void operationPath(MusicAlbumDTO musicAlbumDTO, Path path) {
        try {
            Path standardPath = createStandardPath(musicAlbumDTO);
            Files.list(path).forEach(s -> {
                try {
                    String fileName = s.getFileName().toString();
                    if (!KaleidoUtil.isAudioFile(fileName)) {
                        return;
                    }
                    Integer trackIndex = KaleidoUtil.parseTrackIndex(fileName);
                    if (trackIndex == null) {
                        log.info("== 无法确定曲号信息，保持不动: {}", fileName);
                        return;
                    }
                    MusicTrackDTO musicTrackDTO = musicAlbumDTO.getTrack(trackIndex);
                    if (musicTrackDTO == null) {
                        log.info("== 无法找到曲号信息，保持不动: {}", fileName);
                        return;
                    }
                    moveExistingFilesToRecycleBin(standardPath, trackIndex);
                    musicTrackDTO.setFilename(s.getFileName().toString());
                    Path trackPath = standardPath.resolve(KaleidoUtil.genMusicTrackFilename(musicTrackDTO));
                    writeAudioTag(musicAlbumDTO, musicTrackDTO, s);
                    downloadLyric(musicTrackDTO, trackPath);
                    Files.move(s, trackPath, StandardCopyOption.REPLACE_EXISTING);
                    log.info("== 移动音频文件: {}", fileName);
                } catch (Exception e) {
                    ExceptionUtil.wrapAndThrow(e);
                }
            });
            // 下载专辑封面
            downloadAlbumCover(musicAlbumDTO, standardPath);
            // 删除空文件夹
            deletePathIfEmpty(path);
        } catch (IOException e) {
            ExceptionUtil.wrapAndThrow(e);
        }
    }

    private void downloadLyric(MusicTrackDTO musicTrackDTO, Path path) {
        try {
            if (StringUtils.isEmpty(musicTrackDTO.getNeteaseId())) {
                return;
            }
            String lyric = tmmApiService.findLyric(musicTrackDTO.getNeteaseId());
            if (StringUtils.isNotEmpty(lyric)) {
                Path lyricPath = path.resolveSibling(FilenameUtils.getBaseName(path.getFileName().toString()) + ".lrc");
                FileUtils.writeStringToFile(lyricPath.toFile(), lyric, StandardCharsets.UTF_8);
                log.info("== 下载歌词: {}", lyricPath);
            }
        } catch (IOException e) {
            ExceptionUtil.wrapAndThrow(e);
        }
    }

    private void deletePathIfEmpty(Path path) throws IOException {
        if (Files.list(path).noneMatch(s -> KaleidoUtil.isAudioFile(s.getFileName().toString()))) {
            NioFileUtil.deleteIfExists(path);
            log.info("== 源目录已空，进行删除: {}", path);
        }
    }

    private void downloadAlbumCover(MusicAlbumDTO musicAlbumDTO, Path path) {
        if (StringUtils.isEmpty(musicAlbumDTO.getThumb())) {
            return;
        }
        HttpUtil.downloadFile(musicAlbumDTO.getThumb(), path.resolve(KaleidoConstants.COVER).toFile());
        log.info("== 下载专辑封面: {}", musicAlbumDTO.getThumb());
    }

    private void moveExistingFilesToRecycleBin(Path path, Integer trackIndex) throws IOException {
        Files.list(path).forEach(s -> {
            try {
                String fileName = s.getFileName().toString();
                Integer trIndex = KaleidoUtil.parseTrackIndex(fileName);
                if (Objects.equals(trackIndex, trIndex)) {
                    Path recyclePath = KaleidoUtil.getMusicRecyclePath(path.toString());
                    KaleidoUtil.createFolderPath(recyclePath);
                    Files.move(s, recyclePath.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
                    log.info("== 原音乐文件移至回收站: {}", s);
                }
            } catch (IOException e) {
                ExceptionUtil.wrapAndThrow(e);
            }
        });
    }

    private Path createStandardPath(MusicAlbumDTO musicAlbumDTO) throws IOException {
        String folderName = KaleidoUtil.genMusicFolder(musicAlbumDTO);
        Path folderPath = KaleidoUtil.getMusicPath(folderName);
        KaleidoUtil.createFolderPath(folderPath);
        log.info("== 创建音乐文件夹: {}", folderPath);
        return folderPath;
    }

    private Album findTmmAlbumByNFO(Path path) {
        try {
            AlbumNFO albumNFO = NFOUtil.read(AlbumNFO.class, path, KaleidoConstants.ALBUM_NFO);
            return tmmApiService.findAlbum(albumNFO.getNeteaseId(), albumNFO.getMusicbrainzId());
        } catch (Exception e) {
            log.info("== NFO文件无法读取: {}", path.resolve(KaleidoConstants.ALBUM_NFO));
        }
        return null;
    }

    private void renameDirIfChanged(MusicAlbumDTO musicAlbumDTO) throws IOException {
        String newPath = KaleidoUtil.genMusicFolder(musicAlbumDTO);
        if (StringUtils.isEmpty(musicAlbumDTO.getPath())) {
            MusicAlbumDTO existMusicAlbumDTO = musicAlbumService.findById(musicAlbumDTO.getId());
            musicAlbumDTO.setPath(existMusicAlbumDTO.getPath());
        }
        if (!StringUtils.equals(newPath, musicAlbumDTO.getPath())) {
            Path newMusicPath = KaleidoUtil.getMusicPath(newPath);
            if (Files.notExists(newMusicPath)) {
                Files.createDirectories(newMusicPath);
            }
            Path musicPath = KaleidoUtil.getMusicPath(musicAlbumDTO.getPath());
            if (Files.exists(musicPath)) {
                NioFileUtil.renameDir(musicPath, newMusicPath);
            }
            musicAlbumDTO.setPath(newPath);
        }
    }

    private void renameFileIfChanged(MusicTrackDTO musicTrackDTO) throws IOException {
        if (StringUtils.isEmpty(musicTrackDTO.getFilename())) {
            MusicTrackDTO existMusicTrackDTO = musicTrackService.findById(musicTrackDTO.getId());
            musicTrackDTO.setFilename(existMusicTrackDTO.getFilename());
        }
        String filename = KaleidoUtil.genMusicTrackFilename(musicTrackDTO);
        if (!StringUtils.equals(filename, musicTrackDTO.getFilename())) {
            MusicAlbumDTO musicAlbumDTO = musicAlbumService.findById(musicTrackDTO.getAlbumId());
            Path path = KaleidoUtil.getMusicFilePath(musicAlbumDTO.getPath(), musicTrackDTO.getFilename());
            if (Files.exists(path)) {
                Files.move(path, path.resolveSibling(filename));
            }
            String lyricFilename = FilenameUtils.getBaseName(musicTrackDTO.getFilename()) + ".lrc";
            Path lyricPath = path.resolveSibling(lyricFilename);
            if (Files.exists(lyricPath)) {
                Files.move(lyricPath, path.resolveSibling(FilenameUtils.getBaseName(filename) + ".lrc"));
            }
            musicTrackDTO.setFilename(filename);
        }
    }

    private void readAudioTag(MusicAlbumDTO musicAlbumDTO) throws Exception {
        Path path = KaleidoUtil.getMusicPath(musicAlbumDTO.getPath());
        Path audioPath = Files.list(path).filter(s -> KaleidoUtil.isAudioFile(s.getFileName().toString())).findFirst()
                .orElse(null);
        if (audioPath == null || Files.notExists(audioPath)) {
            return;
        }
        AudioFile audioFile = AudioFileIO.read(audioPath.toFile());
        Tag tag = audioFile.getTag();
        AudioTagUtil.toMusicAlbumDTO(tag, musicAlbumDTO);
    }

    private void readAudioTag(MusicTrackDTO musicTrackDTO) throws Exception {
        MusicAlbumDTO musicAlbumDTO = musicAlbumService.findById(musicTrackDTO.getAlbumId());
        Path audioPath = KaleidoUtil.getMusicFilePath(musicAlbumDTO.getPath(), musicTrackDTO.getFilename());
        if (Files.exists(audioPath)) {
            AudioFile audioFile = AudioFileIO.read(audioPath.toFile());
            Tag tag = audioFile.getTag();
            AudioTagUtil.toMusicTrackDTO(tag, musicTrackDTO);
        }
    }

    private void writeAudioTag(MusicAlbumDTO musicAlbumDTO, MusicTrackDTO musicTrackDTO, Path path) throws Exception {
        AudioFile audioFile = AudioFileIO.read(path.toFile());
        Tag tag = audioFile.getTag();
        AudioTagUtil.toTag(musicAlbumDTO, musicTrackDTO, tag);
        audioFile.commit();
    }

    public void downloadLyric(MusicAlbumDTO musicAlbumDTO) {
        List<MusicTrackDTO> musicTrackDTOList = musicTrackService.listByAlbumId(musicAlbumDTO.getId());
        musicTrackDTOList.forEach(s -> {
            try {
                String lyric = tmmApiService.findLyric(s.getNeteaseId());
                if (StringUtils.isNotEmpty(lyric)) {
                    Path musicFilePath = KaleidoUtil.getMusicFilePath(musicAlbumDTO.getPath(), s.getFilename());
                    File lyricFile = musicFilePath.resolveSibling(FilenameUtils.getBaseName(s.getFilename()) + ".lrc")
                            .toFile();
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
            Path importPath = KaleidoUtil.getMusicImportPath();
            String filename = FilenameUtils.getBaseName(path.getFileName().toString());
            if (StringUtils.isNotEmpty(album.getTitle())
                    && !StringUtils.isAllEmpty(album.getNeteaseId(), album.getMusicbrainzId())) {
                filename = album.getTitle() + "("
                        + StringUtils.defaultIfEmpty(album.getNeteaseId(), album.getMusicbrainzId()) + ")";
            }
            Path newPath = importPath.resolve(filename);
            if (Files.isDirectory(path)) {
                NioFileUtil.deleteByFilter(path, "nfo");
                if (!StringUtils.equals(newPath.toString(), path.toString())) {
                    NioFileUtil.renameDir(path, newPath, StandardCopyOption.REPLACE_EXISTING);
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
        MusicAlbumDTO musicAlbumDTO = musicAlbumService.findById(albumId);
        boolean isSame = KaleidoUtil.isSame(musicAlbumDTO, album);
        TmmUtil.toMusicAlbumDTO(musicAlbumDTO, album);
        if (!isSame) {
            Path path = KaleidoUtil.getMusicPath(musicAlbumDTO.getPath());
            operationPath(musicAlbumDTO, path);
        }
        saveAlbum(musicAlbumDTO);
        List<MusicTrackDTO> musicTrackDTOList = musicTrackService.listByAlbumId(albumId);
        for (MusicTrackDTO musicTrackDTO : musicTrackDTOList) {
            Song song = album.getSong(musicTrackDTO.getTrackIndex());
            TmmUtil.toMusicTrackDTO(musicTrackDTO, song);
            saveTrack(musicTrackDTO);
        }
    }

}
