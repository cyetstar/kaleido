package cc.onelooker.kaleido.entity;

import cc.onelooker.kaleido.dto.SysUserRoleDTO;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.zjjcnt.common.core.entity.IdEntity;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableField;

/**
 * 用户和角色关系表DO
 *
 * @author xiadawei
 * @date 2022-04-26 00:40:59
 * @see SysUserRoleDTO
 */
@Data
@TableName("sys_user_role")
public class SysUserRoleDO implements IdEntity<Long> {
    private static final long serialVersionUID = -1715193781901722476L;

    /**
     * 关系id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 用户id
     */
    @TableField(value = "user_id")
    private Long userId;

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
