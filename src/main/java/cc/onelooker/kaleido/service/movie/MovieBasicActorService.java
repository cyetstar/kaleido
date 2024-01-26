package cc.onelooker.kaleido.service.movie;

import cc.onelooker.kaleido.dto.movie.MovieBasicActorDTO;
import com.zjjcnt.common.core.service.IBaseService;

import java.util.List;

/**
 * 电影演职员关联表Service
 *
 * @author cyetstar
 * @date 2023-11-26 01:19:02
 */
public interface MovieBasicActorService extends IBaseService<MovieBasicActorDTO> {

    List<MovieBasicActorDTO> listByMovieId(Long movieId);

    List<MovieBasicActorDTO> listActorId(Long actorId);

    MovieBasicActorDTO findByMovieIdAndActorId(Long movieId, Long actorId);

    MovieBasicActorDTO insertByMovieIdAndActorIdAndRole(Long movieId, Long actorId, String role);

    boolean deleteByMovieId(Long movieId);

}