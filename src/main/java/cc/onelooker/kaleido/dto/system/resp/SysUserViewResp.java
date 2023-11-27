package cc.onelooker.kaleido.dto.system.resp;

import com.zjjcnt.common.core.annotation.DictSzbm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户表响应对象
 *
 * @author cyetstar
 * @date 2022-11-10 16:46:50
 */
@Data
@ApiModel("用户表响应对象")
public class SysUserViewResp {

    @ApiModelProperty("用户id")
    private Long id;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("用户姓名")
    private String name;

    @ApiModelProperty("用户手机")
    private String mobile;

    @ApiModelProperty("简介")
    private String description;

    @DictSzbm
    @ApiModelProperty("部门编码")
    private String deptCode;

    @DictSzbm
    @ApiModelProperty("查询过滤码")
    private String filterCode;

    @ApiModelProperty("角色id")
    private String[] roleIds;
}
