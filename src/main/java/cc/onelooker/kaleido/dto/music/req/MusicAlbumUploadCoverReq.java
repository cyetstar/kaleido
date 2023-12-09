package cc.onelooker.kaleido.dto.music.req;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author xiadawei
 * @Date 2023-12-08 01:07:00
 * @Description TODO
 */
@Data
public class MusicAlbumUploadCoverReq {

    private Long id;

    private String path;

    private MultipartFile file;
}
