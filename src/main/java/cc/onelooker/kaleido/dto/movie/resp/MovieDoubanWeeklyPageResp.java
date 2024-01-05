package cc.onelooker.kaleido.dto.movie.resp;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zjjcnt.common.core.annotation.StringDateFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

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

    @ApiModelProperty("电影名")
    private String title;

    @ApiModelProperty("原片名")
    private String originalTitle;

    @ApiModelProperty("首映年份")
    private String year;

    @ApiModelProperty("海报")
    private String thumb;

    @StringDateFormat
    @ApiModelProperty("上榜日期")
    private String listingDate;

    @ApiModelProperty("下榜日期")
    private String delistingDate;

    @ApiModelProperty("最高名次")
    private Integer top;

    @ApiModelProperty("备注")
    private String memo;

    @ApiModelProperty("电影id")
    private Long movieId;

    @ApiModelProperty("imdb编号")
    private String imdb;

    @JsonProperty
    @StringDateFormat
    public String getDelistingDate() {
        if (StringUtils.equals(delistingDate, "99999999")) {
            return null;
        }
        return delistingDate;
    }
}
