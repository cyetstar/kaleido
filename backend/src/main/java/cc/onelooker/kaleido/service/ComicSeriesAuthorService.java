package cc.onelooker.kaleido.service;

import cc.onelooker.kaleido.dto.ComicSeriesAuthorDTO;
import com.zjjcnt.common.core.service.IBaseService;

import java.util.List;

/**
 * 漫画书籍作者关联表Service
 *
 * @author cyetstar
 * @date 2024-03-12 17:47:50
 */
public interface ComicSeriesAuthorService extends IBaseService<ComicSeriesAuthorDTO> {

    List<ComicSeriesAuthorDTO> listBySeriesId(String seriesId);

    List<ComicSeriesAuthorDTO> listByAuthorId(String authorId);
}