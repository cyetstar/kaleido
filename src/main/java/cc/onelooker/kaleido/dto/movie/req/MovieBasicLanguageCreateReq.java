package cc.onelooker.kaleido.dto.movie.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 电影语言关联表请求对象
 *
 * @author cyetstar
 * @date 2023-11-26 01:19:02
 */
@Data
@ApiModel("电影语言关联表请求对象")
public class MovieBasicLanguageCreateReq {

    @ApiModelProperty("电影id")
    private Long movieId;

    @ApiModelProperty("语言id")
    private Long movieLanguageId;

}
