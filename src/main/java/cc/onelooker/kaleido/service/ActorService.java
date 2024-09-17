package cc.onelooker.kaleido.service;

import cc.onelooker.kaleido.dto.ActorDTO;
import cc.onelooker.kaleido.enums.ActorRole;
import com.zjjcnt.common.core.service.IBaseService;

import java.util.List;

/**
 * 演职员Service
 *
 * @author cyetstar
 * @date 2023-11-26 01:19:02
 */
public interface ActorService extends IBaseService<ActorDTO> {

    List<ActorDTO> listByMovieId(String movieId);

    List<ActorDTO> listBySeasonId(String seasonId);

    ActorDTO findByName(String name);

    ActorDTO findByThumb(String thumb);

    ActorDTO findByDoubanId(String doubanId);

    void updateSeasonActors(List<ActorDTO> authorDTOList, String seasonId, ActorRole actorRole);

    void updateMovieActors(List<ActorDTO> authorDTOList, String movieId, ActorRole actorRole);

}