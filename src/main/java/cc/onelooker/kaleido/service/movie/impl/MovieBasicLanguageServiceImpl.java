package cc.onelooker.kaleido.service.movie.impl;

import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.zjjcnt.common.core.service.impl.AbstractBaseServiceImpl;
import cc.onelooker.kaleido.service.movie.MovieBasicLanguageService;
import cc.onelooker.kaleido.entity.movie.MovieBasicLanguageDO;
import cc.onelooker.kaleido.dto.movie.MovieBasicLanguageDTO;
import cc.onelooker.kaleido.convert.movie.MovieBasicLanguageConvert;
import cc.onelooker.kaleido.mapper.movie.MovieBasicLanguageMapper;

import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * 电影语言关联表ServiceImpl
 *
 * @author cyetstar
 * @date 2023-11-26 01:19:02
 */
@Service
public class MovieBasicLanguageServiceImpl extends AbstractBaseServiceImpl<MovieBasicLanguageMapper, MovieBasicLanguageDO, MovieBasicLanguageDTO> implements MovieBasicLanguageService {

    MovieBasicLanguageConvert convert = MovieBasicLanguageConvert.INSTANCE;

    @Override
    protected Wrapper<MovieBasicLanguageDO> genQueryWrapper(MovieBasicLanguageDTO dto) {
        LambdaQueryWrapper<MovieBasicLanguageDO> query = new LambdaQueryWrapper<>();
        query.eq(Objects.nonNull(dto.getMovieId()), MovieBasicLanguageDO::getMovieId, dto.getMovieId());
        query.eq(Objects.nonNull(dto.getLanguageId()), MovieBasicLanguageDO::getLanguageId, dto.getLanguageId());
        return query;
    }

    @Override
    public MovieBasicLanguageDTO convertToDTO(MovieBasicLanguageDO movieBasicLanguageDO) {
        return convert.convert(movieBasicLanguageDO);
    }

    @Override
    public MovieBasicLanguageDO convertToDO(MovieBasicLanguageDTO movieBasicLanguageDTO) {
        return convert.convertToDO(movieBasicLanguageDTO);
    }

    @Override
    public MovieBasicLanguageDTO findByMovieIdAndLanguageId(Long movieId, Long languageId) {
        Validate.notNull(movieId);
        Validate.notNull(languageId);
        MovieBasicLanguageDTO param = new MovieBasicLanguageDTO();
        param.setMovieId(movieId);
        param.setLanguageId(languageId);
        return find(param);
    }

    @Override
    public MovieBasicLanguageDTO insertByMovieIdAndLanguageId(Long movieId, Long languageId) {
        MovieBasicLanguageDTO dto = new MovieBasicLanguageDTO();
        dto.setMovieId(movieId);
        dto.setLanguageId(languageId);
        return insert(dto);
    }

    @Override
    public List<MovieBasicLanguageDTO> listByMovieId(Long movieId) {
        Validate.notNull(movieId);
        MovieBasicLanguageDTO param = new MovieBasicLanguageDTO();
        param.setMovieId(movieId);
        return list(param);
    }

    @Override
    public boolean deleteByMovieId(Long movieId) {
        Validate.notNull(movieId);
        MovieBasicLanguageDTO param = new MovieBasicLanguageDTO();
        param.setMovieId(movieId);
        return delete(param);
    }
}