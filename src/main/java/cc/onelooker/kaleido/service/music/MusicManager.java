package cc.onelooker.kaleido.service.music;

import cc.onelooker.kaleido.dto.music.MusicArtistDTO;
import cc.onelooker.kaleido.dto.music.MusicArtistReleaseDTO;
import cc.onelooker.kaleido.dto.music.MusicReleaseDTO;
import cc.onelooker.kaleido.dto.music.MusicTrackDTO;
import cc.onelooker.kaleido.netease.NeteaseApiService;
import cc.onelooker.kaleido.netease.domain.Album;
import cc.onelooker.kaleido.netease.domain.Artist;
import cc.onelooker.kaleido.netease.domain.Song;
import cc.onelooker.kaleido.plex.PlexApiService;
import cc.onelooker.kaleido.plex.resp.GetLibraries;
import cc.onelooker.kaleido.plex.resp.GetMusicAlbums;
import cc.onelooker.kaleido.plex.resp.GetMusicArtists;
import cc.onelooker.kaleido.plex.resp.GetMusicTracks;
import cc.onelooker.kaleido.utils.ConfigUtils;
import cc.onelooker.kaleido.utils.LocalDateTimeUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Maps;
import com.zjjcnt.common.core.domain.PageResult;
import com.zjjcnt.common.core.exception.ServiceException;
import com.zjjcnt.common.util.DateTimeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
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
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

/**
 * @Author xiadawei
 * @Date 2023-10-01 16:09:00
 * @Description TODO
 */
@Slf4j
@Component
public class MusicManager {

    @Autowired
    private MusicReleaseService musicReleaseService;

    @Autowired
    private MusicArtistService musicArtistService;

    @Autowired
    private MusicArtistReleaseService musicArtistReleaseService;

    @Autowired
    private MusicTrackService musicTrackService;

    @Autowired
    private PlexApiService plexApiService;

    @Autowired
    private NeteaseApiService neteaseApiService;

    public void syncPlex() {
        String libraryId = ConfigUtils.getSysConfig("plexMusicLibraryId");
        if (StringUtils.isBlank(libraryId)) {
            throw new ServiceException(2005, "请设置需要同步音乐库信息");
        }
        GetLibraries.Directory directory = plexApiService.getLibraries().stream().filter(s -> StringUtils.equals(s.getKey(), libraryId)).findFirst().get();
        String libraryPath = directory.getLocation().getPath();
        List<GetMusicArtists.Metadata> metadataList = plexApiService.getMusicArtists(libraryId);
        for (GetMusicArtists.Metadata metadata : metadataList) {
            MusicArtistDTO musicArtistDTO = musicArtistService.createIfNotExist(metadata);
            if (LocalDateTimeUtils.isAfter(metadata.getUpdatedAt(), musicArtistDTO.getXgsj())) {
                syncPlexAlbum(libraryId, libraryPath, musicArtistDTO);
                //同步完才更新修改时间
                if (metadata.getUpdatedAt() != null) {
                    musicArtistDTO.setXgsj(DateTimeUtils.parseTimestamp(metadata.getUpdatedAt() * 1000L));
                    musicArtistService.update(musicArtistDTO);
                }
            }
        }
    }

