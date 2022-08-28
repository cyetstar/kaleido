package cc.onelooker.kaleido.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import org.springframework.stereotype.Service;
import com.zjjcnt.common.core.service.impl.AbstractBaseServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import cc.onelooker.kaleido.service.SysConfigService;
import cc.onelooker.kaleido.entity.SysConfigDO;
import cc.onelooker.kaleido.dto.SysConfigDTO;
import cc.onelooker.kaleido.convert.SysConfigConvert;
import cc.onelooker.kaleido.mapper.SysConfigMapper;
import org.apache.commons.lang3.StringUtils;
import java.util.*;

/**
 * 系统配置表ServiceImpl
 *
 * @author xiadawei
 * @date 2022-04-26 00:41:00
 */
@Service
public class SysConfigServiceImpl extends AbstractBaseServiceImpl<SysConfigMapper, SysConfigDO, SysConfigDTO> implements SysConfigService {

    SysConfigConvert convert = SysConfigConvert.INSTANCE;

    @Override
    protected Wrapper<SysConfigDO> genQueryWrapper(SysConfigDTO sysConfigDTO) {
        LambdaQueryWrapper<SysConfigDO> query = new LambdaQueryWrapper<>();
        query.eq(Objects.nonNull(sysConfigDTO.getId()), SysConfigDO::getId, sysConfigDTO.getId());
        query.eq(StringUtils.isNotEmpty(sysConfigDTO.getConfigName()), SysConfigDO::getConfigName, sysConfigDTO.getConfigName());
        query.eq(StringUtils.isNotEmpty(sysConfigDTO.getConfigKey()), SysConfigDO::getConfigKey, sysConfigDTO.getConfigKey());
        query.eq(StringUtils.isNotEmpty(sysConfigDTO.getConfigValue()), SysConfigDO::getConfigValue, sysConfigDTO.getConfigValue());
        query.eq(Objects.nonNull(sysConfigDTO.getIsDeleted()), SysConfigDO::getIsDeleted, sysConfigDTO.getIsDeleted());
        query.eq(Objects.nonNull(sysConfigDTO.getCreateTime()), SysConfigDO::getCreateTime, sysConfigDTO.getCreateTime());
        query.eq(Objects.nonNull(sysConfigDTO.getUpdateTime()), SysConfigDO::getUpdateTime, sysConfigDTO.getUpdateTime());
        query.eq(StringUtils.isNotEmpty(sysConfigDTO.getCreatedBy()), SysConfigDO::getCreatedBy, sysConfigDTO.getCreatedBy());
        query.eq(StringUtils.isNotEmpty(sysConfigDTO.getUpdatedBy()), SysConfigDO::getUpdatedBy, sysConfigDTO.getUpdatedBy());
        return query;
    }

    @Override
    public SysConfigDTO convertToDTO(SysConfigDO sysConfigDO) {
        return convert.convert(sysConfigDO);
    }

    @Override
    public SysConfigDO convertToDO(SysConfigDTO sysConfigDTO) {
        return convert.convertToDO(sysConfigDTO);
    }
}