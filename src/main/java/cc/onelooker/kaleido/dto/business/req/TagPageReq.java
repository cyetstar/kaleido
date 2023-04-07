package cc.onelooker.kaleido.dto.business.req;

import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.lang.Long;
import java.lang.String;

/**
 * 请求对象
 *
 * @author cyetstar
 * @date 2023-04-06 13:32:12
 *
 */
@Data
@ApiModel("请求对象")
public class TagPageReq{

    @ApiModelProperty("")
    private String category;

    @ApiModelProperty("")
    private String value;
}
