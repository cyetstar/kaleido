package cc.onelooker.kaleido.service.system.impl;

import cc.onelooker.kaleido.convert.system.SysUserConvert;
import cc.onelooker.kaleido.dto.system.SysUserDTO;
import cc.onelooker.kaleido.entity.system.SysUserDO;
import cc.onelooker.kaleido.mapper.system.SysUserMapper;
import cc.onelooker.kaleido.service.KaleidoBaseServiceImpl;
import cc.onelooker.kaleido.service.system.SysUserRoleService;
import cc.onelooker.kaleido.service.system.SysUserService;
import cc.onelooker.kaleido.utils.CurrentUserUtils;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 用户表ServiceImpl
 *
 * @author cyetstar
 * @date 2022-04-26 00:41:00
 */
@Service
public class SysUserServiceImpl extends KaleidoBaseServiceImpl<SysUserMapper, SysUserDO, SysUserDTO> implements SysUserService {

    SysUserConvert convert = SysUserConvert.INSTANCE;

    @Autowired
    private SysUserRoleService sysUserRoleService;

    @Override
    protected Wrapper<SysUserDO> genQueryWrapper(SysUserDTO sysUserDTO) {
        LambdaQueryWrapper<SysUserDO> query = new LambdaQueryWrapper<>();
        query.like(StringUtils.isNotEmpty(sysUserDTO.getUsername()), SysUserDO::getUsername, sysUserDTO.getUsername());
        query.eq(StringUtils.isNotEmpty(sysUserDTO.getPassword()), SysUserDO::getPassword, sysUserDTO.getPassword());
        query.like(StringUtils.isNotEmpty(sysUserDTO.getName()), SysUserDO::getName, sysUserDTO.getName());
        query.like(StringUtils.isNotEmpty(sysUserDTO.getMobile()), SysUserDO::getMobile, sysUserDTO.getMobile());
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
    public List<SysUserDTO> listByRoleCode(String roleCode) {
        return convertToDTO(baseMapper.listByRoleCode(roleCode));
    }

    @Override
    @Transactional
    public SysUserDTO insert(SysUserDTO dto) {
        dto.setCreateTime(new Date());
        dto.setCreatedBy(CurrentUserUtils.getUsername());
        dto.setUpdatedBy(dto.getCreatedBy());
        dto.setUpdateTime(dto.getCreateTime());
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
            sysUserRoleService.deleteByUserId(dto.getId());
            saveUserRole(dto.getId(), dto.getRoleIds());
        }
        dto.setUpdateTime(new Date());
        dto.setUpdatedBy(CurrentUserUtils.getUsername());
        return super.update(dto);
    }

    private void saveUserRole(Long userId, Long[] roleIds) {
        for (Long roleId : roleIds) {
            sysUserRoleService.insert(userId, roleId);
        }
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
    public SysUserDTO findByDeptCodeAndRoleCode(String deptCode, String roleCode) {
        List<SysUserDO> sysUserDOList = baseMapper.listByDeptCodeAndRoleCode(deptCode, roleCode);
        return sysUserDOList.isEmpty() ? null : convertToDTO(sysUserDOList.get(0));
    }

    @Override
    protected boolean checkUnique(SysUserDTO dto) {
        if (dto.getId() != null && dto.getUsername() == null) {
            return true;
        }
        SysUserDTO param = new SysUserDTO();
        param.setUsername(dto.getUsername());
        SysUserDTO exist = find(param);
        return doCheckUnique(dto, exist);
    }
}