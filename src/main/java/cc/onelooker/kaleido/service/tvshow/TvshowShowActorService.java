package cc.onelooker.kaleido.service.tvshow;

import cc.onelooker.kaleido.dto.tvshow.TvshowShowActorDTO;
import com.zjjcnt.common.core.service.IBaseService;

import java.util.List;

/**
 * 剧集演职员关联表Service
 *
 * @author cyetstar
 * @date 2023-11-26 23:27:03
 */
public interface TvshowShowActorService extends IBaseService<TvshowShowActorDTO> {

    TvshowShowActorDTO findByShowIdAndActorId(Long showId, Long actorId);

    TvshowShowActorDTO insert(Long showId, Long actorId, String role);

    List<TvshowShowActorDTO> listByShowId(Long showId);

    void deleteByShowIdAndRole(Long showId, String role);
}