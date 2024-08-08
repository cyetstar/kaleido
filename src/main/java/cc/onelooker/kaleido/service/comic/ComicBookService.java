package cc.onelooker.kaleido.service.comic;

import cc.onelooker.kaleido.dto.comic.ComicBookDTO;
import com.zjjcnt.common.core.service.IBaseService;

import java.util.List;

/**
 * 漫画书籍Service
 *
 * @author cyetstar
 * @date 2024-03-12 17:47:50
 */
public interface ComicBookService extends IBaseService<ComicBookDTO> {

    List<ComicBookDTO> listBySeriesId(String seriesId);

    void save(ComicBookDTO dto);
}