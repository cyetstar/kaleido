package cc.onelooker.kaleido.service.business;

import com.zjjcnt.common.core.service.IBaseService;

import java.lang.Long;
import java.lang.String;
import java.util.List;

import cc.onelooker.kaleido.dto.business.MovieLanguageDTO;

/**
 * 语言Service
 *
 * @author cyetstar
 * @date 2023-04-18 23:04:56
 */
public interface MovieLanguageService extends IBaseService<MovieLanguageDTO> {

    MovieLanguageDTO findByMc(String mc);

    List<MovieLanguageDTO> listByMovieId(Long movieId);
}