package cc.onelooker.kaleido.dto.tvshow.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 单集请求对象
 *
 * @author cyetstar
 * @date 2023-11-27 22:51:36
 *
 */
@Data
@ApiModel("单集请求对象")
public class TvshowEpisodeUpdateReq{

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("单季id")
    private Long seasonId;

    @ApiModelProperty("标题")
    private String title;

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

    @ApiModelProperty("集号")
    private Integer episodeIndex;

    @ApiModelProperty("评分")
    private BigDecimal rating;

    @ApiModelProperty("片长(秒)")
    private Integer duration;

    @ApiModelProperty("海报")
    private String thumb;

    @ApiModelProperty("艺术图")
    private String art;

    @ApiModelProperty("加入时间")
    private Long addedAt;

    @ApiModelProperty("更新时间")
    private Long updatedAt;

}
