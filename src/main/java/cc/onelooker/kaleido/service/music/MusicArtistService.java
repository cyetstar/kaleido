package cc.onelooker.kaleido.service.music;

import cc.onelooker.kaleido.dto.music.MusicArtistDTO;
import cc.onelooker.kaleido.plex.resp.GetMusicArtists;
import com.zjjcnt.common.core.service.IBaseService;

import java.util.List;

/**
 * 艺术家Service
 *
 * @author cyetstar
 * @date 2023-09-29 22:32:53
 */
public interface MusicArtistService extends IBaseService<MusicArtistDTO> {

    MusicArtistDTO findByPlexId(String key);

    MusicArtistDTO createIfNotExist(GetMusicArtists.Metadata metadata);

    List<MusicArtistDTO> listByReleaseId(Long releaseId);
}