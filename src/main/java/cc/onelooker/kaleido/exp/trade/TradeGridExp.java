package cc.onelooker.kaleido.exp.trade;

import lombok.Data;
import com.alibaba.excel.annotation.ExcelProperty;

import java.lang.Long;
import java.lang.String;
import java.math.BigDecimal;
import java.lang.Integer;
import com.zjjcnt.common.core.annotation.Dict;

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

    @ExcelProperty("网格策略id")
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
