package cc.onelooker.kaleido.service.impl;

import cc.onelooker.kaleido.convert.SysRoleConvert;
import cc.onelooker.kaleido.dto.SysRoleDTO;
import cc.onelooker.kaleido.dto.SysRoleResourceDTO;
import cc.onelooker.kaleido.entity.SysRoleDO;
import cc.onelooker.kaleido.mapper.SysRoleMapper;
import cc.onelooker.kaleido.service.DictionaryBaseServiceImpl;
import cc.onelooker.kaleido.service.SysRoleResourceService;
import cc.onelooker.kaleido.service.SysRoleService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 角色表ServiceImpl
 *
 * @author cyetstar
 * @date 2022-04-26 00:41:00
 */
@Service
public class SysRoleServiceImpl extends DictionaryBaseServiceImpl<SysRoleMapper, SysRoleDO, SysRoleDTO> implements SysRoleService {

    SysRoleConvert convert = SysRoleConvert.INSTANCE;

    @Autowired
    private SysRoleResourceService sysRoleResourceService;

    @Override
    protected Wrapper<SysRoleDO> genQueryWrapper(SysRoleDTO sysRoleDTO) {
        LambdaQueryWrapper<SysRoleDO> query = new LambdaQueryWrapper<>();
        query.eq(Objects.nonNull(sysRoleDTO.getParentId()), SysRoleDO::getParentId, sysRoleDTO.getParentId());
        query.like(StringUtils.isNotEmpty(sysRoleDTO.getCode()), SysRoleDO::getCode, sysRoleDTO.getCode());
        query.like(StringUtils.isNotEmpty(sysRoleDTO.getName()), SysRoleDO::getName, sysRoleDTO.getName());
        query.eq(StringUtils.isNotEmpty(sysRoleDTO.getDescription()), SysRoleDO::getDescription, sysRoleDTO.getDescription());
        query.eq(Objects.nonNull(sysRoleDTO.getSubCount()), SysRoleDO::getSubCount, sysRoleDTO.getSubCount());
        query.eq(Objects.nonNull(sysRoleDTO.getIsDeleted()), SysRoleDO::getIsDeleted, sysRoleDTO.getIsDeleted());
        return query;
    }

    @Override
    public SysRoleDTO convertToDTO(SysRoleDO sysRoleDO) {
        return convert.convert(sysRoleDO);
    }

    @Override
    public SysRoleDO convertToDO(SysRoleDTO sysRoleDTO) {
        return convert.convertToDO(sysRoleDTO);
    }

    @Override
    public List<SysRoleDTO> listByUserId(Long userId) {
        Validate.notNull(userId);
        return convertToDTO(baseMapper.listByUserId(userId));
    }

    @Override
    @Transactional
    public void authorize(Long id, List<Long> resourceIds) {
        List<SysRoleResourceDTO> sysRoleResourceDTOList = sysRoleResourceService.listByRoleId(id);
        for (SysRoleResourceDTO sysRoleResourceDTO : sysRoleResourceDTOList) {
            if (!resourceIds.contains(sysRoleResourceDTO.getResourceId())) {
                sysRoleResourceService.deleteById(sysRoleResourceDTO.getId());
            }
        }
        List<Long> existResourceIds = sysRoleResourceDTOList.stream().map(SysRoleResourceDTO::getResourceId).collect(Collectors.toList());
        for (Long resourceId : resourceIds) {
            if (!existResourceIds.contains(resourceId)) {
                sysRoleResourceService.insertBy(id, resourceId);
            }
        }
    }
}