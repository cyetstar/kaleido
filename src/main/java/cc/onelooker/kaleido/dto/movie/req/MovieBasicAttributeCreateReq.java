package cc.onelooker.kaleido.dto.movie.req;

import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.lang.Long;

/**
 * 电影属性值关联表请求对象
 *
 * @author cyetstar
 * @date 2023-12-29 16:15:43
 *
 */
@Data
@ApiModel("电影属性值关联表请求对象")
public class MovieBasicAttributeCreateReq{

    @ApiModelProperty("电影id")
    private Long movieId;

    @ApiModelProperty("属性值id")
    private Long attributeId;

}
