package cc.onelooker.kaleido.dto.business.req;

import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.lang.Long;
import java.lang.String;
import com.zjjcnt.common.core.annotation.Dict;

/**
 * 电影唯一标识请求对象
 *
 * @author cyetstar
 * @date 2023-04-18 23:04:56
 *
 */
@Data
@ApiModel("电影唯一标识请求对象")
public class MovieUniqueidCreateReq{

    @ApiModelProperty("电影id")
    private Long movieId;

    @ApiModelProperty("唯一标识")
    private String uid;

    @ApiModelProperty("是否默认")
    private String sfmr;

    @ApiModelProperty("标识类型")
    private String bslx;
}
