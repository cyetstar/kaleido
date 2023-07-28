package cc.onelooker.kaleido.dto.trade.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 交易账户请求对象
 *
 * @author cyetstar
 * @date 2023-06-23 13:38:46
 *
 */
@Data
@ApiModel("交易账户请求对象")
public class TradeAccountSaveReq {

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("账户名称")
    private String zhmc;

    @ApiModelProperty("初始余额")
    private String csye;

    @ApiModelProperty("当前余额")
    private String dqye;
}