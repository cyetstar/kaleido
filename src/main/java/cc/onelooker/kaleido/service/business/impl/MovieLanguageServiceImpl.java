package cc.onelooker.kaleido.service.business.impl;

import cc.onelooker.kaleido.dto.business.MovieCountryDTO;
import cc.onelooker.kaleido.entity.business.MovieCountryDO;
import cc.onelooker.kaleido.service.DictionaryBaseServiceImpl;
import cc.onelooker.kaleido.service.IDictionaryService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.zjjcnt.common.core.service.impl.AbstractBaseServiceImpl;
import cc.onelooker.kaleido.service.business.MovieLanguageService;
import cc.onelooker.kaleido.entity.business.MovieLanguageDO;
import cc.onelooker.kaleido.dto.business.MovieLanguageDTO;
import cc.onelooker.kaleido.convert.business.MovieLanguageConvert;
import cc.onelooker.kaleido.mapper.business.MovieLanguageMapper;

import org.apache.commons.lang3.StringUtils;

import java.util.*;

import java.lang.Long;
import java.lang.String;

/**
 * 语言ServiceImpl
 *
 * @author cyetstar
 * @date 2023-04-18 23:04:56
 */
@Service
public class MovieLanguageServiceImpl extends DictionaryBaseServiceImpl<MovieLanguageMapper, MovieLanguageDO, MovieLanguageDTO> implements MovieLanguageService, IDictionaryService {

    MovieLanguageConvert convert = MovieLanguageConvert.INSTANCE;

    @Override
    protected Wrapper<MovieLanguageDO> genQueryWrapper(MovieLanguageDTO dto) {
        LambdaQueryWrapper<MovieLanguageDO> query = new LambdaQueryWrapper<>();
        query.eq(StringUtils.isNotEmpty(dto.getMc()), MovieLanguageDO::getMc, dto.getMc());
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
    public MovieLanguageDTO findByMc(String mc) {
        MovieLanguageDTO param = new MovieLanguageDTO();
        param.setMc(mc);
        return find(param);
    }

    @Override
    public List<MovieLanguageDTO> listByMovieId(Long movieId) {
        List<MovieLanguageDO> movieSetDOList = baseMapper.listByMovieId(movieId);
        return convertToDTO(movieSetDOList);
    }
}