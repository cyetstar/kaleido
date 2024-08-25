package cc.onelooker.kaleido.entity;

import cc.onelooker.kaleido.dto.SysDictDTO;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zjjcnt.common.core.entity.IdEntity;
import lombok.Data;

import java.util.Date;

/**
 * 字典表DO
 *
 * @author cyetstar
 * @date 2022-04-26 00:40:52
 * @see SysDictDTO
 */
@Data
@TableName("sys_dict")
public class SysDictDO implements IdEntity<Long> {
    private static final long serialVersionUID = 3042827039492364907L;

    /**
     * 字典详情id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 字典类型
     */
    @TableField(value = "dict_type")
    private String dictType;

    /**
     * 字典标签
     */
    @TableField(value = "label")
    private String label;

    /**
     * 字典值
     */
    @TableField(value = "value")
    private String value;

    /**
     * 顺序号
     */
    @TableField(value = "sort")
    private Integer sort;

    /**
     * 是否启用
     */
    @TableField(value = "is_enabled")
    private Boolean isEnabled;

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
