package cc.onelooker.kaleido.dto.comic.req;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author xiadawei
 * @Date 2024-04-15 20:37:00
 * @Description TODO
 */
@Data
public class ComicBookUploadCoverReq {

    private String id;

    private String data;
}
