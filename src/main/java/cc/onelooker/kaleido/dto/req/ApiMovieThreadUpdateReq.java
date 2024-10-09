package cc.onelooker.kaleido.dto.req;

import lombok.Data;

/**
 * @Author xiadawei
 * @Date 2024-10-08 17:16:00
 * @Description TODO
 */
@Data
public class ApiMovieThreadUpdateReq {

    private String id;

    private String title;

    private String url;

    private String doubanId;

    private String imdbId;

    private String bgmId;

    private String status;
}
