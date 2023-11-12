package cc.onelooker.kaleido.dto.movie.exp;

import com.alibaba.excel.annotation.ExcelProperty;
import com.zjjcnt.common.core.annotation.Dict;
import lombok.Data;

/**
 * 电影唯一标识导出对象
 *
 * @author cyetstar
 * @date 2023-04-18 23:04:56
 *
 */
@Data
public class MovieUniqueidExp{

    @ExcelProperty("主键")
    private Long id;

    @ExcelProperty("电影id")
    private Long movieId;

    @ExcelProperty("唯一标识")
    private String uid;

    @Dict("sfmr")
    @ExcelProperty("是否默认")
    private String sfmr;

    @ExcelProperty("标识类型")
    private String bslx;
}
