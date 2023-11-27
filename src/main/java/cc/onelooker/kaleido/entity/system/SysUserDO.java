package cc.onelooker.kaleido.entity.system;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zjjcnt.common.core.entity.IdEntity;
import lombok.Data;

import java.util.Date;

/**
 * 用户表DO
 *
 * @author cyetstar
 * @date 2022-04-26 00:40:56
 * @see cc.onelooker.kaleido.dto.system.SysUserDTO
 */
@Data
@TableName("sys_user")
public class SysUserDO implements IdEntity<Long> {
    private static final long serialVersionUID = -4200845536799121720L;

    /**
     * 用户id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 用户名
     */
    @TableField(value = "username")
    private String username;

    /**
     * 用户密码密文
     */
    @TableField(value = "password")
    private String password;

    /**
     * 用户姓名
     */
    @TableField(value = "name")
    private String name;

    /**
     * 用户手机
     */
    @TableField(value = "mobile")
    private String mobile;

    /**
     * 简介
     */
    @TableField(value = "description")
    private String description;

    /**
     * 部门编码
     */
    @TableField(value = "dept_code")
    private String deptCode;

    /**
     * 部门id
     */
    @TableField(value = "dept_id")
    private Long deptId;

    /**
     * 是否有效用户
     */
    @TableField(value = "is_enabled")
    private Boolean enabled;

    /**
     * 账号是否未过期
     */
    @TableField(value = "is_account_non_expired")
    private Boolean accountNonExpired;

    /**
     * 密码是否未过期
     */
    @TableField(value = "is_credentials_non_expired")
    private Boolean credentialsNonExpired;

    /**
     * 是否未锁定
     */
    @TableField(value = "is_account_non_locked")
    private Boolean accountNonLocked;

    /**
     * 是否已删除1：已删除，0：未删除
     */
    @TableField(value = "is_deleted")
    private Boolean deleted;

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
     * 过滤码
     */
    @TableField(value = "filter_code")
    private String filterCode;

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}
