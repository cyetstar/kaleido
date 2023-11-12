package cc.onelooker.kaleido.service.movie.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.zjjcnt.common.core.service.impl.AbstractBaseServiceImpl;
import cc.onelooker.kaleido.service.movie.MovieRatingService;
import cc.onelooker.kaleido.entity.movie.MovieRatingDO;
import cc.onelooker.kaleido.dto.movie.MovieRatingDTO;
import cc.onelooker.kaleido.convert.movie.MovieRatingConvert;
import cc.onelooker.kaleido.mapper.movie.MovieRatingMapper;

import org.apache.commons.lang3.StringUtils;

import java.util.*;

import java.lang.Long;

import org.springframework.transaction.annotation.Transactional;

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
        query.eq(StringUtils.isNotEmpty(dto.getBslx()), MovieRatingDO::getBslx, dto.getBslx());
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

    @Override
    @Transactional
    public boolean deleteByMovieId(Long movieId) {
        MovieRatingDTO param = new MovieRatingDTO();
        param.setMovieId(movieId);
        return delete(param);
    }

    @Override
    public boolean deleteByMovieIdAndBslx(Long movieId, String bslx) {
        MovieRatingDTO param = new MovieRatingDTO();
        param.setMovieId(movieId);
        param.setBslx(bslx);
        return delete(param);
    }

    @Override
    public List<MovieRatingDTO> listByMovieId(Long movieId) {
        MovieRatingDTO param = new MovieRatingDTO();
        param.setMovieId(movieId);
        return list(param);
    }


}