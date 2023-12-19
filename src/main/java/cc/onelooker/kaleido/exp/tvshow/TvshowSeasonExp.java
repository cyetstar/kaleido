package cc.onelooker.kaleido.exp.tvshow;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * 单季导出对象
 *
 * @author cyetstar
 * @date 2023-11-27 22:51:36
 *
 */
@Data
public class TvshowSeasonExp{

    @ExcelProperty("主键")
    private Long id;

    @ExcelProperty("剧集id")
    private Long showId;

    @ExcelProperty("标题")
    private String title;

    @ExcelProperty("简介")
    private String summary;

    @ExcelProperty("季号")
    private Integer seasonIndex;

    @ExcelProperty("海报")
    private String thumb;

    @ExcelProperty("艺术图")
    private String art;

    @ExcelProperty("加入时间")
    private Long addedAt;

    @ExcelProperty("更新时间")
    private Long updatedAt;
}
