package cc.onelooker.kaleido.service;

import cc.onelooker.kaleido.dto.TvshowShowActorDTO;
import cc.onelooker.kaleido.enums.ActorRole;
import com.zjjcnt.common.core.service.IBaseService;

import java.util.List;

/**
 * 剧集演职员关联表Service
 *
 * @author cyetstar
 * @date 2023-11-26 23:27:03
 */
public interface TvshowShowActorService extends IBaseService<TvshowShowActorDTO> {

    TvshowShowActorDTO findByShowIdAndActorId(String showId, String actorId);

    TvshowShowActorDTO insert(String showId, String actorId, ActorRole actorRole);

    List<TvshowShowActorDTO> listByShowId(String showId);

    void deleteByShowIdAndRole(String showId, ActorRole actorRole);
}