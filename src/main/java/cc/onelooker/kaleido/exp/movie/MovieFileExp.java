package cc.onelooker.kaleido.exp.movie;

import lombok.Data;
import com.alibaba.excel.annotation.ExcelProperty;

import java.lang.Long;
import java.lang.String;
import java.lang.Integer;

/**
 * 电影文件导出对象
 *
 * @author cyetstar
 * @date 2023-06-13 20:35:11
 *
 */
@Data
public class MovieFileExp{

    @ExcelProperty("主键")
    private Long id;

    @ExcelProperty("电影id")
    private Long movieId;

    @ExcelProperty("文件名")
    private String wjm;

    @ExcelProperty("文件路径")
    private String wjlj;

    @ExcelProperty("文件大小")
    private Integer wjdx;
}
