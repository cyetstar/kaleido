package cc.onelooker.kaleido.service.tvshow;

import cc.onelooker.kaleido.dto.tvshow.TvshowEpisodeActorDTO;
import com.zjjcnt.common.core.service.IBaseService;

/**
 * 单集演职员关联表Service
 *
 * @author cyetstar
 * @date 2023-11-26 23:27:03
 */
public interface TvshowEpisodeActorService extends IBaseService<TvshowEpisodeActorDTO> {

    TvshowEpisodeActorDTO findByEpisodeIdAndActorId(Long episodeId, Long actorId);

    TvshowEpisodeActorDTO insert(Long episodeId, Long actorId, String role);

    void deleteByEpisodeIdAndRole(Long episodeId, String role);
}