package cc.onelooker.kaleido.dto.system.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 字典表类型表请求对象
 *
 * @author xiadawei
 * @date 2022-11-13 00:43:42
 */
@Data
@ApiModel("字典表类型表请求对象")
public class SysDictTypeUpdateReq {

    @ApiModelProperty(value = "字典id", required = true)
    @NotNull(message = "主键不能为空")
    private Long id;

    @ApiModelProperty("字典类型")
    private String type;

    @ApiModelProperty("字典名称")
    private String name;

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
