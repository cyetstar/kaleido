package cc.onelooker.kaleido.service.tvshow;

import cc.onelooker.kaleido.dto.tvshow.TvshowSeasonDTO;
import com.zjjcnt.common.core.service.IBaseService;

import java.util.List;

/**
 * 单季Service
 *
 * @author cyetstar
 * @date 2023-11-26 23:27:03
 */
public interface TvshowSeasonService extends IBaseService<TvshowSeasonDTO> {

    List<TvshowSeasonDTO> listByShowId(Long showId);
}