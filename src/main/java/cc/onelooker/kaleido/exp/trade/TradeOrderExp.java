package cc.onelooker.kaleido.exp.trade;

import lombok.Data;
import com.alibaba.excel.annotation.ExcelProperty;

import java.lang.Long;
import java.lang.String;
import java.math.BigDecimal;
import com.zjjcnt.common.core.annotation.Dict;
import java.lang.Integer;
import com.zjjcnt.common.core.annotation.StringDateTimeFormat;

/**
 * 交易订单导出对象
 *
 * @author cyetstar
 * @date 2023-07-05 23:02:49
 *
 */
@Data
public class TradeOrderExp{

    @ExcelProperty("主键")
    private Long id;

    @ExcelProperty("商品代码")
    private String spdm;

    @ExcelProperty("网格id")
    private Long gridId;

    @ExcelProperty("交易所订单号")
    private String jysddh;

    @ExcelProperty("价格")
    private BigDecimal jg;

    @ExcelProperty("原始数量")
    private BigDecimal yssl;

    @ExcelProperty("交易数量")
    private BigDecimal yjsl;

    @ExcelProperty("累计订单金额")
    private BigDecimal ljddje;

    @Dict("ddzt")
    @ExcelProperty("订单状态")
    private String ddzt;

    @Dict("ddsxfs")
    @ExcelProperty("订单时效方式")
    private String ddsxfs;

    @Dict("ddlx")
    @ExcelProperty("订单类型")
    private String ddlx;

    @Dict("ddfx")
    @ExcelProperty("订单方向")
    private String ddfx;

    @ExcelProperty("止损价格")
    private BigDecimal zsjg;

    @ExcelProperty("冰山数量")
    private Integer bssl;

    @StringDateTimeFormat
    @ExcelProperty("订单时间")
    private String ddsj;

    @StringDateTimeFormat
    @ExcelProperty("更新时间")
    private String gxsj;

    @ExcelProperty("原始交易金额")
    private BigDecimal ysjyje;
}
