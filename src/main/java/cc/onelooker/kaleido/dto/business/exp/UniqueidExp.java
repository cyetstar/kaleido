package cc.onelooker.kaleido.dto.business.exp;

import lombok.Data;
import com.alibaba.excel.annotation.ExcelProperty;

import java.lang.Long;
import java.lang.Boolean;
import java.lang.String;

/**
 * 导出对象
 *
 * @author cyetstar
 * @date 2023-04-06 13:32:12
 *
 */
@Data
public class UniqueidExp{

    @ExcelProperty("")
    private Long id;

    @ExcelProperty("")
    private Boolean def;

    @ExcelProperty("")
    private String type;

    @ExcelProperty("")
    private String value;

    @ExcelProperty("")
    private Long movieId;
}
