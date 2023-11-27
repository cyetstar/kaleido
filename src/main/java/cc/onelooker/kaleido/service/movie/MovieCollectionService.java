package cc.onelooker.kaleido.service.movie;

import cc.onelooker.kaleido.dto.movie.MovieCollectionDTO;
import com.zjjcnt.common.core.service.IBaseService;

/**
 * 电影集合Service
 *
 * @author cyetstar
 * @date 2023-11-26 01:19:02
 */
public interface MovieCollectionService extends IBaseService<MovieCollectionDTO> {

    MovieCollectionDTO findByTitle(String title);

    MovieCollectionDTO insert(Long id, String title);
}