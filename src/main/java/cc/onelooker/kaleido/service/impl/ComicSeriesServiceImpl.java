package cc.onelooker.kaleido.service.impl;

import cc.onelooker.kaleido.convert.ComicSeriesConvert;
import cc.onelooker.kaleido.dto.AlternateTitleDTO;
import cc.onelooker.kaleido.dto.AttributeDTO;
import cc.onelooker.kaleido.dto.ComicAuthorDTO;
import cc.onelooker.kaleido.dto.ComicBookDTO;
import cc.onelooker.kaleido.dto.ComicSeriesAuthorDTO;
import cc.onelooker.kaleido.dto.ComicSeriesDTO;
import cc.onelooker.kaleido.entity.ComicSeriesDO;
import cc.onelooker.kaleido.enums.AttributeType;
import cc.onelooker.kaleido.enums.AuthorRole;
import cc.onelooker.kaleido.enums.SubjectType;
import cc.onelooker.kaleido.enums.TaskType;
import cc.onelooker.kaleido.mapper.ComicSeriesMapper;
import cc.onelooker.kaleido.service.AlternateTitleService;
import cc.onelooker.kaleido.service.AttributeService;
import cc.onelooker.kaleido.service.SubjectAttributeService;
import cc.onelooker.kaleido.service.TaskService;
import cc.onelooker.kaleido.service.ComicAuthorService;
import cc.onelooker.kaleido.service.ComicBookService;
import cc.onelooker.kaleido.service.ComicSeriesAuthorService;
import cc.onelooker.kaleido.service.ComicSeriesService;
import cc.onelooker.kaleido.utils.KaleidoUtils;
import cc.onelooker.kaleido.utils.NioFileUtils;
import cn.hutool.core.exceptions.ExceptionUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zjjcnt.common.core.service.impl.AbstractBaseServiceImpl;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Path;
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
    private ComicAuthorService comicAuthorService;

    @Autowired
    private ComicBookService comicBookService;

    @Autowired
    private AttributeService attributeService;

    @Autowired
    private SubjectAttributeService subjectAttributeService;

    @Autowired
    private TaskService taskService;

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
        List<ComicSeriesAuthorDTO> comicSeriesAuthorDTOList = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(comicAuthorDTOList)) {
            for (ComicAuthorDTO comicAuthorDTO : comicAuthorDTOList) {
                comicSeriesAuthorDTOList.addAll(comicSeriesAuthorService.listByAuthorId(comicAuthorDTO.getId()));
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

    @Override
    public List<ComicSeriesDTO> listByAuthorId(String authorId) {
        return baseMapper.listByAuthorId(authorId);
    }

    @Override
    @Transactional
    public void save(ComicSeriesDTO dto) {
        ComicSeriesDTO oldDto = findById(dto.getId());
        alternateTitleService.deleteBySubjectId(dto.getId());
        dto.getAlternateTitleList().forEach(s -> {
            AlternateTitleDTO alternateTitleDTO = new AlternateTitleDTO();
            alternateTitleDTO.setSubjectId(dto.getId());
            alternateTitleDTO.setSubjectType("comic_series");
            alternateTitleDTO.setTitle(s);
            alternateTitleService.insert(alternateTitleDTO);
        });
        comicSeriesAuthorService.deleteBySeriesIdAndRole(dto.getId(), AuthorRole.Writer);
        saveComicAuthor(dto.getId(), dto.getWriterName(), AuthorRole.Writer);
        comicSeriesAuthorService.deleteBySeriesIdAndRole(dto.getId(), AuthorRole.Penciller);
        saveComicAuthor(dto.getId(), dto.getPencillerName(), AuthorRole.Penciller);
        subjectAttributeService.deleteBySubjectIdAndAttributeType(dto.getId(), AttributeType.ComicTag);
        dto.getTagList().forEach(s -> {
            AttributeDTO attributeDTO = attributeService.findByValueAndType(s, AttributeType.ComicTag);
            if (attributeDTO == null) {
                attributeDTO = attributeService.insert(s, AttributeType.ComicTag);
            }
            subjectAttributeService.insert(dto.getId(), attributeDTO.getId());
        });
        List<ComicBookDTO> comicBookDTOList = comicBookService.listBySeriesId(dto.getId());

        String newFolder = KaleidoUtils.genComicFolder(dto.getTitle(), dto.getWriterName(), dto.getPencillerName());
        if (StringUtils.endsWith(oldDto.getPath(), newFolder)) {
            update(dto);
        } else {
            Path oldPath = KaleidoUtils.getComicPath(oldDto.getPath());
            Path newPath = oldPath.resolveSibling(newFolder);
            try {
                NioFileUtils.renameDir(oldPath, newPath);
            } catch (IOException e) {
                ExceptionUtil.wrapAndThrow(e);
            }
            //更新为新路径
            String path = KaleidoUtils.inverseComicPath(newPath.toString()).toString();
            dto.setPath(path);
            update(dto);

            //更新书籍路径
            comicBookDTOList.forEach(s -> {
                s.setPath(StringUtils.replaceOnce(s.getPath(), oldDto.getPath(), dto.getPath()));
                comicBookService.update(s);
            });
        }

        //生成重写ComicInfo任务
        comicBookDTOList.forEach(s -> taskService.newTask(s.getId(), SubjectType.ComicBook, TaskType.writeComicInfo));
    }

    private void saveComicAuthor(String seriesId, String name, AuthorRole role) {
        if (StringUtils.isNotEmpty(name)) {
            ComicAuthorDTO comicAuthorDTO = comicAuthorService.findByName(name);
            if (comicAuthorDTO == null) {
                comicAuthorDTO = comicAuthorService.insert(name);
            }
            comicSeriesAuthorService.insert(seriesId, comicAuthorDTO.getId(), role);
        }
    }
}