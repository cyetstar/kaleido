package cc.onelooker.kaleido.dto.resp;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;

/**
 * 专辑响应对象
 *
 * @author cyetstar
 * @date 2023-11-25 22:16:58
 */
@Data
@ApiModel("专辑响应对象")
public class MusicAlbumViewResp {

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

    @ApiModelProperty("首发年份")
    private String year;

    @ApiModelProperty("首发日期")
    private String originallyAvailableAt;

    @ApiModelProperty("路径")
    private String path;

    @ApiModelProperty("加入时间")
    private Long addedAt;

    @ApiModelProperty("更新时间")
    private Long updatedAt;

    private List<String> styleList;

    private List<String> genreList;

    private List<String> moodList;

    private List<Artist> artistList;

    @JsonProperty
    public List<String> summaryList() {
        return Arrays.asList(StringUtils.split(summary, "\n"));
    }

    @Data
    public static class Artist {

        private String id;

        private String title;

    }
}