    public void syncMetadata() {
        String musicLibraryPath = ConfigUtils.getSysConfig("musicLibraryPath");
        int pageNumber = 1;
        int pageSize = 500;
        while (true) {
            PageResult<MusicReleaseDTO> pageResult = musicReleaseService.page(null, Page.of(pageNumber, pageSize));
            List<MusicReleaseDTO> records = pageResult.getRecords();
            if (CollectionUtils.isEmpty(records)) {
                break;
            }
            for (MusicReleaseDTO musicReleaseDTO : records) {
                try {
//                    List<MusicTrackDTO> musicTrackDTOList = musicTrackService.listByReleaseId(musicReleaseDTO.getId());
//                    for (MusicTrackDTO musicTrackDTO : musicTrackDTOList) {
//                        AudioFile audioFile = AudioFileIO.read(Paths.get(musicLibraryPath, musicReleaseDTO.getWjlj()).toFile());
//                        Tag tag = audioFile.getTag();
//                        musicTrackDTO.setYsj(tag.getFirst(FieldKey.ARTIST));
//                        musicTrackDTO.setDh(Integer.valueOf(tag.getFirst(FieldKey.DISC_NO)));
//                        musicTrackDTO.setQh(Integer.valueOf(tag.getFirst(FieldKey.TRACK)));
//                        musicTrackDTO.setMusicbrainzId(tag.getFirst(FieldKey.MUSICBRAINZ_TRACK_ID));
//                        musicTrackService.update(musicTrackDTO);
//
//                        musicReleaseDTO.setMusicbrainzId(tag.getFirst(FieldKey.MUSICBRAINZ_RELEASEID));
//                        musicReleaseDTO.setRq(tag.getFirst(FieldKey.ALBUM_YEAR));
//                        musicReleaseDTO.setYsj(tag.getFirst(FieldKey.ALBUM_ARTIST));
//                        musicReleaseDTO.setMt(tag.getFirst(FieldKey.MEDIA));
//                        musicReleaseDTO.setSfrq(tag.getFirst(FieldKey.ORIGINALRELEASEDATE));
//                        musicReleaseDTO.setCpgs(tag.getFirst(FieldKey.RECORD_LABEL));
//                        musicReleaseDTO.setZds(Integer.valueOf(tag.getFirst(FieldKey.DISC_TOTAL)));
//                        musicReleaseDTO.setYgs(Integer.valueOf(tag.getFirst(FieldKey.TRACK_TOTAL)));
//                        musicReleaseService.update(musicReleaseDTO);
//                    }

                } catch (Exception e) {

                }
            }
        }
    }

    private void syncPlexAlbum(String libraryId, String libraryPath, MusicArtistDTO musicArtistDTO) {
        List<GetMusicAlbums.Metadata> metadataList = plexApiService.getMusicAlbums(libraryId, musicArtistDTO.getPlexId());
        for (GetMusicAlbums.Metadata metadata : metadataList) {
            MusicReleaseDTO musicReleaseDTO = musicReleaseService.createIfNotExist(metadata, musicArtistDTO);
            if (LocalDateTimeUtils.isAfter(metadata.getUpdatedAt(), musicReleaseDTO.getXgsj())) {
                syncPlexTrack(libraryPath, musicReleaseDTO, musicArtistDTO);
                //同步完才更新修改时间
                if (metadata.getUpdatedAt() != null) {
                    musicReleaseDTO.setXgsj(DateTimeUtils.parseTimestamp(metadata.getUpdatedAt() * 1000L));
                    musicReleaseService.update(musicReleaseDTO);
                }
            }

        }
    }

    private void syncPlexTrack(String libraryPath, MusicReleaseDTO musicReleaseDTO, MusicArtistDTO musicArtistDTO) {
        List<GetMusicTracks.Metadata> metadataList = plexApiService.getMusicTracks(musicReleaseDTO.getPlexId());
        for (GetMusicTracks.Metadata metadata : metadataList) {
            MusicTrackDTO musicTrackDTO = musicTrackService.createIfNotExist(libraryPath, metadata, musicReleaseDTO, musicArtistDTO);
            if (StringUtils.isBlank(musicReleaseDTO.getWjlj())) {
                //更新专辑文件路径
                musicReleaseDTO.setWjlj(StringUtils.substringBeforeLast(musicTrackDTO.getWjlj(), File.separator));
                musicReleaseService.update(musicReleaseDTO);
            }
        }
    }

