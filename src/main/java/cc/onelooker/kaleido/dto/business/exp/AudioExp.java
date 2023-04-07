package cc.onelooker.kaleido.dto.business.exp;

import lombok.Data;
import com.alibaba.excel.annotation.ExcelProperty;

import java.lang.Long;
import java.lang.String;

/**
 * 导出对象
 *
 * @author cyetstar
 * @date 2023-04-06 13:32:12
 *
 */
@Data
public class AudioExp{

    @ExcelProperty("")
    private Long id;

    @ExcelProperty("")
    private String channels;

    @ExcelProperty("")
    private String codec;

    @ExcelProperty("")
    private String language;

    @ExcelProperty("")
    private Long fileinfoId;
}
