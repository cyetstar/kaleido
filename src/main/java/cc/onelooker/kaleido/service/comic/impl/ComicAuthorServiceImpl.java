package cc.onelooker.kaleido.service.comic.impl;

import cc.onelooker.kaleido.convert.comic.ComicAuthorConvert;
import cc.onelooker.kaleido.dto.comic.ComicAuthorDTO;
import cc.onelooker.kaleido.dto.comic.ComicBookAuthorDTO;
import cc.onelooker.kaleido.entity.comic.ComicAuthorDO;
import cc.onelooker.kaleido.mapper.comic.ComicAuthorMapper;
import cc.onelooker.kaleido.service.comic.ComicAuthorService;
import cc.onelooker.kaleido.service.comic.ComicBookAuthorService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zjjcnt.common.core.service.impl.AbstractBaseServiceImpl;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
    private ComicBookAuthorService comicBookAuthorService;

    @Override
    protected Wrapper<ComicAuthorDO> genQueryWrapper(ComicAuthorDTO dto) {
        LambdaQueryWrapper<ComicAuthorDO> query = new LambdaQueryWrapper<>();
        query.eq(StringUtils.isNotEmpty(dto.getName()), ComicAuthorDO::getName, dto.getName());
        query.in(CollectionUtils.isNotEmpty(dto.getIdList()), ComicAuthorDO::getId, dto.getIdList());
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
    public List<ComicAuthorDTO> listByBookId(String bookId) {
        List<ComicAuthorDO> comicAuthorDOList = baseMapper.listByBookId(bookId);
        return convertToDTO(comicAuthorDOList);
    }

    @Override
    public List<ComicAuthorDTO> listByBookIdList(List<String> bookIdList) {
        ComicBookAuthorDTO param = new ComicBookAuthorDTO();
        param.setBookIdList(bookIdList);
        List<ComicBookAuthorDTO> comicBookAuthorDTOList = comicBookAuthorService.list(param);
        Set<String> authorIdSet = comicBookAuthorDTOList.stream().map(ComicBookAuthorDTO::getAuthorId).collect(Collectors.toSet());
        if (CollectionUtils.isEmpty(authorIdSet)) {
            return Lists.newArrayList();
        }
        ComicAuthorDTO param2 = new ComicAuthorDTO();
        param2.setIdList(Lists.newArrayList(authorIdSet.iterator()));
        return list(param2);
    }
}