package cc.onelooker.kaleido.dto.movie.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 演职员响应对象
 *
 * @author cyetstar
 * @date 2023-04-18 23:04:56
 */
@Data
@ApiModel("演职员响应对象")
public class MovieActorSearchResp {

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("姓名")
    private String xm;

    @ApiModelProperty("本名")
    private String bm;

}
