package cc.onelooker.kaleido.service.movie;

import com.zjjcnt.common.core.service.IBaseService;

import cc.onelooker.kaleido.dto.movie.MovieBasicDTO;

/**
 * 电影Service
 *
 * @author cyetstar
 * @date 2023-11-26 01:19:02
 */
public interface MovieBasicService extends IBaseService<MovieBasicDTO> {

    Long findMaxUpdatedAt();

}