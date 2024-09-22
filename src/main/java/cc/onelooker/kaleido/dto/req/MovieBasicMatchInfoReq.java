package cc.onelooker.kaleido.dto.req;

import lombok.Data;

/**
 * @Author cyetstar
 * @Date 2023-12-03 19:50:00
 * @Description TODO
 */
@Data
public class MovieBasicMatchInfoReq {

    private String id;

    private String path;

    private String title;

    private String doubanId;

    private String tmdbId;

    private String imdbId;

    private String matchType;
}
