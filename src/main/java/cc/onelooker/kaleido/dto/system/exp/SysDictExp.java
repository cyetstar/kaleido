package cc.onelooker.kaleido.dto.system.exp;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 字典表导出对象
 *
 * @author xiadawei
 * @date 2022-11-19 23:17:28
 */
@Data
public class SysDictExp {

    @ExcelProperty("字典详情id")
    private Long id;

    @ExcelProperty("字典类型")
    private String dictType;

    @ExcelProperty("字典标签")
    private String label;

    @ExcelProperty("字典值")
    private String value;

    @ExcelProperty("顺序号")
    private Integer sort;

    @ExcelProperty("是否启用")
    private Boolean isEnabled;

    @ExcelProperty("是否已删除1：已删除，0：未删除")
    private Boolean isDeleted;

    @ExcelProperty("创建时间")
    private Date createTime;

    @ExcelProperty("更新时间")
    private Date updateTime;

    @ExcelProperty("创建人")
    private String createdBy;

    @ExcelProperty("更新人")
    private String updatedBy;
}
