package cc.onelooker.kaleido.dto.tvshow.req;

import lombok.Data;

import java.util.List;

/**
 * @Author cyetstar
 * @Date 2023-12-03 19:50:00
 * @Description TODO
 */
@Data
public class TvshowShowMatchPathReq {

    private List<String> paths ;

    private String doubanId;

    private String tmdbId;

    private String tvdbId;
}
