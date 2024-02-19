package cc.onelooker.kaleido.dto.tvshow.req;

import lombok.Data;

/**
 * @Author cyetstar
 * @Date 2023-12-03 19:50:00
 * @Description TODO
 */
@Data
public class TvshowShowMatchInfoReq {

    private Long id;

    private String doubanId;

    private String tmdbId;

    private String imdbId;
}
