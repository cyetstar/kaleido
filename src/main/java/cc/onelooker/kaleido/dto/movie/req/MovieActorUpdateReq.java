package cc.onelooker.kaleido.dto.movie.req;

import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.lang.Long;
import java.lang.String;

/**
 * 演职员请求对象
 *
 * @author cyetstar
 * @date 2023-04-18 23:04:56
 *
 */
@Data
@ApiModel("演职员请求对象")
public class MovieActorUpdateReq{

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("姓名")
    private String xm;

    @ApiModelProperty("本名")
    private String bm;

    @ApiModelProperty("豆瓣编号")
    private String doubanId;
}