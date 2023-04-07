package cc.onelooker.kaleido.entity.system;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zjjcnt.common.core.entity.IdEntity;
import cc.onelooker.kaleido.dto.system.SysRoleResourceDTO;
import lombok.Data;

import java.util.Date;

/**
 * 角色和资源关系表DO
 *
 * @author xiadawei
 * @date 2022-08-15 17:38:56
 * @see SysRoleResourceDTO
 */
@Data
@TableName("sys_role_resource")
public class SysRoleResourceDO implements IdEntity<Long> {
    private static final long serialVersionUID = 2528482550843234523L;

    /**
     * 关系id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 角色id
     */
    @TableField(value = "resource_id")
    private Long resourceId;

    /**
     * 资源id
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
