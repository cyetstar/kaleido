package cc.onelooker.kaleido.dto.movie.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 电影属性值关联表响应对象
 *
 * @author cyetstar
 * @date 2023-12-29 16:15:43
 *
 */
@Data
@ApiModel("电影属性值关联表响应对象")
public class MovieBasicAttributeCreateResp{

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("电影id")
    private Long movieId;

    @ApiModelProperty("属性值id")
    private Long attributeId;
}
