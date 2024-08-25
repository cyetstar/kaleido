package cc.onelooker.kaleido.service;

import cc.onelooker.kaleido.dto.MusicAlbumDTO;
import com.zjjcnt.common.core.service.IBaseService;

import java.util.List;

/**
 * 专辑Service
 *
 * @author cyetstar
 * @date 2023-11-25 22:16:58
 */
public interface MusicAlbumService extends IBaseService<MusicAlbumDTO> {

    List<MusicAlbumDTO> listByArtistId(String artistId);

    Long findMaxUpdatedAt();

}