package cc.onelooker.kaleido.dto.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 曲目响应对象
 *
 * @author cyetstar
 * @date 2023-11-25 22:16:58
 */
@Data
@ApiModel("曲目响应对象")
public class MusicTrackViewResp {

    @ApiModelProperty("主键")
    private String id;

    @ApiModelProperty("MusicBrainz编号")
    private String musicbrainzId;

    @ApiModelProperty("网易云音乐编号")
    private String neteaseId;

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("艺术家")
    private String artists;

    @ApiModelProperty("文件格式")
    private String format;

    @ApiModelProperty("文件路径")
    private String path;

    @ApiModelProperty("专辑id")
    private Long albumId;

    @ApiModelProperty("曲长(毫秒)")
    private Integer duration;

    @ApiModelProperty("曲号")
    private Integer trackIndex;

    @ApiModelProperty("碟号")
    private Integer discIndex;

    @ApiModelProperty("加入时间")
    private Long addedAt;

    @ApiModelProperty("更新时间")
    private Long updatedAt;
}
