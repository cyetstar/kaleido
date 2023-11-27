package cc.onelooker.kaleido.dto.system;

import com.zjjcnt.common.core.dto.BaseDTO;
import lombok.Data;

import java.util.Date;

/**
 * 角色和菜单关系表DTO
 *
 * @author cyetstar
 * @date 2022-11-13 01:12:24
 * @see cc.onelooker.kaleido.entity.system.SysRoleMenuDO
 */
@Data
public class SysRoleMenuDTO implements BaseDTO<Long> {
    private static final long serialVersionUID = -308415524026135244L;

    /**
     * id
     */
    private Long id;

    /**
     * 菜单id
     */
    private Long menuId;

    /**
     * 角色id
     */
    private Long roleId;

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

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}
