package cc.onelooker.kaleido.service;

import cc.onelooker.kaleido.dto.PathInfoDTO;
import com.zjjcnt.common.core.service.IBaseService;

/**
 * 目录信息Service
 *
 * @author cyetstar
 * @date 2024-03-23 23:08:41
 */
public interface PathInfoService extends IBaseService<PathInfoDTO> {

    void insert(String path, String bgmId);

    PathInfoDTO findByPath(String path);

    void deleteByPath(String path);
}