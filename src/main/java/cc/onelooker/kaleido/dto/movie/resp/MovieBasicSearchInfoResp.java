package cc.onelooker.kaleido.dto.movie.resp;

import lombok.Data;

/**
 * @Author cyetstar
 * @Date 2023-12-03 18:45:00
 * @Description TODO
 */
@Data
public class MovieBasicSearchInfoResp {

    private String doubanId;

    private String tmdbId;

    private String imdbId;

    private String title;

    private String originalTitle;

    private String year;

    private String poster;
}