    public int updateAudioTag(Long releaseId) {
        int error = 0;
        List<MusicTrackDTO> musicTrackDTOList = musicTrackService.listByReleaseId(releaseId);
        for (int i = 0; i < musicTrackDTOList.size(); i++) {
            MusicTrackDTO musicTrackDTO = musicTrackDTOList.get(i);
            if (StringUtils.isEmpty(musicTrackDTO.getWjlj())) {
                continue;
            }
            try {
                String musicLibraryPath = ConfigUtils.getSysConfig("musicLibraryPath");
                File file = Paths.get(musicLibraryPath, musicTrackDTO.getWjlj()).toFile();
                AudioFile audioFile = AudioFileIO.read(file);
                Map<String, String> infoMap = readTag(audioFile.getTag());

                musicTrackDTO.setQc(audioFile.getAudioHeader().getTrackLength());
                musicTrackDTO.setYsj(infoMap.get("ARTIST"));
                musicTrackDTO.setDh(Integer.valueOf(infoMap.get("DISC_NO")));
                musicTrackDTO.setQh(Integer.valueOf(infoMap.get("TRACK")));
                musicTrackDTO.setMusicbrainzId(infoMap.get("MUSICBRAINZ_TRACK_ID"));
                musicTrackService.update(musicTrackDTO);
                if (i == 0) {
                    //只更新一次
                    MusicReleaseDTO musicReleaseDTO = musicReleaseService.findById(musicTrackDTO.getReleaseId());
                    musicReleaseDTO.setMusicbrainzId(infoMap.get("MUSICBRAINZ_ALBUMID"));
                    musicReleaseDTO.setYsj(infoMap.get("ALBUMARTIST"));
                    musicReleaseDTO.setZjlx(infoMap.get("RELEASETYPE"));
                    musicReleaseDTO.setYylp(infoMap.get("GENRE"));
                    musicReleaseDTO.setFxgj(infoMap.get("RELEASECOUNTRY"));
                    musicReleaseDTO.setRq(infoMap.get("DATE"));
                    musicReleaseDTO.setCpgs(infoMap.get("RECORD_LABEL"));
                    musicReleaseDTO.setSfrq(infoMap.get("ORIGINALDATE"));
                    musicReleaseDTO.setMt(infoMap.get("MEDIA"));
                    musicReleaseService.update(musicReleaseDTO);
                }
            } catch (Exception e) {
                log.error("读取音频文件失败，曲目名称：{}", musicTrackDTO.getBt(), e);
                error++;
            }
        }
        return error;
    }

    private Map<String, String> readTag(Tag tag) {
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

    public int downloadLyric(Long releaseId) {
        int error = 0;
        List<MusicTrackDTO> musicTrackDTOList = musicTrackService.listByReleaseId(releaseId);

        for (int i = 0; i < musicTrackDTOList.size(); i++) {
            MusicTrackDTO musicTrackDTO = musicTrackDTOList.get(i);
            if (StringUtils.isEmpty(musicTrackDTO.getWjlj())) {
                continue;
            }
            try {
                String musicLibraryPath = ConfigUtils.getSysConfig("musicLibraryPath");
                File file = Paths.get(musicLibraryPath, FilenameUtils.removeExtension(musicTrackDTO.getWjlj()) + ".lrc").toFile();
                String lyric = neteaseApiService.getLyric(musicTrackDTO.getNeteaseId());
                if (StringUtils.isNotEmpty(lyric)) {
                    FileUtils.writeStringToFile(file, lyric, StandardCharsets.UTF_8);
                }
            } catch (Exception e) {
                log.error("下载歌词失败，曲目名称：{}", musicTrackDTO.getBt());
                error++;
            }
        }
        return error;
    }

    @Transactional
    public void matchNetease(Long releaseId, String neteaseId) {
        Album album = neteaseApiService.getAlbum(neteaseId);
        List<Song> songs = album.getSongs();
        Artist artist = album.getArtist();

        MusicReleaseDTO musicReleaseDTO = musicReleaseService.findById(releaseId);
        musicReleaseDTO.setNeteaseId(neteaseId);
        musicReleaseService.update(musicReleaseDTO);

        List<MusicTrackDTO> musicTrackDTOList = musicTrackService.listByReleaseId(releaseId);
        for (MusicTrackDTO musicTrackDTO : musicTrackDTOList) {
            Song song = songs.stream().filter(s -> StringUtils.equals(musicTrackDTO.getBt(), s.getName()) || s.getNo().equals(musicTrackDTO.getQh())).findFirst().orElseGet(null);
            if (song != null) {
                musicTrackDTO.setNeteaseId(song.getId());
                musicTrackService.update(musicTrackDTO);
            }
        }

        List<MusicArtistReleaseDTO> musicArtistReleaseDTOList = musicArtistReleaseService.listByReleaseId(musicReleaseDTO.getId());
        for (MusicArtistReleaseDTO musicArtistReleaseDTO : musicArtistReleaseDTOList) {
            MusicArtistDTO musicArtistDTO = musicArtistService.findById(musicArtistReleaseDTO.getArtistId());
            if (StringUtils.equals(artist.getName(), musicArtistDTO.getMc())) {
                musicArtistDTO.setNeteaseId(artist.getId());
                musicArtistService.update(musicArtistDTO);
            }
        }
    }
}
