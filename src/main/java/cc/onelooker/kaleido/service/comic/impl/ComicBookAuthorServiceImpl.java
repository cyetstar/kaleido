package cc.onelooker.kaleido.service.comic.impl;

import cc.onelooker.kaleido.convert.comic.ComicBookAuthorConvert;
import cc.onelooker.kaleido.dto.comic.ComicBookAuthorDTO;
import cc.onelooker.kaleido.entity.comic.ComicBookAuthorDO;
import cc.onelooker.kaleido.mapper.comic.ComicBookAuthorMapper;
import cc.onelooker.kaleido.service.comic.ComicBookAuthorService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zjjcnt.common.core.service.impl.AbstractBaseServiceImpl;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 漫画书籍作者关联表ServiceImpl
 *
 * @author cyetstar
 * @date 2024-03-12 17:47:50
 */
@Service
public class ComicBookAuthorServiceImpl extends AbstractBaseServiceImpl<ComicBookAuthorMapper, ComicBookAuthorDO, ComicBookAuthorDTO> implements ComicBookAuthorService {

    ComicBookAuthorConvert convert = ComicBookAuthorConvert.INSTANCE;

    @Override
    protected Wrapper<ComicBookAuthorDO> genQueryWrapper(ComicBookAuthorDTO dto) {
        LambdaQueryWrapper<ComicBookAuthorDO> query = new LambdaQueryWrapper<>();
        query.eq(StringUtils.isNotEmpty(dto.getBookId()), ComicBookAuthorDO::getBookId, dto.getBookId());
        query.eq(StringUtils.isNotEmpty(dto.getAuthorId()), ComicBookAuthorDO::getAuthorId, dto.getAuthorId());
        query.eq(StringUtils.isNotEmpty(dto.getRole()), ComicBookAuthorDO::getRole, dto.getRole());
        query.in(CollectionUtils.isNotEmpty(dto.getBookIdList()), ComicBookAuthorDO::getBookId, dto.getBookIdList());
        return query;
    }

    @Override
    public ComicBookAuthorDTO convertToDTO(ComicBookAuthorDO comicBookAuthorDO) {
        return convert.convert(comicBookAuthorDO);
    }

    @Override
    public ComicBookAuthorDO convertToDO(ComicBookAuthorDTO comicBookAuthorDTO) {
        return convert.convertToDO(comicBookAuthorDTO);
    }

    @Override
    @Transactional
    public void deleteByBookId(String bookId) {
        Validate.notEmpty(bookId);
        ComicBookAuthorDTO param = new ComicBookAuthorDTO();
        param.setBookId(bookId);
        delete(param);
    }

    @Override
    @Transactional
    public void insert(String bookId, String authorId, String role) {
        ComicBookAuthorDTO dto = new ComicBookAuthorDTO();
        dto.setBookId(bookId);
        dto.setAuthorId(authorId);
        dto.setRole(role);
        insert(dto);
    }
}