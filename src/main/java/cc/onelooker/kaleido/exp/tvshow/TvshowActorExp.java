package cc.onelooker.kaleido.exp.tvshow;

import lombok.Data;
import com.alibaba.excel.annotation.ExcelProperty;

import java.lang.Long;
import java.lang.String;

/**
 * 剧集演职员导出对象
 *
 * @author cyetstar
 * @date 2023-11-27 22:51:36
 *
 */
@Data
public class TvshowActorExp{

    @ExcelProperty("主键")
    private Long id;

    @ExcelProperty("姓名")
    private String name;

    @ExcelProperty("原名")
    private String originalName;

    @ExcelProperty("豆瓣编号")
    private String doubanId;
}
