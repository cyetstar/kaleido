package cc.onelooker.kaleido.dto.system.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 系统配置表响应对象
 *
 * @author xiadawei
 * @date 2022-11-13 00:43:42
 */
@Data
@ApiModel("系统配置表响应对象")
public class SysConfigViewResp {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("配置名称")
    private String configName;

    @ApiModelProperty("配置键名")
    private String configKey;

    @ApiModelProperty("配置键值")
    private String configValue;

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
