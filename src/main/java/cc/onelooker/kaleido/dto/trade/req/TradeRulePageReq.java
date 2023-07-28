package cc.onelooker.kaleido.dto.trade.req;

import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.lang.Long;
import java.math.BigDecimal;
import java.lang.String;

/**
 * 交易规则请求对象
 *
 * @author cyetstar
 * @date 2023-06-23 13:38:46
 *
 */
@Data
@ApiModel("交易规则请求对象")
public class TradeRulePageReq{

    @ApiModelProperty("交易账户id")
    private Long accountId;

    @ApiModelProperty("盈亏比")
    private String ykb;

    @ApiModelProperty("风险比")
    private String fxb;

    @ApiModelProperty("风险金额")
    private String fxje;
}
