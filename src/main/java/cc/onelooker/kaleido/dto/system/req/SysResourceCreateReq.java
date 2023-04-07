package cc.onelooker.kaleido.dto.system.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 资源表请求对象
 *
 * @author xiadawei
 * @date 2022-11-13 00:43:42
 */
@Data
@ApiModel("资源表请求对象")
public class SysResourceCreateReq {

    @ApiModelProperty("资源code")
    private String code;

    @ApiModelProperty("资源类型")
    private String type;

    @ApiModelProperty("资源名称")
    private String name;

    @ApiModelProperty("资源url")
    private String url;

    @ApiModelProperty("资源方法")
    private String method;

    @ApiModelProperty("简介")
    private String description;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("更新时间")
    private Date updateTime;

    @ApiModelProperty("创建人")
    private String createdBy;

    @ApiModelProperty("更新人")
    private String updatedBy;
}
