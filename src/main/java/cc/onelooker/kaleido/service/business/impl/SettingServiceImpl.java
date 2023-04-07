package cc.onelooker.kaleido.service.business.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.zjjcnt.common.core.service.impl.AbstractBaseServiceImpl;
import cc.onelooker.kaleido.service.business.SettingService;
import cc.onelooker.kaleido.entity.business.SettingDO;
import cc.onelooker.kaleido.dto.business.SettingDTO;
import cc.onelooker.kaleido.convert.business.SettingConvert;
import cc.onelooker.kaleido.mapper.business.SettingMapper;

import org.apache.commons.lang3.StringUtils;
import java.util.*;

import java.lang.Long;
import java.lang.String;

/**
 * ServiceImpl
 *
 * @author cyetstar
 * @date 2023-04-06 13:32:12
 */
@Service
public class SettingServiceImpl extends AbstractBaseServiceImpl<SettingMapper, SettingDO, SettingDTO> implements SettingService {

    SettingConvert convert = SettingConvert.INSTANCE;

    @Override
    protected Wrapper<SettingDO> genQueryWrapper(SettingDTO dto) {
        LambdaQueryWrapper<SettingDO> query = new LambdaQueryWrapper<>();
        query.eq(Objects.nonNull(dto.getName()), SettingDO::getName, dto.getName());
        query.eq(Objects.nonNull(dto.getValue()), SettingDO::getValue, dto.getValue());
        return query;
    }

    @Override
    public SettingDTO convertToDTO(SettingDO settingDO) {
        return convert.convert(settingDO);
    }

    @Override
    public SettingDO convertToDO(SettingDTO settingDTO) {
        return convert.convertToDO(settingDTO);
    }
}