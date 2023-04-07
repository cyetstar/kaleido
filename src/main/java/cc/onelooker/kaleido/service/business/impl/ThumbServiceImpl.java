package cc.onelooker.kaleido.service.business.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.zjjcnt.common.core.service.impl.AbstractBaseServiceImpl;
import cc.onelooker.kaleido.service.business.ThumbService;
import cc.onelooker.kaleido.entity.business.ThumbDO;
import cc.onelooker.kaleido.dto.business.ThumbDTO;
import cc.onelooker.kaleido.convert.business.ThumbConvert;
import cc.onelooker.kaleido.mapper.business.ThumbMapper;

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
public class ThumbServiceImpl extends AbstractBaseServiceImpl<ThumbMapper, ThumbDO, ThumbDTO> implements ThumbService {

    ThumbConvert convert = ThumbConvert.INSTANCE;

    @Override
    protected Wrapper<ThumbDO> genQueryWrapper(ThumbDTO dto) {
        LambdaQueryWrapper<ThumbDO> query = new LambdaQueryWrapper<>();
        query.eq(Objects.nonNull(dto.getAspect()), ThumbDO::getAspect, dto.getAspect());
        query.eq(Objects.nonNull(dto.getPreview()), ThumbDO::getPreview, dto.getPreview());
        query.eq(Objects.nonNull(dto.getValue()), ThumbDO::getValue, dto.getValue());
        query.eq(Objects.nonNull(dto.getMovieId()), ThumbDO::getMovieId, dto.getMovieId());
        return query;
    }

    @Override
    public ThumbDTO convertToDTO(ThumbDO thumbDO) {
        return convert.convert(thumbDO);
    }

    @Override
    public ThumbDO convertToDO(ThumbDTO thumbDTO) {
        return convert.convertToDO(thumbDTO);
    }
}