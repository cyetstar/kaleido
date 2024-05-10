package cc.onelooker.kaleido.service.comic.impl;

import cc.onelooker.kaleido.convert.comic.ComicSeriesConvert;
import cc.onelooker.kaleido.dto.AlternateTitleDTO;
import cc.onelooker.kaleido.dto.comic.ComicAuthorDTO;
import cc.onelooker.kaleido.dto.comic.ComicSeriesDTO;
import cc.onelooker.kaleido.entity.comic.ComicSeriesDO;
import cc.onelooker.kaleido.mapper.comic.ComicSeriesMapper;
import cc.onelooker.kaleido.service.AlternateTitleService;
import cc.onelooker.kaleido.service.comic.ComicAuthorService;
import cc.onelooker.kaleido.service.comic.ComicSeriesService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zjjcnt.common.core.service.impl.AbstractBaseServiceImpl;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 漫画系列ServiceImpl
 *
 * @author cyetstar
 * @date 2024-03-12 17:47:50
 */
@Service
public class ComicSeriesServiceImpl extends AbstractBaseServiceImpl<ComicSeriesMapper, ComicSeriesDO, ComicSeriesDTO> implements ComicSeriesService {

    ComicSeriesConvert convert = ComicSeriesConvert.INSTANCE;

    @Autowired
    private AlternateTitleService alternateTitleService;

    @Autowired
    private ComicAuthorService comicAuthorService;

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
        if (StringUtils.isNotEmpty(dto.getKeyword())) {
            List<String> idList = Lists.newArrayList();
            idList.addAll(listSeriesIdByTitle(dto.getKeyword()));
            idList.addAll(listSeriesIdByAuthor(dto.getKeyword()));
            if (CollectionUtils.isNotEmpty(idList)) {
                query.and(q -> q.in(ComicSeriesDO::getId, idList).or().like(ComicSeriesDO::getTitle, dto.getKeyword()));
            } else {
                query.like(ComicSeriesDO::getTitle, dto.getKeyword());
            }
        }
        return query;
    }

    private List<String> listSeriesIdByTitle(String title) {
        List<AlternateTitleDTO> alternateTitleDTOList = alternateTitleService.listByTitleAndSubjectType(title, "comic_series");
        return alternateTitleDTOList.stream().map(AlternateTitleDTO::getSubjectId).collect(Collectors.toList());
    }

    private List<String> listSeriesIdByAuthor(String author) {
        List<ComicAuthorDTO> comicAuthorDTOList = comicAuthorService.listByKeyword(author);
        List<ComicSeriesDTO> comicSeriesDTOList = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(comicAuthorDTOList)) {
            for (ComicAuthorDTO comicAuthorDTO : comicAuthorDTOList) {
                comicSeriesDTOList.addAll(listByAuthorId(comicAuthorDTO.getId()));
            }
        }
        return comicSeriesDTOList.stream().map(ComicSeriesDTO::getId).collect(Collectors.toList());
    }

    @Override
    public ComicSeriesDTO convertToDTO(ComicSeriesDO comicSeriesDO) {
        return convert.convert(comicSeriesDO);
    }

    @Override
    public ComicSeriesDO convertToDO(ComicSeriesDTO comicSeriesDTO) {
        return convert.convertToDO(comicSeriesDTO);
    }

    @Override
    public List<ComicSeriesDTO> listByAuthorId(String authorId) {
        return baseMapper.listByAuthorId(authorId);
    }
}