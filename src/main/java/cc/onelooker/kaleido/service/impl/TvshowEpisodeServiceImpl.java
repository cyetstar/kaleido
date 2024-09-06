package cc.onelooker.kaleido.service.impl;

import cc.onelooker.kaleido.convert.TvshowEpisodeConvert;
import cc.onelooker.kaleido.dto.TvshowEpisodeDTO;
import cc.onelooker.kaleido.entity.TvshowEpisodeDO;
import cc.onelooker.kaleido.mapper.TvshowEpisodeMapper;
import cc.onelooker.kaleido.service.TvshowEpisodeService;
import cc.onelooker.kaleido.service.TvshowSeasonService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zjjcnt.common.core.service.impl.AbstractBaseServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * 单集ServiceImpl
 *
 * @author cyetstar
 * @date 2023-11-26 23:27:03
 */
@Service
public class TvshowEpisodeServiceImpl extends AbstractBaseServiceImpl<TvshowEpisodeMapper, TvshowEpisodeDO, TvshowEpisodeDTO> implements TvshowEpisodeService {

    TvshowEpisodeConvert convert = TvshowEpisodeConvert.INSTANCE;

    @Autowired
    private TvshowSeasonService tvshowSeasonService;

    @Override
    protected Wrapper<TvshowEpisodeDO> genQueryWrapper(TvshowEpisodeDTO dto) {
        LambdaQueryWrapper<TvshowEpisodeDO> query = new LambdaQueryWrapper<>();
        query.eq(StringUtils.isNotEmpty(dto.getShowId()), TvshowEpisodeDO::getShowId, dto.getShowId());
        query.eq(StringUtils.isNotEmpty(dto.getSeasonId()), TvshowEpisodeDO::getSeasonId, dto.getSeasonId());
        query.eq(StringUtils.isNotEmpty(dto.getTitle()), TvshowEpisodeDO::getTitle, dto.getTitle());
        query.eq(StringUtils.isNotEmpty(dto.getTitle()), TvshowEpisodeDO::getTitle, dto.getTitle());
        query.eq(StringUtils.isNotEmpty(dto.getStudio()), TvshowEpisodeDO::getStudio, dto.getStudio());
        query.eq(StringUtils.isNotEmpty(dto.getContentRating()), TvshowEpisodeDO::getContentRating, dto.getContentRating());
        query.eq(StringUtils.isNotEmpty(dto.getSummary()), TvshowEpisodeDO::getSummary, dto.getSummary());
        query.eq(StringUtils.isNotEmpty(dto.getYear()), TvshowEpisodeDO::getYear, dto.getYear());
        query.eq(StringUtils.isNotEmpty(dto.getOriginallyAvailableAt()), TvshowEpisodeDO::getOriginallyAvailableAt, dto.getOriginallyAvailableAt());
        query.eq(Objects.nonNull(dto.getEpisodeIndex()), TvshowEpisodeDO::getEpisodeIndex, dto.getEpisodeIndex());
        query.eq(Objects.nonNull(dto.getRating()), TvshowEpisodeDO::getRating, dto.getRating());
        query.eq(Objects.nonNull(dto.getDuration()), TvshowEpisodeDO::getDuration, dto.getDuration());
        query.eq(StringUtils.isNotEmpty(dto.getThumb()), TvshowEpisodeDO::getThumb, dto.getThumb());
        query.eq(StringUtils.isNotEmpty(dto.getArt()), TvshowEpisodeDO::getArt, dto.getArt());
        query.eq(Objects.nonNull(dto.getAddedAt()), TvshowEpisodeDO::getAddedAt, dto.getAddedAt());
        query.eq(Objects.nonNull(dto.getUpdatedAt()), TvshowEpisodeDO::getUpdatedAt, dto.getUpdatedAt());
        return query;
    }

    @Override
    public TvshowEpisodeDTO convertToDTO(TvshowEpisodeDO tvshowEpisodeDO) {
        return convert.convert(tvshowEpisodeDO);
    }

    @Override
    public TvshowEpisodeDO convertToDO(TvshowEpisodeDTO tvshowEpisodeDTO) {
        return convert.convertToDO(tvshowEpisodeDTO);
    }

    @Override
    public Long findMaxUpdatedAt() {
        return baseMapper.findMaxUpdatedAt();
    }

    @Override
    public List<TvshowEpisodeDTO> listBySeasonId(String seasonId) {
        Validate.notEmpty(seasonId);
        TvshowEpisodeDTO param = new TvshowEpisodeDTO();
        param.setSeasonId(seasonId);
        return list(param);
    }

    @Override
    @Transactional
    public boolean deleteById(Serializable id) {
        TvshowEpisodeDTO tvshowEpisodeDTO = findById(id);
        boolean result = super.deleteById(id);
        TvshowEpisodeDTO param = new TvshowEpisodeDTO();
        param.setSeasonId(tvshowEpisodeDTO.getSeasonId());
        long count = count(param);
        if (count == 0) {
            tvshowSeasonService.deleteById(tvshowEpisodeDTO.getSeasonId());
        }
        return result;
    }
}