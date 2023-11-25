package cc.onelooker.kaleido.exp.movie;

import lombok.Data;
import com.alibaba.excel.annotation.ExcelProperty;

import java.lang.Long;
import java.lang.String;

/**
 * 国家地区导出对象
 *
 * @author cyetstar
 * @date 2023-11-26 01:19:02
 *
 */
@Data
public class MovieCountryExp{

    @ExcelProperty("主键")
    private Long id;

    @ExcelProperty("标识")
    private String tag;
}
