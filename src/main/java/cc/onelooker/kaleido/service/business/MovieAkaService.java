package cc.onelooker.kaleido.service.business;

import com.zjjcnt.common.core.service.IBaseService;

import java.lang.Long;
import java.lang.String;
import java.util.List;

import cc.onelooker.kaleido.dto.business.MovieAkaDTO;

/**
 * 别名Service
 *
 * @author cyetstar
 * @date 2023-04-18 23:04:56
 */
public interface MovieAkaService extends IBaseService<MovieAkaDTO> {

    boolean deleteByMovieId(Long movieId);

    List<MovieAkaDTO> listByMovieId(Long movieId);
}