package cc.onelooker.kaleido.service;

import cc.onelooker.kaleido.dto.MovieBasicActorDTO;
import cc.onelooker.kaleido.enums.ActorRole;
import com.zjjcnt.common.core.service.IBaseService;

import java.util.List;

/**
 * 电影演职员关联表Service
 *
 * @author cyetstar
 * @date 2023-11-26 01:19:02
 */
public interface MovieBasicActorService extends IBaseService<MovieBasicActorDTO> {

    List<MovieBasicActorDTO> listByMovieId(String movieId);

    List<MovieBasicActorDTO> listActorId(String actorId);

    MovieBasicActorDTO findByMovieIdAndActorId(String movieId, String actorId);

    MovieBasicActorDTO insertByMovieIdAndActorIdAndRole(String movieId, String actorId, ActorRole role);

    boolean deleteByMovieId(String movieId);

    boolean deleteByMovieIdAndRole(String movieId, ActorRole role);

}