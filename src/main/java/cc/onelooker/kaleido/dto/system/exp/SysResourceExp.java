package cc.onelooker.kaleido.dto.system.exp;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 资源表导出对象
 *
 * @author cyetstar
 * @date 2022-11-19 23:17:28
 */
@Data
public class SysResourceExp {

    @ExcelProperty("资源id")
    private Long id;

    @ExcelProperty("资源code")
    private String code;

    @ExcelProperty("资源类型")
    private String type;

    @ExcelProperty("资源名称")
    private String name;

    @ExcelProperty("资源url")
    private String url;

    @ExcelProperty("资源方法")
    private String method;

    @ExcelProperty("简介")
    private String description;

    @ExcelProperty("创建时间")
    private Date createTime;

    @ExcelProperty("更新时间")
    private Date updateTime;

    @ExcelProperty("创建人")
    private String createdBy;

    @ExcelProperty("更新人")
    private String updatedBy;
}
