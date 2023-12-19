package cc.onelooker.kaleido.dto.trade.req;

import com.zjjcnt.common.core.annotation.StringDateTimeFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 策略请求对象
 *
 * @author cyetstar
 * @date 2023-07-05 23:02:49
 *
 */
@Data
@ApiModel("策略请求对象")
public class TradeStrategyUpdateReq{

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("商品代码")
    private Long symbolId;

    @ApiModelProperty("交易方向")
    private String jyfx;

    @ApiModelProperty("区间最高值")
    private String qjzgz;

    @ApiModelProperty("区间最低值")
    private String qjzdz;

    @ApiModelProperty("网格模式")
    private String wgms;

    @ApiModelProperty("网格数量")
    private Integer wgsl;

    @ApiModelProperty("投入金额")
    private String trje;

    @ApiModelProperty("区间上移上限")
    private String qjsysx;

    @ApiModelProperty("区间下移下限")
    private String qjxyxx;

    @ApiModelProperty("是否区间上移")
    private String sfqjsy;

    @ApiModelProperty("是否区间下移")
    private String sfqjxy;

    @ApiModelProperty("开始条件")
    private String kstj;

    @ApiModelProperty("停止条件")
    private String tztj;

    @ApiModelProperty("是否停止时卖出")
    private String sftzsmc;

    @ApiModelProperty("止盈价格")
    private String zyjg;

    @ApiModelProperty("止损价格")
    private String zsjg;

    @StringDateTimeFormat
    @ApiModelProperty("开始时间")
    private String kssj;

    @ApiModelProperty("是否增加网格")
    private String sfzjwg;
}
