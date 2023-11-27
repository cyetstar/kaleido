package cc.onelooker.kaleido.dto.system;

import com.zjjcnt.common.core.dto.BaseDTO;
import cc.onelooker.kaleido.entity.system.SysDictTypeDO;
import lombok.Data;

import java.util.Date;

/**
 * 字典表类型表DTO
 *
 * @author cyetstar
 * @date 2022-04-26 00:41:00
 * @see SysDictTypeDO
 */
@Data
public class SysDictTypeDTO implements BaseDTO<Long> {
    private static final long serialVersionUID = -2028867634008904522L;

    /**
     * 字典id
     */
    private Long id;

    /**
     * 字典类型
     */
    private String type;

    /**
     * 字典名称
     */
    private String name;

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

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}
