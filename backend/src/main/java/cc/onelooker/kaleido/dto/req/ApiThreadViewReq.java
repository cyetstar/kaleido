package cc.onelooker.kaleido.dto.req;

import lombok.Data;

/**
 * @Author xiadawei
 * @Date 2024-10-08 18:05:00
 * @Description TODO
 */
@Data
public class ApiThreadViewReq {

    private String id;

    private String imdbId;

    private String doubanId;

    private String bgmId;
}
