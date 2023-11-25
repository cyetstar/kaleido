package cc.onelooker.kaleido.service.movie.impl;

import cc.onelooker.kaleido.dto.movie.MovieGenreDTO;
import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.zjjcnt.common.core.service.impl.AbstractBaseServiceImpl;
import cc.onelooker.kaleido.service.movie.MovieGenreService;
import cc.onelooker.kaleido.entity.movie.MovieGenreDO;
import cc.onelooker.kaleido.dto.movie.MovieGenreDTO;
import cc.onelooker.kaleido.convert.movie.MovieGenreConvert;
import cc.onelooker.kaleido.mapper.movie.MovieGenreMapper;


import org.apache.commons.lang3.StringUtils;
import java.util.*;

/**
 * 电影类型ServiceImpl
 *
 * @author cyetstar
 * @date 2023-11-26 01:19:02
 */
@Service
public class MovieGenreServiceImpl extends AbstractBaseServiceImpl<MovieGenreMapper, MovieGenreDO, MovieGenreDTO> implements MovieGenreService {

    MovieGenreConvert convert = MovieGenreConvert.INSTANCE;

    @Override
    protected Wrapper<MovieGenreDO> genQueryWrapper(MovieGenreDTO dto) {
        LambdaQueryWrapper<MovieGenreDO> query = new LambdaQueryWrapper<>();
        query.eq(StringUtils.isNotEmpty(dto.getTag()), MovieGenreDO::getTag, dto.getTag());
        return query;
    }

    @Override
    public MovieGenreDTO convertToDTO(MovieGenreDO movieGenreDO) {
        return convert.convert(movieGenreDO);
    }

    @Override
    public MovieGenreDO convertToDO(MovieGenreDTO movieGenreDTO) {
        return convert.convertToDO(movieGenreDTO);
    }

    @Override
    public MovieGenreDTO findByTag(String tag) {
        Validate.notNull(tag);
        MovieGenreDTO param = new MovieGenreDTO();
        param.setTag(tag);
        return find(param);
    }

    @Override
    public MovieGenreDTO insertByTag(String tag) {
        MovieGenreDTO movieGenreDTO = new MovieGenreDTO();
        movieGenreDTO.setTag(tag);
        return insert(movieGenreDTO);
    }

}