package cc.onelooker.kaleido.dto.movie.req;

import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.lang.Long;
import java.lang.String;

/**
 * 电影唯一标识请求对象
 *
 * @author cyetstar
 * @date 2023-04-18 23:04:56
 *
 */
@Data
@ApiModel("电影唯一标识请求对象")
public class MovieUniqueidUpdateReq{

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("电影id")
    private Long movieId;

    @ApiModelProperty("唯一标识")
    private String uid;

    @ApiModelProperty("是否默认")
    private String sfmr;

    @ApiModelProperty("标识类型")
    private String bslx;
}
