package cc.onelooker.kaleido.service.movie;

import cc.onelooker.kaleido.dto.movie.MovieBasicCollectionDTO;
import com.zjjcnt.common.core.service.IBaseService;

import cc.onelooker.kaleido.dto.movie.MovieBasicCollectionDTO;

/**
 * 电影集合关联表Service
 *
 * @author cyetstar
 * @date 2023-11-26 01:19:02
 */
public interface MovieBasicCollectionService extends IBaseService<MovieBasicCollectionDTO> {

    MovieBasicCollectionDTO findByMovieIdAndCollectionId(Long movieId, Long countryId);

    MovieBasicCollectionDTO insertByMovieIdAndCollectionId(Long movieId, Long countryId);
}