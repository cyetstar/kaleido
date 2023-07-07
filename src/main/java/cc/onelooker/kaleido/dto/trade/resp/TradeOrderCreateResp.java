package cc.onelooker.kaleido.dto.trade.resp;

import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.lang.Long;
import java.lang.String;
import java.math.BigDecimal;
import com.zjjcnt.common.core.annotation.Dict;
import java.lang.Integer;
import com.zjjcnt.common.core.annotation.StringDateTimeFormat;

/**
 * 交易订单响应对象
 *
 * @author cyetstar
 * @date 2023-07-05 23:02:49
 *
 */
@Data
@ApiModel("交易订单响应对象")
public class TradeOrderCreateResp{

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("商品代码")
    private String spdm;

    @ApiModelProperty("网格id")
    private Long gridId;

    @ApiModelProperty("交易所订单号")
    private String jysddh;

    @ApiModelProperty("价格")
    private BigDecimal jg;

    @ApiModelProperty("原始数量")
    private BigDecimal yssl;

    @ApiModelProperty("交易数量")
    private BigDecimal yjsl;

    @ApiModelProperty("累计订单金额")
    private BigDecimal ljddje;

    @Dict("ddzt")
    @ApiModelProperty("订单状态")
    private String ddzt;

    @Dict("ddsxfs")
    @ApiModelProperty("订单时效方式")
    private String ddsxfs;

    @Dict("ddlx")
    @ApiModelProperty("订单类型")
    private String ddlx;

    @Dict("ddfx")
    @ApiModelProperty("订单方向")
    private String ddfx;

    @ApiModelProperty("止损价格")
    private BigDecimal zsjg;

    @ApiModelProperty("冰山数量")
    private Integer bssl;

    @StringDateTimeFormat
    @ApiModelProperty("订单时间")
    private String ddsj;

    @StringDateTimeFormat
    @ApiModelProperty("更新时间")
    private String gxsj;

    @ApiModelProperty("原始交易金额")
    private BigDecimal ysjyje;
}
