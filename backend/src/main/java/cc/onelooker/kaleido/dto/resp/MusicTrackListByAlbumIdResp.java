package cc.onelooker.kaleido.dto.resp;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

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

    @ApiModelProperty("是否有歌词")
    private String hasLyric;

    private List<Artist> artistList;

    @JsonProperty
    public String getDurationLabel() {
        //将秒转分秒格式
        if (duration != null) {
            return String.format("%d:%02d", duration / 1000 / 60, duration % 60);
        }
        return null;
    }

    @Data
    public static class Artist {

        private String id;

        private String title;

    }
}
