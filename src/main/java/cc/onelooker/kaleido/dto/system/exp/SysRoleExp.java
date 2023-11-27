package cc.onelooker.kaleido.dto.system.exp;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 角色表导出对象
 *
 * @author cyetstar
 * @date 2022-11-19 23:17:28
 */
@Data
public class SysRoleExp {

    @ExcelProperty("角色id")
    private Long id;

    @ExcelProperty("父角色id")
    private Long parentId;

    @ExcelProperty("角色code")
    private String code;

    @ExcelProperty("角色名称")
    private String name;

    @ExcelProperty("简介")
    private String description;

    @ExcelProperty("子节点数")
    private Integer subCount;

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
