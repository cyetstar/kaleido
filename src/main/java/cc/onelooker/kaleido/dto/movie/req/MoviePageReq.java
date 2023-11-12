package cc.onelooker.kaleido.dto.movie.req;

import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.lang.String;
import java.lang.Integer;

import com.zjjcnt.common.core.annotation.StringDateFormat;
import com.zjjcnt.common.core.annotation.StringDateTimeFormat;

/**
 * 电影请求对象
 *
 * @author cyetstar
 * @date 2023-04-18 23:04:56
 */
@Data
@ApiModel("电影请求对象")
public class MoviePageReq {

    @ApiModelProperty("电影名称")
    private String dymc;

    @ApiModelProperty("原片名")
    private String ypm;

    @ApiModelProperty("用户评分")
    private Integer yhpf;

    @ApiModelProperty("电影类型")
    private String dylx;

    @ApiModelProperty("国家地区")
    private String gjdq;

    @ApiModelProperty("电影语言")
    private String dyyy;

    @ApiModelProperty("电影等级")
    private String dydj;

    @ApiModelProperty("是否观看")
    private String gkbz;

    @ApiModelProperty("是否收藏")
    private String scbz;

}
