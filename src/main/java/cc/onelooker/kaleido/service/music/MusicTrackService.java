package cc.onelooker.kaleido.service.music;

import cc.onelooker.kaleido.dto.music.MusicAlbumDTO;
import cc.onelooker.kaleido.dto.music.MusicArtistDTO;
import cc.onelooker.kaleido.dto.music.MusicTrackDTO;
import cc.onelooker.kaleido.plex.resp.GetMusicTracks;
import com.zjjcnt.common.core.service.IBaseService;

import java.util.List;

/**
 * 曲目Service
 *
 * @author cyetstar
 * @date 2023-11-20 22:35:26
 */
public interface MusicTrackService extends IBaseService<MusicTrackDTO> {

    MusicTrackDTO findByPlexId(String plexId);

    MusicTrackDTO createIfNotExist(String libraryPath, GetMusicTracks.Metadata metadata, MusicAlbumDTO musicAlbumDTO, MusicArtistDTO musicArtistDTO);

    List<MusicTrackDTO> listByAlbumId(Long AlbumId);
}