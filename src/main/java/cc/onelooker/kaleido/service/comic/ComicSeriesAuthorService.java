package cc.onelooker.kaleido.service.comic;

import cc.onelooker.kaleido.dto.comic.ComicSeriesAuthorDTO;
import cc.onelooker.kaleido.enums.AuthorRole;
import com.zjjcnt.common.core.service.IBaseService;

import java.util.List;

/**
 * 漫画书籍作者关联表Service
 *
 * @author cyetstar
 * @date 2024-03-12 17:47:50
 */
public interface ComicSeriesAuthorService extends IBaseService<ComicSeriesAuthorDTO> {

    void insert(String seriesId, String authorId, AuthorRole role);

    void deleteBySeriesId(String seriesId);

    void deleteBySeriesIdAndRole(String seriesId, AuthorRole role);

    List<ComicSeriesAuthorDTO> listBySeriesId(String seriesId);

    List<ComicSeriesAuthorDTO> listByAuthorId(String authorId);
}