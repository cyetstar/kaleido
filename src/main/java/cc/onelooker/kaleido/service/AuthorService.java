package cc.onelooker.kaleido.service;

import cc.onelooker.kaleido.dto.AuthorDTO;
import cc.onelooker.kaleido.enums.AuthorRole;
import com.zjjcnt.common.core.service.IBaseService;

import java.util.List;

/**
 * 漫画作者Service
 *
 * @author cyetstar
 * @date 2024-03-12 17:47:50
 */
public interface AuthorService extends IBaseService<AuthorDTO> {

    AuthorDTO findByName(String name);

    AuthorDTO insert(String name);

    List<AuthorDTO> listByKeyword(String keyword);

    List<AuthorDTO> listBySeriesId(String seriesId);

    void updateAuthors(List<AuthorDTO> authorDTOList, String seriesId, AuthorRole authorRole);
}