package cc.onelooker.kaleido.service.business.impl;

import cc.onelooker.kaleido.dto.business.MovieRatingDTO;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.zjjcnt.common.core.service.impl.AbstractBaseServiceImpl;
import cc.onelooker.kaleido.service.business.MovieTagService;
import cc.onelooker.kaleido.entity.business.MovieTagDO;
import cc.onelooker.kaleido.dto.business.MovieTagDTO;
import cc.onelooker.kaleido.convert.business.MovieTagConvert;
import cc.onelooker.kaleido.mapper.business.MovieTagMapper;

import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import java.lang.Long;
import java.lang.String;

/**
 * 电影标签ServiceImpl
 *
 * @author cyetstar
 * @date 2023-04-18 23:04:56
 */
@Service
public class MovieTagServiceImpl extends AbstractBaseServiceImpl<MovieTagMapper, MovieTagDO, MovieTagDTO> implements MovieTagService {

    MovieTagConvert convert = MovieTagConvert.INSTANCE;

    @Override
    protected Wrapper<MovieTagDO> genQueryWrapper(MovieTagDTO dto) {
        LambdaQueryWrapper<MovieTagDO> query = new LambdaQueryWrapper<>();
        query.eq(Objects.nonNull(dto.getMovieId()), MovieTagDO::getMovieId, dto.getMovieId());
        query.eq(StringUtils.isNotEmpty(dto.getMc()), MovieTagDO::getMc, dto.getMc());
        return query;
    }

    @Override
    public MovieTagDTO convertToDTO(MovieTagDO movieTagDO) {
        return convert.convert(movieTagDO);
    }

    @Override
    public MovieTagDO convertToDO(MovieTagDTO movieTagDTO) {
        return convert.convertToDO(movieTagDTO);
    }

    @Override
    public MovieTagDTO findByMovieIdAndMc(Long movieId, String mc) {
        MovieTagDTO param = new MovieTagDTO();
        param.setMovieId(movieId);
        param.setMc(mc);
        return find(param);
    }

    @Override
    @Transactional
    public boolean deleteByMovieId(Long movieId) {
        MovieTagDTO param = new MovieTagDTO();
        param.setMovieId(movieId);
        return delete(param);
    }

    @Override
    public List<MovieTagDTO> listByMovieId(Long movieId) {
        MovieTagDTO param = new MovieTagDTO();
        param.setMovieId(movieId);
        return list(param);
    }
}