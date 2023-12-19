package cc.onelooker.kaleido.exp.tvshow;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 剧集导出对象
 *
 * @author cyetstar
 * @date 2023-11-27 22:51:36
 *
 */
@Data
public class TvshowShowExp{

    @ExcelProperty("主键")
    private Long id;

    @ExcelProperty("剧集名")
    private String title;

    @ExcelProperty("原剧集名")
    private String originalTitle;

    @ExcelProperty("制片公司")
    private String studio;

    @ExcelProperty("剧集评级")
    private String contentRating;

    @ExcelProperty("简介")
    private String summary;

    @ExcelProperty("首播年份")
    private String year;

    @ExcelProperty("首播日期")
    private String originallyAvailableAt;

    @ExcelProperty("评分")
    private BigDecimal rating;

    @ExcelProperty("海报")
    private String thumb;

    @ExcelProperty("艺术图")
    private String art;

    @ExcelProperty("季数")
    private Integer totalSeasons;

    @ExcelProperty("加入时间")
    private Long addedAt;

    @ExcelProperty("更新时间")
    private Long updatedAt;
}
