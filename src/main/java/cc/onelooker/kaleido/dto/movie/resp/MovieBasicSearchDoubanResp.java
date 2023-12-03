package cc.onelooker.kaleido.dto.movie.resp;

import lombok.Data;

import java.util.List;

/**
 * @Author xiadawei
 * @Date 2023-12-03 18:45:00
 * @Description TODO
 */
@Data
public class MovieBasicSearchDoubanResp {

    private String doubanId;

    private String title;

    private String originalTitle;

    private String subtype;

    private String year;

    private List<String> genres;

    private String picUrl;
}
