package cc.onelooker.kaleido.service;

import cc.onelooker.kaleido.dto.MovieThreadDTO;
import com.zjjcnt.common.core.service.IBaseService;

/**
 * 电影发布记录Service
 *
 * @author cyetstar
 * @date 2023-12-18 14:53:14
 */
public interface MovieThreadService extends IBaseService<MovieThreadDTO> {

    MovieThreadDTO findByFilename(String filename);
}