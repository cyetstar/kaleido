package cc.onelooker.kaleido.service.business.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.zjjcnt.common.core.service.impl.AbstractBaseServiceImpl;
import cc.onelooker.kaleido.service.business.StudioService;
import cc.onelooker.kaleido.entity.business.StudioDO;
import cc.onelooker.kaleido.dto.business.StudioDTO;
import cc.onelooker.kaleido.convert.business.StudioConvert;
import cc.onelooker.kaleido.mapper.business.StudioMapper;

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
public class StudioServiceImpl extends AbstractBaseServiceImpl<StudioMapper, StudioDO, StudioDTO> implements StudioService {

    StudioConvert convert = StudioConvert.INSTANCE;

    @Override
    protected Wrapper<StudioDO> genQueryWrapper(StudioDTO dto) {
        LambdaQueryWrapper<StudioDO> query = new LambdaQueryWrapper<>();
        query.eq(Objects.nonNull(dto.getCategory()), StudioDO::getCategory, dto.getCategory());
        query.eq(Objects.nonNull(dto.getValue()), StudioDO::getValue, dto.getValue());
        return query;
    }

    @Override
    public StudioDTO convertToDTO(StudioDO studioDO) {
        return convert.convert(studioDO);
    }

    @Override
    public StudioDO convertToDO(StudioDTO studioDTO) {
        return convert.convertToDO(studioDTO);
    }
}