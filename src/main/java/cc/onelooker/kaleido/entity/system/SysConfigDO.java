package cc.onelooker.kaleido.entity.system;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zjjcnt.common.core.entity.IdEntity;
import lombok.Data;

import java.util.Date;

/**
 * 系统配置表DO
 *
 * @author cyetstar
 * @date 2022-11-13 00:43:40
 * @see cc.onelooker.kaleido.dto.system.SysConfigDTO
 */
@Data
@TableName("sys_config")
public class SysConfigDO implements IdEntity<Long> {
    private static final long serialVersionUID = -9166209979982836890L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 配置名称
     */
    @TableField(value = "config_name")
    private String configName;

    /**
     * 配置键名
     */
    @TableField(value = "config_key")
    private String configKey;

    /**
     * 配置键值
     */
    @TableField(value = "config_value")
    private String configValue;

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
