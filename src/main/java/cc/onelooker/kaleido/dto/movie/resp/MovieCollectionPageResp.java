package cc.onelooker.kaleido.dto.movie.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 电影集合响应对象
 *
 * @author cyetstar
 * @date 2023-11-26 01:19:02
 */
@Data
@ApiModel("电影集合响应对象")
public class MovieCollectionPageResp {

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("简介")
    private String summary;

    @ApiModelProperty("缩略图")
    private String thumb;

    @ApiModelProperty("项目数量")
    private String childCount;

    @ApiModelProperty("加入时间")
    private Long addedAt;

    @ApiModelProperty("更新时间")
    private Long updatedAt;
}
