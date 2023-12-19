package cc.onelooker.kaleido.service.movie;

import cc.onelooker.kaleido.dto.movie.MovieLanguageDTO;
import com.zjjcnt.common.core.service.IBaseService;

/**
 * 语言Service
 *
 * @author cyetstar
 * @date 2023-11-26 01:19:02
 */
public interface MovieLanguageService extends IBaseService<MovieLanguageDTO> {

    MovieLanguageDTO findByTag(String tag);

    MovieLanguageDTO insert(String tag);
}