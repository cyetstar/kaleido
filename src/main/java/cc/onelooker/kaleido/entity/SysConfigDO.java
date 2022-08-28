package cc.onelooker.kaleido.entity;

import cc.onelooker.kaleido.dto.SysConfigDTO;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.zjjcnt.common.core.entity.IdEntity;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableField;

/**
 * 系统配置表DO
 *
 * @author xiadawei
 * @date 2022-04-26 00:40:51
 * @see SysConfigDTO
 */
@Data
@TableName("sys_config")
public class SysConfigDO implements IdEntity<Long> {
    private static final long serialVersionUID = -911843734432227182L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
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
