package cc.onelooker.kaleido.dto.music.req;

import com.zjjcnt.common.core.annotation.StringDateTimeFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 曲目请求对象
 *
 * @author cyetstar
 * @date 2023-11-20 22:35:26
 */
@Data
@ApiModel("曲目请求对象")
public class MusicTrackPageReq {

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

    @StringDateTimeFormat
    @ApiModelProperty("创建时间")
    private String cjsj;

    @StringDateTimeFormat
    @ApiModelProperty("修改时间")
    private String xgsj;

}
