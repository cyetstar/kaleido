package cc.onelooker.kaleido.dto.business.exp;

import lombok.Data;
import com.alibaba.excel.annotation.ExcelProperty;

import java.lang.Long;
import java.util.Date;
import java.lang.String;
import java.lang.Double;
import java.lang.Integer;
import java.lang.Boolean;

/**
 * 导出对象
 *
 * @author cyetstar
 * @date 2023-04-06 13:32:12
 *
 */
@Data
public class ThreadExp{

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
