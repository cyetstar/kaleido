package cc.onelooker.kaleido.service.movie;

import com.zjjcnt.common.core.service.IBaseService;

import java.lang.Long;
import java.lang.String;
import java.util.List;

import cc.onelooker.kaleido.dto.movie.MovieSetDTO;

/**
 * 电影集合Service
 *
 * @author cyetstar
 * @date 2023-04-18 23:04:56
 */
public interface MovieSetService extends IBaseService<MovieSetDTO> {

    MovieSetDTO findByMc(String mc);

    List<MovieSetDTO> listByMovieId(Long movieId);
}