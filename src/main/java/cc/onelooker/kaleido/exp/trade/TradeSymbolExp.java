package cc.onelooker.kaleido.exp.trade;

import com.alibaba.excel.annotation.ExcelProperty;
import com.zjjcnt.common.core.annotation.Dict;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 交易商品导出对象
 *
 * @author cyetstar
 * @date 2023-07-06 11:56:08
 *
 */
@Data
public class TradeSymbolExp{

    @ExcelProperty("商品名称")
    private String spmc;

    @ExcelProperty("交易所")
    private String jys;

    @ExcelProperty("主键")
    private Long id;

    @ExcelProperty("交易币")
    private String jyb;

    @ExcelProperty("交易币精度")
    private Integer jybjd;

    @ExcelProperty("交易币手续费精度")
    private Integer jybsxfjd;

    @ExcelProperty("计价币")
    private String jjb;

    @ExcelProperty("计价币价格精度")
    private Integer jjbjgjd;

    @ExcelProperty("计价币资产精度")
    private Integer jjbzcjd;

    @ExcelProperty("计价币手续费精度")
    private Integer jjbsxfjd;

    @Dict("sfyxsjwt")
    @ExcelProperty("是否允许市价委托")
    private String sfyxsjwt;

    @Dict("sfyxapixhjy")
    @ExcelProperty("是否允许api现货交易")
    private String sfyxapixhjy;

    @Dict("sfyxapiggjy")
    @ExcelProperty("是否允许api杠杆交易")
    private String sfyxapiggjy;

    @ExcelProperty("支持订单类型")
    private String zcddlx;

    @ExcelProperty("权限")
    private String qx;

    @ExcelProperty("最大委托数量")
    private Integer zdwtsl;

    @ExcelProperty("marker手续费")
    private BigDecimal markersxf;

    @ExcelProperty("taker手续费")
    private BigDecimal takersxf;

    @ExcelProperty("最小下单金额")
    private BigDecimal zxjdje;

    @ExcelProperty("最小下单数量")
    private BigDecimal zxxdsl;

    @Dict("sfsc")
    @ExcelProperty("是否收藏")
    private String sfsc;
}
