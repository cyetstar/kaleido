package cc.onelooker.kaleido.dto.business.resp;

import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.lang.Long;
import java.lang.String;

/**
 * 响应对象
 *
 * @author cyetstar
 * @date 2023-04-06 13:32:12
 *
 */
@Data
@ApiModel("响应对象")
public class ActorCreateResp{

    @ApiModelProperty("")
    private Long id;

    @ApiModelProperty("")
    private String category;

    @ApiModelProperty("")
    private String doubanId;

    @ApiModelProperty("")
    private String name;

    @ApiModelProperty("")
    private String orders;

    @ApiModelProperty("")
    private String originalname;

    @ApiModelProperty("")
    private String profile;

    @ApiModelProperty("")
    private String role;

    @ApiModelProperty("")
    private String thumb;
}
