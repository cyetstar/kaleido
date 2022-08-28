package cc.onelooker.kaleido.service;

import com.zjjcnt.common.core.service.IBaseService;
import cc.onelooker.kaleido.dto.SysRoleDTO;

import java.util.List;

/**
 * 角色表Service
 *
 * @author xiadawei
 * @date 2022-04-26 00:41:00
 */
public interface SysRoleService extends IBaseService<SysRoleDTO>, IDictionaryService {

    String ADMIN = "ROLE_ADMIN";
    String MANAGER = "ROLE_MANAGER";
    String TEAM_LEADER = "ROLE_TEAM_LEADER";
    String USER = "ROLE_USER";

    List<SysRoleDTO> listByUserId(Long userId);

    void authorize(Long id, List<Long> resourceIds);
}