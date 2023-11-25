package cc.onelooker.kaleido.service.movie.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.zjjcnt.common.core.service.impl.AbstractBaseServiceImpl;
import cc.onelooker.kaleido.service.movie.MovieTagService;
import cc.onelooker.kaleido.entity.movie.MovieTagDO;
import cc.onelooker.kaleido.dto.movie.MovieTagDTO;
import cc.onelooker.kaleido.convert.movie.MovieTagConvert;
import cc.onelooker.kaleido.mapper.movie.MovieTagMapper;


import org.apache.commons.lang3.StringUtils;
import java.util.*;

/**
 * 电影标签ServiceImpl
 *
 * @author cyetstar
 * @date 2023-11-26 01:19:02
 */
@Service
public class MovieTagServiceImpl extends AbstractBaseServiceImpl<MovieTagMapper, MovieTagDO, MovieTagDTO> implements MovieTagService {

    MovieTagConvert convert = MovieTagConvert.INSTANCE;

    @Override
    protected Wrapper<MovieTagDO> genQueryWrapper(MovieTagDTO dto) {
        LambdaQueryWrapper<MovieTagDO> query = new LambdaQueryWrapper<>();
        query.eq(Objects.nonNull(dto.getMovieId()), MovieTagDO::getMovieId, dto.getMovieId());
        query.eq(StringUtils.isNotEmpty(dto.getTag()), MovieTagDO::getTag, dto.getTag());
        return query;
    }

    @Override
    public MovieTagDTO convertToDTO(MovieTagDO movieTagDO) {
        return convert.convert(movieTagDO);
    }

    @Override
    public MovieTagDO convertToDO(MovieTagDTO movieTagDTO) {
        return convert.convertToDO(movieTagDTO);
    }
}