package cc.onelooker.kaleido.service.comic;

import cc.onelooker.kaleido.dto.comic.ComicBookAuthorDTO;
import com.zjjcnt.common.core.service.IBaseService;

/**
 * 漫画书籍作者关联表Service
 *
 * @author cyetstar
 * @date 2024-03-12 17:47:50
 */
public interface ComicBookAuthorService extends IBaseService<ComicBookAuthorDTO> {

    void deleteByBookId(String bookId);

    void insert(String bookId, String authorId, String role);

}