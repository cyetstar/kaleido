package cc.onelooker.kaleido.service;

import cc.onelooker.kaleido.dto.MusicArtistDTO;
import com.zjjcnt.common.core.service.IBaseService;

import java.util.List;

/**
 * 艺术家Service
 *
 * @author cyetstar
 * @date 2023-11-25 22:16:58
 */
public interface MusicArtistService extends IBaseService<MusicArtistDTO> {

    List<MusicArtistDTO> listByAlbumId(String albumId);

    Boolean updateNeteaseId(String id, String neteaseId);
}