package cc.onelooker.kaleido.exp.movie;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * 别名导出对象
 *
 * @author cyetstar
 * @date 2023-11-26 01:19:02
 *
 */
@Data
public class MovieAkaExp{

    @ExcelProperty("主键")
    private Long id;

    @ExcelProperty("电影id")
    private Long movieId;

    @ExcelProperty("电影名")
    private String title;
}
