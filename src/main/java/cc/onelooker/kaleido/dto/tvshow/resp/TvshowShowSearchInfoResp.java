package cc.onelooker.kaleido.dto.tvshow.resp;

import lombok.Data;

/**
 * @Author cyetstar
 * @Date 2023-12-03 18:45:00
 * @Description TODO
 */
@Data
public class TvshowShowSearchInfoResp {

    private String doubanId;

    private String tmdbId;

    private String imdbId;

    private String title;

    private String originalTitle;

    private String year;

    private String poster;
}
