package cc.onelooker.kaleido.dto.system.resp;

import com.zjjcnt.common.core.annotation.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 菜单表响应对象
 *
 * @author xiadawei
 * @date 2022-11-13 01:12:24
 */
@Data
@ApiModel("菜单表响应对象")
public class SysMenuPageResp {

    @ApiModelProperty("id")
    private Long id;

    @Dict("sysMenu")
    @ApiModelProperty("父菜单id")
    private Long parentId;

    @ApiModelProperty("菜单名称")
    private String name;

    @ApiModelProperty("菜单标题")
    private String title;

    @ApiModelProperty("菜单图标")
    private String icon;

    @ApiModelProperty("模块")
    private String app;

    @Dict("sfbz")
    @ApiModelProperty("是否显示")
    private String isHidden;

    @ApiModelProperty("顺序号")
    private Integer orderNum;

}
