package cc.onelooker.kaleido.service.movie;

import com.zjjcnt.common.core.service.IBaseService;

import java.lang.Long;

import cc.onelooker.kaleido.dto.movie.MovieFileDTO;

/**
 * 电影文件Service
 *
 * @author xiadawei
 * @date 2023-06-13 20:35:11
 */
public interface MovieFileService extends IBaseService<MovieFileDTO> {

    boolean deleteByMovieId(Long movieId);

    MovieFileDTO findByMovieId(Long movieId);
}