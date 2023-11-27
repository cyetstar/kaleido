package cc.onelooker.kaleido.exp.movie;

import lombok.Data;
import com.alibaba.excel.annotation.ExcelProperty;

import java.lang.Long;
import java.lang.String;
import java.lang.Integer;

/**
 * 电影文件视频信息导出对象
 *
 * @author cyetstar
 * @date 2023-06-13 20:35:11
 *
 */
@Data
public class MovieFileVideoExp{

    @ExcelProperty("主键")
    private Long id;

    @ExcelProperty("电影文件id")
    private Long movieFileId;

    @ExcelProperty("编解码器")
    private String codec;

    @ExcelProperty("画幅比例")
    private String aspect;

    @ExcelProperty("宽")
    private Integer width;

    @ExcelProperty("高")
    private Integer height;

    @ExcelProperty("时长")
    private Integer durationinseconds;

    @ExcelProperty("立体声模式")
    private String stereomode;
}
