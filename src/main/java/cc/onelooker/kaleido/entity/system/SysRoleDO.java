package cc.onelooker.kaleido.entity.system;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zjjcnt.common.core.entity.IdEntity;
import cc.onelooker.kaleido.dto.system.SysRoleDTO;
import lombok.Data;

import java.util.Date;

/**
 * 角色表DO
 *
 * @author cyetstar
 * @date 2022-04-26 00:40:54
 * @see SysRoleDTO
 */
@Data
@TableName("sys_role")
public class SysRoleDO implements IdEntity<Long> {
    private static final long serialVersionUID = 550039153251654784L;

    /**
     * 角色id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 父角色id
     */
    @TableField(value = "parent_id")
    private Long parentId;

    /**
     * 角色code
     */
    @TableField(value = "code")
    private String code;

    /**
     * 角色名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 简介
     */
    @TableField(value = "description")
    private String description;

    /**
     * 子节点数
     */
    @TableField(value = "sub_count")
    private Integer subCount;

    /**
     * 是否已删除1：已删除，0：未删除
     */
    @TableField(value = "is_deleted")
    private Boolean isDeleted;

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
