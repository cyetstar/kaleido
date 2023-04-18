package cc.onelooker.kaleido.dto.business.req;

import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.lang.Long;
import java.lang.String;
import java.lang.Integer;
import com.zjjcnt.common.core.annotation.StringDateFormat;
import com.zjjcnt.common.core.annotation.Dict;
import com.zjjcnt.common.core.annotation.StringDateTimeFormat;

/**
 * 电影请求对象
 *
 * @author cyetstar
 * @date 2023-04-18 23:04:56
 *
 */
@Data
@ApiModel("电影请求对象")
public class MoviePageReq{

    @ApiModelProperty("电影名称")
    private String dymc;

    @ApiModelProperty("原片名")
    private String ypm;

    @ApiModelProperty("用户评分")
    private Integer yhpf;

    @ApiModelProperty("电影简介")
    private String dyjj;

    @ApiModelProperty("电影标语")
    private String dyby;

    @ApiModelProperty("影片时长")
    private Integer ypsc;

    @ApiModelProperty("电影等级")
    private String dydj;

    @StringDateFormat
    @ApiModelProperty("上映日期")
    private String syrq;

    @ApiModelProperty("官网地址")
    private String gwdz;

    @ApiModelProperty("是否观看")
    private String gkbz;

    @StringDateTimeFormat
    @ApiModelProperty("观看时间")
    private String gksj;

    @ApiModelProperty("是否收藏")
    private String scbz;

    @StringDateTimeFormat
    @ApiModelProperty("收藏时间")
    private String scsj;

    @ApiModelProperty("plex编号")
    private String plexId;

    @StringDateTimeFormat
    @ApiModelProperty("创建时间")
    private String cjsj;

    @StringDateTimeFormat
    @ApiModelProperty("修改时间")
    private String xgsj;
}
