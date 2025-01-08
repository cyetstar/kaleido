package cc.onelooker.kaleido.dto;

import cc.onelooker.kaleido.entity.SysRoleDO;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.Date;
import java.util.List;

/**
 * 角色表DTO
 *
 * @author cyetstar
 * @date 2022-04-26 00:41:00
 * @see SysRoleDO
 */
@Data
public class SysRoleDTO implements GrantedAuthority, IDictionary<Long> {
    private static final long serialVersionUID = 1627058453403800691L;

    /**
     * 角色id
     */
    private Long id;

    /**
     * 父角色id
     */
    private Long parentId;

    /**
     * 角色code
     */
    private String code;

    /**
     * 角色名称
     */
    private String name;

    /**
     * 简介
     */
    private String description;

    /**
     * 子节点数
     */
    private Integer subCount;

    /**
     * 是否已删除1：已删除，0：未删除
     */
    private Boolean isDeleted;

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
     * 权限
     */
    private List<String> permissions;

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getAuthority() {
        return getCode();
    }

    @Override
    public String getTitle() {
        return getName();
    }
}
