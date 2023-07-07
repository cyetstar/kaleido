package cc.onelooker.kaleido.dto.trade.req;

import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.lang.String;
import java.lang.Long;
import java.lang.Integer;
import com.zjjcnt.common.core.annotation.Dict;
import java.math.BigDecimal;

/**
 * 交易商品请求对象
 *
 * @author cyetstar
 * @date 2023-07-06 11:56:08
 *
 */
@Data
@ApiModel("交易商品请求对象")
public class TradeSymbolUpdateReq{

    @ApiModelProperty("商品名称")
    private String spmc;

    @ApiModelProperty("交易所")
    private String jys;

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("交易币")
    private String jyb;

    @ApiModelProperty("交易币精度")
    private Integer jybjd;

    @ApiModelProperty("交易币手续费精度")
    private Integer jybsxfjd;

    @ApiModelProperty("计价币")
    private String jjb;

    @ApiModelProperty("计价币价格精度")
    private Integer jjbjgjd;

    @ApiModelProperty("计价币资产精度")
    private Integer jjbzcjd;

    @ApiModelProperty("计价币手续费精度")
    private Integer jjbsxfjd;

    @ApiModelProperty("是否允许市价委托")
    private String sfyxsjwt;

    @ApiModelProperty("是否允许api现货交易")
    private String sfyxapixhjy;

    @ApiModelProperty("是否允许api杠杆交易")
    private String sfyxapiggjy;

    @ApiModelProperty("支持订单类型")
    private String zcddlx;

    @ApiModelProperty("权限")
    private String qx;

    @ApiModelProperty("最大委托数量")
    private BigDecimal zdwtsl;

    @ApiModelProperty("marker手续费")
    private BigDecimal markersxf;

    @ApiModelProperty("taker手续费")
    private BigDecimal takersxf;

    @ApiModelProperty("最小下单金额")
    private BigDecimal zxxdje;

    @ApiModelProperty("最小下单数量")
    private BigDecimal zxxdsl;

    @ApiModelProperty("是否收藏")
    private String sfsc;
}
