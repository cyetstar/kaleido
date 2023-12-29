package cc.onelooker.kaleido.dto.movie.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 电影集合请求对象
 *
 * @author cyetstar
 * @date 2023-11-26 01:19:02
 */
@Data
@ApiModel("电影集合请求对象")
public class MovieCollectionUpdateReq {

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("简介")
    private String summary;

}
