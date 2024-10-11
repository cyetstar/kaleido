package cc.onelooker.kaleido.service.impl;

import cc.onelooker.kaleido.convert.ComicSeriesAuthorConvert;
import cc.onelooker.kaleido.dto.ComicSeriesAuthorDTO;
import cc.onelooker.kaleido.entity.ComicSeriesAuthorDO;
import cc.onelooker.kaleido.mapper.ComicSeriesAuthorMapper;
import cc.onelooker.kaleido.service.ComicSeriesAuthorService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zjjcnt.common.core.service.impl.AbstractBaseServiceImpl;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 漫画书籍作者关联表ServiceImpl
 *
 * @author cyetstar
 * @date 2024-03-12 17:47:50
 */
@Service
public class ComicSeriesAuthorServiceImpl extends AbstractBaseServiceImpl<ComicSeriesAuthorMapper, ComicSeriesAuthorDO, ComicSeriesAuthorDTO> implements ComicSeriesAuthorService {

    ComicSeriesAuthorConvert convert = ComicSeriesAuthorConvert.INSTANCE;

    @Override
    protected Wrapper<ComicSeriesAuthorDO> genQueryWrapper(ComicSeriesAuthorDTO dto) {
        LambdaQueryWrapper<ComicSeriesAuthorDO> query = new LambdaQueryWrapper<>();
        query.eq(StringUtils.isNotEmpty(dto.getSeriesId()), ComicSeriesAuthorDO::getSeriesId, dto.getSeriesId());
        query.eq(StringUtils.isNotEmpty(dto.getAuthorId()), ComicSeriesAuthorDO::getAuthorId, dto.getAuthorId());
        query.eq(StringUtils.isNotEmpty(dto.getRole()), ComicSeriesAuthorDO::getRole, dto.getRole());
        query.in(CollectionUtils.isNotEmpty(dto.getSeriesIdList()), ComicSeriesAuthorDO::getSeriesId, dto.getSeriesIdList());
        return query;
    }

    @Override
    public ComicSeriesAuthorDTO convertToDTO(ComicSeriesAuthorDO comicSeriesAuthorDO) {
        return convert.convert(comicSeriesAuthorDO);
    }

    @Override
    public ComicSeriesAuthorDO convertToDO(ComicSeriesAuthorDTO comicSeriesAuthorDTO) {
        return convert.convertToDO(comicSeriesAuthorDTO);
    }

    @Override
    public List<ComicSeriesAuthorDTO> listBySeriesId(String seriesId) {
        Validate.notEmpty(seriesId);
        ComicSeriesAuthorDTO param = new ComicSeriesAuthorDTO();
        param.setSeriesId(seriesId);
        return list(param);
    }

    @Override
    public List<ComicSeriesAuthorDTO> listByAuthorId(String authorId) {
        Validate.notEmpty(authorId);
        ComicSeriesAuthorDTO param = new ComicSeriesAuthorDTO();
        param.setAuthorId(authorId);
        return list(param);
    }
}