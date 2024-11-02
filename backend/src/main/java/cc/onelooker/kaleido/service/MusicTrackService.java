package cc.onelooker.kaleido.service;

import cc.onelooker.kaleido.dto.MusicTrackDTO;
import com.zjjcnt.common.core.service.IBaseService;

import java.util.List;

/**
 * 曲目Service
 *
 * @author cyetstar
 * @date 2023-11-25 22:16:58
 */
public interface MusicTrackService extends IBaseService<MusicTrackDTO> {

    List<MusicTrackDTO> listByAlbumId(String albumId);

    boolean deleteByAlbumId(String albumId);

    List<MusicTrackDTO> listByKeyword(String keyword);
}