package cc.onelooker.kaleido.dto.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 别名请求对象
 *
 * @author cyetstar
 * @date 2024-03-12 17:48:21
 */
@Data
@ApiModel("别名请求对象")
public class AlternateTitleUpdateReq {

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("标题")
    private String title;

}
