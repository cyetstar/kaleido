package cc.onelooker.kaleido.service.impl;

import cc.onelooker.kaleido.convert.SysUserConvert;
import cc.onelooker.kaleido.dto.SysUserDTO;
import cc.onelooker.kaleido.entity.SysUserDO;
import cc.onelooker.kaleido.mapper.SysUserMapper;
import cc.onelooker.kaleido.service.SysUserRoleService;
import cc.onelooker.kaleido.service.SysUserService;
import cc.onelooker.kaleido.utils.CurrentUserUtils;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 用户表ServiceImpl
 *
 * @author xiadawei
 * @date 2022-04-26 00:41:00
 */
@Service
public class SysUserServiceImpl extends KaleidoBaseServiceImpl<SysUserMapper, SysUserDO, SysUserDTO> implements SysUserService {

    SysUserConvert convert = SysUserConvert.INSTANCE;

    @Autowired
    private SysUserRoleService sysUserRoleService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    protected Wrapper<SysUserDO> genQueryWrapper(SysUserDTO sysUserDTO) {
        LambdaQueryWrapper<SysUserDO> query = new LambdaQueryWrapper<>();
        query.eq(StringUtils.isNotEmpty(sysUserDTO.getUsername()), SysUserDO::getUsername, sysUserDTO.getUsername());
        query.eq(StringUtils.isNotEmpty(sysUserDTO.getPassword()), SysUserDO::getPassword, sysUserDTO.getPassword());
        query.eq(StringUtils.isNotEmpty(sysUserDTO.getName()), SysUserDO::getName, sysUserDTO.getName());
        query.eq(StringUtils.isNotEmpty(sysUserDTO.getMobile()), SysUserDO::getMobile, sysUserDTO.getMobile());
        query.eq(StringUtils.isNotEmpty(sysUserDTO.getDescription()), SysUserDO::getDescription, sysUserDTO.getDescription());
        query.eq(StringUtils.isNotEmpty(sysUserDTO.getDeptCode()), SysUserDO::getDeptCode, sysUserDTO.getDeptCode());
        query.eq(Objects.nonNull(sysUserDTO.getDeptId()), SysUserDO::getDeptId, sysUserDTO.getDeptId());
        query.eq(Objects.nonNull(sysUserDTO.getEnabled()), SysUserDO::getEnabled, sysUserDTO.getEnabled());
        query.eq(Objects.nonNull(sysUserDTO.getAccountNonExpired()), SysUserDO::getAccountNonExpired, sysUserDTO.getAccountNonExpired());
        query.eq(Objects.nonNull(sysUserDTO.getCredentialsNonExpired()), SysUserDO::getCredentialsNonExpired, sysUserDTO.getCredentialsNonExpired());
        query.eq(Objects.nonNull(sysUserDTO.getAccountNonLocked()), SysUserDO::getAccountNonLocked, sysUserDTO.getAccountNonLocked());
        query.eq(Objects.nonNull(sysUserDTO.getDeleted()), SysUserDO::getDeleted, sysUserDTO.getDeleted());
        return query;
    }

    @Override
    public SysUserDTO convertToDTO(SysUserDO sysUserDO) {
        return convert.convert(sysUserDO);
    }

    @Override
    public SysUserDO convertToDO(SysUserDTO sysUserDTO) {
        return convert.convertToDO(sysUserDTO);
    }

    @Override
    public SysUserDTO findByUsername(String username) {
        Validate.notEmpty(username);
        SysUserDTO param = new SysUserDTO();
        param.setUsername(username);
        return find(param);
    }

    @Override
    public List<SysUserDTO> listByRoleId(Long roleId) {
        return convertToDTO(baseMapper.listByRoleId(roleId));
    }

    @Override
    @Transactional
    public SysUserDTO insert(SysUserDTO dto) {
        dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        dto.setAccountNonExpired(true);
        dto.setAccountNonLocked(true);
        dto.setCredentialsNonExpired(true);
        dto.setEnabled(true);
        dto.setDeleted(false);
        dto.setCreateTime(new Date());
        dto.setCreatedBy(CurrentUserUtils.getUsername());
        dto.setUpdateTime(new Date());
        dto.setUpdatedBy(CurrentUserUtils.getUsername());
        dto = super.insert(dto);
        if (dto.getRoleIds() != null) {
            saveUserRole(dto.getId(), dto.getRoleIds());
        }
        return dto;
    }

    @Override
    @Transactional
    public boolean update(SysUserDTO dto) {
        if (dto.getRoleIds() != null) {
            saveUserRole(dto.getId(), dto.getRoleIds());
        }
        dto.setUpdateTime(new Date());
        dto.setUpdatedBy(CurrentUserUtils.getName());
        return super.update(dto);
    }

    private void saveUserRole(Long userId, Long[] roleIds) {
        sysUserRoleService.deleteByUserId(userId);
        for (Long roleId : roleIds) {
            sysUserRoleService.insert(userId, roleId);
        }
    }

    @Override
    @Transactional
    public boolean updatePassword(SysUserDTO dto) {
        dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        dto.setUpdateTime(new Date());
        dto.setUpdatedBy(CurrentUserUtils.getUsername());
        return super.update(dto);
    }

    @Override
    @Transactional
    public void lock(String username) {
        SysUserDTO sysUserDTO = findByUsername(username);
        sysUserDTO.setAccountNonLocked(false);
        super.update(sysUserDTO);
    }

    @Override
    @Transactional
    public void lock(Long id, boolean locked) {
        SysUserDTO sysUserDTO = findById(id);
        sysUserDTO.setAccountNonLocked(!locked);
        super.update(sysUserDTO);
    }

    @Override
    protected boolean checkUnique(SysUserDTO dto) {
        SysUserDTO param = new SysUserDTO();
        param.setUsername(dto.getUsername());
        SysUserDTO exist = find(param);
        return doCheckUnique(dto, exist);
    }

    @Override
    @Transactional
    public void register(SysUserDTO sysUserDTO) {
        sysUserDTO.setPassword(passwordEncoder.encode(sysUserDTO.getPassword()));
        sysUserDTO.setAccountNonExpired(true);
        sysUserDTO.setAccountNonLocked(true);
        sysUserDTO.setCredentialsNonExpired(true);
        sysUserDTO.setEnabled(true);
        sysUserDTO.setDeleted(false);
        sysUserDTO.setCreateTime(new Date());
        sysUserDTO.setCreatedBy(sysUserDTO.getUsername());
        sysUserDTO.setUpdateTime(new Date());
        sysUserDTO.setUpdatedBy(sysUserDTO.getUsername());
        insert(sysUserDTO);
    }
}