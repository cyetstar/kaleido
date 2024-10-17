package cc.onelooker.kaleido.dto.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 剧集响应对象
 *
 * @author cyetstar
 * @date 2023-11-27 22:51:36
 */
@Data
@ApiModel("剧集响应对象")
public class TvshowShowPageResp {

    @ApiModelProperty("主键")
    private String id;

    @ApiModelProperty("剧集名")
    private String title;

    @ApiModelProperty("原剧集名")
    private String originalTitle;

    @ApiModelProperty("制片公司")
    private String studio;

    @ApiModelProperty("剧集评级")
    private String contentRating;

    @ApiModelProperty("简介")
    private String summary;

    @ApiModelProperty("首播年份")
    private String year;

    @ApiModelProperty("首播日期")
    private String originallyAvailableAt;

    @ApiModelProperty("评分")
    private BigDecimal rating;

    @ApiModelProperty("海报")
    private String thumb;

    @ApiModelProperty("艺术图")
    private String art;

    @ApiModelProperty("季数")
    private Integer totalSeasons;

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
