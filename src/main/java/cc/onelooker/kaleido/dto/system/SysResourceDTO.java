package cc.onelooker.kaleido.dto.system;

import com.zjjcnt.common.core.dto.BaseDTO;
import cc.onelooker.kaleido.entity.system.SysResourceDO;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;

import java.util.Date;
import java.util.List;

/**
 * 资源表DTO
 *
 * @author cyetstar
 * @date 2022-08-15 17:38:57
 * @see SysResourceDO
 */
@Data
public class SysResourceDTO implements GrantedAuthority, Comparable<SysResourceDTO>, BaseDTO<Long> {
    private static final long serialVersionUID = -2578048957402798715L;

    /**
     * 资源id
     */
    private Long id;

    /**
     * 资源code
     */
    private String code;

    /**
     * 资源类型
     */
    private String type;

    /**
     * 资源名称
     */
    private String name;

    /**
     * 资源url
     */
    private String url;

    /**
     * 资源方法
     */
    private String method;

    /**
     * 简介
     */
    private String description;

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

    private boolean checked;

    private List<Long> idArr;

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getAuthority() {
        return getCode();
    }

    @Override
    public int compareTo(SysResourceDTO o) {
        return StringUtils.compare(code, o.code, true);
    }
}
