package cc.onelooker.kaleido.service;

import cc.onelooker.kaleido.dto.ComicBookDTO;
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

}