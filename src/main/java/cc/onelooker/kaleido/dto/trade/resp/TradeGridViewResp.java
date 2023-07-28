package cc.onelooker.kaleido.dto.trade.resp;

import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.lang.Long;
import java.lang.String;
import java.math.BigDecimal;
import java.lang.Integer;
import com.zjjcnt.common.core.annotation.Dict;

/**
 * 交易网格响应对象
 *
 * @author cyetstar
 * @date 2023-07-05 23:02:49
 *
 */
@Data
@ApiModel("交易网格响应对象")
public class TradeGridViewResp{

    @ApiModelProperty("主键")
    private Long id;

    @Dict("tradeSymbol")
    @ApiModelProperty("商品代码")
    private Long symbolId;

    @ApiModelProperty("策略id")
    private Long strategyId;

    @ApiModelProperty("网格金额")
    private String wgje;

    @ApiModelProperty("入场价格")
    private String rcjg;

    @ApiModelProperty("出场价格")
    private String ccjg;

    @ApiModelProperty("套利次数")
    private Integer tlcs;

    @ApiModelProperty("套利金额")
    private String tlje;

    @Dict("sfzc")
    @ApiModelProperty("是否在场")
    private String sfzc;

    @Dict("sfyx")
    @ApiModelProperty("是否运行")
    private String sfyx;
}
