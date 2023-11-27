package cc.onelooker.kaleido.dto.system.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 角色和菜单关系表响应对象
 *
 * @author cyetstar
 * @date 2022-11-13 01:12:24
 */
@Data
@ApiModel("角色和菜单关系表响应对象")
public class SysRoleMenuCreateResp {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("菜单id")
    private Long menuId;

    @ApiModelProperty("角色id")
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
