package cc.onelooker.kaleido.dto.system.req;

import lombok.Data;

import java.util.List;

/**
 * @Author cyetstar
 * @Date 2023-12-04 23:38:00
 * @Description TODO
 */
@Data
public class FileCopyOrCutReq {

    private List<String> pathList;

    private String destPath;

    private Boolean copy;
}
