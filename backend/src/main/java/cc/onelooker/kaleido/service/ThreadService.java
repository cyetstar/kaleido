package cc.onelooker.kaleido.service;

import cc.onelooker.kaleido.dto.ThreadDTO;
import com.zjjcnt.common.core.service.IBaseService;

import java.util.List;

/**
 * 发布记录Service
 *
 * @author cyetstar
 * @date 2023-12-18 14:53:14
 */
public interface ThreadService extends IBaseService<ThreadDTO> {

    List<ThreadDTO> listByDoubanId(String doubanId);

    List<ThreadDTO> listByImdbId(String imdbId);

    List<ThreadDTO> listByBgmId(String bgmId);

    void updateStatus(String id, String status);
}