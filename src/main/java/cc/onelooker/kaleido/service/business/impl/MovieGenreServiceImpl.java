package cc.onelooker.kaleido.service.business.impl;

import cc.onelooker.kaleido.dto.business.MovieCountryDTO;
import cc.onelooker.kaleido.entity.business.MovieCountryDO;
import cc.onelooker.kaleido.service.DictionaryBaseServiceImpl;
import cc.onelooker.kaleido.service.IDictionaryService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.zjjcnt.common.core.service.impl.AbstractBaseServiceImpl;
import cc.onelooker.kaleido.service.business.MovieGenreService;
import cc.onelooker.kaleido.entity.business.MovieGenreDO;
import cc.onelooker.kaleido.dto.business.MovieGenreDTO;
import cc.onelooker.kaleido.convert.business.MovieGenreConvert;
import cc.onelooker.kaleido.mapper.business.MovieGenreMapper;

import org.apache.commons.lang3.StringUtils;

import java.util.*;

import java.lang.Long;
import java.lang.String;

/**
 * 影片类型ServiceImpl
 *
 * @author cyetstar
 * @date 2023-04-18 23:04:56
 */
@Service
public class MovieGenreServiceImpl extends DictionaryBaseServiceImpl<MovieGenreMapper, MovieGenreDO, MovieGenreDTO> implements MovieGenreService, IDictionaryService {

    MovieGenreConvert convert = MovieGenreConvert.INSTANCE;

    @Override
    protected Wrapper<MovieGenreDO> genQueryWrapper(MovieGenreDTO dto) {
        LambdaQueryWrapper<MovieGenreDO> query = new LambdaQueryWrapper<>();
        query.eq(StringUtils.isNotEmpty(dto.getMc()), MovieGenreDO::getMc, dto.getMc());
        return query;
    }

    @Override
    public MovieGenreDTO convertToDTO(MovieGenreDO movieGenreDO) {
        return convert.convert(movieGenreDO);
    }

    @Override
    public MovieGenreDO convertToDO(MovieGenreDTO movieGenreDTO) {
        return convert.convertToDO(movieGenreDTO);
    }

    @Override
    public MovieGenreDTO findByMc(String mc) {
        MovieGenreDTO param = new MovieGenreDTO();
        param.setMc(mc);
        return find(param);
    }

    @Override
    public List<MovieGenreDTO> listByMovieId(Long movieId) {
        List<MovieGenreDO> movieGenreDOList = baseMapper.listByMovieId(movieId);
        return convertToDTO(movieGenreDOList);
    }
}