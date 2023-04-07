package cc.onelooker.kaleido.dto.system.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 角色和资源关系表请求对象
 *
 * @author xiadawei
 * @date 2022-11-13 00:43:42
 */
@Data
@ApiModel("角色和资源关系表请求对象")
public class SysRoleResourceCreateReq {

    @ApiModelProperty("角色id")
    private Long resourceId;

    @ApiModelProperty("资源id")
    private Long roleId;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("更新时间")
    private Date updateTime;

    @ApiModelProperty("创建人")
    private String createdBy;

    @ApiModelProperty("更新人")
    private String updatedBy;
}
