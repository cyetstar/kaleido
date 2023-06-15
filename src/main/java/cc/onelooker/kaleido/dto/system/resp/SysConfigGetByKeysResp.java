package cc.onelooker.kaleido.dto.system.resp;

import lombok.Data;

/**
 * @Author xiadawei
 * @Date 2023-05-23 20:42:00
 * @Description TODO
 */
@Data
public class SysConfigGetByKeysResp {

    private String configKey;

    private String configValue;
}
