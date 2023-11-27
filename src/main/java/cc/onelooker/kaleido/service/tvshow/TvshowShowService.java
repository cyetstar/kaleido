package cc.onelooker.kaleido.service.tvshow;

import com.zjjcnt.common.core.service.IBaseService;

import cc.onelooker.kaleido.dto.tvshow.TvshowShowDTO;

/**
 * 剧集Service
 *
 * @author cyetstar
 * @date 2023-11-26 23:27:03
 */
public interface TvshowShowService extends IBaseService<TvshowShowDTO> {

    Long findMaxUpdatedAt();
}