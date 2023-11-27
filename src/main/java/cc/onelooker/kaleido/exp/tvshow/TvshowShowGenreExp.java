package cc.onelooker.kaleido.exp.tvshow;

import lombok.Data;
import com.alibaba.excel.annotation.ExcelProperty;

import java.lang.Long;

/**
 * 剧集类型关联表导出对象
 *
 * @author cyetstar
 * @date 2023-11-27 22:51:36
 *
 */
@Data
public class TvshowShowGenreExp{

    @ExcelProperty("主键")
    private Long id;

    @ExcelProperty("剧集id")
    private Long showId;

    @ExcelProperty("类型id")
    private Long genreId;
}
