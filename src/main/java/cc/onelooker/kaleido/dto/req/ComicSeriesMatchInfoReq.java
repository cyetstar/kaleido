package cc.onelooker.kaleido.dto.req;

import lombok.Data;

/**
 * @Author xiadawei
 * @Date 2024-03-21 21:26:00
 * @Description TODO
 */
@Data
public class ComicSeriesMatchInfoReq {

    private String id;

    private String bgmId;

    private String series;

    private String path;

    private String matchType;
}
