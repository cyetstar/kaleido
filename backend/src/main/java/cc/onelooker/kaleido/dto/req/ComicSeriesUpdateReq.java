package cc.onelooker.kaleido.dto.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 漫画系列请求对象
 *
 * @author cyetstar
 * @date 2024-03-12 17:47:50
 */
@Data
@ApiModel("漫画系列请求对象")
public class ComicSeriesUpdateReq {

    @ApiModelProperty("主键")
    private String id;

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("原标题")
    private String originalTitle;

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

    @ApiModelProperty("番组计划编号")
    private String bgmId;

    @ApiModelProperty("作者")
    private List<String> writerList;

    @ApiModelProperty("作画")
    private List<String> pencillerList;

    @ApiModelProperty("标签")
    private List<String> tagList;

    @ApiModelProperty("别名")
    private List<String> alternateTitleList;

}
