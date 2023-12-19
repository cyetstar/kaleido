package cc.onelooker.kaleido.service.system;

import cc.onelooker.kaleido.dto.system.SysUserDTO;
import com.zjjcnt.common.core.service.IBaseService;

import java.util.List;

/**
 * 用户表Service
 *
 * @author cyetstar
 * @date 2022-04-26 00:41:00
 */
public interface SysUserService extends IBaseService<SysUserDTO> {

    SysUserDTO findByUsername(String username);

    List<SysUserDTO> listByRoleId(Long roleId);

    List<SysUserDTO> listByRoleCode(String roleCode);

    void lock(String username);

    void lock(Long id, boolean locked);

    SysUserDTO findByDeptCodeAndRoleCode(String deptCode, String roleCode);
}