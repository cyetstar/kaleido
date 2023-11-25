package cc.onelooker.kaleido.service.movie;

import com.zjjcnt.common.core.service.IBaseService;

import java.lang.Long;
import java.util.List;

import cc.onelooker.kaleido.dto.movie.MovieAkaDTO;

/**
 * 别名Service
 *
 * @author cyetstar
 * @date 2023-04-18 23:04:56
 */
public interface MovieAkaService extends IBaseService<MovieAkaDTO> {

    boolean deleteByMovieId(Long movieId);

    List<MovieAkaDTO> listByMovieId(Long movieId);
}