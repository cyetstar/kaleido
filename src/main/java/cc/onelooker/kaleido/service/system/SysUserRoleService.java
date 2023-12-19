package cc.onelooker.kaleido.service.system;

import cc.onelooker.kaleido.dto.system.SysUserRoleDTO;
import com.zjjcnt.common.core.service.IBaseService;

import java.util.List;

/**
 * 用户和角色关系表Service
 *
 * @author cyetstar
 * @date 2022-04-26 00:41:00
 */
public interface SysUserRoleService extends IBaseService<SysUserRoleDTO> {

    void insert(Long userId, Long roleId);

    void deleteByUserId(Long userId);

    List<SysUserRoleDTO> listByUserId(Long userId);
}