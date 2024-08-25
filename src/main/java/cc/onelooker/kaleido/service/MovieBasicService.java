package cc.onelooker.kaleido.service;

import cc.onelooker.kaleido.dto.MovieBasicDTO;
import com.zjjcnt.common.core.service.IBaseService;

/**
 * 电影Service
 *
 * @author cyetstar
 * @date 2023-11-26 01:19:02
 */
public interface MovieBasicService extends IBaseService<MovieBasicDTO> {

    Long findMaxUpdatedAt();

    MovieBasicDTO findByDoubanId(String doubanId);

    MovieBasicDTO findByImdb(String imdb);

}