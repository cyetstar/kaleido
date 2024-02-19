package cc.onelooker.kaleido.service.tvshow;

import cc.onelooker.kaleido.dto.tvshow.TvshowShowGenreDTO;
import com.zjjcnt.common.core.service.IBaseService;

import java.util.List;

/**
 * 剧集类型关联表Service
 *
 * @author cyetstar
 * @date 2023-11-26 23:27:03
 */
public interface TvshowShowGenreService extends IBaseService<TvshowShowGenreDTO> {

    TvshowShowGenreDTO findByShowIdAndGenreId(Long showId, Long genreId);

    List<TvshowShowGenreDTO> listByShowId(Long showId);

    List<TvshowShowGenreDTO> listByGenreId(Long genreId);

    TvshowShowGenreDTO insert(Long showId, Long genreId);

    void deleteByShowId(Long showId);
}