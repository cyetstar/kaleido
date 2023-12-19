package cc.onelooker.kaleido.service.movie;

import cc.onelooker.kaleido.dto.movie.MovieAkaDTO;
import com.zjjcnt.common.core.service.IBaseService;

import java.util.List;

/**
 * 别名Service
 *
 * @author cyetstar
 * @date 2023-11-26 01:19:02
 */
public interface MovieAkaService extends IBaseService<MovieAkaDTO> {

    List<MovieAkaDTO> listByMovieId(Long movieId);

    MovieAkaDTO findByTitleAndMovieId(String title, Long movieId);

    boolean deleteByMovieId(Long movieId);
}