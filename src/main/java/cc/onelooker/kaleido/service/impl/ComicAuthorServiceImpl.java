package cc.onelooker.kaleido.service.impl;

import cc.onelooker.kaleido.convert.ComicAuthorConvert;
import cc.onelooker.kaleido.dto.ComicAuthorDTO;
import cc.onelooker.kaleido.entity.ComicAuthorDO;
import cc.onelooker.kaleido.mapper.ComicAuthorMapper;
import cc.onelooker.kaleido.service.ComicAuthorService;
import cc.onelooker.kaleido.service.ComicSeriesAuthorService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zjjcnt.common.core.service.impl.AbstractBaseServiceImpl;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 漫画作者ServiceImpl
 *
 * @author cyetstar
 * @date 2024-03-12 17:47:50
 */
@Service
public class ComicAuthorServiceImpl extends AbstractBaseServiceImpl<ComicAuthorMapper, ComicAuthorDO, ComicAuthorDTO> implements ComicAuthorService {

    ComicAuthorConvert convert = ComicAuthorConvert.INSTANCE;

    @Autowired
    private ComicSeriesAuthorService comicSeriesAuthorService;

    @Override
    protected Wrapper<ComicAuthorDO> genQueryWrapper(ComicAuthorDTO dto) {
        LambdaQueryWrapper<ComicAuthorDO> query = new LambdaQueryWrapper<>();
        query.eq(StringUtils.isNotEmpty(dto.getName()), ComicAuthorDO::getName, dto.getName());
        query.in(CollectionUtils.isNotEmpty(dto.getIdList()), ComicAuthorDO::getId, dto.getIdList());
        query.like(StringUtils.isNotEmpty(dto.getKeyword()), ComicAuthorDO::getName, dto.getKeyword());
        return query;
    }

    @Override
    public ComicAuthorDTO convertToDTO(ComicAuthorDO comicAuthorDO) {
        return convert.convert(comicAuthorDO);
    }

    @Override
    public ComicAuthorDO convertToDO(ComicAuthorDTO comicAuthorDTO) {
        return convert.convertToDO(comicAuthorDTO);
    }

    @Override
    public ComicAuthorDTO findByName(String name) {
        Validate.notEmpty(name);
        ComicAuthorDTO param = new ComicAuthorDTO();
        param.setName(name);
        return find(param);
    }

    @Override
    public ComicAuthorDTO insert(String name) {
        ComicAuthorDTO dto = new ComicAuthorDTO();
        dto.setName(name);
        return insert(dto);
    }

    @Override
    public List<ComicAuthorDTO> listByKeyword(String keyword) {
        ComicAuthorDTO param = new ComicAuthorDTO();
        param.setKeyword(keyword);
        return list(param);
    }

    @Override
    public List<ComicAuthorDTO> listBySeriesId(String seriesId) {
        List<ComicAuthorDO> comicAuthorDOList = baseMapper.listBySeriesId(seriesId);
        return convertToDTO(comicAuthorDOList);
    }
}