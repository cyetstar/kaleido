package cc.onelooker.kaleido.service.business;

import cc.onelooker.kaleido.enums.ActorRole;
import com.zjjcnt.common.core.service.IBaseService;

import java.lang.Long;

import com.zjjcnt.common.core.annotation.Crypto;

import java.lang.String;
import java.util.List;

import com.zjjcnt.common.core.annotation.StringDateTimeFormat;
import cc.onelooker.kaleido.dto.business.MovieActorDTO;

/**
 * 演职员Service
 *
 * @author cyetstar
 * @date 2023-04-18 23:04:56
 */
public interface MovieActorService extends IBaseService<MovieActorDTO> {

    MovieActorDTO findByXm(String xm);

    List<MovieActorDTO> listByMovieIdAndJs(Long movieId, String js);
}