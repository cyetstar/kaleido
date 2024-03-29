package cc.onelooker.kaleido.service.movie;

import cc.onelooker.kaleido.dto.movie.MovieTagDTO;
import com.zjjcnt.common.core.service.IBaseService;

import java.util.List;

/**
 * 电影标签Service
 *
 * @author cyetstar
 * @date 2023-11-26 01:19:02
 */
public interface MovieTagService extends IBaseService<MovieTagDTO> {

    List<MovieTagDTO> listByMovieId(Long movieId);

    MovieTagDTO findByTagAndMovieId(String tag, Long movieId);

    boolean deleteByMovieId(Long movieId);
}