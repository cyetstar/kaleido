package cc.onelooker.kaleido.dto.trade.req;

import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.lang.Long;
import java.lang.String;
import java.math.BigDecimal;
import java.lang.Integer;
import com.zjjcnt.common.core.annotation.Dict;

/**
 * 交易网格请求对象
 *
 * @author cyetstar
 * @date 2023-07-05 23:02:49
 *
 */
@Data
@ApiModel("交易网格请求对象")
public class TradeGridCreateReq{

    @ApiModelProperty("商品代码")
    private Long symbolId;

    @ApiModelProperty("策略id")
    private Long strategyId;

    @ApiModelProperty("入场价格")
    private String rcjg;

    @ApiModelProperty("出场价格")
    private String ccjg;

    @ApiModelProperty("套利次数")
    private Integer tlcs;

    @ApiModelProperty("套利金额")
    private String tlje;

    @ApiModelProperty("是否在场")
    private String sfzc;

    @ApiModelProperty("是否运行")
    private String sfyx;
}
