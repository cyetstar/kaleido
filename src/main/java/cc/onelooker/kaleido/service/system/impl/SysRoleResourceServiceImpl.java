package cc.onelooker.kaleido.service.system.impl;

import cc.onelooker.kaleido.convert.system.SysRoleResourceConvert;
import cc.onelooker.kaleido.dto.system.SysRoleResourceDTO;
import cc.onelooker.kaleido.dto.system.req.SysRoleResourceBindReq;
import cc.onelooker.kaleido.entity.system.SysRoleResourceDO;
import cc.onelooker.kaleido.mapper.system.SysRoleResourceMapper;
import cc.onelooker.kaleido.service.KaleidoBaseServiceImpl;
import cc.onelooker.kaleido.service.system.SysRoleResourceService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 角色和资源关系表ServiceImpl
 *
 * @author cyetstar
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

    @Override
    public boolean bind(SysRoleResourceBindReq req) {
        // 获取原本所有的资源
        SysRoleResourceDTO queryDto = new SysRoleResourceDTO();
        queryDto.setRoleId(req.getRoleId());
        List<SysRoleResourceDTO> roleResources = list(queryDto);
        Map<Long, SysRoleResourceDTO> roleResourceMaps = roleResources.stream().collect(Collectors.toMap(SysRoleResourceDTO::getResourceId, item -> item));
        for (Long resourceId : req.getResourceIds()) {
            SysRoleResourceDTO sysRoleResourceDTO = roleResourceMaps.remove(resourceId);
            if (sysRoleResourceDTO == null) {
                sysRoleResourceDTO = new SysRoleResourceDTO();
                sysRoleResourceDTO.setRoleId(req.getRoleId());
                sysRoleResourceDTO.setResourceId(resourceId);
                insert(sysRoleResourceDTO);
            }
        }
        // 移除不存在的资源
        if (roleResourceMaps.size() > 0) {
            roleResourceMaps.values().forEach(item -> {
                deleteById(item.getId());
            });
        }
        return true;
    }

}