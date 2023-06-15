package cc.onelooker.kaleido.service.business;

import com.zjjcnt.common.core.service.IBaseService;

import java.lang.Long;
import java.math.BigDecimal;
import java.lang.Integer;
import com.zjjcnt.common.core.annotation.Dict;
import java.lang.String;
import java.util.List;

import cc.onelooker.kaleido.dto.business.MovieRatingDTO;

/**
 * 电影评分Service
 *
 * @author cyetstar
 * @date 2023-04-18 23:04:56
 */
public interface MovieRatingService extends IBaseService<MovieRatingDTO> {

    boolean deleteByMovieId(Long movieId);

    List<MovieRatingDTO> listByMovieId(Long movieId);
}