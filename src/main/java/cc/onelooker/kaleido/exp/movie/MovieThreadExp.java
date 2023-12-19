package cc.onelooker.kaleido.exp.movie;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 电影发布记录导出对象
 *
 * @author cyetstar
 * @date 2023-12-18 14:53:14
 *
 */
@Data
public class MovieThreadExp{

    @ExcelProperty("")
    private Long id;

    @ExcelProperty("")
    private Date createdAt;

    @ExcelProperty("")
    private String doubanId;

    @ExcelProperty("")
    private String imdb;

    @ExcelProperty("")
    private String links;

    @ExcelProperty("")
    private String publishDate;

    @ExcelProperty("")
    private Double rating;

    @ExcelProperty("")
    private Integer status;

    @ExcelProperty("")
    private Boolean thanks;

    @ExcelProperty("")
    private String title;

    @ExcelProperty("")
    private String type;

    @ExcelProperty("")
    private Date updatedAt;

    @ExcelProperty("")
    private String url;
}
