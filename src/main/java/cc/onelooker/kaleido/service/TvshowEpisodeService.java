package cc.onelooker.kaleido.service;

import cc.onelooker.kaleido.dto.TvshowEpisodeDTO;
import com.zjjcnt.common.core.service.IBaseService;

import java.util.List;

/**
 * 单集Service
 *
 * @author cyetstar
 * @date 2023-11-26 23:27:03
 */
public interface TvshowEpisodeService extends IBaseService<TvshowEpisodeDTO> {

    Long findMaxUpdatedAt();

    List<TvshowEpisodeDTO> listBySeasonId(String seasonId);
}