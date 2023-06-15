package cc.onelooker.kaleido.exp.business;

import lombok.Data;
import com.alibaba.excel.annotation.ExcelProperty;

import java.lang.Long;
import java.lang.String;

/**
 * 电影文件音频信息导出对象
 *
 * @author xiadawei
 * @date 2023-06-13 20:35:11
 *
 */
@Data
public class MovieFileAudioExp{

    @ExcelProperty("主键")
    private Long id;

    @ExcelProperty("电影文件id")
    private Long movieFileId;

    @ExcelProperty("编解码器")
    private String codec;

    @ExcelProperty("语言")
    private String lanuage;

    @ExcelProperty("声道")
    private String channels;
}
