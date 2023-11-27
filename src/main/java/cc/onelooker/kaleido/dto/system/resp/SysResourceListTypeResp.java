package cc.onelooker.kaleido.dto.system.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 资源表请求对象
 *
 * @author cyetstar
 * @date 2022-11-13 00:43:42
 */
@Data
@ApiModel("资源表响应对象")
public class SysResourceListTypeResp {

    @ApiModelProperty("资源id")
    private Long id;

    @ApiModelProperty("资源code")
    private String code;

    @ApiModelProperty("资源类型")
    private String type;

    @ApiModelProperty("资源名称")
    private String name;

}
