package cc.onelooker.kaleido.dto.comic.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 漫画系列响应对象
 *
 * @author cyetstar
 * @date 2024-03-12 17:47:50
 *
 */
@Data
@ApiModel("漫画系列响应对象")
public class ComicSeriesPageResp{

    @ApiModelProperty("主键")
    private String id;

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("简介")
    private String summary;

    @ApiModelProperty("出版社")
    private String publisher;

    @ApiModelProperty("卷数")
    private Integer bookCount;

    @ApiModelProperty("评分")
    private Float rating;

    @ApiModelProperty("状态")
    private String status;

    @ApiModelProperty("路径")
    private String path;

    @ApiModelProperty("封面")
    private String cover;

    @ApiModelProperty("番组计划编号")
    private String bgmId;

    @ApiModelProperty("加入时间")
    private Long addedAt;

    @ApiModelProperty("更新时间")
    private Long updatedAt;
}
