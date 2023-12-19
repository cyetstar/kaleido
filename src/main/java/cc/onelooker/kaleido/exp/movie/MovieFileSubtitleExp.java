package cc.onelooker.kaleido.exp.movie;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * 电影文件字幕信息导出对象
 *
 * @author cyetstar
 * @date 2023-06-13 20:35:11
 *
 */
@Data
public class MovieFileSubtitleExp{

    @ExcelProperty("主键")
    private Long id;

    @ExcelProperty("电影文件id")
    private Long movieFileId;

    @ExcelProperty("语言")
    private String lanuage;
}
