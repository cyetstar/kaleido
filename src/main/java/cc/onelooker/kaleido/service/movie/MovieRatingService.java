package cc.onelooker.kaleido.service.movie;

import com.zjjcnt.common.core.service.IBaseService;

import java.lang.Long;
import java.util.List;

import cc.onelooker.kaleido.dto.movie.MovieRatingDTO;

/**
 * 电影评分Service
 *
 * @author cyetstar
 * @date 2023-04-18 23:04:56
 */
public interface MovieRatingService extends IBaseService<MovieRatingDTO> {

    boolean deleteByMovieId(Long movieId);

    boolean deleteByMovieIdAndBslx(Long movieId, String bslx);
    List<MovieRatingDTO> listByMovieId(Long movieId);

}