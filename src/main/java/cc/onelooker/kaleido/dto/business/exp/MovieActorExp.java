package cc.onelooker.kaleido.dto.business.exp;

import com.alibaba.excel.annotation.ExcelProperty;
import com.zjjcnt.common.core.annotation.StringDateTimeFormat;
import lombok.Data;

/**
 * 演职员导出对象
 *
 * @author cyetstar
 * @date 2023-04-18 23:04:56
 *
 */
@Data
public class MovieActorExp{

    @ExcelProperty("主键")
    private Long id;

    @ExcelProperty("姓名")
    private String xm;

    @ExcelProperty("本名")
    private String bm;

    @ExcelProperty("豆瓣编号")
    private String doubanId;

    @StringDateTimeFormat
    @ExcelProperty("创建时间")
    private String cjsj;

    @StringDateTimeFormat
    @ExcelProperty("修改时间")
    private String xgsj;
}
