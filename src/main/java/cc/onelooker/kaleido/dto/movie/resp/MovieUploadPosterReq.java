package cc.onelooker.kaleido.dto.movie.resp;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author xiadawei
 * @Date 2023-08-06 20:13:00
 * @Description TODO
 */
@Data
public class MovieUploadPosterReq {

    private Long id;

    private MultipartFile file;
}
