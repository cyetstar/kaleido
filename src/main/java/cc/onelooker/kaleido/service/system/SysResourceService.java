package cc.onelooker.kaleido.service.system;

import com.zjjcnt.common.core.service.IBaseService;
import cc.onelooker.kaleido.dto.system.SysResourceDTO;

import java.util.List;

/**
 * 资源表Service
 *
 * @author cyetstar
 * @date 2022-08-15 17:38:57
 */
public interface SysResourceService extends IBaseService<SysResourceDTO> {

    List<SysResourceDTO> listByUserId(Long userId);

    List<SysResourceDTO> listByRoleId(Long roleId);

    SysResourceDTO findByCode(String code);

    void init(List<SysResourceDTO> resourceDTOList);
}