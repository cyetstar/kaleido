package cc.onelooker.kaleido.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import cc.onelooker.kaleido.convert.SysResourceConvert;
import cc.onelooker.kaleido.dto.SysResourceDTO;
import cc.onelooker.kaleido.entity.SysResourceDO;
import cc.onelooker.kaleido.mapper.SysResourceMapper;
import cc.onelooker.kaleido.service.SysResourceService;
import cc.onelooker.kaleido.service.SysRoleResourceService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 资源表ServiceImpl
 *
 * @author xiadawei
 * @date 2022-08-15 17:38:57
 */
@Service
public class SysResourceServiceImpl extends KaleidoBaseServiceImpl<SysResourceMapper, SysResourceDO, SysResourceDTO> implements SysResourceService {

    SysResourceConvert convert = SysResourceConvert.INSTANCE;

    @Autowired
    private SysRoleResourceService sysRoleResourceService;

    @Override
    protected Wrapper<SysResourceDO> genQueryWrapper(SysResourceDTO sysResourceDTO) {
        LambdaQueryWrapper<SysResourceDO> query = new LambdaQueryWrapper<>();
        query.eq(Objects.nonNull(sysResourceDTO.getId()), SysResourceDO::getId, sysResourceDTO.getId());
        query.eq(StringUtils.isNotEmpty(sysResourceDTO.getCode()), SysResourceDO::getCode, sysResourceDTO.getCode());
        query.eq(StringUtils.isNotEmpty(sysResourceDTO.getType()), SysResourceDO::getType, sysResourceDTO.getType());
        query.eq(StringUtils.isNotEmpty(sysResourceDTO.getName()), SysResourceDO::getName, sysResourceDTO.getName());
        query.eq(StringUtils.isNotEmpty(sysResourceDTO.getUrl()), SysResourceDO::getUrl, sysResourceDTO.getUrl());
        query.eq(StringUtils.isNotEmpty(sysResourceDTO.getMethod()), SysResourceDO::getMethod, sysResourceDTO.getMethod());
        return query;
    }

    @Override
    public SysResourceDTO convertToDTO(SysResourceDO sysResourceDO) {
        return convert.convert(sysResourceDO);
    }

    @Override
    public SysResourceDO convertToDO(SysResourceDTO sysResourceDTO) {
        return convert.convertToDO(sysResourceDTO);
    }

    @Override
    public List<SysResourceDTO> listByUserId(Long userId) {
        return convertToDTO(baseMapper.listByUserId(userId));
    }

    @Override
    public List<SysResourceDTO> listByRoleId(Long roleId) {
        return convertToDTO(baseMapper.listByRoleId(roleId));
    }

    @Override
    @Transactional
    public SysResourceDTO insert(SysResourceDTO dto) {
        dto.setCreateTime(new Date());
        return super.insert(dto);
    }

    @Override
    @Transactional
    public boolean deleteById(Serializable id) {
        sysRoleResourceService.deleteByResourceId((Long) id);
        return super.deleteById(id);
    }

    @Override
    protected boolean checkUnique(SysResourceDTO dto) {
        SysResourceDTO param = new SysResourceDTO();
        param.setUrl(dto.getUrl());
        SysResourceDTO exist = find(param);
        return doCheckUnique(dto, exist);
    }
}