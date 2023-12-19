package cc.onelooker.kaleido.service.system.impl;

import cc.onelooker.kaleido.convert.system.SysRoleMenuConvert;
import cc.onelooker.kaleido.dto.system.SysRoleMenuDTO;
import cc.onelooker.kaleido.entity.system.SysRoleMenuDO;
import cc.onelooker.kaleido.mapper.system.SysRoleMenuMapper;
import cc.onelooker.kaleido.service.system.SysRoleMenuService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zjjcnt.common.core.service.impl.AbstractBaseServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * 角色和菜单关系表ServiceImpl
 *
 * @author cyetstar
 * @date 2022-11-13 01:12:24
 */
@Service
public class SysRoleMenuServiceImpl extends AbstractBaseServiceImpl<SysRoleMenuMapper, SysRoleMenuDO, SysRoleMenuDTO> implements SysRoleMenuService {

    SysRoleMenuConvert convert = SysRoleMenuConvert.INSTANCE;

    @Override
    protected Wrapper<SysRoleMenuDO> genQueryWrapper(SysRoleMenuDTO sysRoleMenuDTO) {
        LambdaQueryWrapper<SysRoleMenuDO> query = new LambdaQueryWrapper<>();
        query.eq(Objects.nonNull(sysRoleMenuDTO.getId()), SysRoleMenuDO::getId, sysRoleMenuDTO.getId());
        query.eq(Objects.nonNull(sysRoleMenuDTO.getMenuId()), SysRoleMenuDO::getMenuId, sysRoleMenuDTO.getMenuId());
        query.eq(Objects.nonNull(sysRoleMenuDTO.getRoleId()), SysRoleMenuDO::getRoleId, sysRoleMenuDTO.getRoleId());
        query.eq(Objects.nonNull(sysRoleMenuDTO.getCreateTime()), SysRoleMenuDO::getCreateTime, sysRoleMenuDTO.getCreateTime());
        query.eq(Objects.nonNull(sysRoleMenuDTO.getUpdateTime()), SysRoleMenuDO::getUpdateTime, sysRoleMenuDTO.getUpdateTime());
        query.eq(StringUtils.isNotEmpty(sysRoleMenuDTO.getCreatedBy()), SysRoleMenuDO::getCreatedBy, sysRoleMenuDTO.getCreatedBy());
        query.eq(StringUtils.isNotEmpty(sysRoleMenuDTO.getUpdatedBy()), SysRoleMenuDO::getUpdatedBy, sysRoleMenuDTO.getUpdatedBy());
        return query;
    }

    @Override
    public SysRoleMenuDTO convertToDTO(SysRoleMenuDO sysRoleMenuDO) {
        return convert.convert(sysRoleMenuDO);
    }

    @Override
    public SysRoleMenuDO convertToDO(SysRoleMenuDTO sysRoleMenuDTO) {
        return convert.convertToDO(sysRoleMenuDTO);
    }
}