package cc.onelooker.kaleido.dto.movie.resp;

import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.lang.Long;
import java.lang.String;

/**
 * 电影属性值响应对象
 *
 * @author cyetstar
 * @date 2023-12-29 16:15:43
 *
 */
@Data
@ApiModel("电影属性值响应对象")
public class MovieAttributeViewResp{

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("属性值")
    private String attribute;

    @ApiModelProperty("类型")
    private String type;
}
