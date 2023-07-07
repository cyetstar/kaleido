package cc.onelooker.kaleido.exp.trade;

import lombok.Data;
import com.alibaba.excel.annotation.ExcelProperty;

import java.lang.Long;
import java.math.BigDecimal;
import java.lang.String;

/**
 * 交易规则导出对象
 *
 * @author cyetstar
 * @date 2023-06-23 13:38:46
 *
 */
@Data
public class TradeRuleExp{

    @ExcelProperty("主键")
    private Long id;

    @ExcelProperty("交易账户id")
    private Long accountId;

    @ExcelProperty("盈亏比")
    private BigDecimal ykb;

    @ExcelProperty("风险比")
    private String fxb;

    @ExcelProperty("风险金额")
    private BigDecimal fxje;
}
