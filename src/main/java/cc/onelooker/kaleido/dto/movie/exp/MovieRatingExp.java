package cc.onelooker.kaleido.dto.movie.exp;

import com.alibaba.excel.annotation.ExcelProperty;
import com.zjjcnt.common.core.annotation.Dict;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 电影评分导出对象
 *
 * @author cyetstar
 * @date 2023-04-18 23:04:56
 *
 */
@Data
public class MovieRatingExp{

    @ExcelProperty("主键")
    private Long id;

    @ExcelProperty("电影id")
    private Long movieId;

    @ExcelProperty("平均分")
    private BigDecimal pjf;

    @ExcelProperty("投票数")
    private Integer tps;

    @ExcelProperty("最大值")
    private Integer zdz;

    @Dict("sfmr")
    @ExcelProperty("是否默认")
    private String sfmr;

    @ExcelProperty("标识类型")
    private String bslx;
}
