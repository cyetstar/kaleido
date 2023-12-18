package cc.onelooker.kaleido.service.movie;

import com.zjjcnt.common.core.service.IBaseService;

import cc.onelooker.kaleido.dto.movie.MovieThreadFilenameDTO;

/**
 * 电影发布文件Service
 *
 * @author cyetstar
 * @date 2023-12-18 16:03:32
 */
public interface MovieThreadFilenameService extends IBaseService<MovieThreadFilenameDTO> {

    MovieThreadFilenameDTO findByValue(String value);
}