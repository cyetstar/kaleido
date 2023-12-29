package cc.onelooker.kaleido.dto.movie.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 国家地区响应对象
 *
 * @author cyetstar
 * @date 2023-11-26 01:19:02
 */
@Data
@ApiModel("国家地区响应对象")
public class MovieCountryViewResp {

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("标识")
    private String tag;
}
