package cc.onelooker.kaleido.dto.music.resp;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 曲目响应对象
 *
 * @author cyetstar
 * @date 2023-11-20 22:35:26
 */
@Data
@ApiModel("曲目响应对象")
public class MusicTrackListByAlbumIdResp {

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

    @ApiModelProperty("是否有歌词")
    private String sfygc;

    @JsonProperty
    public String lengthLabel() {
        //将秒转分秒格式
        if (length != null) {
            return String.format("%d:%02d", length / 60, length % 60);
        }
        return null;
    }
}
