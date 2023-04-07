package cc.onelooker.kaleido.dto.business.req;

import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.lang.Long;
import java.lang.Double;
import java.lang.Boolean;
import java.lang.Integer;
import java.lang.String;

/**
 * 请求对象
 *
 * @author cyetstar
 * @date 2023-04-06 13:32:12
 *
 */
@Data
@ApiModel("请求对象")
public class RatingCreateReq{

    @ApiModelProperty("")
    private Double average;

    @ApiModelProperty("")
    private Boolean def;

    @ApiModelProperty("")
    private Integer max;

    @ApiModelProperty("")
    private String type;

    @ApiModelProperty("")
    private Integer votes;

    @ApiModelProperty("")
    private Long movieId;
}
