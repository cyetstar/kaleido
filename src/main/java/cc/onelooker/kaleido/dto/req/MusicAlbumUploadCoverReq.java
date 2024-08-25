package cc.onelooker.kaleido.dto.req;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author cyetstar
 * @Date 2023-12-08 01:07:00
 * @Description TODO
 */
@Data
public class MusicAlbumUploadCoverReq {

    private String id;

    private String path;

    private MultipartFile file;
}
