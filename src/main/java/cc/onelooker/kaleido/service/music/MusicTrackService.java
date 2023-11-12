package cc.onelooker.kaleido.service.music;

import cc.onelooker.kaleido.dto.music.MusicArtistDTO;
import cc.onelooker.kaleido.dto.music.MusicReleaseDTO;
import cc.onelooker.kaleido.plex.resp.GetMusicTracks;
import com.zjjcnt.common.core.service.IBaseService;

import java.lang.String;
import java.util.List;

import cc.onelooker.kaleido.dto.music.MusicTrackDTO;

/**
 * 曲目Service
 *
 * @author cyetstar
 * @date 2023-09-29 22:32:53
 */
public interface MusicTrackService extends IBaseService<MusicTrackDTO> {

    MusicTrackDTO findByPlexId(String plexId);

    MusicTrackDTO createIfNotExist(String plexMusicLibraryPath, GetMusicTracks.Metadata metadata, MusicReleaseDTO musicReleaseDTO, MusicArtistDTO musicArtistDTO);

    List<MusicTrackDTO> listByReleaseId(Long releaseId);
}