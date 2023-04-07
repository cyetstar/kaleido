package cc.onelooker.kaleido.service.system.impl;

import cc.onelooker.kaleido.service.ExBaseServiceImpl;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import cc.onelooker.kaleido.convert.system.SysMenuConvert;
import cc.onelooker.kaleido.dto.system.SysMenuDTO;
import cc.onelooker.kaleido.entity.system.SysMenuDO;
import cc.onelooker.kaleido.mapper.system.SysMenuMapper;
import cc.onelooker.kaleido.service.DictionaryBaseServiceImpl;
import cc.onelooker.kaleido.service.system.SysMenuService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * 菜单表ServiceImpl
 *
 * @author xiadawei
 * @date 2022-11-13 01:12:24
 */
@Service
public class SysMenuServiceImpl extends ExBaseServiceImpl<SysMenuMapper, SysMenuDO, SysMenuDTO> implements SysMenuService {

    SysMenuConvert convert = SysMenuConvert.INSTANCE;

    @Override
    protected Wrapper<SysMenuDO> genQueryWrapper(SysMenuDTO sysMenuDTO) {
        LambdaQueryWrapper<SysMenuDO> query = new LambdaQueryWrapper<>();
        query.eq(Objects.nonNull(sysMenuDTO.getId()), SysMenuDO::getId, sysMenuDTO.getId());
        query.eq(Objects.nonNull(sysMenuDTO.getParentId()), SysMenuDO::getParentId, sysMenuDTO.getParentId());
        query.eq(StringUtils.isNotEmpty(sysMenuDTO.getType()), SysMenuDO::getType, sysMenuDTO.getType());
        query.eq(StringUtils.isNotEmpty(sysMenuDTO.getPath()), SysMenuDO::getPath, sysMenuDTO.getPath());
        query.like(StringUtils.isNotEmpty(sysMenuDTO.getName()), SysMenuDO::getName, sysMenuDTO.getName());
        query.eq(StringUtils.isNotEmpty(sysMenuDTO.getEqName()), SysMenuDO::getName, sysMenuDTO.getEqName());
        query.like(StringUtils.isNotEmpty(sysMenuDTO.getTitle()), SysMenuDO::getTitle, sysMenuDTO.getTitle());
        query.eq(StringUtils.isNotEmpty(sysMenuDTO.getDescription()), SysMenuDO::getDescription, sysMenuDTO.getDescription());
        query.eq(StringUtils.isNotEmpty(sysMenuDTO.getRedirect()), SysMenuDO::getRedirect, sysMenuDTO.getRedirect());
        query.eq(StringUtils.isNotEmpty(sysMenuDTO.getComponent()), SysMenuDO::getComponent, sysMenuDTO.getComponent());
        query.eq(StringUtils.isNotEmpty(sysMenuDTO.getIcon()), SysMenuDO::getIcon, sysMenuDTO.getIcon());
        query.eq(StringUtils.isNotEmpty(sysMenuDTO.getMeta()), SysMenuDO::getMeta, sysMenuDTO.getMeta());
        query.eq(Objects.nonNull(sysMenuDTO.getIsShowRoot()), SysMenuDO::getIsShowRoot, sysMenuDTO.getIsShowRoot());
        query.eq(Objects.nonNull(sysMenuDTO.getIsHidden()), SysMenuDO::getIsHidden, sysMenuDTO.getIsHidden());
        query.eq(Objects.nonNull(sysMenuDTO.getOrderNum()), SysMenuDO::getOrderNum, sysMenuDTO.getOrderNum());
        query.eq(Objects.nonNull(sysMenuDTO.getSubCount()), SysMenuDO::getSubCount, sysMenuDTO.getSubCount());
        query.like(StringUtils.isNotEmpty(sysMenuDTO.getApp()), SysMenuDO::getApp, sysMenuDTO.getApp());
        query.eq(StringUtils.isNotEmpty(sysMenuDTO.getPermission()), SysMenuDO::getPermission, sysMenuDTO.getPermission());
        query.eq(Objects.nonNull(sysMenuDTO.getIsDeleted()), SysMenuDO::getIsDeleted, sysMenuDTO.getIsDeleted());
        query.eq(Objects.nonNull(sysMenuDTO.getCreateTime()), SysMenuDO::getCreateTime, sysMenuDTO.getCreateTime());
        query.eq(Objects.nonNull(sysMenuDTO.getUpdateTime()), SysMenuDO::getUpdateTime, sysMenuDTO.getUpdateTime());
        query.eq(StringUtils.isNotEmpty(sysMenuDTO.getCreatedBy()), SysMenuDO::getCreatedBy, sysMenuDTO.getCreatedBy());
        query.eq(StringUtils.isNotEmpty(sysMenuDTO.getUpdatedBy()), SysMenuDO::getUpdatedBy, sysMenuDTO.getUpdatedBy());
        return query;
    }

    @Override
    public SysMenuDTO convertToDTO(SysMenuDO sysMenuDO) {
        return convert.convert(sysMenuDO);
    }

    @Override
    public SysMenuDO convertToDO(SysMenuDTO sysMenuDTO) {
        return convert.convertToDO(sysMenuDTO);
    }

    @Override
    @Transactional
    public void init(List<SysMenuDTO> sysMenuDTOList) {
        saveInit(sysMenuDTOList, 0L);
    }

    private void saveInit(List<SysMenuDTO> sysMenuDTOList, Long parentId) {
        if (sysMenuDTOList == null) {
            return;
        }
        for (SysMenuDTO sysMenuDTO : sysMenuDTOList) {
            SysMenuDTO exist = findByName(sysMenuDTO.getName());
            if (exist == null) {
                sysMenuDTO.setParentId(parentId);
                sysMenuDTO = insert(sysMenuDTO);
            } else {
                exist.setParentId(parentId);
                exist.setPath(sysMenuDTO.getPath());
                exist.setPermission(sysMenuDTO.getPermission());
                update(exist);
            }
            saveInit(sysMenuDTO.getChildren(), exist != null ? exist.getId() : null);
        }
    }

    public SysMenuDTO findByName(String name) {
        SysMenuDTO param = new SysMenuDTO();
        param.setEqName(name);
        return find(param);
    }

    @Override
    public List<SysMenuDTO> listByUserId(Long userId) {
        List<SysMenuDO> sysMenuDOList = baseMapper.listByUserId(userId);
        return convertToDTO(sysMenuDOList);
    }

    @Override
    public List<SysMenuDTO> listByApp(String app) {
        Validate.notEmpty(app);
        SysMenuDTO param = new SysMenuDTO();
        param.setApp(app);
        return list(param);
    }
}