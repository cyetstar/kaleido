package cc.onelooker.kaleido.dto.movie.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 电影集合关联表响应对象
 *
 * @author cyetstar
 * @date 2023-11-26 01:19:02
 */
@Data
@ApiModel("电影集合关联表响应对象")
public class MovieBasicCollectionViewResp {

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("电影id")
    private Long movieId;

    @ApiModelProperty("集合id")
    private Long setsId;
}
