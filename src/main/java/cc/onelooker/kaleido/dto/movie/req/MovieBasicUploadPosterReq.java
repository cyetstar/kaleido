package cc.onelooker.kaleido.dto.movie.req;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author xiadawei
 * @Date 2023-12-10 00:52:00
 * @Description TODO
 */
@Data
public class MovieBasicUploadPosterReq {

    private Long id;

    private String path;

    private MultipartFile file;
}
