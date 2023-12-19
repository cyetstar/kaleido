package cc.onelooker.kaleido.service.tvshow;

import cc.onelooker.kaleido.dto.tvshow.TvshowActorDTO;
import com.zjjcnt.common.core.service.IBaseService;

import java.util.List;

/**
 * 剧集演职员Service
 *
 * @author cyetstar
 * @date 2023-11-26 23:27:03
 */
public interface TvshowActorService extends IBaseService<TvshowActorDTO> {

    List<TvshowActorDTO> listByShowId(Long showId);
}