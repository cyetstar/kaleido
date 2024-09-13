package cc.onelooker.kaleido.service.impl;

import cc.onelooker.kaleido.convert.TvshowSeasonConvert;
import cc.onelooker.kaleido.dto.TvshowSeasonDTO;
import cc.onelooker.kaleido.entity.TvshowSeasonDO;
import cc.onelooker.kaleido.mapper.TvshowSeasonMapper;
import cc.onelooker.kaleido.service.TvshowSeasonService;
import cc.onelooker.kaleido.service.TvshowShowService;
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
 * 单季ServiceImpl
 *
 * @author cyetstar
 * @date 2023-11-26 23:27:03
 */
@Service
public class TvshowSeasonServiceImpl extends AbstractBaseServiceImpl<TvshowSeasonMapper, TvshowSeasonDO, TvshowSeasonDTO> implements TvshowSeasonService {

    TvshowSeasonConvert convert = TvshowSeasonConvert.INSTANCE;

    @Autowired
    private TvshowShowService tvshowShowService;

    @Override
    protected Wrapper<TvshowSeasonDO> genQueryWrapper(TvshowSeasonDTO dto) {
        LambdaQueryWrapper<TvshowSeasonDO> query = new LambdaQueryWrapper<>();
        query.eq(StringUtils.isNotEmpty(dto.getShowId()), TvshowSeasonDO::getShowId, dto.getShowId());
        query.eq(StringUtils.isNotEmpty(dto.getTitle()), TvshowSeasonDO::getTitle, dto.getTitle());
        query.eq(StringUtils.isNotEmpty(dto.getSummary()), TvshowSeasonDO::getSummary, dto.getSummary());
        query.eq(Objects.nonNull(dto.getSeasonIndex()), TvshowSeasonDO::getSeasonIndex, dto.getSeasonIndex());
        query.eq(StringUtils.isNotEmpty(dto.getThumb()), TvshowSeasonDO::getThumb, dto.getThumb());
        query.eq(StringUtils.isNotEmpty(dto.getArt()), TvshowSeasonDO::getArt, dto.getArt());
        query.eq(StringUtils.isNotEmpty(dto.getTmdbId()), TvshowSeasonDO::getTmdbId, dto.getTmdbId());
        query.eq(StringUtils.isNotEmpty(dto.getDoubanId()), TvshowSeasonDO::getDoubanId, dto.getDoubanId());
        query.eq(StringUtils.isNotEmpty(dto.getImdbId()), TvshowSeasonDO::getImdbId, dto.getImdbId());
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

    @Override
    @Transactional
    public boolean deleteById(Serializable id) {
        TvshowSeasonDTO tvshowSeasonDTO = findById(id);
        boolean result = super.deleteById(id);
        TvshowSeasonDTO param = new TvshowSeasonDTO();
        param.setShowId(tvshowSeasonDTO.getShowId());
        long count = count(param);
        if (count == 0) {
            tvshowShowService.deleteById(tvshowSeasonDTO.getShowId());
        }
        return result;
    }

    @Override
    public List<TvshowSeasonDTO> listByShowId(String showId) {
        Validate.notEmpty(showId);
        TvshowSeasonDTO param = new TvshowSeasonDTO();
        param.setShowId(showId);
        return list(param);
    }

    @Override
    public TvshowSeasonDTO findByDoubanId(String doubanId) {
        Validate.notEmpty(doubanId);
        TvshowSeasonDTO param = new TvshowSeasonDTO();
        param.setDoubanId(doubanId);
        return find(param);
    }
}