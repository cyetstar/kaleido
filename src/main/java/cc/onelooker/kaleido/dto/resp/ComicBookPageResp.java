package cc.onelooker.kaleido.dto.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 漫画书籍响应对象
 *
 * @author cyetstar
 * @date 2024-03-12 17:47:50
 */
@Data
@ApiModel("漫画书籍响应对象")
public class ComicBookPageResp {

    @ApiModelProperty("主键")
    private String id;

    @ApiModelProperty("系列id")
    private String seriesId;

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("原标题")
    private String originalTitle;

    @ApiModelProperty("简介")
    private String summary;

    @ApiModelProperty("卷号")
    private Integer bookNumber;

    @ApiModelProperty("页数")
    private Integer pageCount;

    @ApiModelProperty("路径")
    private String path;

    @ApiModelProperty("文件大小")
    private Long fileSize;

    @ApiModelProperty("番组计划编号")
    private String bgmId;

    @ApiModelProperty("封面页码")
    private Integer coverPageNumber;

    @ApiModelProperty("封面裁切数据")
    private String coverBoxData;

    @ApiModelProperty("加入时间")
    private Long addedAt;

    @ApiModelProperty("更新时间")
    private Long updatedAt;
}
