package cc.onelooker.kaleido.entity.system;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zjjcnt.common.core.entity.IdEntity;
import lombok.Data;

import java.util.Date;

/**
 * 角色和菜单关系表DO
 *
 * @author xiadawei
 * @date 2022-11-13 01:12:24
 * @see cc.onelooker.kaleido.dto.system.SysRoleMenuDTO
 */
@Data
@TableName("sys_role_menu")
public class SysRoleMenuDO implements IdEntity<Long> {
    private static final long serialVersionUID = 5714665482635652641L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 菜单id
     */
    @TableField(value = "menu_id")
    private Long menuId;

    /**
     * 角色id
     */
    @TableField(value = "role_id")
    private Long roleId;

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


    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}
