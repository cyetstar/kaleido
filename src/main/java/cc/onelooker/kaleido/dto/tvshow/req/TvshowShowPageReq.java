package cc.onelooker.kaleido.dto.tvshow.req;

import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.lang.Long;
import java.lang.String;
import java.lang.Integer;

/**
 * 剧集请求对象
 *
 * @author cyetstar
 * @date 2023-11-27 22:51:36
 *
 */
@Data
@ApiModel("剧集请求对象")
public class TvshowShowPageReq{

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

    @ApiModelProperty("加入时间")
    private Long addedAt;

    @ApiModelProperty("更新时间")
    private Long updatedAt;

    @ApiModelProperty("关键字")
    private String keyword;

    @ApiModelProperty("类型")
    private String genre;

}
