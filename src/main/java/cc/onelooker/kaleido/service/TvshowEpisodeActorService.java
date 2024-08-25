package cc.onelooker.kaleido.service;

import cc.onelooker.kaleido.dto.TvshowEpisodeActorDTO;
import cc.onelooker.kaleido.enums.ActorRole;
import cc.onelooker.kaleido.enums.AuthorRole;
import com.zjjcnt.common.core.service.IBaseService;

/**
 * 单集演职员关联表Service
 *
 * @author cyetstar
 * @date 2023-11-26 23:27:03
 */
public interface TvshowEpisodeActorService extends IBaseService<TvshowEpisodeActorDTO> {

    TvshowEpisodeActorDTO findByEpisodeIdAndActorId(String episodeId, String actorId);

    TvshowEpisodeActorDTO insert(String episodeId, String actorId, ActorRole actorRole);

    void deleteByEpisodeIdAndRole(String episodeId, ActorRole actorRole);
}