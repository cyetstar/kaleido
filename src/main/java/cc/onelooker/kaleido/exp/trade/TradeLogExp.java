package cc.onelooker.kaleido.exp.trade;

import com.alibaba.excel.annotation.ExcelProperty;
import com.zjjcnt.common.core.annotation.Dict;
import com.zjjcnt.common.core.annotation.StringDateTimeFormat;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 交易记录导出对象
 *
 * @author cyetstar
 * @date 2023-06-23 13:38:46
 *
 */
@Data
public class TradeLogExp{

    @ExcelProperty("主键")
    private Long id;

    @ExcelProperty("交易账户id")
    private Long accountId;

    @Dict("jyfx")
    @ExcelProperty("交易方向")
    private String jyfx;

    @Dict("sfjg")
    @ExcelProperty("胜负结果")
    private String sfjg;

    @ExcelProperty("入场价格")
    private BigDecimal rcjg;

    @ExcelProperty("出场价格")
    private BigDecimal ccjg;

    @ExcelProperty("止损价格")
    private BigDecimal zsjg;

    @ExcelProperty("止盈价格")
    private BigDecimal zyjg;

    @ExcelProperty("头寸规模")
    private BigDecimal tcgm;

    @ExcelProperty("盈亏金额")
    private BigDecimal ykje;

    @StringDateTimeFormat
    @ExcelProperty("交易时间")
    private String yjsj;

    @StringDateTimeFormat
    @ExcelProperty("创建时间")
    private String cjsj;

    @StringDateTimeFormat
    @ExcelProperty("修改时间")
    private String xgsj;
}
