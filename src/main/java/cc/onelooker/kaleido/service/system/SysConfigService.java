package cc.onelooker.kaleido.service.system;

import cc.onelooker.kaleido.dto.system.SysConfigDTO;
import com.zjjcnt.common.core.service.IBaseService;

import java.util.List;

/**
 * 系统配置表Service
 *
 * @author cyetstar
 * @date 2022-11-13 00:43:42
 */
public interface SysConfigService extends IBaseService<SysConfigDTO> {

    void save(List<SysConfigDTO> sysConfigDTOList);

    void save(SysConfigDTO sysConfigDTO);

    SysConfigDTO findByConfigKey(String configKey);
}