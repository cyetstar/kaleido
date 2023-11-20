package cc.onelooker.kaleido.dto.music.resp;

import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.lang.Long;
import java.lang.String;
import java.lang.Integer;

/**
 * 曲目响应对象
 *
 * @author cyetstar
 * @date 2023-11-20 22:35:26
 *
 */
@Data
@ApiModel("曲目响应对象")
public class MusicTrackViewResp{

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("专辑Id")
    private Long albumId;

    @ApiModelProperty("MusicBrainz编号")
    private String musicbrainzId;

    @ApiModelProperty("Plex编号")
    private String plexId;

    @ApiModelProperty("网易云音乐编号")
    private String neteaseId;

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("艺术家")
    private String artists;

    @ApiModelProperty("曲长")
    private Integer length;

    @ApiModelProperty("曲号")
    private Integer trackNumber;

    @ApiModelProperty("碟号")
    private Integer discNumber;

    @ApiModelProperty("文件格式")
    private String format;

    @ApiModelProperty("文件路径")
    private String path;
}
