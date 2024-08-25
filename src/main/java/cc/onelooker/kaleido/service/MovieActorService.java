package cc.onelooker.kaleido.service;

import cc.onelooker.kaleido.dto.MovieActorDTO;
import com.zjjcnt.common.core.service.IBaseService;

import java.util.List;

/**
 * 演职员Service
 *
 * @author cyetstar
 * @date 2023-11-26 01:19:02
 */
public interface MovieActorService extends IBaseService<MovieActorDTO> {

    List<MovieActorDTO> listByMovieId(String movieId);

    MovieActorDTO findByName(String name);

    MovieActorDTO findByThumb(String thumb);

    MovieActorDTO findByDoubanId(String doubanId);
}