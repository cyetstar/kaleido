package cc.onelooker.kaleido.service.business;

import com.zjjcnt.common.core.service.IBaseService;

import java.io.IOException;
import java.lang.Long;
import java.lang.String;
import java.lang.Integer;
import com.zjjcnt.common.core.annotation.StringDateFormat;
import com.zjjcnt.common.core.annotation.Dict;
import com.zjjcnt.common.core.annotation.StringDateTimeFormat;
import cc.onelooker.kaleido.dto.business.MovieDTO;

/**
 * 电影Service
 *
 * @author cyetstar
 * @date 2023-04-18 23:04:56
 */
public interface MovieService extends IBaseService<MovieDTO> {

    void importNFO(String libraryPath, String[] filterPaths) throws IOException;

}