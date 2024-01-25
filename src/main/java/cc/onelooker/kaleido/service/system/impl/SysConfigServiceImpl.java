package cc.onelooker.kaleido.service.system.impl;

import cc.onelooker.kaleido.convert.system.SysConfigConvert;
import cc.onelooker.kaleido.dto.system.SysConfigDTO;
import cc.onelooker.kaleido.entity.system.SysConfigDO;
import cc.onelooker.kaleido.mapper.system.SysConfigMapper;
import cc.onelooker.kaleido.service.system.SysConfigService;
import cc.onelooker.kaleido.utils.ConfigUtils;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zjjcnt.common.core.service.impl.AbstractBaseServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * 系统配置表ServiceImpl
 *
 * @author cyetstar
 * @date 2022-11-13 00:43:42
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

    @Override
    @Transactional
    public void save(List<SysConfigDTO> sysConfigDTOList) {
        for (SysConfigDTO sysConfigDTO : sysConfigDTOList) {
            save(sysConfigDTO);
        }
    }

    @Override
    @Transactional
    public void save(SysConfigDTO sysConfigDTO) {
        SysConfigDTO exist = findByConfigKey(sysConfigDTO.getConfigKey());
        if (exist == null) {
            sysConfigDTO.setIsDeleted(false);
            insert(sysConfigDTO);
        } else {
            exist.setConfigValue(sysConfigDTO.getConfigValue());
            update(exist);
        }
        ConfigUtils.setSysConfig(sysConfigDTO.getConfigKey(), sysConfigDTO.getConfigValue());
    }

    @Override
    public SysConfigDTO findByConfigKey(String configKey) {
        SysConfigDTO param = new SysConfigDTO();
        param.setConfigKey(configKey);
        return find(param);
    }
}