package cc.onelooker.kaleido.service.movie;

import cc.onelooker.kaleido.dto.movie.MovieDTO;
import com.zjjcnt.common.core.service.IBaseService;

import java.lang.Long;
import java.lang.String;
import java.util.List;

import cc.onelooker.kaleido.dto.movie.MovieUniqueidDTO;

/**
 * 电影唯一标识Service
 *
 * @author cyetstar
 * @date 2023-04-18 23:04:56
 */
public interface MovieUniqueidService extends IBaseService<MovieUniqueidDTO> {

    MovieDTO findByUidAndBslx(String uid, String bslx);

    boolean deleteByMovieId(Long movieId);

    boolean deleteByMovieIdAndBslx(Long movieId, String bslx);

    List<MovieUniqueidDTO> listByMovieId(Long movieId);

}