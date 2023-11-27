package cc.onelooker.kaleido.dto.system.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户表请求对象
 *
 * @author cyetstar
 * @date 2022-11-10 16:46:50
 */
@Data
@ApiModel("用户表请求对象")
public class SysUserCreateReq {

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("用户密码密文")
    private String password;

    @ApiModelProperty("用户姓名")
    private String name;

    @ApiModelProperty("用户手机")
    private String mobile;

    @ApiModelProperty("部门编码")
    private String deptCode;

    @ApiModelProperty("过滤码")
    private String filterCode;

    @ApiModelProperty("角色id")
    private Long[] roleIds;
}
