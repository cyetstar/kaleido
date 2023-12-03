package cc.onelooker.kaleido.service.movie.impl;

import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.zjjcnt.common.core.service.impl.AbstractBaseServiceImpl;
import cc.onelooker.kaleido.service.movie.MovieBasicService;
import cc.onelooker.kaleido.entity.movie.MovieBasicDO;
import cc.onelooker.kaleido.dto.movie.MovieBasicDTO;
import cc.onelooker.kaleido.convert.movie.MovieBasicConvert;
import cc.onelooker.kaleido.mapper.movie.MovieBasicMapper;

import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * 电影ServiceImpl
 *
 * @author cyetstar
 * @date 2023-11-26 01:19:02
 */
@Service
public class MovieBasicServiceImpl extends AbstractBaseServiceImpl<MovieBasicMapper, MovieBasicDO, MovieBasicDTO> implements MovieBasicService {

    MovieBasicConvert convert = MovieBasicConvert.INSTANCE;

    @Override
    protected Wrapper<MovieBasicDO> genQueryWrapper(MovieBasicDTO dto) {
        LambdaQueryWrapper<MovieBasicDO> query = new LambdaQueryWrapper<>();
        query.eq(StringUtils.isNotEmpty(dto.getTitle()), MovieBasicDO::getTitle, dto.getTitle());
        query.eq(StringUtils.isNotEmpty(dto.getOriginalTitle()), MovieBasicDO::getOriginalTitle, dto.getOriginalTitle());
        query.eq(StringUtils.isNotEmpty(dto.getTitleSort()), MovieBasicDO::getTitleSort, dto.getTitleSort());
        query.eq(StringUtils.isNotEmpty(dto.getYear()), MovieBasicDO::getYear, dto.getYear());
        query.eq(StringUtils.isNotEmpty(dto.getThumb()), MovieBasicDO::getThumb, dto.getThumb());
        query.eq(StringUtils.isNotEmpty(dto.getArt()), MovieBasicDO::getArt, dto.getArt());
        query.eq(Objects.nonNull(dto.getUserRating()), MovieBasicDO::getUserRating, dto.getUserRating());
        query.eq(Objects.nonNull(dto.getSummary()), MovieBasicDO::getSummary, dto.getSummary());
        query.eq(Objects.nonNull(dto.getDuration()), MovieBasicDO::getDuration, dto.getDuration());
        query.eq(StringUtils.isNotEmpty(dto.getContentRating()), MovieBasicDO::getContentRating, dto.getContentRating());
        query.ge(StringUtils.isNotEmpty(dto.getOriginallyAvailableAtStart()), MovieBasicDO::getOriginallyAvailableAt, dto.getOriginallyAvailableAtStart());
        query.le(StringUtils.isNotEmpty(dto.getOriginallyAvailableAtEnd()), MovieBasicDO::getOriginallyAvailableAt, dto.getOriginallyAvailableAtEnd());
        query.eq(StringUtils.isNotEmpty(dto.getStudio()), MovieBasicDO::getStudio, dto.getStudio());
        query.eq(Objects.nonNull(dto.getRating()), MovieBasicDO::getRating, dto.getRating());
        query.eq(Objects.nonNull(dto.getLastViewedAt()), MovieBasicDO::getLastViewedAt, dto.getLastViewedAt());
        query.eq(Objects.nonNull(dto.getViewCount()), MovieBasicDO::getViewCount, dto.getViewCount());
        query.eq(StringUtils.isNotEmpty(dto.getImdb()), MovieBasicDO::getImdb, dto.getImdb());
        query.eq(StringUtils.isNotEmpty(dto.getDoubanId()), MovieBasicDO::getDoubanId, dto.getDoubanId());
        query.eq(Objects.nonNull(dto.getAddedAt()), MovieBasicDO::getAddedAt, dto.getAddedAt());
        query.eq(Objects.nonNull(dto.getUpdatedAt()), MovieBasicDO::getUpdatedAt, dto.getUpdatedAt());
        return query;
    }

    @Override
    public MovieBasicDTO convertToDTO(MovieBasicDO movieBasicDO) {
        return convert.convert(movieBasicDO);
    }

    @Override
    public MovieBasicDO convertToDO(MovieBasicDTO movieBasicDTO) {
        return convert.convertToDO(movieBasicDTO);
    }

    @Override
    public Long findMaxUpdatedAt() {
        return baseMapper.findMaxUpdatedAt();
    }

    @Override
    public Boolean updateDoubanId(Long id, String doubanId) {
        MovieBasicDO movieBasicDO = new MovieBasicDO();
        movieBasicDO.setDoubanId(doubanId);
        movieBasicDO.setId(id);
        return SqlHelper.retBool(baseMapper.updateById(movieBasicDO));
    }
}