package cc.onelooker.kaleido.service.movie;

import cc.onelooker.kaleido.dto.movie.MovieBasicGenreDTO;
import com.zjjcnt.common.core.service.IBaseService;

import cc.onelooker.kaleido.dto.movie.MovieBasicGenreDTO;

import java.util.List;

/**
 * 电影类型关联表Service
 *
 * @author cyetstar
 * @date 2023-11-26 01:19:02
 */
public interface MovieBasicGenreService extends IBaseService<MovieBasicGenreDTO> {

    MovieBasicGenreDTO findByMovieIdAndGenreId(Long movieId, Long countryId);

    MovieBasicGenreDTO insertByMovieIdAndGenreId(Long movieId, Long countryId);

    List<MovieBasicGenreDTO> listByMovieId(Long movieId);
}