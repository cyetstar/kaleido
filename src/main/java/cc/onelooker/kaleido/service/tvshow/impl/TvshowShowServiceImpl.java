package cc.onelooker.kaleido.service.tvshow.impl;

import cc.onelooker.kaleido.dto.tvshow.TvshowShowGenreDTO;
import cc.onelooker.kaleido.entity.movie.MovieBasicDO;
import cc.onelooker.kaleido.service.tvshow.TvshowShowGenreService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zjjcnt.common.core.domain.PageResult;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.zjjcnt.common.core.service.impl.AbstractBaseServiceImpl;
import cc.onelooker.kaleido.service.tvshow.TvshowShowService;
import cc.onelooker.kaleido.entity.tvshow.TvshowShowDO;
import cc.onelooker.kaleido.dto.tvshow.TvshowShowDTO;
import cc.onelooker.kaleido.convert.tvshow.TvshowShowConvert;
import cc.onelooker.kaleido.mapper.tvshow.TvshowShowMapper;

import com.zjjcnt.common.core.utils.ColumnUtils;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nullable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 剧集ServiceImpl
 *
 * @author cyetstar
 * @date 2023-11-26 23:27:03
 */
@Service
public class TvshowShowServiceImpl extends AbstractBaseServiceImpl<TvshowShowMapper, TvshowShowDO, TvshowShowDTO> implements TvshowShowService {

    TvshowShowConvert convert = TvshowShowConvert.INSTANCE;

    @Autowired
    private TvshowShowGenreService tvshowShowGenreService;

    @Override
    protected Wrapper<TvshowShowDO> genQueryWrapper(TvshowShowDTO dto) {
        LambdaQueryWrapper<TvshowShowDO> query = new LambdaQueryWrapper<>();
        query.eq(StringUtils.isNotEmpty(dto.getTitle()), TvshowShowDO::getTitle, dto.getTitle());
        query.eq(StringUtils.isNotEmpty(dto.getOriginalTitle()), TvshowShowDO::getOriginalTitle, dto.getOriginalTitle());
        query.eq(StringUtils.isNotEmpty(dto.getStudio()), TvshowShowDO::getStudio, dto.getStudio());
        query.eq(StringUtils.isNotEmpty(dto.getContentRating()), TvshowShowDO::getContentRating, dto.getContentRating());
        query.eq(Objects.nonNull(dto.getSummary()), TvshowShowDO::getSummary, dto.getSummary());
        query.eq(StringUtils.isNotEmpty(dto.getYear()), TvshowShowDO::getYear, dto.getYear());
        query.eq(StringUtils.isNotEmpty(dto.getOriginallyAvailableAt()), TvshowShowDO::getOriginallyAvailableAt, dto.getOriginallyAvailableAt());
        query.eq(Objects.nonNull(dto.getRating()), TvshowShowDO::getRating, dto.getRating());
        query.eq(StringUtils.isNotEmpty(dto.getThumb()), TvshowShowDO::getThumb, dto.getThumb());
        query.eq(StringUtils.isNotEmpty(dto.getArt()), TvshowShowDO::getArt, dto.getArt());
        query.eq(Objects.nonNull(dto.getTotalSeasons()), TvshowShowDO::getTotalSeasons, dto.getTotalSeasons());
        query.eq(Objects.nonNull(dto.getAddedAt()), TvshowShowDO::getAddedAt, dto.getAddedAt());
        query.eq(Objects.nonNull(dto.getUpdatedAt()), TvshowShowDO::getUpdatedAt, dto.getUpdatedAt());
        if (StringUtils.isNotEmpty(dto.getKeyword())) {
            query.like(TvshowShowDO::getTitle, dto.getKeyword()).or().like(TvshowShowDO::getOriginalTitle, dto.getKeyword());
        }
        query.in(CollectionUtils.isNotEmpty(dto.getIdList()), TvshowShowDO::getId, dto.getIdList());
        return query;
    }

    @Override
    public TvshowShowDTO convertToDTO(TvshowShowDO tvshowShowDO) {
        return convert.convert(tvshowShowDO);
    }

    @Override
    public TvshowShowDO convertToDO(TvshowShowDTO tvshowShowDTO) {
        return convert.convertToDO(tvshowShowDTO);
    }

    @Override
    public PageResult<TvshowShowDTO> page(@Nullable TvshowShowDTO dto, Page page) {
        if (Objects.nonNull(dto.getGenreId())) {
            List<TvshowShowGenreDTO> tvshowShowGenreDTOList = tvshowShowGenreService.listByGenreId(dto.getGenreId());
            List<Long> tvshowIdList = tvshowShowGenreDTOList.stream().map(TvshowShowGenreDTO::getShowId).collect(Collectors.toList());
            dto.setIdList(tvshowIdList);
        }
        return super.page(dto, page);
    }

    @Override
    public Long findMaxUpdatedAt() {
        return baseMapper.findMaxUpdatedAt();
    }
}