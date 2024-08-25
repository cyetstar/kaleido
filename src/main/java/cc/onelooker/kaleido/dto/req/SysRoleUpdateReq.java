package cc.onelooker.kaleido.dto.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 角色表请求对象
 *
 * @author cyetstar
 * @date 2022-11-13 00:43:42
 */
@Data
@ApiModel("角色表请求对象")
public class SysRoleUpdateReq {

    @ApiModelProperty("角色id")
    private Long id;

    @ApiModelProperty("父角色id")
    private Long parentId;

    @ApiModelProperty("角色code")
    private String code;

    @ApiModelProperty("角色名称")
    private String name;

    @ApiModelProperty("简介")
    private String description;

    @ApiModelProperty("子节点数")
    private Integer subCount;

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
}
