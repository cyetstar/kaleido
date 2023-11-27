package cc.onelooker.kaleido.service.movie.impl;

import cc.onelooker.kaleido.service.DictionaryBaseServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.zjjcnt.common.core.service.impl.AbstractBaseServiceImpl;
import cc.onelooker.kaleido.service.movie.MovieLanguageService;
import cc.onelooker.kaleido.entity.movie.MovieLanguageDO;
import cc.onelooker.kaleido.dto.movie.MovieLanguageDTO;
import cc.onelooker.kaleido.convert.movie.MovieLanguageConvert;
import cc.onelooker.kaleido.mapper.movie.MovieLanguageMapper;


import org.apache.commons.lang3.StringUtils;
import java.util.*;

/**
 * 语言ServiceImpl
 *
 * @author cyetstar
 * @date 2023-11-26 01:19:02
 */
@Service
public class MovieLanguageServiceImpl extends DictionaryBaseServiceImpl<MovieLanguageMapper, MovieLanguageDO, MovieLanguageDTO> implements MovieLanguageService {

    MovieLanguageConvert convert = MovieLanguageConvert.INSTANCE;

    @Override
    protected Wrapper<MovieLanguageDO> genQueryWrapper(MovieLanguageDTO dto) {
        LambdaQueryWrapper<MovieLanguageDO> query = new LambdaQueryWrapper<>();
        query.eq(StringUtils.isNotEmpty(dto.getTag()), MovieLanguageDO::getTag, dto.getTag());
        return query;
    }

    @Override
    public MovieLanguageDTO convertToDTO(MovieLanguageDO movieLanguageDO) {
        return convert.convert(movieLanguageDO);
    }

    @Override
    public MovieLanguageDO convertToDO(MovieLanguageDTO movieLanguageDTO) {
        return convert.convertToDO(movieLanguageDTO);
    }
}