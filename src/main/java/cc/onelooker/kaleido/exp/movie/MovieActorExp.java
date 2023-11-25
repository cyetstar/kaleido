package cc.onelooker.kaleido.exp.movie;

import lombok.Data;
import com.alibaba.excel.annotation.ExcelProperty;

import java.lang.Long;
import java.lang.String;

/**
 * 演职员导出对象
 *
 * @author cyetstar
 * @date 2023-11-26 01:19:02
 *
 */
@Data
public class MovieActorExp{

    @ExcelProperty("主键")
    private Long id;

    @ExcelProperty("豆瓣编号")
    private String doubanId;

    @ExcelProperty("姓名")
    private String name;

    @ExcelProperty("原名")
    private String originalName;
}
