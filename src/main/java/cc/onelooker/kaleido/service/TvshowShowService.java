package cc.onelooker.kaleido.service;

import cc.onelooker.kaleido.dto.TvshowShowDTO;
import com.zjjcnt.common.core.service.IBaseService;

/**
 * 剧集Service
 *
 * @author cyetstar
 * @date 2023-11-26 23:27:03
 */
public interface TvshowShowService extends IBaseService<TvshowShowDTO> {

    Long findMaxUpdatedAt();
}