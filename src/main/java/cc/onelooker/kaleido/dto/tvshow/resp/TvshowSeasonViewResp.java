package cc.onelooker.kaleido.dto.tvshow.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 单季响应对象
 *
 * @author cyetstar
 * @date 2023-11-27 22:51:36
 */
@Data
@ApiModel("单季响应对象")
public class TvshowSeasonViewResp {

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

    @ApiModelProperty("评分")
    private Float rating;

    @ApiModelProperty("海报")
    private String thumb;

    @ApiModelProperty("艺术图")
    private String art;

    @ApiModelProperty("IMDb编号")
    private String imdbId;

    @ApiModelProperty("豆瓣编号")
    private String doubanId;

    @ApiModelProperty("TMDB编号")
    private String tmdbId;

    @ApiModelProperty("加入时间")
    private Long addedAt;

    @ApiModelProperty("更新时间")
    private Long updatedAt;
}
