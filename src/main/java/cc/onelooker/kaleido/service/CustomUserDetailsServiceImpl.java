package cc.onelooker.kaleido.service;

import cc.onelooker.kaleido.dto.system.SysRoleDTO;
import cc.onelooker.kaleido.dto.system.SysUserDTO;
import cc.onelooker.kaleido.service.system.SysRoleService;
import cc.onelooker.kaleido.service.system.SysUserService;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.zjjcnt.common.core.dict.Dictionary;
import com.zjjcnt.common.security.component.AbstractCustomUserDetailsServiceImpl;
import com.zjjcnt.common.security.component.CustomUserDetailsService;
import com.zjjcnt.common.security.domain.CustomUserDetails;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Hyliu
 * @version 1.0.0
 * @ClassName CustomUserDetailsServiceImpl.java
 * @Description TODO
 * @createTime 2021年12月04日 01:14:00
 */
@Service("userDetailsService")
@DS("auth")
@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
public class CustomUserDetailsServiceImpl extends AbstractCustomUserDetailsServiceImpl implements CustomUserDetailsService {

    @Resource
    private SysUserService sysUserService;

    @Resource
    private SysRoleService sysRoleService;

    @Override
    public CustomUserDetails findByUsername(String username) {
        SysUserDTO sysUserDTO = sysUserService.findByUsername(username);
        if (sysUserDTO == null) {
            return null;
        }
        List<SysRoleDTO> sysRoleDTOList = sysRoleService.listByUserId(sysUserDTO.getId());
        Set<GrantedAuthority> grantedAuthorities = sysRoleDTOList.stream()
                .map(s -> new SimpleGrantedAuthority(s.getCode())).collect(Collectors.toSet());
        CustomUserDetails customUserDetails = new CustomUserDetails(sysUserDTO.getUsername(),
                sysUserDTO.getPassword(),
                sysUserDTO.getEnabled(),
                sysUserDTO.getAccountNonExpired(),
                sysUserDTO.getCredentialsNonExpired(),
                sysUserDTO.getAccountNonLocked(),
                grantedAuthorities
        );
        customUserDetails.setUserId(sysUserDTO.getId());
        customUserDetails.setName(sysUserDTO.getName());
        customUserDetails.setMobile(sysUserDTO.getMobile());
        customUserDetails.setDeptCode(sysUserDTO.getDeptCode());
        customUserDetails.setDeptName(Dictionary.getSsxq(sysUserDTO.getDeptCode()));
        customUserDetails.setFilterCode(sysUserDTO.getFilterCode());
        return customUserDetails;
    }

}
