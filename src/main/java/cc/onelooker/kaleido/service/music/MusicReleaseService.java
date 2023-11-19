package cc.onelooker.kaleido.service.music;

import cc.onelooker.kaleido.dto.music.MusicArtistDTO;
import cc.onelooker.kaleido.dto.music.MusicReleaseDTO;
import cc.onelooker.kaleido.plex.resp.GetMusicAlbums;
import com.zjjcnt.common.core.service.IBaseService;

/**
 * 发行品Service
 *
 * @author cyetstar
 * @date 2023-09-29 22:32:53
 */
public interface MusicReleaseService extends IBaseService<MusicReleaseDTO> {

    MusicReleaseDTO findByPlexId(String plexId);

    MusicReleaseDTO createIfNotExist(GetMusicAlbums.Metadata metadata, MusicArtistDTO musicArtistDTO);

}