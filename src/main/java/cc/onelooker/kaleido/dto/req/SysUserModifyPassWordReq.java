package cc.onelooker.kaleido.dto.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author lijun
 * @date 2023/2/16 0016 15:50
 * @description
 */
@ApiModel("修改用户密码")
public class SysUserModifyPassWordReq {

    @ApiModelProperty("用户id")
    @NotNull(message = "用户id不可为空")
    private Long id;

    @ApiModelProperty("新密码")
    @NotBlank(message = "新密码不可为空")
    private String password;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}