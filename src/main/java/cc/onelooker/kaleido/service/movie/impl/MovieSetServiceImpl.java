package cc.onelooker.kaleido.service.movie.impl;

import cc.onelooker.kaleido.service.KaleidoBaseServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import cc.onelooker.kaleido.service.movie.MovieSetService;
import cc.onelooker.kaleido.entity.movie.MovieSetDO;
import cc.onelooker.kaleido.dto.movie.MovieSetDTO;
import cc.onelooker.kaleido.convert.movie.MovieSetConvert;
import cc.onelooker.kaleido.mapper.movie.MovieSetMapper;

import org.apache.commons.lang3.StringUtils;

import java.util.*;

import java.lang.Long;
import java.lang.String;

/**
 * 电影集合ServiceImpl
 *
 * @author cyetstar
 * @date 2023-04-18 23:04:56
 */
@Service
public class MovieSetServiceImpl extends KaleidoBaseServiceImpl<MovieSetMapper, MovieSetDO, MovieSetDTO> implements MovieSetService {

    MovieSetConvert convert = MovieSetConvert.INSTANCE;

    @Override
    protected Wrapper<MovieSetDO> genQueryWrapper(MovieSetDTO dto) {
        LambdaQueryWrapper<MovieSetDO> query = new LambdaQueryWrapper<>();
        query.eq(StringUtils.isNotEmpty(dto.getMc()), MovieSetDO::getMc, dto.getMc());
        query.eq(StringUtils.isNotEmpty(dto.getJhsm()), MovieSetDO::getJhsm, dto.getJhsm());
        query.eq(StringUtils.isNotEmpty(dto.getCjsj()), MovieSetDO::getCjsj, dto.getCjsj());
        query.eq(StringUtils.isNotEmpty(dto.getXgsj()), MovieSetDO::getXgsj, dto.getXgsj());
        return query;
    }

    @Override
    public MovieSetDTO convertToDTO(MovieSetDO movieSetDO) {
        return convert.convert(movieSetDO);
    }

    @Override
    public MovieSetDO convertToDO(MovieSetDTO movieSetDTO) {
        return convert.convertToDO(movieSetDTO);
    }

    @Override
    public MovieSetDTO findByMc(String mc) {
        MovieSetDTO param = new MovieSetDTO();
        param.setMc(mc);
        return find(param);
    }

    @Override
    public List<MovieSetDTO> listByMovieId(Long movieId) {
        List<MovieSetDO> movieSetDOList = baseMapper.listByMovieId(movieId);
        return convertToDTO(movieSetDOList);
    }
}