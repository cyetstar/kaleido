package cc.onelooker.kaleido.exp.tvshow;

import lombok.Data;
import com.alibaba.excel.annotation.ExcelProperty;

import java.math.BigDecimal;
import java.lang.Long;
import java.lang.String;
import java.lang.Integer;

/**
 * 单集导出对象
 *
 * @author cyetstar
 * @date 2023-11-27 22:51:36
 *
 */
@Data
public class TvshowEpisodeExp{

    @ExcelProperty("主键")
    private Long id;

    @ExcelProperty("单季id")
    private Long seasonId;

    @ExcelProperty("标题")
    private String title;

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

    @ExcelProperty("集号")
    private Integer episodeIndex;

    @ExcelProperty("评分")
    private BigDecimal rating;

    @ExcelProperty("片长(秒)")
    private Integer duration;

    @ExcelProperty("海报")
    private String thumb;

    @ExcelProperty("艺术图")
    private String art;

    @ExcelProperty("加入时间")
    private Long addedAt;

    @ExcelProperty("更新时间")
    private Long updatedAt;
}
