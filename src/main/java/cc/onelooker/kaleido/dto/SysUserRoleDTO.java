package cc.onelooker.kaleido.dto;

import cc.onelooker.kaleido.entity.SysUserRoleDO;
import com.zjjcnt.common.core.dto.BaseDTO;
import lombok.Data;

import java.util.Date;

/**
 * 用户和角色关系表DTO
 *
 * @author cyetstar
 * @date 2022-04-26 00:41:00
 * @see SysUserRoleDO
 */
@Data
public class SysUserRoleDTO implements BaseDTO<Long> {
    private static final long serialVersionUID = -1910100272238771669L;

    /**
     * 关系id
     */
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

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
