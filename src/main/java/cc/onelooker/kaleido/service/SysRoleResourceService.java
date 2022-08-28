package cc.onelooker.kaleido.service;

import com.zjjcnt.common.core.service.IBaseService;
import cc.onelooker.kaleido.dto.SysRoleResourceDTO;

import java.util.List;

/**
 * 角色和资源关系表Service
 *
 * @author xiadawei
 * @date 2022-08-15 17:38:57
 */
public interface SysRoleResourceService extends IBaseService<SysRoleResourceDTO> {

    void deleteByResourceId(Long resourceId);

    List<SysRoleResourceDTO> listByRoleId(Long roleId);

    void insertBy(Long roleId, Long resourceId);
}