package cc.onelooker.kaleido.dto;

import java.util.Date;

import cc.onelooker.kaleido.entity.SysRoleResourceDO;
import lombok.Data;
import com.zjjcnt.common.core.dto.BaseDTO;

/**
 * 角色和资源关系表DTO
 *
 * @author xiadawei
 * @date 2022-08-15 17:38:57
 * @see SysRoleResourceDO
 */
@Data
public class SysRoleResourceDTO implements BaseDTO<Long> {
    private static final long serialVersionUID = -2089366305369381276L;

    /**
     * 关系id
     */
    private Long id;

    /**
     * 角色id
     */
    private Long resourceId;

    /**
     * 资源id
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
