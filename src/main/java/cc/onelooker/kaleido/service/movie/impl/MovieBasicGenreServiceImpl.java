package cc.onelooker.kaleido.service.movie.impl;

import cc.onelooker.kaleido.convert.movie.MovieBasicGenreConvert;
import cc.onelooker.kaleido.dto.movie.MovieBasicGenreDTO;
import cc.onelooker.kaleido.entity.movie.MovieBasicGenreDO;
import cc.onelooker.kaleido.mapper.movie.MovieBasicGenreMapper;
import cc.onelooker.kaleido.service.movie.MovieBasicGenreService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zjjcnt.common.core.service.impl.AbstractBaseServiceImpl;
import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * 电影类型关联表ServiceImpl
 *
 * @author cyetstar
 * @date 2023-11-26 01:19:02
 */
@Service
public class MovieBasicGenreServiceImpl extends AbstractBaseServiceImpl<MovieBasicGenreMapper, MovieBasicGenreDO, MovieBasicGenreDTO> implements MovieBasicGenreService {

    MovieBasicGenreConvert convert = MovieBasicGenreConvert.INSTANCE;

    @Override
    protected Wrapper<MovieBasicGenreDO> genQueryWrapper(MovieBasicGenreDTO dto) {
        LambdaQueryWrapper<MovieBasicGenreDO> query = new LambdaQueryWrapper<>();
        query.eq(Objects.nonNull(dto.getMovieId()), MovieBasicGenreDO::getMovieId, dto.getMovieId());
        query.eq(Objects.nonNull(dto.getGenreId()), MovieBasicGenreDO::getGenreId, dto.getGenreId());
        return query;
    }

    @Override
    public MovieBasicGenreDTO convertToDTO(MovieBasicGenreDO movieBasicGenreDO) {
        return convert.convert(movieBasicGenreDO);
    }

    @Override
    public MovieBasicGenreDO convertToDO(MovieBasicGenreDTO movieBasicGenreDTO) {
        return convert.convertToDO(movieBasicGenreDTO);
    }

    @Override
    public MovieBasicGenreDTO findByMovieIdAndGenreId(Long movieId, Long countryId) {
        Validate.notNull(movieId);
        Validate.notNull(countryId);
        MovieBasicGenreDTO param = new MovieBasicGenreDTO();
        param.setMovieId(movieId);
        param.setGenreId(countryId);
        return find(param);
    }

    @Override
    public MovieBasicGenreDTO insertByMovieIdAndGenreId(Long movieId, Long countryId) {
        MovieBasicGenreDTO movieBasicGenreDTO = new MovieBasicGenreDTO();
        movieBasicGenreDTO.setMovieId(movieId);
        movieBasicGenreDTO.setGenreId(countryId);
        return insert(movieBasicGenreDTO);
    }

    @Override
    public List<MovieBasicGenreDTO> listByMovieId(Long movieId) {
        Validate.notNull(movieId);
        MovieBasicGenreDTO param = new MovieBasicGenreDTO();
        param.setMovieId(movieId);
        return list(param);
    }

    @Override
    public List<MovieBasicGenreDTO> listByGenreId(Long genreId) {
        Validate.notNull(genreId);
        MovieBasicGenreDTO param = new MovieBasicGenreDTO();
        param.setGenreId(genreId);
        return list(param);
    }
}