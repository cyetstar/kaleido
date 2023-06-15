package cc.onelooker.kaleido.service.business;

import com.zjjcnt.common.core.service.IBaseService;

import java.lang.Long;
import java.lang.String;
import java.util.List;

import com.zjjcnt.common.core.annotation.StringDateTimeFormat;
import cc.onelooker.kaleido.dto.business.MovieSetDTO;

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