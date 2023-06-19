package cc.onelooker.kaleido.service.business.impl;

import cc.onelooker.kaleido.enums.ActorRole;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.zjjcnt.common.core.service.impl.AbstractBaseServiceImpl;
import cc.onelooker.kaleido.service.business.MovieActorService;
import cc.onelooker.kaleido.entity.business.MovieActorDO;
import cc.onelooker.kaleido.dto.business.MovieActorDTO;
import cc.onelooker.kaleido.convert.business.MovieActorConvert;
import cc.onelooker.kaleido.mapper.business.MovieActorMapper;

import org.apache.commons.lang3.StringUtils;

import java.util.*;

import java.lang.Long;

import com.zjjcnt.common.core.annotation.Crypto;

import java.lang.String;

import com.zjjcnt.common.core.annotation.StringDateTimeFormat;

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