package cc.onelooker.kaleido.dto.resp;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 字典表类型表响应对象
 *
 * @author cyetstar
 * @date 2022-11-13 00:43:42
 */
@Data
@ApiModel("字典表类型表响应对象")
public class SysDictTypePageResp {

    @ApiModelProperty("字典id")
    private Long id;

    @ApiModelProperty("字典类型")
    private String type;

    @ApiModelProperty("字典名称")
    private String name;

    @ApiModelProperty("是否已删除1：已删除，0：未删除")
    private Boolean isDeleted;

    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @ApiModelProperty("更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    @ApiModelProperty("创建人")
    private String createdBy;

    @ApiModelProperty("更新人")
    private String updatedBy;
}
