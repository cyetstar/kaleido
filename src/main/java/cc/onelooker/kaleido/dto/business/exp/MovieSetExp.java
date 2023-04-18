package cc.onelooker.kaleido.dto.business.exp;

import com.alibaba.excel.annotation.ExcelProperty;
import com.zjjcnt.common.core.annotation.StringDateTimeFormat;
import lombok.Data;

/**
 * 电影集合导出对象
 *
 * @author cyetstar
 * @date 2023-04-18 23:04:56
 *
 */
@Data
public class MovieSetExp{

    @ExcelProperty("主键")
    private Long id;

    @ExcelProperty("名称")
    private String mc;

    @ExcelProperty("集合说明")
    private String jhsm;

    @StringDateTimeFormat
    @ExcelProperty("创建时间")
    private String cjsj;

    @StringDateTimeFormat
    @ExcelProperty("修改时间")
    private String xgsj;
}
