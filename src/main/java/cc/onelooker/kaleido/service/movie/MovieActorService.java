package cc.onelooker.kaleido.service.movie;

import com.zjjcnt.common.core.service.IBaseService;

import cc.onelooker.kaleido.dto.movie.MovieActorDTO;

import java.util.List;

/**
 * 演职员Service
 *
 * @author cyetstar
 * @date 2023-11-26 01:19:02
 */
public interface MovieActorService extends IBaseService<MovieActorDTO> {

    List<MovieActorDTO> listByMovieId(Long movieId);
}