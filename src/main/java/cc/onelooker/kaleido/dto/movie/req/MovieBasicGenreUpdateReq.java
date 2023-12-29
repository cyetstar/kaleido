package cc.onelooker.kaleido.dto.movie.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 电影类型关联表请求对象
 *
 * @author cyetstar
 * @date 2023-11-26 01:19:02
 */
@Data
@ApiModel("电影类型关联表请求对象")
public class MovieBasicGenreUpdateReq {

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("电影id")
    private Long movieId;

    @ApiModelProperty("影片类型id")
    private Long movieGenreId;

}
