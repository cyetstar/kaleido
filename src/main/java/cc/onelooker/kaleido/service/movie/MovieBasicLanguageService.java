package cc.onelooker.kaleido.service.movie;

import com.zjjcnt.common.core.service.IBaseService;

import cc.onelooker.kaleido.dto.movie.MovieBasicLanguageDTO;

import java.util.List;

/**
 * 电影语言关联表Service
 *
 * @author cyetstar
 * @date 2023-11-26 01:19:02
 */
public interface MovieBasicLanguageService extends IBaseService<MovieBasicLanguageDTO> {

    MovieBasicLanguageDTO findByMovieIdAndLanguageId(Long movieId, Long languageId);

    MovieBasicLanguageDTO insertByMovieIdAndLanguageId(Long movieId, Long languageId);

    List<MovieBasicLanguageDTO> listByMovieId(Long movieId);
}