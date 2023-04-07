package cc.onelooker.kaleido.dto.business.exp;

import lombok.Data;
import com.alibaba.excel.annotation.ExcelProperty;

import java.lang.Long;

/**
 * 导出对象
 *
 * @author cyetstar
 * @date 2023-04-06 13:32:12
 *
 */
@Data
public class MovieDirectorExp{

    @ExcelProperty("")
    private Long movieId;

    @ExcelProperty("")
    private Long directorId;
}
