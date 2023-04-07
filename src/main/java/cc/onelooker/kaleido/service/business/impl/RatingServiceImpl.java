package cc.onelooker.kaleido.service.business.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.zjjcnt.common.core.service.impl.AbstractBaseServiceImpl;
import cc.onelooker.kaleido.service.business.RatingService;
import cc.onelooker.kaleido.entity.business.RatingDO;
import cc.onelooker.kaleido.dto.business.RatingDTO;
import cc.onelooker.kaleido.convert.business.RatingConvert;
import cc.onelooker.kaleido.mapper.business.RatingMapper;

import org.apache.commons.lang3.StringUtils;
import java.util.*;

import java.lang.Long;
import java.lang.Double;
import java.lang.Boolean;
import java.lang.Integer;
import java.lang.String;

/**
 * ServiceImpl
 *
 * @author cyetstar
 * @date 2023-04-06 13:32:12
 */
@Service
public class RatingServiceImpl extends AbstractBaseServiceImpl<RatingMapper, RatingDO, RatingDTO> implements RatingService {

    RatingConvert convert = RatingConvert.INSTANCE;

    @Override
    protected Wrapper<RatingDO> genQueryWrapper(RatingDTO dto) {
        LambdaQueryWrapper<RatingDO> query = new LambdaQueryWrapper<>();
        query.eq(Objects.nonNull(dto.getAverage()), RatingDO::getAverage, dto.getAverage());
        query.eq(Objects.nonNull(dto.getDef()), RatingDO::getDef, dto.getDef());
        query.eq(Objects.nonNull(dto.getMax()), RatingDO::getMax, dto.getMax());
        query.eq(Objects.nonNull(dto.getType()), RatingDO::getType, dto.getType());
        query.eq(Objects.nonNull(dto.getVotes()), RatingDO::getVotes, dto.getVotes());
        query.eq(Objects.nonNull(dto.getMovieId()), RatingDO::getMovieId, dto.getMovieId());
        return query;
    }

    @Override
    public RatingDTO convertToDTO(RatingDO ratingDO) {
        return convert.convert(ratingDO);
    }

    @Override
    public RatingDO convertToDO(RatingDTO ratingDTO) {
        return convert.convertToDO(ratingDTO);
    }
}