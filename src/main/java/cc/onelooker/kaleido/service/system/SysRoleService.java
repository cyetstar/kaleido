package cc.onelooker.kaleido.service.system;

import cc.onelooker.kaleido.service.IDictionaryService;
import com.zjjcnt.common.core.service.IBaseService;
import cc.onelooker.kaleido.dto.system.SysRoleDTO;

import java.util.List;

/**
 * 角色表Service
 *
 * @author xiadawei
 * @date 2022-04-26 00:41:00
 */
public interface SysRoleService extends IBaseService<SysRoleDTO>, IDictionaryService {

    List<SysRoleDTO> listByUserId(Long userId);

    void authorize(Long id, List<Long> resourceIds);
}