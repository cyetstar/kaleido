package cc.onelooker.kaleido.service.business.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.zjjcnt.common.core.service.impl.AbstractBaseServiceImpl;
import cc.onelooker.kaleido.service.business.MovieActorLinkService;
import cc.onelooker.kaleido.entity.business.MovieActorLinkDO;
import cc.onelooker.kaleido.dto.business.MovieActorLinkDTO;
import cc.onelooker.kaleido.convert.business.MovieActorLinkConvert;
import cc.onelooker.kaleido.mapper.business.MovieActorLinkMapper;

import org.apache.commons.lang3.StringUtils;
import java.util.*;

import java.lang.Long;
import java.lang.String;

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
}