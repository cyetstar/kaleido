package cc.onelooker.kaleido.dto.system.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Author cyetstar
 * @Date 2022-11-17 16:56:00
 * @Description TODO
 */
@Data
@ApiModel("角色授权请求对象")
public class SysRoleAuthorizeReq {

    @ApiModelProperty("角色id")
    private Long id;

    @ApiModelProperty("资源id")
    private List<Long> resourceIdList;
}
