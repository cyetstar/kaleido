package cc.onelooker.kaleido.service.impl;

import cc.onelooker.kaleido.convert.ComicSeriesConvert;
import cc.onelooker.kaleido.dto.AlternateTitleDTO;
import cc.onelooker.kaleido.dto.AuthorDTO;
import cc.onelooker.kaleido.dto.ComicSeriesAuthorDTO;
import cc.onelooker.kaleido.dto.ComicSeriesDTO;
import cc.onelooker.kaleido.entity.ComicSeriesDO;
import cc.onelooker.kaleido.enums.SubjectType;
import cc.onelooker.kaleido.mapper.ComicSeriesMapper;
import cc.onelooker.kaleido.service.AlternateTitleService;
import cc.onelooker.kaleido.service.AuthorService;
import cc.onelooker.kaleido.service.ComicSeriesAuthorService;
import cc.onelooker.kaleido.service.ComicSeriesService;
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
    private ComicSeriesAuthorService comicSeriesAuthorService;

    @Autowired
    private AuthorService authorService;

    @Override
    protected Wrapper<ComicSeriesDO> genQueryWrapper(ComicSeriesDTO dto) {
        LambdaQueryWrapper<ComicSeriesDO> query = new LambdaQueryWrapper<>();
        query.eq(StringUtils.isNotEmpty(dto.getId()), ComicSeriesDO::getId, dto.getId());
        query.eq(StringUtils.isNotEmpty(dto.getTitle()), ComicSeriesDO::getTitle, dto.getTitle());
        query.eq(Objects.nonNull(dto.getSummary()), ComicSeriesDO::getSummary, dto.getSummary());
        query.eq(StringUtils.isNotEmpty(dto.getPublisher()), ComicSeriesDO::getPublisher, dto.getPublisher());
        query.eq(Objects.nonNull(dto.getBookCount()), ComicSeriesDO::getBookCount, dto.getBookCount());
        query.eq(Objects.nonNull(dto.getRating()), ComicSeriesDO::getRating, dto.getRating());
        query.eq(StringUtils.isNotEmpty(dto.getStatus()), ComicSeriesDO::getStatus, dto.getStatus());
        query.eq(StringUtils.isNotEmpty(dto.getPath()), ComicSeriesDO::getPath, dto.getPath());
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
        List<AlternateTitleDTO> alternateTitleDTOList = alternateTitleService.listByTitleAndSubjectType(title, SubjectType.ComicSeries);
        return alternateTitleDTOList.stream().map(AlternateTitleDTO::getSubjectId).collect(Collectors.toList());
    }

    private List<String> listSeriesIdByAuthor(String author) {
        List<AuthorDTO> authorDTOList = authorService.listByKeyword(author);
        List<ComicSeriesAuthorDTO> comicSeriesAuthorDTOList = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(authorDTOList)) {
            for (AuthorDTO authorDTO : authorDTOList) {
                comicSeriesAuthorDTOList.addAll(comicSeriesAuthorService.listByAuthorId(authorDTO.getId()));
            }
        }
        return comicSeriesAuthorDTOList.stream().map(ComicSeriesAuthorDTO::getSeriesId).collect(Collectors.toList());
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