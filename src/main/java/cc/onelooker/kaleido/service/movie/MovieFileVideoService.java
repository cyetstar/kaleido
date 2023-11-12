package cc.onelooker.kaleido.service.movie;

import com.zjjcnt.common.core.service.IBaseService;

import java.lang.Long;

import cc.onelooker.kaleido.dto.movie.MovieFileVideoDTO;

/**
 * 电影文件视频信息Service
 *
 * @author xiadawei
 * @date 2023-06-13 20:35:11
 */
public interface MovieFileVideoService extends IBaseService<MovieFileVideoDTO> {

    boolean deleteByMovieFileId(Long movieFileId);

    MovieFileVideoDTO findByMovieFileId(Long movieFileId);
}