package cc.onelooker.kaleido.service.business;

import com.zjjcnt.common.core.service.IBaseService;

import java.lang.Long;
import java.lang.String;
import java.lang.Integer;
import cc.onelooker.kaleido.dto.business.MovieFileDTO;

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