package cc.onelooker.kaleido.exp.movie;

import lombok.Data;
import com.alibaba.excel.annotation.ExcelProperty;

import java.lang.Long;
import java.lang.String;

/**
 * 电影集合导出对象
 *
 * @author cyetstar
 * @date 2023-11-26 01:19:02
 *
 */
@Data
public class MovieCollectionExp{

    @ExcelProperty("主键")
    private Long id;

    @ExcelProperty("标题")
    private String title;

    @ExcelProperty("简介")
    private String summary;
}
