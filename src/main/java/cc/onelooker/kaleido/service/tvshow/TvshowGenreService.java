package cc.onelooker.kaleido.service.tvshow;

import cc.onelooker.kaleido.dto.tvshow.TvshowGenreDTO;
import com.zjjcnt.common.core.service.IBaseService;

/**
 * 剧集类型Service
 *
 * @author cyetstar
 * @date 2023-11-26 23:27:03
 */
public interface TvshowGenreService extends IBaseService<TvshowGenreDTO> {

    TvshowGenreDTO insert(Long id, String tag);
}