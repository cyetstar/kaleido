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
public class ThumbViewResp{

    @ApiModelProperty("")
    private Long id;

    @ApiModelProperty("")
    private String aspect;

    @ApiModelProperty("")
    private String preview;

    @ApiModelProperty("")
    private String value;

    @ApiModelProperty("")
    private Long movieId;
}
