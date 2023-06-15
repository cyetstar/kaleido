package cc.onelooker.kaleido.service.business;

import cc.onelooker.kaleido.dto.business.MovieDTO;
import cc.onelooker.kaleido.entity.business.MovieDO;
import com.zjjcnt.common.core.service.IBaseService;

import java.io.Serializable;
import java.lang.Long;
import java.lang.String;
import java.util.List;

import com.zjjcnt.common.core.annotation.Dict;
import cc.onelooker.kaleido.dto.business.MovieUniqueidDTO;

/**
 * 电影唯一标识Service
 *
 * @author cyetstar
 * @date 2023-04-18 23:04:56
 */
public interface MovieUniqueidService extends IBaseService<MovieUniqueidDTO> {

    MovieDTO findByUidAndBslx(String uid, String bslx);

    boolean deleteByMovieId(Long movieId);

    List<MovieUniqueidDTO> listByMovieId(Long movieId);
}