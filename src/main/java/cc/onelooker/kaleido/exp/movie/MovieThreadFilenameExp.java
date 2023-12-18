package cc.onelooker.kaleido.exp.movie;

import lombok.Data;
import com.alibaba.excel.annotation.ExcelProperty;

import java.lang.Long;
import java.lang.String;

/**
 * 电影发布文件导出对象
 *
 * @author cyetstar
 * @date 2023-12-18 16:03:32
 *
 */
@Data
public class MovieThreadFilenameExp{

    @ExcelProperty("")
    private Long id;

    @ExcelProperty("")
    private String value;

    @ExcelProperty("")
    private Long threadId;
}
