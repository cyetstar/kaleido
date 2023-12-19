package cc.onelooker.kaleido.exp.trade;

import com.alibaba.excel.annotation.ExcelProperty;
import com.zjjcnt.common.core.annotation.Dict;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 交易网格导出对象
 *
 * @author cyetstar
 * @date 2023-07-05 23:02:49
 *
 */
@Data
public class TradeGridExp{

    @ExcelProperty("主键")
    private Long id;

    @ExcelProperty("商品代码")
    private String spdm;

    @ExcelProperty("策略id")
    private Long strategId;

    @ExcelProperty("入场价格")
    private BigDecimal rcjg;

    @ExcelProperty("出场价格")
    private BigDecimal ccjg;

    @ExcelProperty("套利次数")
    private Integer tlcs;

    @ExcelProperty("套利金额")
    private BigDecimal tlje;

    @Dict("sfzc")
    @ExcelProperty("是否在场")
    private String sfzc;

    @Dict("sfyx")
    @ExcelProperty("是否运行")
    private String sfyx;
}
