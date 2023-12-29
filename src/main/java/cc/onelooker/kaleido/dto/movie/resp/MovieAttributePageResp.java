package cc.onelooker.kaleido.dto.movie.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 电影属性值响应对象
 *
 * @author cyetstar
 * @date 2023-12-22 11:18:26
 */
@Data
@ApiModel("电影属性值响应对象")
public class MovieAttributePageResp {

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("属性值")
    private String attribute;

    @ApiModelProperty("类型")
    private String type;
}
