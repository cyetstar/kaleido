package cc.onelooker.kaleido.service.business.impl;

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

/**
 * ServiceImpl
 *
 * @author cyetstar
 * @date 2023-04-06 13:32:12
 */
@Service
public class MovieLanguageServiceImpl extends AbstractBaseServiceImpl<MovieLanguageMapper, MovieLanguageDO, MovieLanguageDTO> implements MovieLanguageService {

    MovieLanguageConvert convert = MovieLanguageConvert.INSTANCE;

    @Override
    protected Wrapper<MovieLanguageDO> genQueryWrapper(MovieLanguageDTO dto) {
        LambdaQueryWrapper<MovieLanguageDO> query = new LambdaQueryWrapper<>();
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