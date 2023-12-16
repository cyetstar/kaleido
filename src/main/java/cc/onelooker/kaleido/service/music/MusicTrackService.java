package cc.onelooker.kaleido.service.music;

import cc.onelooker.kaleido.dto.music.MusicTrackDTO;
import com.zjjcnt.common.core.service.IBaseService;

import java.util.List;

/**
 * 曲目Service
 *
 * @author cyetstar
 * @date 2023-11-25 22:16:58
 */
public interface MusicTrackService extends IBaseService<MusicTrackDTO> {

    List<MusicTrackDTO> listByAlbumId(Long albumId);

    boolean deleteByAlbumId(Long albumId);
}