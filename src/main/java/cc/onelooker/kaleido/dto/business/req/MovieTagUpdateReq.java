package cc.onelooker.kaleido.dto.business.req;

import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.lang.Long;
import java.lang.String;

/**
 * 电影标签请求对象
 *
 * @author cyetstar
 * @date 2023-04-18 23:04:56
 *
 */
@Data
@ApiModel("电影标签请求对象")
public class MovieTagUpdateReq{

    @ApiModelProperty("电影id")
    private Long movieId;

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("名称")
    private String mc;
}
