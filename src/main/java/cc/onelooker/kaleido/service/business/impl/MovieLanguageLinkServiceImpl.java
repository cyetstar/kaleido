package cc.onelooker.kaleido.service.business.impl;

import cc.onelooker.kaleido.dto.business.MovieActorLinkDTO;
import cc.onelooker.kaleido.dto.business.MovieGenreLinkDTO;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.zjjcnt.common.core.service.impl.AbstractBaseServiceImpl;
import cc.onelooker.kaleido.service.business.MovieLanguageLinkService;
import cc.onelooker.kaleido.entity.business.MovieLanguageLinkDO;
import cc.onelooker.kaleido.dto.business.MovieLanguageLinkDTO;
import cc.onelooker.kaleido.convert.business.MovieLanguageLinkConvert;
import cc.onelooker.kaleido.mapper.business.MovieLanguageLinkMapper;

import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import java.lang.Long;

/**
 * 电影语言关联表ServiceImpl
 *
 * @author cyetstar
 * @date 2023-04-18 23:04:56
 */
@Service
public class MovieLanguageLinkServiceImpl extends AbstractBaseServiceImpl<MovieLanguageLinkMapper, MovieLanguageLinkDO, MovieLanguageLinkDTO> implements MovieLanguageLinkService {

    MovieLanguageLinkConvert convert = MovieLanguageLinkConvert.INSTANCE;

    @Override
    protected Wrapper<MovieLanguageLinkDO> genQueryWrapper(MovieLanguageLinkDTO dto) {
        LambdaQueryWrapper<MovieLanguageLinkDO> query = new LambdaQueryWrapper<>();
        query.eq(Objects.nonNull(dto.getMovieId()), MovieLanguageLinkDO::getMovieId, dto.getMovieId());
        query.eq(Objects.nonNull(dto.getMovieLanguageId()), MovieLanguageLinkDO::getMovieLanguageId, dto.getMovieLanguageId());
        return query;
    }

    @Override
    public MovieLanguageLinkDTO convertToDTO(MovieLanguageLinkDO movieLanguageLinkDO) {
        return convert.convert(movieLanguageLinkDO);
    }

    @Override
    public MovieLanguageLinkDO convertToDO(MovieLanguageLinkDTO movieLanguageLinkDTO) {
        return convert.convertToDO(movieLanguageLinkDTO);
    }

    @Override
    @Transactional
    public MovieLanguageLinkDTO insert(Long movieId, Long movieLanguageId) {
        MovieLanguageLinkDTO dto = new MovieLanguageLinkDTO();
        dto.setMovieId(movieId);
        dto.setMovieLanguageId(movieLanguageId);
        return insert(dto);
    }

    @Override
    @Transactional
    public boolean deleteByMovieId(Long movieId) {
        MovieLanguageLinkDTO param = new MovieLanguageLinkDTO();
        param.setMovieId(movieId);
        return delete(param);
    }
}