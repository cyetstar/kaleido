package cc.onelooker.kaleido.dto.trade.req;

import com.zjjcnt.common.core.annotation.StringDateTimeFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 交易记录请求对象
 *
 * @author cyetstar
 * @date 2023-06-23 13:38:46
 */
@Data
@ApiModel("交易记录请求对象")
public class TradeLogUpdateReq {

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("商品代码")
    private Long symbolId;

    @ApiModelProperty("交易账户id")
    private Long accountId;

    @ApiModelProperty("交易方向")
    private String jyfx;

    @ApiModelProperty("胜负结果")
    private String sfjg;

    @ApiModelProperty("时间周期")
    private String sjzq;

    @ApiModelProperty("入场价格")
    private String rcjg;

    @ApiModelProperty("出场价格")
    private String ccjg;

    @ApiModelProperty("头寸规模")
    private String tcgm;

    @ApiModelProperty("盈亏金额")
    private String ykje;

    @StringDateTimeFormat
    @ApiModelProperty("交易时间")
    private String jysj;
}
