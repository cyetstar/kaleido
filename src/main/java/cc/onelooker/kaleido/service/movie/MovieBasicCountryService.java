package cc.onelooker.kaleido.service.movie;

import cc.onelooker.kaleido.dto.movie.MovieBasicCountryDTO;
import com.zjjcnt.common.core.service.IBaseService;

import java.util.List;

/**
 * 电影国家地区关联表Service
 *
 * @author cyetstar
 * @date 2023-11-26 01:19:02
 */
public interface MovieBasicCountryService extends IBaseService<MovieBasicCountryDTO> {

    MovieBasicCountryDTO findByMovieIdAndCountryId(Long movieId, Long countryId);

    MovieBasicCountryDTO insertByMovieIdAndCountryId(Long movieId, Long countryId);

    List<MovieBasicCountryDTO> listByMovieId(Long movieId);

    List<MovieBasicCountryDTO> listByCountryId(Long countryId);

    boolean deleteByMovieId(Long movieId);
}