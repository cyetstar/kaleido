package cc.onelooker.kaleido.service.business.impl;

import cc.onelooker.kaleido.entity.business.MovieSetDO;
import cc.onelooker.kaleido.service.DictionaryBaseServiceImpl;
import cc.onelooker.kaleido.service.IDictionaryService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.zjjcnt.common.core.service.impl.AbstractBaseServiceImpl;
import cc.onelooker.kaleido.service.business.MovieCountryService;
import cc.onelooker.kaleido.entity.business.MovieCountryDO;
import cc.onelooker.kaleido.dto.business.MovieCountryDTO;
import cc.onelooker.kaleido.convert.business.MovieCountryConvert;
import cc.onelooker.kaleido.mapper.business.MovieCountryMapper;

import org.apache.commons.lang3.StringUtils;

import java.util.*;

import java.lang.Long;
import java.lang.String;

/**
 * 国家地区ServiceImpl
 *
 * @author cyetstar
 * @date 2023-04-18 23:04:56
 */
@Service
public class MovieCountryServiceImpl extends DictionaryBaseServiceImpl<MovieCountryMapper, MovieCountryDO, MovieCountryDTO> implements MovieCountryService, IDictionaryService {

    MovieCountryConvert convert = MovieCountryConvert.INSTANCE;

    @Override
    protected Wrapper<MovieCountryDO> genQueryWrapper(MovieCountryDTO dto) {
        LambdaQueryWrapper<MovieCountryDO> query = new LambdaQueryWrapper<>();
        query.eq(StringUtils.isNotEmpty(dto.getMc()), MovieCountryDO::getMc, dto.getMc());
        return query;
    }

    @Override
    public MovieCountryDTO convertToDTO(MovieCountryDO movieCountryDO) {
        return convert.convert(movieCountryDO);
    }

    @Override
    public MovieCountryDO convertToDO(MovieCountryDTO movieCountryDTO) {
        return convert.convertToDO(movieCountryDTO);
    }

    @Override
    public MovieCountryDTO findByMc(String mc) {
        MovieCountryDTO param = new MovieCountryDTO();
        param.setMc(mc);
        return find(param);
    }

    @Override
    public List<MovieCountryDTO> listByMovieId(Long movieId) {
        List<MovieCountryDO> movieSetDOList = baseMapper.listByMovieId(movieId);
        return convertToDTO(movieSetDOList);
    }
}