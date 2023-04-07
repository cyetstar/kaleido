package cc.onelooker.kaleido.dto.system.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author yejianyu
 * @date 2021/2/7
 */
@ApiModel
@Data
public class SysConfigReq {

    @NotNull(message = "配置名称不能为空")
    @ApiModelProperty(value = "配置名称", required = true)
    private String configName;

    @NotBlank(message = "配置键名不能为空")
    @ApiModelProperty(value = "配置键名", required = true)
    private String configKey;

    @NotBlank(message = "配置键值不能为空")
    @ApiModelProperty(value = "配置键值", required = true)
    private String configValue;

}
