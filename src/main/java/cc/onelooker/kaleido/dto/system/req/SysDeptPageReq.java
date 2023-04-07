package cc.onelooker.kaleido.dto.system.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 部门表请求对象
 *
 * @author xiadawei
 * @date 2022-11-13 01:12:24
 */
@Data
@ApiModel("部门表请求对象")
public class SysDeptPageReq {

    @ApiModelProperty("部门编码")
    private String deptCode;

    @ApiModelProperty("父部门编码")
    private String parentCode;

    @ApiModelProperty("父部门id")
    private Long parentId;

    @ApiModelProperty("部门名称")
    private String deptName;

    @ApiModelProperty("负责人")
    private String leader;

    @ApiModelProperty("联系电话")
    private String phone;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("顺序号")
    private Integer orderNum;

    @ApiModelProperty("子节点数")
    private Integer subCount;

    @ApiModelProperty("是否启用")
    private Boolean isEnabled;

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
    private String ancestors;
}
