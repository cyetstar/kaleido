package cc.onelooker.kaleido.dto.business.req;

import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.lang.Long;
import java.math.BigDecimal;
import java.lang.Integer;
import com.zjjcnt.common.core.annotation.Dict;
import java.lang.String;

/**
 * 电影评分请求对象
 *
 * @author cyetstar
 * @date 2023-04-18 23:04:56
 *
 */
@Data
@ApiModel("电影评分请求对象")
public class MovieRatingPageReq{

    @ApiModelProperty("电影id")
    private Long movieId;

    @ApiModelProperty("平均分")
    private BigDecimal pjf;

    @ApiModelProperty("投票数")
    private Integer tps;

    @ApiModelProperty("最大值")
    private Integer zdz;

    @ApiModelProperty("是否默认")
    private String sfmr;

    @ApiModelProperty("评分类型")
    private String pflx;
}
