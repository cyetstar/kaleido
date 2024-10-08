package cc.onelooker.kaleido.service;

import cc.onelooker.kaleido.dto.SysRoleResourceDTO;
import cc.onelooker.kaleido.dto.req.SysRoleResourceBindReq;
import com.zjjcnt.common.core.service.IBaseService;

import java.util.List;

/**
 * 角色和资源关系表Service
 *
 * @author cyetstar
 * @date 2022-08-15 17:38:57
 */
public interface SysRoleResourceService extends IBaseService<SysRoleResourceDTO> {

    void deleteByResourceId(Long resourceId);

    List<SysRoleResourceDTO> listByRoleId(Long roleId);

    void insertBy(Long roleId, Long resourceId);

    boolean bind(SysRoleResourceBindReq req);
}