package cc.onelooker.kaleido.service.movie.impl;

import cc.onelooker.kaleido.enums.ActorRole;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.zjjcnt.common.core.service.impl.AbstractBaseServiceImpl;
import cc.onelooker.kaleido.service.movie.MovieActorLinkService;
import cc.onelooker.kaleido.entity.movie.MovieActorLinkDO;
import cc.onelooker.kaleido.dto.movie.MovieActorLinkDTO;
import cc.onelooker.kaleido.convert.movie.MovieActorLinkConvert;
import cc.onelooker.kaleido.mapper.movie.MovieActorLinkMapper;

import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import java.lang.Long;

/**
 * 电影演职员关联表ServiceImpl
 *
 * @author cyetstar
 * @date 2023-04-18 23:04:56
 */
@Service
public class MovieActorLinkServiceImpl extends AbstractBaseServiceImpl<MovieActorLinkMapper, MovieActorLinkDO, MovieActorLinkDTO> implements MovieActorLinkService {

    MovieActorLinkConvert convert = MovieActorLinkConvert.INSTANCE;

    @Override
    protected Wrapper<MovieActorLinkDO> genQueryWrapper(MovieActorLinkDTO dto) {
        LambdaQueryWrapper<MovieActorLinkDO> query = new LambdaQueryWrapper<>();
        query.eq(Objects.nonNull(dto.getMovieId()), MovieActorLinkDO::getMovieId, dto.getMovieId());
        query.eq(Objects.nonNull(dto.getActorId()), MovieActorLinkDO::getActorId, dto.getActorId());
        query.eq(StringUtils.isNotEmpty(dto.getJs()), MovieActorLinkDO::getJs, dto.getJs());
        return query;
    }

    @Override
    public MovieActorLinkDTO convertToDTO(MovieActorLinkDO movieActorLinkDO) {
        return convert.convert(movieActorLinkDO);
    }

    @Override
    public MovieActorLinkDO convertToDO(MovieActorLinkDTO movieActorLinkDTO) {
        return convert.convertToDO(movieActorLinkDTO);
    }

    @Override
    @Transactional
    public MovieActorLinkDTO insert(Long movieId, Long movieActorId, ActorRole actorRole) {
        MovieActorLinkDTO dto = new MovieActorLinkDTO();
        dto.setMovieId(movieId);
        dto.setActorId(movieActorId);
        dto.setJs(actorRole.name());
        return insert(dto);
    }

    @Override
    @Transactional
    public boolean deleteByMovieId(Long movieId) {
        MovieActorLinkDTO param = new MovieActorLinkDTO();
        param.setMovieId(movieId);
        return delete(param);
    }
}