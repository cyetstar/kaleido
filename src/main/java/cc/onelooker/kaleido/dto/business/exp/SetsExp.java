package cc.onelooker.kaleido.dto.business.exp;

import lombok.Data;
import com.alibaba.excel.annotation.ExcelProperty;

import java.lang.Long;
import java.lang.String;
import java.lang.Integer;

/**
 * 导出对象
 *
 * @author cyetstar
 * @date 2023-04-06 13:32:12
 *
 */
@Data
public class SetsExp{

    @ExcelProperty("")
    private Long id;

    @ExcelProperty("")
    private String category;

    @ExcelProperty("")
    private String doubanId;

    @ExcelProperty("")
    private String doubanResult;

    @ExcelProperty("")
    private Integer movieSize;

    @ExcelProperty("")
    private String name;

    @ExcelProperty("")
    private String overview;
}
