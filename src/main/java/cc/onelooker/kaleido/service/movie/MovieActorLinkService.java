package cc.onelooker.kaleido.service.movie;

import cc.onelooker.kaleido.enums.ActorRole;
import com.zjjcnt.common.core.service.IBaseService;

import java.lang.Long;

import cc.onelooker.kaleido.dto.movie.MovieActorLinkDTO;

/**
 * 电影演职员关联表Service
 *
 * @author cyetstar
 * @date 2023-04-18 23:04:56
 */
public interface MovieActorLinkService extends IBaseService<MovieActorLinkDTO> {

    MovieActorLinkDTO insert(Long movieId, Long movieActorId, ActorRole actorRole);

    boolean deleteByMovieId(Long movieId);
}