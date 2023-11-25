package cc.onelooker.kaleido.exp.movie;

import lombok.Data;
import com.alibaba.excel.annotation.ExcelProperty;

import java.lang.Long;

/**
 * 电影国家地区关联表导出对象
 *
 * @author cyetstar
 * @date 2023-11-26 01:19:02
 *
 */
@Data
public class MovieBasicCountryExp{

    @ExcelProperty("主键")
    private Long id;

    @ExcelProperty("电影id")
    private Long movieId;

    @ExcelProperty("国家地区id")
    private Long movieCountryId;
}
