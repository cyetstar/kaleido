package cc.onelooker.kaleido.service.business;

import com.zjjcnt.common.core.service.IBaseService;

import java.lang.Long;
import java.lang.String;
import java.util.List;

import cc.onelooker.kaleido.dto.business.MovieGenreDTO;

/**
 * 影片类型Service
 *
 * @author cyetstar
 * @date 2023-04-18 23:04:56
 */
public interface MovieGenreService extends IBaseService<MovieGenreDTO> {

    MovieGenreDTO findByMc(String mc);

    List<MovieGenreDTO> listByMovieId(Long movieId);
}