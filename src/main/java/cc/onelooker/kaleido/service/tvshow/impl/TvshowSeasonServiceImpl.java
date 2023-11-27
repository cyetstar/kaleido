package cc.onelooker.kaleido.service.tvshow.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.zjjcnt.common.core.service.impl.AbstractBaseServiceImpl;
import cc.onelooker.kaleido.service.tvshow.TvshowSeasonService;
import cc.onelooker.kaleido.entity.tvshow.TvshowSeasonDO;
import cc.onelooker.kaleido.dto.tvshow.TvshowSeasonDTO;
import cc.onelooker.kaleido.convert.tvshow.TvshowSeasonConvert;
import cc.onelooker.kaleido.mapper.tvshow.TvshowSeasonMapper;

import com.zjjcnt.common.core.utils.ColumnUtils;
import org.apache.commons.lang3.StringUtils;
import java.util.*;

/**
 * 单季ServiceImpl
 *
 * @author cyetstar
 * @date 2023-11-26 23:27:03
 */
@Service
public class TvshowSeasonServiceImpl extends AbstractBaseServiceImpl<TvshowSeasonMapper, TvshowSeasonDO, TvshowSeasonDTO> implements TvshowSeasonService {

    TvshowSeasonConvert convert = TvshowSeasonConvert.INSTANCE;

    @Override
    protected Wrapper<TvshowSeasonDO> genQueryWrapper(TvshowSeasonDTO dto) {
        LambdaQueryWrapper<TvshowSeasonDO> query = new LambdaQueryWrapper<>();
        query.eq(Objects.nonNull(dto.getShowId()), TvshowSeasonDO::getShowId, dto.getShowId());
        query.eq(StringUtils.isNotEmpty(dto.getTitle()), TvshowSeasonDO::getTitle, dto.getTitle());
        query.eq(Objects.nonNull(dto.getSummary()), TvshowSeasonDO::getSummary, dto.getSummary());
        query.eq(Objects.nonNull(dto.getSeasonIndex()), TvshowSeasonDO::getSeasonIndex, dto.getSeasonIndex());
        query.eq(StringUtils.isNotEmpty(dto.getThumb()), TvshowSeasonDO::getThumb, dto.getThumb());
        query.eq(StringUtils.isNotEmpty(dto.getArt()), TvshowSeasonDO::getArt, dto.getArt());
        query.eq(Objects.nonNull(dto.getAddedAt()), TvshowSeasonDO::getAddedAt, dto.getAddedAt());
        query.eq(Objects.nonNull(dto.getUpdatedAt()), TvshowSeasonDO::getUpdatedAt, dto.getUpdatedAt());
        return query;
    }

    @Override
    public TvshowSeasonDTO convertToDTO(TvshowSeasonDO tvshowSeasonDO) {
        return convert.convert(tvshowSeasonDO);
    }

    @Override
    public TvshowSeasonDO convertToDO(TvshowSeasonDTO tvshowSeasonDTO) {
        return convert.convertToDO(tvshowSeasonDTO);
    }
}