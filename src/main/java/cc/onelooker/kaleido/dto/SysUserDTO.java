package cc.onelooker.kaleido.dto;

import cc.onelooker.kaleido.entity.SysUserDO;
import com.zjjcnt.common.core.dto.BaseDTO;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 用户表DTO
 *
 * @author cyetstar
 * @date 2022-04-26 00:41:00
 * @see SysUserDO
 */
@Data
public class SysUserDTO implements BaseDTO<Long> {
    private static final long serialVersionUID = 2376440228792988238L;

    /**
     * 用户id
     */
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户密码密文
     */
    private String password;

    /**
     * 用户姓名
     */
    private String name;

    /**
     * 用户手机
     */
    private String mobile;

    /**
     * 简介
     */
    private String description;

    /**
     * 部门编码
     */
    private String deptCode;

    /**
     * 部门id
     */
    private Long deptId;

    /**
     * 是否有效用户
     */
    private Boolean enabled;

    /**
     * 账号是否未过期
     */
    private Boolean accountNonExpired;

    /**
     * 密码是否未过期
     */
    private Boolean credentialsNonExpired;

    /**
     * 是否未锁定
     */
    private Boolean accountNonLocked;

    /**
     * 是否已删除1：已删除，0：未删除
     */
    private Boolean deleted;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 创建人
     */
    private String createdBy;

    /**
     * 更新人
     */
    private String updatedBy;

    /**
     * 过滤码
     */
    private String filterCode;

    //输入
    private Long employeeId;

    private String keyword;

    private String password2;

    private Long[] roleIds;

    //输出
    private List<SysRoleDTO> sysRoleDTOList;

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}
