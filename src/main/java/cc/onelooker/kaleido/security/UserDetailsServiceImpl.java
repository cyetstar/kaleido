package cc.onelooker.kaleido.security;

import cc.onelooker.kaleido.dto.SysResourceDTO;
import cc.onelooker.kaleido.dto.SysRoleDTO;
import cc.onelooker.kaleido.dto.SysUserDTO;
import cc.onelooker.kaleido.service.SysResourceService;
import cc.onelooker.kaleido.service.SysRoleService;
import cc.onelooker.kaleido.service.SysUserService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zjjcnt.common.security.domain.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author xiadawei
 * @Date 2022-04-26 00:42:00
 * @Description TODO
 */
@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysResourceService sysResourceService;

    @Autowired
    private SysRoleService sysRoleService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUserDTO sysUserDTO = sysUserService.findByUsername(username);
        if (sysUserDTO == null) {
            throw new UsernameNotFoundException("用户名或密码错误");
        }
        List<SysResourceDTO> sysResourceDTOList = sysResourceService.listByUserId(sysUserDTO.getId());
        List<SysRoleDTO> sysRoleDTOList = sysRoleService.listByUserId(sysUserDTO.getId());
        Collection<GrantedAuthority> authorities = Lists.newArrayList();
        authorities.addAll(sysResourceDTOList);
        authorities.addAll(sysRoleDTOList);
        CustomUserDetails customUserDetails = new CustomUserDetails(sysUserDTO.getUsername(), sysUserDTO.getPassword(),
                sysUserDTO.getEnabled(), sysUserDTO.getAccountNonExpired(), sysUserDTO.getCredentialsNonExpired(), sysUserDTO.getAccountNonLocked(),
                authorities);
        customUserDetails.setName(sysUserDTO.getName());
        HashMap<String, Object> additionalInfos = Maps.newHashMap();
        additionalInfos.put("roles", sysRoleDTOList.stream().map(SysRoleDTO::getCode).collect(Collectors.toList()));
        customUserDetails.setAdditionalInfos(additionalInfos);
        return customUserDetails;
    }

}
