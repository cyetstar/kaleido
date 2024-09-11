package cc.onelooker.kaleido.dto.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 单季请求对象
 *
 * @author cyetstar
 * @date 2023-11-27 22:51:36
 */
@Data
@ApiModel("单季请求对象")
public class TvshowSeasonUpdateReq {

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("剧集id")
    private Long showId;

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("原标题")
    private String originalTitle;

    @ApiModelProperty("简介")
    private String summary;

    @ApiModelProperty("季号")
    private Integer seasonIndex;

    @ApiModelProperty("首播年份")
    private String year;

    @ApiModelProperty("首播日期")
    private String originallyAvailableAt;

    @ApiModelProperty("IMDb编号")
    private String imdbId;

    @ApiModelProperty("豆瓣编号")
    private String doubanId;

    @ApiModelProperty("TMDB编号")
    private String tmdbId;

    @ApiModelProperty("导演主键列表")
    private List<String> directorList;

    @ApiModelProperty("编剧主键列表")
    private List<String> writerList;

    @ApiModelProperty("演员主键列表")
    private List<Actor> actorList;

    @Data
    public static class Actor {

        private String id;

        private String name;

        private String playRole;

    }
}
