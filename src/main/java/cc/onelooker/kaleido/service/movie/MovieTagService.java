package cc.onelooker.kaleido.service.movie;

import com.zjjcnt.common.core.service.IBaseService;

import java.lang.Long;
import java.lang.String;
import java.util.List;

import cc.onelooker.kaleido.dto.movie.MovieTagDTO;

/**
 * 电影标签Service
 *
 * @author cyetstar
 * @date 2023-04-18 23:04:56
 */
public interface MovieTagService extends IBaseService<MovieTagDTO> {

    MovieTagDTO findByMovieIdAndMc(Long movieId, String mc);

    boolean deleteByMovieId(Long movieId);

    List<MovieTagDTO> listByMovieId(Long movieId);
}