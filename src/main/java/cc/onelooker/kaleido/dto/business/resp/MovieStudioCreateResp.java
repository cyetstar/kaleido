package cc.onelooker.kaleido.dto.business.resp;

import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.lang.Long;

/**
 * 响应对象
 *
 * @author cyetstar
 * @date 2023-04-06 13:32:12
 *
 */
@Data
@ApiModel("响应对象")
public class MovieStudioCreateResp{

    @ApiModelProperty("")
    private Long movieId;

    @ApiModelProperty("")
    private Long studioId;
}
