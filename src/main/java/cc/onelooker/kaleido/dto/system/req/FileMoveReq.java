package cc.onelooker.kaleido.dto.system.req;

import lombok.Data;

import java.util.List;

/**
 * @Author xiadawei
 * @Date 2023-12-04 23:38:00
 * @Description TODO
 */
@Data
public class FileMoveReq {

    private List<String> pathList;

    private String destPath;
}
