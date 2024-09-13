package cc.onelooker.kaleido.service;

import cc.onelooker.kaleido.dto.ArtistDTO;
import com.zjjcnt.common.core.service.IBaseService;

import java.util.List;

/**
 * 艺术家Service
 *
 * @author cyetstar
 * @date 2023-11-25 22:16:58
 */
public interface ArtistService extends IBaseService<ArtistDTO> {

    List<ArtistDTO> listByAlbumId(String albumId);

    Boolean updateNeteaseId(String id, String neteaseId);
}