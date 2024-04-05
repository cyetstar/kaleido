package cc.onelooker.kaleido.dto.movie.req;

import com.zjjcnt.common.core.annotation.StringDateFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 豆瓣电影口碑榜请求对象
 *
 * @author cyetstar
 * @date 2023-12-29 16:15:43
 *
 */
@Data
@ApiModel("豆瓣电影口碑榜请求对象")
public class MovieDoubanWeeklyCreateReq{

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

    @StringDateFormat
    @ApiModelProperty("下榜日期")
    private String delistingDate;

    @ApiModelProperty("最高名次")
    private Integer top;

    @ApiModelProperty("备注")
    private String memo;

}
