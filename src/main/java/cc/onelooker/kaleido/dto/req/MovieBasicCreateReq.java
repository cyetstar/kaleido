package cc.onelooker.kaleido.dto.req;

import com.zjjcnt.common.core.annotation.StringDateFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 电影请求对象
 *
 * @author cyetstar
 * @date 2023-11-26 01:19:02
 */
@Data
@ApiModel("电影请求对象")
public class MovieBasicCreateReq {

    @ApiModelProperty("电影名")
    private String title;

    @ApiModelProperty("原片名")
    private String originalTitle;

    @ApiModelProperty("排序名")
    private String titleSort;

    @ApiModelProperty("首映年份")
    private String year;

    @ApiModelProperty("海报")
    private String thumb;

    @ApiModelProperty("艺术图")
    private String art;

    @ApiModelProperty("用户评分")
    private Integer userRating;

    @ApiModelProperty("简介")
    private String summary;

    @ApiModelProperty("片长(秒)")
    private Integer duration;

    @ApiModelProperty("电影评级")
    private String contentRating;

    @StringDateFormat
    @ApiModelProperty("首映日期")
    private String originallyAvailableAt;

    @ApiModelProperty("电影公司")
    private String studio;

    @ApiModelProperty("评分")
    private Float rating;

    @ApiModelProperty("观看时间")
    private Long lastViewedAt;

    @ApiModelProperty("观看次数")
    private Integer viewCount;

    @ApiModelProperty("IMDb编号")
    private String imdb;

    @ApiModelProperty("豆瓣编号")
    private String doubanId;

    @ApiModelProperty("加入时间")
    private Long addedAt;

    @ApiModelProperty("更新时间")
    private Long updatedAt;

}
