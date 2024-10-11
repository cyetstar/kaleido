package cc.onelooker.kaleido.dto.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 电影集合响应对象
 *
 * @author cyetstar
 * @date 2023-12-29 16:15:43
 *
 */
@Data
@ApiModel("电影集合响应对象")
public class MovieCollectionPageResp{

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("简介")
    private String summary;

    @ApiModelProperty("海报")
    private String thumb;

    @ApiModelProperty("项目数量")
    private Integer childCount;

    @ApiModelProperty("豆瓣编号")
    private String doubanId;
}
