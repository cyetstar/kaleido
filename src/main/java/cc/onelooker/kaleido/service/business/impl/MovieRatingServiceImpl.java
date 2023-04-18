package cc.onelooker.kaleido.service.business.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.zjjcnt.common.core.service.impl.AbstractBaseServiceImpl;
import cc.onelooker.kaleido.service.business.MovieRatingService;
import cc.onelooker.kaleido.entity.business.MovieRatingDO;
import cc.onelooker.kaleido.dto.business.MovieRatingDTO;
import cc.onelooker.kaleido.convert.business.MovieRatingConvert;
import cc.onelooker.kaleido.mapper.business.MovieRatingMapper;

import org.apache.commons.lang3.StringUtils;
import java.util.*;

import java.lang.Long;
import java.math.BigDecimal;
import java.lang.Integer;
import com.zjjcnt.common.core.annotation.Dict;
import java.lang.String;

/**
 * 电影评分ServiceImpl
 *
 * @author cyetstar
 * @date 2023-04-18 23:04:56
 */
@Service
public class MovieRatingServiceImpl extends AbstractBaseServiceImpl<MovieRatingMapper, MovieRatingDO, MovieRatingDTO> implements MovieRatingService {

    MovieRatingConvert convert = MovieRatingConvert.INSTANCE;

    @Override
    protected Wrapper<MovieRatingDO> genQueryWrapper(MovieRatingDTO dto) {
        LambdaQueryWrapper<MovieRatingDO> query = new LambdaQueryWrapper<>();
        query.eq(Objects.nonNull(dto.getMovieId()), MovieRatingDO::getMovieId, dto.getMovieId());
        query.eq(Objects.nonNull(dto.getPjf()), MovieRatingDO::getPjf, dto.getPjf());
        query.eq(Objects.nonNull(dto.getTps()), MovieRatingDO::getTps, dto.getTps());
        query.eq(Objects.nonNull(dto.getZdz()), MovieRatingDO::getZdz, dto.getZdz());
        query.eq(StringUtils.isNotEmpty(dto.getSfmr()), MovieRatingDO::getSfmr, dto.getSfmr());
        query.eq(StringUtils.isNotEmpty(dto.getPflx()), MovieRatingDO::getPflx, dto.getPflx());
        return query;
    }

    @Override
    public MovieRatingDTO convertToDTO(MovieRatingDO movieRatingDO) {
        return convert.convert(movieRatingDO);
    }

    @Override
    public MovieRatingDO convertToDO(MovieRatingDTO movieRatingDTO) {
        return convert.convertToDO(movieRatingDTO);
    }
}