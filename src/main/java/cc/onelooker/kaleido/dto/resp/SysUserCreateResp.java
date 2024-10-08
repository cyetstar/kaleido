package cc.onelooker.kaleido.dto.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 用户表响应对象
 *
 * @author cyetstar
 * @date 2022-11-10 16:46:50
 */
@Data
@ApiModel("用户表响应对象")
public class SysUserCreateResp {

    @ApiModelProperty("用户id")
    private Long id;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("用户密码密文")
    private String password;

    @ApiModelProperty("用户姓名")
    private String name;

    @ApiModelProperty("用户手机")
    private String mobile;

    @ApiModelProperty("简介")
    private String description;

    @ApiModelProperty("部门编码")
    private String deptCode;

    @ApiModelProperty("部门id")
    private Long deptId;

    @ApiModelProperty("是否有效用户")
    private Boolean isEnabled;

    @ApiModelProperty("账号是否未过期")
    private Boolean isAccountNonExpired;

    @ApiModelProperty("密码是否未过期")
    private Boolean isCredentialsNonExpired;

    @ApiModelProperty("是否未锁定")
    private Boolean isAccountNonLocked;

    @ApiModelProperty("是否已删除1：已删除，0：未删除")
    private Boolean isDeleted;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("更新时间")
    private Date updateTime;

    @ApiModelProperty("创建人")
    private String createdBy;

    @ApiModelProperty("更新人")
    private String updatedBy;

    @ApiModelProperty("")
    private String filterCode;
}
