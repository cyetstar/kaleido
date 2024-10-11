package cc.onelooker.kaleido.dto.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 属性响应对象
 *
 * @author cyetstar
 * @date 2024-03-12 17:48:21
 *
 */
@Data
@ApiModel("属性响应对象")
public class AttributeViewResp{

    @ApiModelProperty("主键")
    private String id;

    @ApiModelProperty("属性值")
    private String value;

    @ApiModelProperty("类型")
    private String type;
}
