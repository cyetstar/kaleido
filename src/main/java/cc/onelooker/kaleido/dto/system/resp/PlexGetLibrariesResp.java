package cc.onelooker.kaleido.dto.system.resp;

import lombok.Data;

/**
 * @Author xiadawei
 * @Date 2023-10-01 21:36:00
 * @Description TODO
 */
@Data
public class PlexGetLibrariesResp {

    private String key;
    private String name;
    private String type;
    private String path;
}
