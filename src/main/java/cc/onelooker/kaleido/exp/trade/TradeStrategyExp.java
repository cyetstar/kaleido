package cc.onelooker.kaleido.exp.trade;

import com.alibaba.excel.annotation.ExcelProperty;
import com.zjjcnt.common.core.annotation.Dict;
import com.zjjcnt.common.core.annotation.StringDateTimeFormat;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 策略导出对象
 *
 * @author cyetstar
 * @date 2023-07-05 23:02:49
 *
 */
@Data
public class TradeStrategyExp{

    @ExcelProperty("主键")
    private Long id;

    @ExcelProperty("商品代码")
    private String spdm;

    @StringDateTimeFormat
    @ExcelProperty("创建时间")
    private String cjsj;

    @StringDateTimeFormat
    @ExcelProperty("修改时间")
    private String xgsj;

    @Dict("jyfx")
    @ExcelProperty("交易方向")
    private String jyfx;

    @ExcelProperty("区间最高值")
    private BigDecimal qjzgz;

    @ExcelProperty("区间最低值")
    private BigDecimal qjzdz;

    @Dict("wgms")
    @ExcelProperty("网格模式")
    private String wgms;

    @ExcelProperty("网格数量")
    private Integer wgsl;

    @ExcelProperty("投入金额")
    private BigDecimal trje;

    @ExcelProperty("区间上移上限")
    private BigDecimal qjsysx;

    @ExcelProperty("区间下移下限")
    private BigDecimal qjxyxx;

    @Dict("sfqjsy")
    @ExcelProperty("是否区间上移")
    private String sfqjsy;

    @Dict("sfqjxy")
    @ExcelProperty("是否区间下移")
    private String sfqjxy;

    @Dict("kstj")
    @ExcelProperty("开始条件")
    private String kstj;

    @Dict("tztj")
    @ExcelProperty("停止条件")
    private String tztj;

    @Dict("sftzsmc")
    @ExcelProperty("是否停止时卖出")
    private String sftzsmc;

    @ExcelProperty("止盈价格")
    private BigDecimal zyjg;

    @ExcelProperty("止损价格")
    private BigDecimal zsjg;

    @StringDateTimeFormat
    @ExcelProperty("开始时间")
    private String kssj;

    @Dict("sfzjwg")
    @ExcelProperty("是否增加网格")
    private String sfzjwg;
}
