package cc.onelooker.kaleido.service.movie;

import cc.onelooker.kaleido.dto.movie.MovieBasicDTO;
import com.zjjcnt.common.core.service.IBaseService;

/**
 * 电影Service
 *
 * @author cyetstar
 * @date 2023-11-26 01:19:02
 */
public interface MovieBasicService extends IBaseService<MovieBasicDTO> {

    Long findMaxUpdatedAt();

    Boolean updateDoubanId(Long id, String doubanId);

    MovieBasicDTO findByDoubanId(String doubanId);

    MovieBasicDTO findByImdb(String imdb);
}