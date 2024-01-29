package cc.onelooker.kaleido.dto.movie.resp;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zjjcnt.common.util.JsonUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Map;

/**
 * 豆瓣电影口碑榜响应对象
 *
 * @author cyetstar
 * @date 2023-12-29 16:15:43
 */
@Data
@ApiModel("豆瓣电影口碑榜响应对象")
public class MovieDoubanWeeklyPageResp {

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("豆瓣编号")
    private String doubanId;

    @ApiModelProperty("电影名")
    private String title;

    @ApiModelProperty("原片名")
    private String originalTitle;

    @ApiModelProperty("首映年份")
    private String year;

    @ApiModelProperty("海报")
    private String thumb;

    @ApiModelProperty("最高名次")
    private Integer top;

    @ApiModelProperty("备注")
    private String memo;

    @ApiModelProperty("上榜情况")
    private String listingDetail;

    @ApiModelProperty("在榜状态")
    private String status;

    @ApiModelProperty("电影id")
    private Long movieId;

    @ApiModelProperty("imdb编号")
    private String imdb;

    @JsonProperty
    private Map<String, Integer> getListingDetailMap() {
        return JsonUtils.parseMapType(this.listingDetail, Integer.class);
    }

}
