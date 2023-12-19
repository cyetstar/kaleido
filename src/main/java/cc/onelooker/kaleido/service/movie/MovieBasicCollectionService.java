package cc.onelooker.kaleido.service.movie;

import cc.onelooker.kaleido.dto.movie.MovieBasicCollectionDTO;
import com.zjjcnt.common.core.service.IBaseService;

import java.util.List;

/**
 * 电影集合关联表Service
 *
 * @author cyetstar
 * @date 2023-11-26 01:19:02
 */
public interface MovieBasicCollectionService extends IBaseService<MovieBasicCollectionDTO> {

    List<MovieBasicCollectionDTO> listByCollectionId(Long collectionId);
    List<MovieBasicCollectionDTO> listMovieId(Long movieId);

    MovieBasicCollectionDTO findByMovieIdAndCollectionId(Long movieId, Long collectionId);

    MovieBasicCollectionDTO insertByMovieIdAndCollectionId(Long movieId, Long collectionId);

    boolean deleteByMovieId(Long movieId);

    boolean deleteByCollectionId(Long collectionId);

    boolean deleteByMovieIdAndCollectionId(Long movieId, Long collectionId);

}