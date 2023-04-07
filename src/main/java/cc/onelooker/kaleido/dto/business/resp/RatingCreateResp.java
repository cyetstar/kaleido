package cc.onelooker.kaleido.dto.business.resp;

import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.lang.Long;
import java.lang.Double;
import java.lang.Boolean;
import java.lang.Integer;
import java.lang.String;

/**
 * 响应对象
 *
 * @author cyetstar
 * @date 2023-04-06 13:32:12
 *
 */
@Data
@ApiModel("响应对象")
public class RatingCreateResp{

    @ApiModelProperty("")
    private Long id;

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
