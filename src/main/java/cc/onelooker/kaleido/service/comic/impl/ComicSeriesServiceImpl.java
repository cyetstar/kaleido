package cc.onelooker.kaleido.service.comic.impl;

import cc.onelooker.kaleido.convert.comic.ComicSeriesConvert;
import cc.onelooker.kaleido.dto.comic.ComicSeriesDTO;
import cc.onelooker.kaleido.entity.comic.ComicSeriesDO;
import cc.onelooker.kaleido.mapper.comic.ComicSeriesMapper;
import cc.onelooker.kaleido.service.comic.ComicSeriesService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zjjcnt.common.core.service.impl.AbstractBaseServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * 漫画系列ServiceImpl
 *
 * @author cyetstar
 * @date 2024-03-12 17:47:50
 */
@Service
public class ComicSeriesServiceImpl extends AbstractBaseServiceImpl<ComicSeriesMapper, ComicSeriesDO, ComicSeriesDTO> implements ComicSeriesService {

    ComicSeriesConvert convert = ComicSeriesConvert.INSTANCE;

    @Override
    protected Wrapper<ComicSeriesDO> genQueryWrapper(ComicSeriesDTO dto) {
        LambdaQueryWrapper<ComicSeriesDO> query = new LambdaQueryWrapper<>();
        query.eq(StringUtils.isNotEmpty(dto.getTitle()), ComicSeriesDO::getTitle, dto.getTitle());
        query.eq(Objects.nonNull(dto.getSummary()), ComicSeriesDO::getSummary, dto.getSummary());
        query.eq(StringUtils.isNotEmpty(dto.getPublisher()), ComicSeriesDO::getPublisher, dto.getPublisher());
        query.eq(Objects.nonNull(dto.getBookCount()), ComicSeriesDO::getBookCount, dto.getBookCount());
        query.eq(Objects.nonNull(dto.getRating()), ComicSeriesDO::getRating, dto.getRating());
        query.eq(StringUtils.isNotEmpty(dto.getStatus()), ComicSeriesDO::getStatus, dto.getStatus());
        query.eq(StringUtils.isNotEmpty(dto.getPath()), ComicSeriesDO::getPath, dto.getPath());
        query.eq(StringUtils.isNotEmpty(dto.getCover()), ComicSeriesDO::getCover, dto.getCover());
        query.eq(StringUtils.isNotEmpty(dto.getBgmId()), ComicSeriesDO::getBgmId, dto.getBgmId());
        query.like(StringUtils.isNotEmpty(dto.getKeyword()), ComicSeriesDO::getTitle, dto.getKeyword());
        return query;
    }

    @Override
    public ComicSeriesDTO convertToDTO(ComicSeriesDO comicSeriesDO) {
        return convert.convert(comicSeriesDO);
    }

    @Override
    public ComicSeriesDO convertToDO(ComicSeriesDTO comicSeriesDTO) {
        return convert.convertToDO(comicSeriesDTO);
    }
}