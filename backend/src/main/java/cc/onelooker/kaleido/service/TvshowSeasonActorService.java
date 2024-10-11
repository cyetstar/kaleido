package cc.onelooker.kaleido.service;

import cc.onelooker.kaleido.dto.TvshowSeasonActorDTO;
import cc.onelooker.kaleido.enums.ActorRole;
import com.zjjcnt.common.core.service.IBaseService;

import java.util.List;

/**
 * 剧集演职员关联表Service
 *
 * @author cyetstar
 * @date 2023-11-26 23:27:03
 */
public interface TvshowSeasonActorService extends IBaseService<TvshowSeasonActorDTO> {

    TvshowSeasonActorDTO insert(String seasonId, String actorId, ActorRole actorRole);

    List<TvshowSeasonActorDTO> listBySeasonId(String seasonId);
}