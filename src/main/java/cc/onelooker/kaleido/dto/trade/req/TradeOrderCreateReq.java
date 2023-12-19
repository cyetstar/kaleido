package cc.onelooker.kaleido.dto.trade.req;

import com.zjjcnt.common.core.annotation.StringDateTimeFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 交易订单请求对象
 *
 * @author cyetstar
 * @date 2023-07-05 23:02:49
 *
 */
@Data
@ApiModel("交易订单请求对象")
public class TradeOrderCreateReq{

    @ApiModelProperty("商品代码")
    private Long symbolId;

    @ApiModelProperty("网格id")
    private Long gridId;

    @ApiModelProperty("交易所订单号")
    private String jysddh;

    @ApiModelProperty("价格")
    private String jg;

    @ApiModelProperty("原始数量")
    private String yssl;

    @ApiModelProperty("交易数量")
    private String jysl;

    @ApiModelProperty("累计订单金额")
    private String ljddje;

    @ApiModelProperty("订单状态")
    private String ddzt;

    @ApiModelProperty("订单时效方式")
    private String ddsxfs;

    @ApiModelProperty("订单类型")
    private String ddlx;

    @ApiModelProperty("订单方向")
    private String ddfx;

    @ApiModelProperty("止损价格")
    private String zsjg;

    @ApiModelProperty("冰山数量")
    private Integer bssl;

    @StringDateTimeFormat
    @ApiModelProperty("订单时间")
    private String ddsj;

    @StringDateTimeFormat
    @ApiModelProperty("更新时间")
    private String gxsj;

    @ApiModelProperty("原始交易金额")
    private String ysjyje;
}
