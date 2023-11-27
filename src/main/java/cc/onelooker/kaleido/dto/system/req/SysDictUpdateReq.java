package cc.onelooker.kaleido.dto.system.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 字典表请求对象
 *
 * @author cyetstar
 * @date 2022-11-13 00:43:42
 */
@Data
@ApiModel("字典表请求对象")
public class SysDictUpdateReq {

    @ApiModelProperty("字典详情id")
    private Long id;

    @ApiModelProperty("字典类型")
    private String dictType;

    @ApiModelProperty("字典标签")
    private String label;

    @ApiModelProperty("字典值")
    private String value;

    @ApiModelProperty("顺序号")
    private Integer sort;

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
}
