package cc.onelooker.kaleido.dto.req;

import lombok.Data;

/**
 * @Author cyetstar
 * @Date 2023-12-04 23:38:00
 * @Description TODO
 */
@Data
public class FileRenameReq {

    private String path;

    private String newPath;
}
