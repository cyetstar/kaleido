package cc.onelooker.kaleido.service.movie.impl;

import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.zjjcnt.common.core.service.impl.AbstractBaseServiceImpl;
import cc.onelooker.kaleido.service.movie.MovieAkaService;
import cc.onelooker.kaleido.entity.movie.MovieAkaDO;
import cc.onelooker.kaleido.dto.movie.MovieAkaDTO;
import cc.onelooker.kaleido.convert.movie.MovieAkaConvert;
import cc.onelooker.kaleido.mapper.movie.MovieAkaMapper;

import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * 别名ServiceImpl
 *
 * @author cyetstar
 * @date 2023-11-26 01:19:02
 */
@Service
public class MovieAkaServiceImpl extends AbstractBaseServiceImpl<MovieAkaMapper, MovieAkaDO, MovieAkaDTO> implements MovieAkaService {

    MovieAkaConvert convert = MovieAkaConvert.INSTANCE;

    @Override
    protected Wrapper<MovieAkaDO> genQueryWrapper(MovieAkaDTO dto) {
        LambdaQueryWrapper<MovieAkaDO> query = new LambdaQueryWrapper<>();
        query.eq(Objects.nonNull(dto.getMovieId()), MovieAkaDO::getMovieId, dto.getMovieId());
        query.eq(StringUtils.isNotEmpty(dto.getTitle()), MovieAkaDO::getTitle, dto.getTitle());
        return query;
    }

    @Override
    public MovieAkaDTO convertToDTO(MovieAkaDO movieAkaDO) {
        return convert.convert(movieAkaDO);
    }

    @Override
    public MovieAkaDO convertToDO(MovieAkaDTO movieAkaDTO) {
        return convert.convertToDO(movieAkaDTO);
    }

    @Override
    public List<MovieAkaDTO> listByMovieId(Long movieId) {
        Validate.notNull(movieId);
        MovieAkaDTO param = new MovieAkaDTO();
        param.setMovieId(movieId);
        return list(param);
    }

    @Override
    public MovieAkaDTO findByTitleAndMovieId(String title, Long movieId) {
        Validate.notEmpty(title);
        Validate.notNull(movieId);
        MovieAkaDTO param = new MovieAkaDTO();
        param.setTitle(title);
        param.setMovieId(movieId);
        return find(param);
    }
}