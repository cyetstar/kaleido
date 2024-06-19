package cc.onelooker.kaleido.service.comic;

import cc.onelooker.kaleido.dto.comic.ComicSeriesAuthorDTO;
import com.zjjcnt.common.core.service.IBaseService;

import java.util.List;

/**
 * 漫画书籍作者关联表Service
 *
 * @author cyetstar
 * @date 2024-03-12 17:47:50
 */
public interface ComicSeriesAuthorService extends IBaseService<ComicSeriesAuthorDTO> {

    void insert(String seriesId, String authorId, String role);

    void deleteBySeriesId(String seriesId);

    void deleteBySeriesIdAndRole(String seriesId, String role);

    List<ComicSeriesAuthorDTO> listBySeriesId(String seriesId);
}