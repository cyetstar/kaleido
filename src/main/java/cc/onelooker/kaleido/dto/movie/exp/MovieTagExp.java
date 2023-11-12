package cc.onelooker.kaleido.dto.movie.exp;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * 电影标签导出对象
 *
 * @author cyetstar
 * @date 2023-04-18 23:04:56
 *
 */
@Data
public class MovieTagExp{

    @ExcelProperty("电影id")
    private Long movieId;

    @ExcelProperty("主键")
    private Long id;

    @ExcelProperty("名称")
    private String mc;
}
