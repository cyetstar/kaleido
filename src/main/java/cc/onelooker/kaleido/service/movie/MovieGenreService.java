package cc.onelooker.kaleido.service.movie;

import cc.onelooker.kaleido.dto.movie.MovieGenreDTO;
import com.zjjcnt.common.core.service.IBaseService;

import cc.onelooker.kaleido.dto.movie.MovieGenreDTO;

import java.util.List;

/**
 * 电影类型Service
 *
 * @author cyetstar
 * @date 2023-11-26 01:19:02
 */
public interface MovieGenreService extends IBaseService<MovieGenreDTO> {

    MovieGenreDTO findByTag(String tag);

    MovieGenreDTO insertByTag(String tag);

}