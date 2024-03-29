package cc.onelooker.kaleido.service.movie.impl;

import cc.onelooker.kaleido.convert.movie.MovieLanguageConvert;
import cc.onelooker.kaleido.dto.movie.MovieLanguageDTO;
import cc.onelooker.kaleido.entity.movie.MovieLanguageDO;
import cc.onelooker.kaleido.mapper.movie.MovieLanguageMapper;
import cc.onelooker.kaleido.service.DictionaryBaseServiceImpl;
import cc.onelooker.kaleido.service.movie.MovieLanguageService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Service;

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

    @Override
    public MovieLanguageDTO findByTag(String tag) {
        Validate.notEmpty(tag);
        MovieLanguageDTO param = new MovieLanguageDTO();
        param.setTag(tag);
        return find(param);
    }

    @Override
    public MovieLanguageDTO insert(String tag) {
        MovieLanguageDTO movieLanguageDTO = new MovieLanguageDTO();
        movieLanguageDTO.setTag(tag);
        return insert(movieLanguageDTO);
    }
}