package cc.onelooker.kaleido.entity.system;

import cc.onelooker.kaleido.dto.system.SysResourceDTO;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zjjcnt.common.core.entity.IdEntity;
import lombok.Data;

import java.util.Date;

/**
 * 资源表DO
 *
 * @author cyetstar
 * @date 2022-08-15 17:38:53
 * @see SysResourceDTO
 */
@Data
@TableName("sys_resource")
public class SysResourceDO implements IdEntity<Long> {
    private static final long serialVersionUID = -3591764674992239464L;

    /**
     * 资源id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 资源code
     */
    @TableField(value = "code")
    private String code;

    /**
     * 资源类型
     */
    @TableField(value = "type")
    private String type;

    /**
     * 资源名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 资源url
     */
    @TableField(value = "url")
    private String url;

    /**
     * 资源方法
     */
    @TableField(value = "method")
    private String method;

    /**
     * 简介
     */
    @TableField(value = "description")
    private String description;

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
