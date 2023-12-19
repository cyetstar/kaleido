package cc.onelooker.kaleido.service.system.impl;

import cc.onelooker.kaleido.convert.system.SysUserRoleConvert;
import cc.onelooker.kaleido.dto.system.SysUserRoleDTO;
import cc.onelooker.kaleido.entity.system.SysUserRoleDO;
import cc.onelooker.kaleido.mapper.system.SysUserRoleMapper;
import cc.onelooker.kaleido.service.system.SysUserRoleService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zjjcnt.common.core.service.impl.AbstractBaseServiceImpl;
import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * 用户和角色关系表ServiceImpl
 *
 * @author cyetstar
 * @date 2022-04-26 00:41:00
 */
@Service
public class SysUserRoleServiceImpl extends AbstractBaseServiceImpl<SysUserRoleMapper, SysUserRoleDO, SysUserRoleDTO> implements SysUserRoleService {

    SysUserRoleConvert convert = SysUserRoleConvert.INSTANCE;

    @Override
    protected Wrapper<SysUserRoleDO> genQueryWrapper(SysUserRoleDTO sysUserRoleDTO) {
        LambdaQueryWrapper<SysUserRoleDO> query = new LambdaQueryWrapper<>();
        query.eq(Objects.nonNull(sysUserRoleDTO.getUserId()), SysUserRoleDO::getUserId, sysUserRoleDTO.getUserId());
        query.eq(Objects.nonNull(sysUserRoleDTO.getRoleId()), SysUserRoleDO::getRoleId, sysUserRoleDTO.getRoleId());
        return query;
    }

    @Override
    public SysUserRoleDTO convertToDTO(SysUserRoleDO sysUserRoleDO) {
        return convert.convert(sysUserRoleDO);
    }

    @Override
    public SysUserRoleDO convertToDO(SysUserRoleDTO sysUserRoleDTO) {
        return convert.convertToDO(sysUserRoleDTO);
    }

    @Override
    @Transactional
    public void insert(Long userId, Long roleId) {
        Validate.notNull(userId);
        Validate.notNull(roleId);
        SysUserRoleDTO sysUserRoleDTO = new SysUserRoleDTO();
        sysUserRoleDTO.setUserId(userId);
        sysUserRoleDTO.setRoleId(roleId);
        super.insert(sysUserRoleDTO);
    }

    @Override
    @Transactional
    public void deleteByUserId(Long userId) {
        Validate.notNull(userId);
        SysUserRoleDTO param = new SysUserRoleDTO();
        param.setUserId(userId);
        delete(param);
    }

    @Override
    public List<SysUserRoleDTO> listByUserId(Long userId) {
        Validate.notNull(userId);
        SysUserRoleDTO param = new SysUserRoleDTO();
        param.setUserId(userId);
        return list(param);
    }
}