package cc.onelooker.kaleido.dto.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 作者响应对象
 *
 * @author cyetstar
 * @date 2024-03-12 17:47:50
 */
@Data
@ApiModel("作者响应对象")
public class AuthorPageResp {

    @ApiModelProperty("主键")
    private String id;

    @ApiModelProperty("姓名")
    private String name;
}
