package cc.onelooker.kaleido.service.impl;

import cc.onelooker.kaleido.convert.ComicBookConvert;
import cc.onelooker.kaleido.dto.ComicBookDTO;
import cc.onelooker.kaleido.entity.ComicBookDO;
import cc.onelooker.kaleido.mapper.ComicBookMapper;
import cc.onelooker.kaleido.service.ComicBookService;
import cc.onelooker.kaleido.service.ComicSeriesService;
import cc.onelooker.kaleido.service.TaskService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zjjcnt.common.core.service.impl.AbstractBaseServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
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

    @Autowired
    private ComicSeriesService comicSeriesService;

    @Autowired
    private TaskService taskService;

    @Override
    protected Wrapper<ComicBookDO> genQueryWrapper(ComicBookDTO dto) {
        LambdaQueryWrapper<ComicBookDO> query = new LambdaQueryWrapper<>();
        query.eq(StringUtils.isNotEmpty(dto.getSeriesId()), ComicBookDO::getSeriesId, dto.getSeriesId());
        query.eq(StringUtils.isNotEmpty(dto.getTitle()), ComicBookDO::getTitle, dto.getTitle());
        query.eq(StringUtils.isNotEmpty(dto.getOriginalTitle()), ComicBookDO::getOriginalTitle, dto.getOriginalTitle());
        query.eq(Objects.nonNull(dto.getBookNumber()), ComicBookDO::getBookNumber, dto.getBookNumber());
        query.eq(Objects.nonNull(dto.getPageCount()), ComicBookDO::getPageCount, dto.getPageCount());
        query.eq(StringUtils.isNotEmpty(dto.getFilename()), ComicBookDO::getFilename, dto.getFilename());
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

    @Override
    public List<ComicBookDTO> listBySeriesId(String seriesId) {
        ComicBookDTO param = new ComicBookDTO();
        param.setSeriesId(seriesId);
        return list(param);
    }

    @Override
    @Transactional
    public void clearCover(String id) {
        baseMapper.clearCover(id);
    }

    @Override
    @Transactional
    public boolean deleteById(Serializable id) {
        ComicBookDTO comicBookDTO = findById(id);
        boolean result = super.deleteById(id);
        ComicBookDTO param = new ComicBookDTO();
        param.setSeriesId(comicBookDTO.getSeriesId());
        long count = count(param);
        if (count == 0) {
            comicSeriesService.deleteById(comicBookDTO.getSeriesId());
        }
        return result;
    }

}