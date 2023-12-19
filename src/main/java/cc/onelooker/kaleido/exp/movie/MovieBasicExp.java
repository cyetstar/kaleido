package cc.onelooker.kaleido.exp.movie;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 电影导出对象
 *
 * @author cyetstar
 * @date 2023-11-26 01:19:02
 *
 */
@Data
public class MovieBasicExp{

    @ExcelProperty("主键")
    private Long id;

    @ExcelProperty("电影名")
    private String title;

    @ExcelProperty("原片名")
    private String originalTitle;

    @ExcelProperty("排序名")
    private String titleSort;

    @ExcelProperty("首映年份")
    private String year;

    @ExcelProperty("海报")
    private String thumb;

    @ExcelProperty("艺术图")
    private String art;

    @ExcelProperty("用户评分")
    private Integer userRating;

    @ExcelProperty("简介")
    private String summary;

    @ExcelProperty("片长(秒)")
    private Integer duration;

    @ExcelProperty("电影评级")
    private String contentRating;

    @ExcelProperty("首映日期")
    private String originallyAvailableAt;

    @ExcelProperty("电影公司")
    private String studio;

    @ExcelProperty("评分")
    private BigDecimal rating;

    @ExcelProperty("观看时间")
    private Long lastViewedAt;

    @ExcelProperty("观看次数")
    private Integer viewCount;

    @ExcelProperty("IMDb编号")
    private String imdb;

    @ExcelProperty("豆瓣编号")
    private String doubanId;

    @ExcelProperty("加入时间")
    private Long addedAt;

    @ExcelProperty("更新时间")
    private Long updatedAt;
}
