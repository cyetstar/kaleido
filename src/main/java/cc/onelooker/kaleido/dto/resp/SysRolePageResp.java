package cc.onelooker.kaleido.dto.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 角色表响应对象
 *
 * @author cyetstar
 * @date 2022-11-13 00:43:42
 */
@Data
@ApiModel("角色表响应对象")
public class SysRolePageResp {

    @ApiModelProperty("角色id")
    private Long id;

    @ApiModelProperty("角色代码")
    private String code;

    @ApiModelProperty("角色名称")
    private String name;

    @ApiModelProperty("简介")
    private String description;
}
