package cc.onelooker.kaleido.dto;

import cc.onelooker.kaleido.entity.SysConfigDO;
import com.zjjcnt.common.core.dto.BaseDTO;
import lombok.Data;

import java.util.Date;

/**
 * 系统配置表DTO
 *
 * @author cyetstar
 * @date 2022-04-26 00:41:00
 * @see SysConfigDO
 */
@Data
public class SysConfigDTO implements BaseDTO<Long> {
    private static final long serialVersionUID = 1812766589691059588L;

    /**
     * id
     */
    private Long id;

    /**
     * 配置名称
     */
    private String configName;

    /**
     * 配置键名
     */
    private String configKey;

    /**
     * 配置键值
     */
    private String configValue;

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

    public SysConfigDTO() {
    }

    public SysConfigDTO(String configName, String configKey, String configValue) {
        this.configName = configName;
        this.configKey = configKey;
        this.configValue = configValue;
    }
}
