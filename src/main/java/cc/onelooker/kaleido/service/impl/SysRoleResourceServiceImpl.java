package cc.onelooker.kaleido.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import cc.onelooker.kaleido.convert.SysRoleResourceConvert;
import cc.onelooker.kaleido.dto.SysRoleResourceDTO;
import cc.onelooker.kaleido.entity.SysRoleResourceDO;
import cc.onelooker.kaleido.mapper.SysRoleResourceMapper;
import cc.onelooker.kaleido.service.SysRoleResourceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 角色和资源关系表ServiceImpl
 *
 * @author xiadawei
 * @date 2022-08-15 17:38:57
 */
@Service
public class SysRoleResourceServiceImpl extends KaleidoBaseServiceImpl<SysRoleResourceMapper, SysRoleResourceDO, SysRoleResourceDTO> implements SysRoleResourceService {

    SysRoleResourceConvert convert = SysRoleResourceConvert.INSTANCE;

    @Override
    protected Wrapper<SysRoleResourceDO> genQueryWrapper(SysRoleResourceDTO sysRoleResourceDTO) {
        LambdaQueryWrapper<SysRoleResourceDO> query = new LambdaQueryWrapper<>();
        query.eq(Objects.nonNull(sysRoleResourceDTO.getId()), SysRoleResourceDO::getId, sysRoleResourceDTO.getId());
        query.eq(Objects.nonNull(sysRoleResourceDTO.getResourceId()), SysRoleResourceDO::getResourceId, sysRoleResourceDTO.getResourceId());
        query.eq(Objects.nonNull(sysRoleResourceDTO.getRoleId()), SysRoleResourceDO::getRoleId, sysRoleResourceDTO.getRoleId());
        return query;
    }

    @Override
    public SysRoleResourceDTO convertToDTO(SysRoleResourceDO sysRoleResourceDO) {
        return convert.convert(sysRoleResourceDO);
    }

    @Override
    public SysRoleResourceDO convertToDO(SysRoleResourceDTO sysRoleResourceDTO) {
        return convert.convertToDO(sysRoleResourceDTO);
    }

    @Override
    public List<SysRoleResourceDTO> listByRoleId(Long roleId) {
        SysRoleResourceDTO param = new SysRoleResourceDTO();
        param.setRoleId(roleId);
        return list(param);
    }

    @Override
    @Transactional
    public void deleteByResourceId(Long resourceId) {
        SysRoleResourceDTO param = new SysRoleResourceDTO();
        param.setResourceId(resourceId);
        delete(param);
    }

    @Override
    @Transactional
    public void insertBy(Long roleId, Long resourceId) {
        SysRoleResourceDTO sysRoleResourceDTO = new SysRoleResourceDTO();
        sysRoleResourceDTO.setRoleId(roleId);
        sysRoleResourceDTO.setResourceId(resourceId);
        sysRoleResourceDTO.setCreateTime(new Date());
        insert(sysRoleResourceDTO);
    }
}