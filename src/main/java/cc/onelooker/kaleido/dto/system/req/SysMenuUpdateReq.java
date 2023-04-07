package cc.onelooker.kaleido.dto.system.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 菜单表请求对象
 *
 * @author xiadawei
 * @date 2022-11-13 01:12:24
 */
@Data
@ApiModel("菜单表请求对象")
public class SysMenuUpdateReq {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("父菜单id")
    private Long parentId;

    @ApiModelProperty("菜单类型")
    private String type;

    @ApiModelProperty("菜单路径")
    private String path;

    @ApiModelProperty("菜单名称")
    private String name;

    @ApiModelProperty("菜单标题")
    private String title;

    @ApiModelProperty("描述")
    private String description;

    @ApiModelProperty("重定向路径")
    private String redirect;

    @ApiModelProperty("菜单组件")
    private String component;

    @ApiModelProperty("菜单图标")
    private String icon;

    @ApiModelProperty("额外信息，json格式")
    private String meta;

    @ApiModelProperty("是否显示根")
    private String isShowRoot;

    @ApiModelProperty("是否显示")
    private String isHidden;

    @ApiModelProperty("顺序号")
    private Integer orderNum;

    @ApiModelProperty("子节点数")
    private Integer subCount;

    @ApiModelProperty("所属应用")
    private String app;

    @ApiModelProperty("")
    private String permission;

    @ApiModelProperty("是否已删除1：已删除，0：未删除")
    private String isDeleted;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("更新时间")
    private Date updateTime;

    @ApiModelProperty("创建人")
    private String createdBy;

    @ApiModelProperty("更新人")
    private String updatedBy;
}
