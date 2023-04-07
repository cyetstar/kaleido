package cc.onelooker.kaleido.entity.system;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zjjcnt.common.core.entity.IdEntity;
import cc.onelooker.kaleido.dto.system.SysDictTypeDTO;
import lombok.Data;

import java.util.Date;

/**
 * 字典表类型表DO
 *
 * @author xiadawei
 * @date 2022-04-26 00:40:54
 * @see SysDictTypeDTO
 */
@Data
@TableName("sys_dict_type")
public class SysDictTypeDO implements IdEntity<Long> {
    private static final long serialVersionUID = 6473725610018629340L;

    /**
     * 字典id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 字典类型
     */
    @TableField(value = "type")
    private String type;

    /**
     * 字典名称
     */
    @TableField(value = "name")
    private String name;

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
