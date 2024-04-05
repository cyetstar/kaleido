package cc.onelooker.kaleido.service.comic.impl;

import cc.onelooker.kaleido.convert.comic.ComicBookConvert;
import cc.onelooker.kaleido.dto.comic.ComicBookDTO;
import cc.onelooker.kaleido.entity.comic.ComicBookDO;
import cc.onelooker.kaleido.mapper.comic.ComicBookMapper;
import cc.onelooker.kaleido.service.comic.ComicBookService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zjjcnt.common.core.service.impl.AbstractBaseServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * 漫画书籍ServiceImpl
 *
 * @author cyetstar
 * @date 2024-03-12 17:47:50
 */
@Service
public class ComicBookServiceImpl extends AbstractBaseServiceImpl<ComicBookMapper, ComicBookDO, ComicBookDTO> implements ComicBookService {

    ComicBookConvert convert = ComicBookConvert.INSTANCE;

    @Override
    protected Wrapper<ComicBookDO> genQueryWrapper(ComicBookDTO dto) {
        LambdaQueryWrapper<ComicBookDO> query = new LambdaQueryWrapper<>();
        query.eq(StringUtils.isNotEmpty(dto.getSeriesId()), ComicBookDO::getSeriesId, dto.getSeriesId());
        query.eq(StringUtils.isNotEmpty(dto.getTitle()), ComicBookDO::getTitle, dto.getTitle());
        query.eq(Objects.nonNull(dto.getSummary()), ComicBookDO::getSummary, dto.getSummary());
        query.eq(Objects.nonNull(dto.getBookNumber()), ComicBookDO::getBookNumber, dto.getBookNumber());
        query.eq(Objects.nonNull(dto.getPageCount()), ComicBookDO::getPageCount, dto.getPageCount());
        query.eq(StringUtils.isNotEmpty(dto.getPath()), ComicBookDO::getPath, dto.getPath());
        query.eq(StringUtils.isNotEmpty(dto.getCover()), ComicBookDO::getCover, dto.getCover());
        query.eq(Objects.nonNull(dto.getFileSize()), ComicBookDO::getFileSize, dto.getFileSize());
        query.eq(StringUtils.isNotEmpty(dto.getBgmId()), ComicBookDO::getBgmId, dto.getBgmId());
        query.eq(Objects.nonNull(dto.getAddedAt()), ComicBookDO::getAddedAt, dto.getAddedAt());
        query.eq(Objects.nonNull(dto.getUpdatedAt()), ComicBookDO::getUpdatedAt, dto.getUpdatedAt());
        return query;
    }

    @Override
    public ComicBookDTO convertToDTO(ComicBookDO comicBookDO) {
        return convert.convert(comicBookDO);
    }

    @Override
    public ComicBookDO convertToDO(ComicBookDTO comicBookDTO) {
        return convert.convertToDO(comicBookDTO);
    }
}