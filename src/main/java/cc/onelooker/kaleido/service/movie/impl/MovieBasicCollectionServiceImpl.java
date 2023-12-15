package cc.onelooker.kaleido.service.movie.impl;

import cc.onelooker.kaleido.dto.movie.MovieBasicCollectionDTO;
import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.zjjcnt.common.core.service.impl.AbstractBaseServiceImpl;
import cc.onelooker.kaleido.service.movie.MovieBasicCollectionService;
import cc.onelooker.kaleido.entity.movie.MovieBasicCollectionDO;
import cc.onelooker.kaleido.dto.movie.MovieBasicCollectionDTO;
import cc.onelooker.kaleido.convert.movie.MovieBasicCollectionConvert;
import cc.onelooker.kaleido.mapper.movie.MovieBasicCollectionMapper;

import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * 电影集合关联表ServiceImpl
 *
 * @author cyetstar
 * @date 2023-11-26 01:19:02
 */
@Service
public class MovieBasicCollectionServiceImpl extends AbstractBaseServiceImpl<MovieBasicCollectionMapper, MovieBasicCollectionDO, MovieBasicCollectionDTO> implements MovieBasicCollectionService {

    MovieBasicCollectionConvert convert = MovieBasicCollectionConvert.INSTANCE;

    @Override
    protected Wrapper<MovieBasicCollectionDO> genQueryWrapper(MovieBasicCollectionDTO dto) {
        LambdaQueryWrapper<MovieBasicCollectionDO> query = new LambdaQueryWrapper<>();
        query.eq(Objects.nonNull(dto.getMovieId()), MovieBasicCollectionDO::getMovieId, dto.getMovieId());
        query.eq(Objects.nonNull(dto.getCollectionId()), MovieBasicCollectionDO::getCollectionId, dto.getCollectionId());
        return query;
    }

    @Override
    public MovieBasicCollectionDTO convertToDTO(MovieBasicCollectionDO movieBasicCollectionDO) {
        return convert.convert(movieBasicCollectionDO);
    }

    @Override
    public MovieBasicCollectionDO convertToDO(MovieBasicCollectionDTO movieBasicCollectionDTO) {
        return convert.convertToDO(movieBasicCollectionDTO);
    }

    @Override
    public List<MovieBasicCollectionDTO> listByCollectionId(Long collectionId) {
        Validate.notNull(collectionId);
        MovieBasicCollectionDTO param = new MovieBasicCollectionDTO();
        param.setCollectionId(collectionId);
        return list(param);
    }

    @Override
    public List<MovieBasicCollectionDTO> listMovieId(Long movieId) {
        Validate.notNull(movieId);
        MovieBasicCollectionDTO param = new MovieBasicCollectionDTO();
        param.setMovieId(movieId);
        return list(param);
    }

    @Override
    public MovieBasicCollectionDTO findByMovieIdAndCollectionId(Long movieId, Long countryId) {
        Validate.notNull(movieId);
        Validate.notNull(countryId);
        MovieBasicCollectionDTO param = new MovieBasicCollectionDTO();
        param.setMovieId(movieId);
        param.setCollectionId(countryId);
        return find(param);
    }

    @Override
    public MovieBasicCollectionDTO insertByMovieIdAndCollectionId(Long movieId, Long countryId) {
        MovieBasicCollectionDTO movieBasicCollectionDTO = new MovieBasicCollectionDTO();
        movieBasicCollectionDTO.setMovieId(movieId);
        movieBasicCollectionDTO.setCollectionId(countryId);
        return insert(movieBasicCollectionDTO);
    }

    @Override
    public boolean deleteByMovieId(Long movieId) {
        Validate.notNull(movieId);
        MovieBasicCollectionDTO param = new MovieBasicCollectionDTO();
        param.setMovieId(movieId);
        return delete(param);
    }

    @Override
    public boolean deleteByCollectionId(Long collectionId) {
        Validate.notNull(collectionId);
        MovieBasicCollectionDTO param = new MovieBasicCollectionDTO();
        param.setCollectionId(collectionId);
        return delete(param);
    }

    @Override
    public boolean deleteByMovieIdAndCollectionId(Long movieId, Long collectionId) {
        Validate.notNull(movieId);
        Validate.notNull(collectionId);
        MovieBasicCollectionDTO param = new MovieBasicCollectionDTO();
        param.setMovieId(movieId);
        param.setCollectionId(collectionId);
        return delete(param);
    }

}

