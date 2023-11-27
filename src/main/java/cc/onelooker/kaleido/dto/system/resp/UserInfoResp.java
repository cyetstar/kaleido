package cc.onelooker.kaleido.dto.system.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Author cyetstar
 * @Date 2022-07-06 14:41:00
 * @Description TODO
 */
@Data
public class UserInfoResp {

    @ApiModelProperty("用户编号")
    private Long userId;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("姓名")
    private String name;

    @ApiModelProperty("手机号")
    private String mobile;

    @ApiModelProperty("部门代码")
    private String deptCode;

    @ApiModelProperty("部门名称")
    private String deptName;

    @ApiModelProperty("是否已注册")
    private Boolean signup;

    @ApiModelProperty("openid")
    private String openid;

    @ApiModelProperty("角色列表")
    private List<String> roles;

}
