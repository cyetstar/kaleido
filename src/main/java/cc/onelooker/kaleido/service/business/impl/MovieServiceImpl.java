package cc.onelooker.kaleido.service.business.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.zjjcnt.common.core.service.impl.AbstractBaseServiceImpl;
import cc.onelooker.kaleido.service.business.MovieService;
import cc.onelooker.kaleido.entity.business.MovieDO;
import cc.onelooker.kaleido.dto.business.MovieDTO;
import cc.onelooker.kaleido.convert.business.MovieConvert;
import cc.onelooker.kaleido.mapper.business.MovieMapper;

import org.apache.commons.lang3.StringUtils;
import java.util.*;

import java.lang.Long;
import java.lang.String;
import java.lang.Integer;
import com.zjjcnt.common.core.annotation.StringDateFormat;
import com.zjjcnt.common.core.annotation.Dict;
import com.zjjcnt.common.core.annotation.StringDateTimeFormat;

/**
 * 电影ServiceImpl
 *
 * @author cyetstar
 * @date 2023-04-18 23:04:56
 */
@Service
public class MovieServiceImpl extends AbstractBaseServiceImpl<MovieMapper, MovieDO, MovieDTO> implements MovieService {

    MovieConvert convert = MovieConvert.INSTANCE;

    @Override
    protected Wrapper<MovieDO> genQueryWrapper(MovieDTO dto) {
        LambdaQueryWrapper<MovieDO> query = new LambdaQueryWrapper<>();
        query.eq(StringUtils.isNotEmpty(dto.getDymc()), MovieDO::getDymc, dto.getDymc());
        query.eq(StringUtils.isNotEmpty(dto.getYpm()), MovieDO::getYpm, dto.getYpm());
        query.eq(Objects.nonNull(dto.getYhpf()), MovieDO::getYhpf, dto.getYhpf());
        query.eq(Objects.nonNull(dto.getDyjj()), MovieDO::getDyjj, dto.getDyjj());
        query.eq(StringUtils.isNotEmpty(dto.getDyby()), MovieDO::getDyby, dto.getDyby());
        query.eq(Objects.nonNull(dto.getYpsc()), MovieDO::getYpsc, dto.getYpsc());
        query.eq(StringUtils.isNotEmpty(dto.getDydj()), MovieDO::getDydj, dto.getDydj());
        query.eq(StringUtils.isNotEmpty(dto.getSyrq()), MovieDO::getSyrq, dto.getSyrq());
        query.eq(StringUtils.isNotEmpty(dto.getGwdz()), MovieDO::getGwdz, dto.getGwdz());
        query.eq(StringUtils.isNotEmpty(dto.getGkbz()), MovieDO::getGkbz, dto.getGkbz());
        query.eq(StringUtils.isNotEmpty(dto.getGksj()), MovieDO::getGksj, dto.getGksj());
        query.eq(StringUtils.isNotEmpty(dto.getScbz()), MovieDO::getScbz, dto.getScbz());
        query.eq(StringUtils.isNotEmpty(dto.getScsj()), MovieDO::getScsj, dto.getScsj());
        query.eq(StringUtils.isNotEmpty(dto.getPlexId()), MovieDO::getPlexId, dto.getPlexId());
        query.eq(StringUtils.isNotEmpty(dto.getCjsj()), MovieDO::getCjsj, dto.getCjsj());
        query.eq(StringUtils.isNotEmpty(dto.getXgsj()), MovieDO::getXgsj, dto.getXgsj());
        return query;
    }

    @Override
    public MovieDTO convertToDTO(MovieDO movieDO) {
        return convert.convert(movieDO);
    }

    @Override
    public MovieDO convertToDO(MovieDTO movieDTO) {
        return convert.convertToDO(movieDTO);
    }
}