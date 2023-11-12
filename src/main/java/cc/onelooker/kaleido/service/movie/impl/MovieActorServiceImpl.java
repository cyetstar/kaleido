package cc.onelooker.kaleido.service.movie.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.zjjcnt.common.core.service.impl.AbstractBaseServiceImpl;
import cc.onelooker.kaleido.service.movie.MovieActorService;
import cc.onelooker.kaleido.entity.movie.MovieActorDO;
import cc.onelooker.kaleido.dto.movie.MovieActorDTO;
import cc.onelooker.kaleido.convert.movie.MovieActorConvert;
import cc.onelooker.kaleido.mapper.movie.MovieActorMapper;

import org.apache.commons.lang3.StringUtils;

import java.util.*;

import java.lang.Long;

import java.lang.String;

/**
 * 演职员ServiceImpl
 *
 * @author cyetstar
 * @date 2023-04-18 23:04:56
 */
@Service
public class MovieActorServiceImpl extends AbstractBaseServiceImpl<MovieActorMapper, MovieActorDO, MovieActorDTO> implements MovieActorService {

    MovieActorConvert convert = MovieActorConvert.INSTANCE;

    @Override
    protected Wrapper<MovieActorDO> genQueryWrapper(MovieActorDTO dto) {
        LambdaQueryWrapper<MovieActorDO> query = new LambdaQueryWrapper<>();
        query.eq(StringUtils.isNotEmpty(dto.getXm()), MovieActorDO::getXm, dto.getXm());
        query.eq(StringUtils.isNotEmpty(dto.getBm()), MovieActorDO::getBm, dto.getBm());
        query.eq(StringUtils.isNotEmpty(dto.getDoubanId()), MovieActorDO::getDoubanId, dto.getDoubanId());
        if (StringUtils.isNotEmpty(dto.getKeyword())) {
            query.like(MovieActorDO::getXm, dto.getKeyword()).or().like(MovieActorDO::getBm, dto.getKeyword());
        }
        return query;
    }

    @Override
    public MovieActorDTO convertToDTO(MovieActorDO movieActorDO) {
        return convert.convert(movieActorDO);
    }

    @Override
    public MovieActorDO convertToDO(MovieActorDTO movieActorDTO) {
        return convert.convertToDO(movieActorDTO);
    }

    @Override
    public MovieActorDTO findByXm(String xm) {
        MovieActorDTO param = new MovieActorDTO();
        param.setXm(xm);
        return find(param);
    }

    @Override
    public List<MovieActorDTO> listByMovieIdAndJs(Long movieId, String js) {
        List<MovieActorDO> movieActorDOList = baseMapper.listByMovieIdAndJs(movieId, js);
        return convertToDTO(movieActorDOList);
    }
}