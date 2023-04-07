package cc.onelooker.kaleido.dto.system.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author lijun
 * @date 2023/2/16 0016 14:10
 * @description
 */
@ApiModel
public class SysRoleResourceBindReq {

    @NotNull(message = "角色ID不可为空")
    @ApiModelProperty("角色ID")
    private Long roleId;

    @ApiModelProperty("资源ID")
    private List<Long> resourceIds;

    @NotNull
    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(@NotNull Long roleId) {
        this.roleId = roleId;
    }

    public List<Long> getResourceIds() {
        return resourceIds;
    }

    public void setResourceIds(List<Long> resourceIds) {
        this.resourceIds = resourceIds;
    }
}
