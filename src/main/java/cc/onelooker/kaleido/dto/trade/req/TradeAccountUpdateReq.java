package cc.onelooker.kaleido.dto.trade.req;

import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.lang.Long;
import java.lang.String;
import java.math.BigDecimal;
import com.zjjcnt.common.core.annotation.StringDateTimeFormat;

/**
 * 交易账户请求对象
 *
 * @author cyetstar
 * @date 2023-06-23 13:38:46
 *
 */
@Data
@ApiModel("交易账户请求对象")
public class TradeAccountUpdateReq{

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("账户名称")
    private String zhmc;

    @ApiModelProperty("初始余额")
    private BigDecimal csye;

    @ApiModelProperty("当前余额")
    private BigDecimal dqye;
}
