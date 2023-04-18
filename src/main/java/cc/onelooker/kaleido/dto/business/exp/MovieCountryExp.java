package cc.onelooker.kaleido.dto.business.exp;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * 国家地区导出对象
 *
 * @author cyetstar
 * @date 2023-04-18 23:04:56
 *
 */
@Data
public class MovieCountryExp{

    @ExcelProperty("主键")
    private Long id;

    @ExcelProperty("名称")
    private String mc;
}
