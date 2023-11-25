package cc.onelooker.kaleido.service.movie.impl;

import cc.onelooker.kaleido.convert.movie.MovieBasicCountryConvert;
import cc.onelooker.kaleido.dto.movie.MovieBasicCountryDTO;
import cc.onelooker.kaleido.entity.movie.MovieBasicCountryDO;
import cc.onelooker.kaleido.mapper.movie.MovieBasicCountryMapper;
import cc.onelooker.kaleido.service.movie.MovieBasicCountryService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zjjcnt.common.core.service.impl.AbstractBaseServiceImpl;
import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * 电影国家地区关联表ServiceImpl
 *
 * @author cyetstar
 * @date 2023-11-26 01:19:02
 */
@Service
public class MovieBasicCountryServiceImpl extends AbstractBaseServiceImpl<MovieBasicCountryMapper, MovieBasicCountryDO, MovieBasicCountryDTO> implements MovieBasicCountryService {

    MovieBasicCountryConvert convert = MovieBasicCountryConvert.INSTANCE;

    @Override
    protected Wrapper<MovieBasicCountryDO> genQueryWrapper(MovieBasicCountryDTO dto) {
        LambdaQueryWrapper<MovieBasicCountryDO> query = new LambdaQueryWrapper<>();
        query.eq(Objects.nonNull(dto.getMovieId()), MovieBasicCountryDO::getMovieId, dto.getMovieId());
        query.eq(Objects.nonNull(dto.getCountryId()), MovieBasicCountryDO::getCountryId, dto.getCountryId());
        return query;
    }

    @Override
    public MovieBasicCountryDTO convertToDTO(MovieBasicCountryDO movieBasicCountryDO) {
        return convert.convert(movieBasicCountryDO);
    }

    @Override
    public MovieBasicCountryDO convertToDO(MovieBasicCountryDTO movieBasicCountryDTO) {
        return convert.convertToDO(movieBasicCountryDTO);
    }

    @Override
    public MovieBasicCountryDTO findByMovieIdAndCountryId(Long movieId, Long countryId) {
        Validate.notNull(movieId);
        Validate.notNull(countryId);
        MovieBasicCountryDTO param = new MovieBasicCountryDTO();
        param.setMovieId(movieId);
        param.setCountryId(countryId);
        return find(param);
    }

    @Override
    public MovieBasicCountryDTO insertByMovieIdAndCountryId(Long movieId, Long countryId) {
        MovieBasicCountryDTO movieBasicCountryDTO = new MovieBasicCountryDTO();
        movieBasicCountryDTO.setMovieId(movieId);
        movieBasicCountryDTO.setCountryId(countryId);
        return insert(movieBasicCountryDTO);
    }
}