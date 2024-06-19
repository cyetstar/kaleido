package cc.onelooker.kaleido.service.comic;

import cc.onelooker.kaleido.dto.comic.ComicAuthorDTO;
import com.zjjcnt.common.core.service.IBaseService;

import java.util.List;

/**
 * 漫画作者Service
 *
 * @author cyetstar
 * @date 2024-03-12 17:47:50
 */
public interface ComicAuthorService extends IBaseService<ComicAuthorDTO> {

    ComicAuthorDTO findByName(String name);

    ComicAuthorDTO insert(String name);

    List<ComicAuthorDTO> listByKeyword(String keyword);

    List<ComicAuthorDTO> listBySeriesId(String seriesId);
}