package cc.onelooker.kaleido.dto.resp;

import com.zjjcnt.common.core.annotation.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 菜单表响应对象
 *
 * @author cyetstar
 * @date 2022-11-13 01:12:24
 */
@Data
@ApiModel("菜单表响应对象")
public class SysMenuPageResp {

    @ApiModelProperty("id")
    private Long id;

    @Dict("sysMenu")
    @ApiModelProperty("父菜单id")
    private String parentId;

    @ApiModelProperty("菜单名称")
    private String name;

    @ApiModelProperty("菜单标题")
    private String title;

    @ApiModelProperty("菜单图标")
    private String icon;

    @ApiModelProperty("模块")
    private String app;

    @ApiModelProperty("路径")
    private String path;

    @Dict("sfbz")
    @ApiModelProperty("是否显示")
    private String isHidden;

    @ApiModelProperty("顺序号")
    private Integer orderNum;

}
