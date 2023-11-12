package cc.onelooker.kaleido.service.music;

import cc.onelooker.kaleido.dto.music.MusicArtistDTO;
import cc.onelooker.kaleido.dto.music.MusicReleaseDTO;
import cc.onelooker.kaleido.dto.music.MusicTrackDTO;
import cc.onelooker.kaleido.plex.PlexApiService;
import cc.onelooker.kaleido.plex.resp.GetMusicAlbums;
import cc.onelooker.kaleido.plex.resp.GetMusicArtists;
import cc.onelooker.kaleido.plex.resp.GetMusicTracks;
import cc.onelooker.kaleido.utils.ConfigUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zjjcnt.common.core.domain.PageResult;
import com.zjjcnt.common.core.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Paths;
import java.util.List;

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
    private MusicTrackService musicTrackService;

    @Autowired
    private PlexApiService plexApiService;

    public void syncPlex() {
        String plexMusicLibraryId = ConfigUtils.getSysConfig("plexMusicLibraryId");
        String plexMusicLibraryPath = ConfigUtils.getSysConfig("plexMusicLibraryPath");
        if (StringUtils.isBlank(plexMusicLibraryId) || StringUtils.isBlank(plexMusicLibraryPath)) {
            throw new ServiceException(2005, "请设置需要同步音乐库信息");
        }
        List<GetMusicArtists.Metadata> metadataList = plexApiService.getMusicArtists(plexMusicLibraryId);
        for (GetMusicArtists.Metadata metadata : metadataList) {
            MusicArtistDTO musicArtistDTO = musicArtistService.createIfNotExist(metadata);
            syncPlexAlbum(plexMusicLibraryId, plexMusicLibraryPath, musicArtistDTO);
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

    private void syncPlexAlbum(String plexLibraryId, String plexMusicLibraryPath, MusicArtistDTO musicArtistDTO) {
        List<GetMusicAlbums.Metadata> metadataList = plexApiService.getMusicAlbums(plexLibraryId, musicArtistDTO.getPlexId());
        for (GetMusicAlbums.Metadata metadata : metadataList) {
            MusicReleaseDTO musicReleaseDTO = musicReleaseService.createIfNotExist(metadata, musicArtistDTO);
            syncPlexTrack(plexMusicLibraryPath, musicReleaseDTO, musicArtistDTO);
        }
    }

    private void syncPlexTrack(String plexMusicLibraryPath, MusicReleaseDTO musicReleaseDTO, MusicArtistDTO musicArtistDTO) {
        List<GetMusicTracks.Metadata> metadataList = plexApiService.getMusicTracks(musicReleaseDTO.getPlexId());
        for (GetMusicTracks.Metadata metadata : metadataList) {
            MusicTrackDTO musicTrackDTO = musicTrackService.createIfNotExist(plexMusicLibraryPath, metadata, musicReleaseDTO, musicArtistDTO);
            if (StringUtils.isBlank(musicReleaseDTO.getWjlj())) {
                musicReleaseDTO.setWjlj(StringUtils.substringBeforeLast(musicTrackDTO.getWjlj(), File.separator));
                musicReleaseService.update(musicReleaseDTO);
            }
        }
    }

    public int updateReleaseAudioTag(Long releaseId) {
        int update = 0;
        List<MusicTrackDTO> musicTrackDTOList = musicTrackService.listByReleaseId(releaseId);
        for (MusicTrackDTO musicTrackDTO : musicTrackDTOList) {
            if (StringUtils.isEmpty(musicTrackDTO.getWjlj())) {
                continue;
            }
            try {
                AudioFile audioFile = AudioFileIO.read(Paths.get(musicTrackDTO.getWjlj()).toFile());
                Tag tag = audioFile.getTag();
                musicTrackDTO.setYsj(tag.getFirst(FieldKey.ARTIST));
                musicTrackDTO.setDh(Integer.valueOf(tag.getFirst(FieldKey.DISC_NO)));
                musicTrackDTO.setQh(Integer.valueOf(tag.getFirst(FieldKey.TRACK)));
                musicTrackDTO.setMusicbrainzId(tag.getFirst(FieldKey.MUSICBRAINZ_TRACK_ID));
                musicTrackService.update(musicTrackDTO);
                update++;
            } catch (Exception e) {
                log.error("读取音频文件失败，曲目名称：{}", musicTrackDTO.getBt());
            }
        }
        return update;
    }
}
