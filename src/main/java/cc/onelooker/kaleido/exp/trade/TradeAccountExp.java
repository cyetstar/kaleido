package cc.onelooker.kaleido.exp.trade;

import lombok.Data;
import com.alibaba.excel.annotation.ExcelProperty;

import java.lang.Long;
import java.lang.String;
import java.math.BigDecimal;
import com.zjjcnt.common.core.annotation.StringDateTimeFormat;

/**
 * 交易账户导出对象
 *
 * @author cyetstar
 * @date 2023-06-23 13:38:46
 *
 */
@Data
public class TradeAccountExp{

    @ExcelProperty("主键")
    private Long id;

    @ExcelProperty("账户名称")
    private String zhmc;

    @ExcelProperty("初始余额")
    private BigDecimal csye;

    @ExcelProperty("当前余额")
    private BigDecimal dqye;

    @StringDateTimeFormat
    @ExcelProperty("创建时间")
    private String cjsj;

    @StringDateTimeFormat
    @ExcelProperty("修改时间")
    private String xgsj;
}
