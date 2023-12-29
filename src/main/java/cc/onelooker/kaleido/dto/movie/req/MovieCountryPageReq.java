package cc.onelooker.kaleido.dto.movie.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 国家地区请求对象
 *
 * @author cyetstar
 * @date 2023-11-26 01:19:02
 */
@Data
@ApiModel("国家地区请求对象")
public class MovieCountryPageReq {

    @ApiModelProperty("标识")
    private String tag;

}
