package cc.onelooker.kaleido.dto.business.exp;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * 别名导出对象
 *
 * @author cyetstar
 * @date 2023-04-18 23:04:56
 *
 */
@Data
public class MovieAkaExp{

    @ExcelProperty("主键")
    private Long id;

    @ExcelProperty("电影id")
    private Long movieId;

    @ExcelProperty("电影名称")
    private String dymc;
}
