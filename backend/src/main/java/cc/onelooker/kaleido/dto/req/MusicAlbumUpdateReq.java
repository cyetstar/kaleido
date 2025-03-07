package cc.onelooker.kaleido.dto.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 专辑请求对象
 *
 * @author cyetstar
 * @date 2023-11-25 22:16:58
 */
@Data
@ApiModel("专辑请求对象")
public class MusicAlbumUpdateReq {

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

    @ApiModelProperty("简介")
    private String summary;

    @ApiModelProperty("专辑类型")
    private String type;

    @ApiModelProperty("音乐流派")
    private String genre;

    @ApiModelProperty("发行国家")
    private String releaseCountry;

    @ApiModelProperty("唱片公司")
    private String label;

    @ApiModelProperty("碟数")
    private Integer totalDiscs;

    @ApiModelProperty("音轨数")
    private Integer totalTracks;

    @ApiModelProperty("媒体")
    private String media;

    @ApiModelProperty("首发日期")
    private String originallyAvailableAt;

    @ApiModelProperty("艺术家主键列表")
    private List<String> artistList;

    @ApiModelProperty("曲目列表")
    private List<Track> trackList;

    @Data
    public static class Track {
        @ApiModelProperty("主键")
        private String id;

        @ApiModelProperty("标题")
        private String title;

        @ApiModelProperty("曲号")
        private Integer trackIndex;

        @ApiModelProperty("碟号")
        private Integer discIndex;

        @ApiModelProperty("艺术家主键列表")
        private List<String> artistList;
    }
}
