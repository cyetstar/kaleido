package cc.onelooker.kaleido.dto.trade.resp;

import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.lang.Long;
import java.lang.String;
import com.zjjcnt.common.core.annotation.StringDateTimeFormat;
import com.zjjcnt.common.core.annotation.Dict;
import java.math.BigDecimal;
import java.lang.Integer;

/**
 * 网格策略响应对象
 *
 * @author cyetstar
 * @date 2023-07-05 23:02:49
 *
 */
@Data
@ApiModel("网格策略响应对象")
public class TradeGridStrategyPageResp{

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("商品代码")
    private String spdm;

    @Dict("jyfx")
    @ApiModelProperty("交易方向")
    private String jyfx;

    @ApiModelProperty("区间最高值")
    private BigDecimal qjzgz;

    @ApiModelProperty("区间最低值")
    private BigDecimal qjzdz;

    @Dict("wgms")
    @ApiModelProperty("网格模式")
    private String wgms;

    @ApiModelProperty("网格数量")
    private Integer wgsl;

    @ApiModelProperty("投入金额")
    private BigDecimal trje;

    @ApiModelProperty("区间上移上限")
    private BigDecimal qjsysx;

    @ApiModelProperty("区间下移下限")
    private BigDecimal qjxyxx;

    @Dict("sfqjsy")
    @ApiModelProperty("是否区间上移")
    private String sfqjsy;

    @Dict("sfqjxy")
    @ApiModelProperty("是否区间下移")
    private String sfqjxy;

    @Dict("kstj")
    @ApiModelProperty("开始条件")
    private String kstj;

    @Dict("tztj")
    @ApiModelProperty("停止条件")
    private String tztj;

    @Dict("sftzsmc")
    @ApiModelProperty("是否停止时卖出")
    private String sftzsmc;

    @ApiModelProperty("止盈价格")
    private BigDecimal zyjg;

    @ApiModelProperty("止损价格")
    private BigDecimal zsjg;

    @StringDateTimeFormat
    @ApiModelProperty("开始时间")
    private String kssj;

    @Dict("sfzjwg")
    @ApiModelProperty("是否增加网格")
    private String sfzjwg;
}
