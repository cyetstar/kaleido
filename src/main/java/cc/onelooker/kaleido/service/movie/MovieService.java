package cc.onelooker.kaleido.service.movie;

import com.zjjcnt.common.core.service.IBaseService;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.String;

import cc.onelooker.kaleido.dto.movie.MovieDTO;
import org.springframework.web.multipart.MultipartFile;

/**
 * 电影Service
 *
 * @author cyetstar
 * @date 2023-04-18 23:04:56
 */
public interface MovieService extends IBaseService<MovieDTO> {

    void importNFO(String libraryPath, String[] filterPaths) throws IOException;

    void uploadPoster(Long id, MultipartFile file) throws IOException;
}