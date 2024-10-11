package cc.onelooker.kaleido.service;

import cc.onelooker.kaleido.dto.ComicSeriesDTO;
import com.zjjcnt.common.core.service.IBaseService;

/**
 * 漫画系列Service
 *
 * @author cyetstar
 * @date 2024-03-12 17:47:50
 */
public interface ComicSeriesService extends IBaseService<ComicSeriesDTO> {

    ComicSeriesDTO findByBgmId(String bgmId);
}