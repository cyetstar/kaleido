package cc.onelooker.kaleido.service.movie.impl;

import cc.onelooker.kaleido.dto.movie.MovieBasicActorDTO;
import cc.onelooker.kaleido.service.movie.MovieBasicActorService;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.stream.Collectors;

/**
 * 演职员ServiceImpl
 *
 * @author cyetstar
 * @date 2023-11-26 01:19:02
 */
@Service
public class MovieActorServiceImpl extends AbstractBaseServiceImpl<MovieActorMapper, MovieActorDO, MovieActorDTO> implements MovieActorService {

    MovieActorConvert convert = MovieActorConvert.INSTANCE;

    @Autowired
    private MovieBasicActorService movieBasicActorService;

    @Override
    protected Wrapper<MovieActorDO> genQueryWrapper(MovieActorDTO dto) {
        LambdaQueryWrapper<MovieActorDO> query = new LambdaQueryWrapper<>();
        query.eq(StringUtils.isNotEmpty(dto.getDoubanId()), MovieActorDO::getDoubanId, dto.getDoubanId());
        query.eq(StringUtils.isNotEmpty(dto.getName()), MovieActorDO::getName, dto.getName());
        query.eq(StringUtils.isNotEmpty(dto.getOriginalName()), MovieActorDO::getOriginalName, dto.getOriginalName());
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
    public List<MovieActorDTO> listByMovieId(Long movieId) {
        List<MovieBasicActorDTO> movieBasicActorDTOList = movieBasicActorService.listByMovieId(movieId);
        return movieBasicActorDTOList.stream().map(s -> {
            MovieActorDTO movieActorDTO = findById(s.getActorId());
            movieActorDTO.setRole(s.getRole());
            return movieActorDTO;
        }).collect(Collectors.toList());
    }
}