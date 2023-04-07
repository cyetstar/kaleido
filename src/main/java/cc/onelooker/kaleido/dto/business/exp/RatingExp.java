package cc.onelooker.kaleido.dto.business.exp;

import lombok.Data;
import com.alibaba.excel.annotation.ExcelProperty;

import java.lang.Long;
import java.lang.Double;
import java.lang.Boolean;
import java.lang.Integer;
import java.lang.String;

/**
 * 导出对象
 *
 * @author cyetstar
 * @date 2023-04-06 13:32:12
 *
 */
@Data
public class RatingExp{

    @ExcelProperty("")
    private Long id;

    @ExcelProperty("")
    private Double average;

    @ExcelProperty("")
    private Boolean def;

    @ExcelProperty("")
    private Integer max;

    @ExcelProperty("")
    private String type;

    @ExcelProperty("")
    private Integer votes;

    @ExcelProperty("")
    private Long movieId;
}
