package cc.onelooker.kaleido.dto.trade.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 交易账户响应对象
 *
 * @author cyetstar
 * @date 2023-06-23 13:38:46
 */
@Data
@ApiModel("交易账户响应对象")
public class TradeAccountCreateResp {

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("账户名称")
    private String zhmc;

    @ApiModelProperty("初始余额")
    private String csye;

    @ApiModelProperty("当前余额")
    private String dqye;
}
