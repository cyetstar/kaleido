package cc.onelooker.kaleido.dto.req;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author cyetstar
 * @Date 2023-12-10 00:52:00
 * @Description TODO
 */
@Data
public class MovieBasicUploadPosterReq {

    private String id;

    private String path;

    private MultipartFile file;
}
