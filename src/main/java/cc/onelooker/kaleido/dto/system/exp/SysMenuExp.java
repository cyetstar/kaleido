package cc.onelooker.kaleido.dto.system.exp;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 菜单表导出对象
 *
 * @author cyetstar
 * @date 2022-11-19 23:17:28
 */
@Data
public class SysMenuExp {

    @ExcelProperty("id")
    private Long id;

    @ExcelProperty("父菜单id")
    private Long parentId;

    @ExcelProperty("菜单类型")
    private String type;

    @ExcelProperty("菜单路径")
    private String path;

    @ExcelProperty("菜单名称")
    private String name;

    @ExcelProperty("菜单标题")
    private String title;

    @ExcelProperty("描述")
    private String description;

    @ExcelProperty("重定向路径")
    private String redirect;

    @ExcelProperty("菜单组件")
    private String component;

    @ExcelProperty("菜单图标")
    private String icon;

    @ExcelProperty("额外信息，json格式")
    private String meta;

    @ExcelProperty("是否显示根")
    private Boolean isShowRoot;

    @ExcelProperty("是否显示")
    private Boolean isHidden;

    @ExcelProperty("顺序号")
    private Integer orderNum;

    @ExcelProperty("子节点数")
    private Integer subCount;

    @ExcelProperty("所属应用")
    private String app;

    @ExcelProperty("")
    private String permission;

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
