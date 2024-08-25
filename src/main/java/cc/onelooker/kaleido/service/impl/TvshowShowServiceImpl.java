package cc.onelooker.kaleido.service.impl;

import cc.onelooker.kaleido.convert.TvshowShowConvert;
import cc.onelooker.kaleido.dto.SubjectAttributeDTO;
import cc.onelooker.kaleido.dto.TvshowShowDTO;
import cc.onelooker.kaleido.entity.TvshowShowDO;
import cc.onelooker.kaleido.enums.AttributeType;
import cc.onelooker.kaleido.mapper.TvshowShowMapper;
import cc.onelooker.kaleido.service.SubjectAttributeService;
import cc.onelooker.kaleido.service.TvshowShowService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zjjcnt.common.core.domain.PageResult;
import com.zjjcnt.common.core.service.impl.AbstractBaseServiceImpl;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;
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
    private SubjectAttributeService subjectAttributeService;

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
        if (dto != null && StringUtils.isNotEmpty(dto.getKeyword()) && StringUtils.isNotEmpty(dto.getKeywordType())) {
            List<SubjectAttributeDTO> subjectAttributeDTOList = subjectAttributeService.listBySubjectIdAndAttributeType(dto.getId(), AttributeType.TvshowGenre);
            dto.setIdList(subjectAttributeDTOList.stream().map(SubjectAttributeDTO::getSubjectId).collect(Collectors.toList()));
        }
        return super.page(dto, page);
    }

    @Override
    public Long findMaxUpdatedAt() {
        return baseMapper.findMaxUpdatedAt();
    }
}