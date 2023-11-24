package cc.onelooker.kaleido.service.music;

import cc.onelooker.kaleido.dto.music.MusicArtistDTO;
import cc.onelooker.kaleido.plex.resp.GetMusicArtists;
import com.zjjcnt.common.core.service.IBaseService;

import java.util.List;

/**
 * 艺术家Service
 *
 * @author cyetstar
 * @date 2023-11-20 22:35:26
 */
public interface MusicArtistService extends IBaseService<MusicArtistDTO> {

    MusicArtistDTO findByPlexId(String key);

    MusicArtistDTO createIfNotExist(GetMusicArtists.Metadata metadata);

    List<MusicArtistDTO> listByAlbumId(Long albumId);

    Boolean updateNeteaseId(Long id, String neteaseId);
}