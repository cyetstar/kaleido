package cc.onelooker.kaleido.service.music;

import cc.onelooker.kaleido.dto.music.MusicAlbumDTO;
import cc.onelooker.kaleido.dto.music.MusicArtistAlbumDTO;
import cc.onelooker.kaleido.dto.music.MusicArtistDTO;
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
import com.google.common.collect.Maps;
import com.zjjcnt.common.core.exception.ServiceException;
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

    public void syncPlexAlbum() {
        String libraryId = ConfigUtils.getSysConfig("plexMusicLibraryId");
        if (StringUtils.isBlank(libraryId)) {
            throw new ServiceException(2005, "请设置需要同步音乐库信息");
        }
        GetLibraries.Directory directory = plexApiService.listLibrary().stream().filter(s -> StringUtils.equals(s.getKey(), libraryId)).findFirst().get();
        String libraryPath = directory.getLocation().getPath();
        List<GetMusicAlbums.Metadata> metadataList = plexApiService.listAlbum(libraryId);
        for (GetMusicAlbums.Metadata metadata : metadataList) {
            MusicAlbumDTO musicAlbumDTO = createAlbumIfNotExist(metadata);
            if (LocalDateTimeUtils.isAfter(metadata.getUpdatedAt(), musicAlbumDTO.getXgsj())) {
                syncPlexTrack(libraryPath, musicAlbumDTO);
                musicAlbumDTO.setPlexThumb(metadata.getThumb());
                musicAlbumDTO.setLabel(metadata.getStudio());
                musicAlbumDTO.setSummary(metadata.getSummary());
                musicAlbumDTO.setXgsj(metadata.getStringUpdatedAt());
                musicAlbumService.update(musicAlbumDTO);
            }
            MusicArtistDTO musicArtistDTO = createArtistIfNotExist(metadata.getParentRatingKey());
            createArtistAlbumIfNotExist(musicArtistDTO, musicAlbumDTO);
        }
    }

    private void syncPlexTrack(String libraryPath, MusicAlbumDTO musicAlbumDTO) {
        List<GetMusicTracks.Metadata> metadataList = plexApiService.listTrackByAlbum(musicAlbumDTO.getPlexId());
        for (GetMusicTracks.Metadata metadata : metadataList) {
            MusicTrackDTO musicTrackDTO = createTrackIfNotExist(libraryPath, metadata, musicAlbumDTO);
            if (StringUtils.isBlank(musicAlbumDTO.getPath())) {
                //更新专辑文件路径
                musicAlbumDTO.setPath(StringUtils.substringBeforeLast(musicTrackDTO.getPath(), File.separator));
                musicAlbumService.update(musicAlbumDTO);
            }
        }
    }

    private MusicAlbumDTO createAlbumIfNotExist(GetMusicAlbums.Metadata metadata) {
        MusicAlbumDTO musicAlbumDTO = musicAlbumService.findByPlexId(metadata.getRatingKey());
        if (musicAlbumDTO == null) {
            musicAlbumDTO = new MusicAlbumDTO();
            musicAlbumDTO.setPlexId(metadata.getRatingKey());
            musicAlbumDTO.setPlexThumb(metadata.getThumb());
            musicAlbumDTO.setTitle(metadata.getTitle());
            musicAlbumDTO.setSummary(metadata.getSummary());
            musicAlbumDTO.setDate(metadata.getOriginallyAvailableAt());
            musicAlbumDTO.setLabel(metadata.getStudio());
            musicAlbumDTO.setCjsj(metadata.getStringAddedAt());
            musicAlbumDTO = musicAlbumService.insert(musicAlbumDTO);
        }
        return musicAlbumDTO;
    }

    private MusicArtistDTO createArtistIfNotExist(String plexArtistId) {
        MusicArtistDTO musicArtistDTO = musicArtistService.findByPlexId(plexArtistId);
        if (musicArtistDTO == null) {
            GetMusicArtists.Metadata artist = plexApiService.findArtist(plexArtistId);
            musicArtistDTO = new MusicArtistDTO();
            musicArtistDTO.setPlexId(artist.getRatingKey());
            musicArtistDTO.setPlexThumb(artist.getThumb());
            musicArtistDTO.setName(artist.getTitle());
            musicArtistDTO.setSummary(artist.getSummary());
            musicArtistDTO = musicArtistService.insert(musicArtistDTO);
        }
        return musicArtistDTO;
    }

    private void createArtistAlbumIfNotExist(MusicArtistDTO musicArtistDTO, MusicAlbumDTO musicAlbumDTO) {
        MusicArtistAlbumDTO musicArtistAlbumDTO = musicArtistAlbumService.findByArtistIdAndAlbumId(musicArtistDTO.getId(), musicAlbumDTO.getId());
        if (musicArtistAlbumDTO == null) {
            musicArtistAlbumDTO = new MusicArtistAlbumDTO();
            musicArtistAlbumDTO.setArtistId(musicArtistDTO.getId());
            musicArtistAlbumDTO.setAlbumId(musicAlbumDTO.getId());
            musicArtistAlbumService.insert(musicArtistAlbumDTO);
        }
    }

    private MusicTrackDTO createTrackIfNotExist(String libraryPath, GetMusicTracks.Metadata metadata, MusicAlbumDTO musicAlbumDTO) {
        MusicTrackDTO musicTrackDTO = musicTrackService.findByPlexId(metadata.getRatingKey());
        if (musicTrackDTO == null) {
            musicTrackDTO = new MusicTrackDTO();
            musicTrackDTO.setPlexId(metadata.getRatingKey());
            musicTrackDTO.setTitle(metadata.getTitle());
            musicTrackDTO.setAlbumId(musicAlbumDTO.getId());
            musicTrackDTO.setCjsj(metadata.getStringAddedAt());
            musicTrackDTO.setXgsj(metadata.getStringUpdatedAt());
            GetMusicTracks.Media media = metadata.getMedia();
            musicTrackDTO.setFormat(media.getContainer());
            musicTrackDTO.setPath(StringUtils.removeStart(media.getPart().getFile(), libraryPath));
            musicTrackDTO = musicTrackService.insert(musicTrackDTO);
        }
        return musicTrackDTO;
    }

    public int updateAudioTag(Long AlbumId) {
        int error = 0;
        List<MusicTrackDTO> musicTrackDTOList = musicTrackService.listByAlbumId(AlbumId);
        for (int i = 0; i < musicTrackDTOList.size(); i++) {
            MusicTrackDTO musicTrackDTO = musicTrackDTOList.get(i);
            if (StringUtils.isEmpty(musicTrackDTO.getPath())) {
                continue;
            }
            try {
                String musicLibraryPath = ConfigUtils.getSysConfig("musicLibraryPath");
                File file = Paths.get(musicLibraryPath, musicTrackDTO.getPath()).toFile();
                AudioFile audioFile = AudioFileIO.read(file);
                Map<String, String> infoMap = readTag(audioFile.getTag());

                musicTrackDTO.setLength(audioFile.getAudioHeader().getTrackLength());
                musicTrackDTO.setArtists(infoMap.get("ARTIST"));
                musicTrackDTO.setDiscNumber(Integer.valueOf(infoMap.get("DISC_NO")));
                musicTrackDTO.setTrackNumber(Integer.valueOf(infoMap.get("TRACK")));
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
                    musicAlbumDTO.setDate(infoMap.get("DATE"));
                    musicAlbumDTO.setLabel(infoMap.get("RECORD_LABEL"));
                    musicAlbumDTO.setReleaseDate(infoMap.get("ORIGINALDATE"));
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

    public int downloadLyric(Long AlbumId) {
        int error = 0;
        List<MusicTrackDTO> musicTrackDTOList = musicTrackService.listByAlbumId(AlbumId);

        for (int i = 0; i < musicTrackDTOList.size(); i++) {
            MusicTrackDTO musicTrackDTO = musicTrackDTOList.get(i);
            if (StringUtils.isEmpty(musicTrackDTO.getPath())) {
                continue;
            }
            try {
                String musicLibraryPath = ConfigUtils.getSysConfig("musicLibraryPath");
                File file = Paths.get(musicLibraryPath, FilenameUtils.removeExtension(musicTrackDTO.getPath()) + ".lrc").toFile();
                String lyric = neteaseApiService.getLyric(musicTrackDTO.getNeteaseId());
                if (StringUtils.isNotEmpty(lyric)) {
                    FileUtils.writeStringToFile(file, lyric, StandardCharsets.UTF_8);
                }
            } catch (Exception e) {
                log.error("下载歌词失败，曲目名称：{}", musicTrackDTO.getTitle());
                error++;
            }
        }
        return error;
    }

    @Transactional
    public void matchNetease(Long AlbumId, String neteaseId) {
        Album album = neteaseApiService.getAlbum(neteaseId);
        List<Song> songs = album.getSongs();
        Artist artist = album.getArtist();

        MusicAlbumDTO musicAlbumDTO = musicAlbumService.findById(AlbumId);
        musicAlbumDTO.setNeteaseId(neteaseId);
        musicAlbumService.update(musicAlbumDTO);

        List<MusicTrackDTO> musicTrackDTOList = musicTrackService.listByAlbumId(AlbumId);
        for (MusicTrackDTO musicTrackDTO : musicTrackDTOList) {
            Song song = songs.stream().filter(s -> StringUtils.equals(musicTrackDTO.getTitle(), s.getName()) || s.getNo().equals(musicTrackDTO.getTrackNumber())).findFirst().orElseGet(null);
            if (song != null) {
                musicTrackDTO.setNeteaseId(song.getId());
                musicTrackService.update(musicTrackDTO);
            }
        }

        List<MusicArtistAlbumDTO> musicArtistAlbumDTOList = musicArtistAlbumService.listByAlbumId(musicAlbumDTO.getId());
        for (MusicArtistAlbumDTO musicArtistAlbumDTO : musicArtistAlbumDTOList) {
            MusicArtistDTO musicArtistDTO = musicArtistService.findById(musicArtistAlbumDTO.getArtistId());
            if (StringUtils.equals(artist.getName(), musicArtistDTO.getName())) {
                musicArtistDTO.setNeteaseId(artist.getId());
                musicArtistService.update(musicArtistDTO);
            }
        }
    }
}
