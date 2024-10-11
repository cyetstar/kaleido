package cc.onelooker.kaleido.dto.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 发布记录请求对象
 *
 * @author cyetstar
 * @date 2023-12-18 14:53:14
 */
@Data
@ApiModel("发布记录请求对象")
public class ThreadUpdateStatusReq {

    @ApiModelProperty("主键")
    private String id;

    @ApiModelProperty("状态")
    private String status;

}
