package cc.onelooker.kaleido.dto.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 别名响应对象
 *
 * @author cyetstar
 * @date 2024-03-12 17:48:21
 *
 */
@Data
@ApiModel("别名响应对象")
public class AlternateTitlePageResp{

    @ApiModelProperty("主键")
    private String id;

    @ApiModelProperty("标题")
    private String title;
}
