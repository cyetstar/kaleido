package cc.onelooker.kaleido.service.business.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.zjjcnt.common.core.service.impl.AbstractBaseServiceImpl;
import cc.onelooker.kaleido.service.business.SetsService;
import cc.onelooker.kaleido.entity.business.SetsDO;
import cc.onelooker.kaleido.dto.business.SetsDTO;
import cc.onelooker.kaleido.convert.business.SetsConvert;
import cc.onelooker.kaleido.mapper.business.SetsMapper;

import org.apache.commons.lang3.StringUtils;
import java.util.*;

import java.lang.Long;
import java.lang.String;
import java.lang.Integer;

/**
 * ServiceImpl
 *
 * @author cyetstar
 * @date 2023-04-06 13:32:12
 */
@Service
public class SetsServiceImpl extends AbstractBaseServiceImpl<SetsMapper, SetsDO, SetsDTO> implements SetsService {

    SetsConvert convert = SetsConvert.INSTANCE;

    @Override
    protected Wrapper<SetsDO> genQueryWrapper(SetsDTO dto) {
        LambdaQueryWrapper<SetsDO> query = new LambdaQueryWrapper<>();
        query.eq(Objects.nonNull(dto.getCategory()), SetsDO::getCategory, dto.getCategory());
        query.eq(Objects.nonNull(dto.getDoubanId()), SetsDO::getDoubanId, dto.getDoubanId());
        query.eq(Objects.nonNull(dto.getDoubanResult()), SetsDO::getDoubanResult, dto.getDoubanResult());
        query.eq(Objects.nonNull(dto.getMovieSize()), SetsDO::getMovieSize, dto.getMovieSize());
        query.eq(Objects.nonNull(dto.getName()), SetsDO::getName, dto.getName());
        query.eq(Objects.nonNull(dto.getOverview()), SetsDO::getOverview, dto.getOverview());
        return query;
    }

    @Override
    public SetsDTO convertToDTO(SetsDO setsDO) {
        return convert.convert(setsDO);
    }

    @Override
    public SetsDO convertToDO(SetsDTO setsDTO) {
        return convert.convertToDO(setsDTO);
    }
}