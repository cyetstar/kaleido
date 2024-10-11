package cc.onelooker.kaleido.dto.resp;

import com.zjjcnt.common.core.annotation.DictSzbm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 用户表响应对象
 *
 * @author cyetstar
 * @date 2022-11-10 16:46:50
 */
@Data
@ApiModel("用户表响应对象")
public class SysUserPageResp {

    @ApiModelProperty("用户id")
    private Long id;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("用户姓名")
    private String name;

    @ApiModelProperty("用户手机")
    private String mobile;

    @DictSzbm
    @ApiModelProperty("部门代码")
    private String deptCode;

    @DictSzbm
    @ApiModelProperty("查询过滤码")
    private String filterCode;

    @ApiModelProperty("是否启用")
    private Boolean enabled;

    @ApiModelProperty("角色列表")
    private List<SysRolePageResp> sysRoleList;

    @Data
    public static class SysRolePageResp {

        @ApiModelProperty("角色名称")
        private String name;

    }

}
