package cc.onelooker.kaleido.dto.movie.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 电影集合关联表请求对象
 *
 * @author cyetstar
 * @date 2023-12-29 16:15:43
 *
 */
@Data
@ApiModel("电影集合关联表请求对象")
public class MovieBasicCollectionCreateReq{

    @ApiModelProperty("电影id")
    private Long movieId;

    @ApiModelProperty("集合id")
    private Long collectionId;

    @ApiModelProperty("电影名")
    private String title;

    @ApiModelProperty("首映年份")
    private String year;

    @ApiModelProperty("豆瓣编号")
    private String doubanId;

    @ApiModelProperty("海报")
    private String thumb;

}
