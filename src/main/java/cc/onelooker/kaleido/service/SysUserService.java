package cc.onelooker.kaleido.service;

import cc.onelooker.kaleido.dto.SysUserDTO;
import com.zjjcnt.common.core.service.IBaseService;

import java.util.List;

/**
 * 用户表Service
 *
 * @author xiadawei
 * @date 2022-04-26 00:41:00
 */
public interface SysUserService extends IBaseService<SysUserDTO> {

    SysUserDTO findByUsername(String username);

    boolean updatePassword(SysUserDTO dto);

    void register(SysUserDTO sysUserDTO);

    List<SysUserDTO> listByRoleId(Long roleId);

    void lock(String username);

    void lock(Long id, boolean locked);
}