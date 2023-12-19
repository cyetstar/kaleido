package cc.onelooker.kaleido.exp.movie;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * 语言导出对象
 *
 * @author cyetstar
 * @date 2023-11-26 01:19:02
 *
 */
@Data
public class MovieLanguageExp{

    @ExcelProperty("主键")
    private Long id;

    @ExcelProperty("标识")
    private String tag;
}
