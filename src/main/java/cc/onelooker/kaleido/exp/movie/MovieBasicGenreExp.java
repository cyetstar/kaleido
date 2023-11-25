package cc.onelooker.kaleido.exp.movie;

import lombok.Data;
import com.alibaba.excel.annotation.ExcelProperty;

import java.lang.Long;

/**
 * 电影类型关联表导出对象
 *
 * @author cyetstar
 * @date 2023-11-26 01:19:02
 *
 */
@Data
public class MovieBasicGenreExp{

    @ExcelProperty("主键")
    private Long id;

    @ExcelProperty("电影id")
    private Long movieId;

    @ExcelProperty("影片类型id")
    private Long movieGenreId;
}
