package cc.onelooker.kaleido.dto.movie.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 演职员请求对象
 *
 * @author cyetstar
 * @date 2023-11-26 01:19:02
 */
@Data
@ApiModel("演职员请求对象")
public class MovieActorPageReq {

    @ApiModelProperty("豆瓣编号")
    private String doubanId;

    @ApiModelProperty("姓名")
    private String name;

    @ApiModelProperty("原名")
    private String originalName;

}
