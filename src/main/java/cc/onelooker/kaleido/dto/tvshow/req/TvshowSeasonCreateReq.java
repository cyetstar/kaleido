package cc.onelooker.kaleido.dto.tvshow.req;

import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.lang.Long;
import java.lang.String;
import java.lang.Integer;

/**
 * 单季请求对象
 *
 * @author cyetstar
 * @date 2023-11-27 22:51:36
 *
 */
@Data
@ApiModel("单季请求对象")
public class TvshowSeasonCreateReq{

    @ApiModelProperty("剧集id")
    private Long showId;

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("简介")
    private String summary;

    @ApiModelProperty("季号")
    private Integer seasonIndex;

    @ApiModelProperty("海报")
    private String thumb;

    @ApiModelProperty("艺术图")
    private String art;

    @ApiModelProperty("加入时间")
    private Long addedAt;

    @ApiModelProperty("更新时间")
    private Long updatedAt;

}
