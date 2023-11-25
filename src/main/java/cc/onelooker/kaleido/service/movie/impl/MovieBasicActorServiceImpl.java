package cc.onelooker.kaleido.service.movie.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.zjjcnt.common.core.service.impl.AbstractBaseServiceImpl;
import cc.onelooker.kaleido.service.movie.MovieBasicActorService;
import cc.onelooker.kaleido.entity.movie.MovieBasicActorDO;
import cc.onelooker.kaleido.dto.movie.MovieBasicActorDTO;
import cc.onelooker.kaleido.convert.movie.MovieBasicActorConvert;
import cc.onelooker.kaleido.mapper.movie.MovieBasicActorMapper;


import org.apache.commons.lang3.StringUtils;
import java.util.*;

/**
 * 电影演职员关联表ServiceImpl
 *
 * @author cyetstar
 * @date 2023-11-26 01:19:02
 */
@Service
public class MovieBasicActorServiceImpl extends AbstractBaseServiceImpl<MovieBasicActorMapper, MovieBasicActorDO, MovieBasicActorDTO> implements MovieBasicActorService {

    MovieBasicActorConvert convert = MovieBasicActorConvert.INSTANCE;

    @Override
    protected Wrapper<MovieBasicActorDO> genQueryWrapper(MovieBasicActorDTO dto) {
        LambdaQueryWrapper<MovieBasicActorDO> query = new LambdaQueryWrapper<>();
        query.eq(Objects.nonNull(dto.getMovieId()), MovieBasicActorDO::getMovieId, dto.getMovieId());
        query.eq(Objects.nonNull(dto.getActorId()), MovieBasicActorDO::getActorId, dto.getActorId());
        query.eq(StringUtils.isNotEmpty(dto.getJs()), MovieBasicActorDO::getJs, dto.getJs());
        return query;
    }

    @Override
    public MovieBasicActorDTO convertToDTO(MovieBasicActorDO movieBasicActorDO) {
        return convert.convert(movieBasicActorDO);
    }

    @Override
    public MovieBasicActorDO convertToDO(MovieBasicActorDTO movieBasicActorDTO) {
        return convert.convertToDO(movieBasicActorDTO);
    }
}