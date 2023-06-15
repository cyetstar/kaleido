package cc.onelooker.kaleido.service.business.impl;

import cc.onelooker.kaleido.convert.business.MovieConvert;
import cc.onelooker.kaleido.dto.business.MovieDTO;
import cc.onelooker.kaleido.entity.business.MovieDO;
import cc.onelooker.kaleido.service.ExBaseServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.zjjcnt.common.core.service.impl.AbstractBaseServiceImpl;
import cc.onelooker.kaleido.service.business.MovieUniqueidService;
import cc.onelooker.kaleido.entity.business.MovieUniqueidDO;
import cc.onelooker.kaleido.dto.business.MovieUniqueidDTO;
import cc.onelooker.kaleido.convert.business.MovieUniqueidConvert;
import cc.onelooker.kaleido.mapper.business.MovieUniqueidMapper;

import org.apache.commons.lang3.StringUtils;

import java.util.*;

import java.lang.Long;
import java.lang.String;

import com.zjjcnt.common.core.annotation.Dict;
import org.springframework.transaction.annotation.Transactional;

/**
 * 电影唯一标识ServiceImpl
 *
 * @author cyetstar
 * @date 2023-04-18 23:04:56
 */
@Service
public class MovieUniqueidServiceImpl extends ExBaseServiceImpl<MovieUniqueidMapper, MovieUniqueidDO, MovieUniqueidDTO> implements MovieUniqueidService {

    MovieUniqueidConvert convert = MovieUniqueidConvert.INSTANCE;

    @Override
    protected Wrapper<MovieUniqueidDO> genQueryWrapper(MovieUniqueidDTO dto) {
        LambdaQueryWrapper<MovieUniqueidDO> query = new LambdaQueryWrapper<>();
        query.eq(Objects.nonNull(dto.getMovieId()), MovieUniqueidDO::getMovieId, dto.getMovieId());
        query.eq(StringUtils.isNotEmpty(dto.getUid()), MovieUniqueidDO::getUid, dto.getUid());
        query.eq(StringUtils.isNotEmpty(dto.getSfmr()), MovieUniqueidDO::getSfmr, dto.getSfmr());
        query.eq(StringUtils.isNotEmpty(dto.getBslx()), MovieUniqueidDO::getBslx, dto.getBslx());
        return query;
    }

    @Override
    public MovieUniqueidDTO convertToDTO(MovieUniqueidDO movieUniqueidDO) {
        return convert.convert(movieUniqueidDO);
    }

    @Override
    public MovieUniqueidDO convertToDO(MovieUniqueidDTO movieUniqueidDTO) {
        return convert.convertToDO(movieUniqueidDTO);
    }

    @Override
    public MovieDTO findByUidAndBslx(String uid, String bslx) {
        MovieDO movieDO = baseMapper.findByUidAndBslx(uid, bslx);
        return MovieConvert.INSTANCE.convert(movieDO);
    }

    @Override
    @Transactional
    public boolean deleteByMovieId(Long movieId) {
        MovieUniqueidDTO param = new MovieUniqueidDTO();
        param.setMovieId(movieId);
        return delete(param);
    }

    @Override
    public List<MovieUniqueidDTO> listByMovieId(Long movieId) {
        MovieUniqueidDTO param = new MovieUniqueidDTO();
        param.setMovieId(movieId);
        return list(param);
    }
}