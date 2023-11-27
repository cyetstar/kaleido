package cc.onelooker.kaleido.service.tvshow;

import com.zjjcnt.common.core.service.IBaseService;

import cc.onelooker.kaleido.dto.tvshow.TvshowEpisodeDTO;

/**
 * 单集Service
 *
 * @author cyetstar
 * @date 2023-11-26 23:27:03
 */
public interface TvshowEpisodeService extends IBaseService<TvshowEpisodeDTO> {

    Long findMaxUpdatedAt();

}