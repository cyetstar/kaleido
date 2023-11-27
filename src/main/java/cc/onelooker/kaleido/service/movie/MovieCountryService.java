package cc.onelooker.kaleido.service.movie;

import cc.onelooker.kaleido.dto.movie.MovieCountryDTO;
import com.zjjcnt.common.core.service.IBaseService;

/**
 * 国家地区Service
 *
 * @author cyetstar
 * @date 2023-11-26 01:19:02
 */
public interface MovieCountryService extends IBaseService<MovieCountryDTO> {

    MovieCountryDTO findByTag(String tag);

    MovieCountryDTO insert(Long id, String tag);

}