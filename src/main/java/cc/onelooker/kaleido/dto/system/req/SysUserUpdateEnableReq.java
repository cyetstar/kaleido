package cc.onelooker.kaleido.dto.system.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author lijun
 * @date 2023/2/16 0016 11:39
 * @description
 */
@ApiModel("更新用户状态")
public class SysUserUpdateEnableReq {

    @ApiModelProperty("用户id")
    private Long userId;

    @ApiModelProperty("是否启用")
    private Boolean enable;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }
}
