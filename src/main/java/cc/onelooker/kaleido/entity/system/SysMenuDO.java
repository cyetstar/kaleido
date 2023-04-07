package cc.onelooker.kaleido.entity.system;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zjjcnt.common.core.entity.IdEntity;
import lombok.Data;

import java.util.Date;

/**
 * 菜单表DO
 *
 * @author xiadawei
 * @date 2022-11-13 01:12:24
 * @see cc.onelooker.kaleido.dto.system.SysMenuDTO
 */
@Data
@TableName("sys_menu")
public class SysMenuDO implements IdEntity<Long> {
    private static final long serialVersionUID = 4292939240075739549L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 父菜单id
     */
    @TableField(value = "parent_id")
    private Long parentId;

    /**
     * 菜单类型
     */
    @TableField(value = "type")
    private String type;

    /**
     * 菜单路径
     */
    @TableField(value = "path")
    private String path;

    /**
     * 菜单名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 菜单标题
     */
    @TableField(value = "title")
    private String title;

    /**
     * 描述
     */
    @TableField(value = "description")
    private String description;

    /**
     * 重定向路径
     */
    @TableField(value = "redirect")
    private String redirect;

    /**
     * 菜单组件
     */
    @TableField(value = "component")
    private String component;

    /**
     * 菜单图标
     */
    @TableField(value = "icon")
    private String icon;

    /**
     * 额外信息，json格式
     */
    @TableField(value = "meta")
    private String meta;

    /**
     * 是否显示根
     */
    @TableField(value = "is_show_root")
    private String isShowRoot;

    /**
     * 是否显示
     */
    @TableField(value = "is_hidden")
    private String isHidden;

    /**
     * 顺序号
     */
    @TableField(value = "order_num")
    private Integer orderNum;

    /**
     * 子节点数
     */
    @TableField(value = "sub_count")
    private Integer subCount;

    /**
     * 所属应用
     */
    @TableField(value = "app")
    private String app;

    /**
     * 是否已删除1：已删除，0：未删除
     */
    @TableField(value = "is_deleted")
    private String isDeleted;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    private Date updateTime;

    /**
     * 创建人
     */
    @TableField(value = "created_by")
    private String createdBy;

    /**
     * 更新人
     */
    @TableField(value = "updated_by")
    private String updatedBy;

    /**
     * 
     */
    @TableField(value = "permission")
    private String permission;


    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}
