package cc.onelooker.kaleido.service.movie.impl;

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
        query.eq(Objects.nonNull(dto.getMovieLanguageId()), MovieBasicLanguageDO::getMovieLanguageId, dto.getMovieLanguageId());
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
}