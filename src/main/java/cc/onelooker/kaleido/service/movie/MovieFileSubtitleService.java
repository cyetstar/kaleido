package cc.onelooker.kaleido.service.movie;

import com.zjjcnt.common.core.service.IBaseService;

import java.lang.Long;
import java.util.List;

import cc.onelooker.kaleido.dto.movie.MovieFileSubtitleDTO;

/**
 * 电影文件字幕信息Service
 *
 * @author xiadawei
 * @date 2023-06-13 20:35:11
 */
public interface MovieFileSubtitleService extends IBaseService<MovieFileSubtitleDTO> {

    boolean deleteByMovieFileId(Long movieFileId);

    List<MovieFileSubtitleDTO> listByMovieFileId(Long movieFileId);
}