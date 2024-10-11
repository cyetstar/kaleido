package cc.onelooker.kaleido.dto.resp;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;

/**
 * 剧集响应对象
 *
 * @author cyetstar
 * @date 2023-11-27 22:51:36
 */
@Data
@ApiModel("剧集响应对象")
public class TvshowShowViewResp {

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("剧集名")
    private String title;

    @ApiModelProperty("原剧集名")
    private String originalTitle;

    @ApiModelProperty("排序名")
    private String sortTitle;

    @ApiModelProperty("制片公司")
    private String studio;

    @ApiModelProperty("剧集评级")
    private String contentRating;

    @ApiModelProperty("简介")
    private String summary;

    @ApiModelProperty("首播年份")
    private String year;

    @ApiModelProperty("首播日期")
    private String originallyAvailableAt;

    @ApiModelProperty("评分")
    private Float rating;

    @ApiModelProperty("海报")
    private String thumb;

    @ApiModelProperty("艺术图")
    private String art;

    @ApiModelProperty("季数")
    private Integer totalSeasons;

    @ApiModelProperty("IMDb编号")
    private String imdbId;

    @ApiModelProperty("豆瓣编号")
    private String doubanId;

    @ApiModelProperty("TMDB编号")
    private String tmdbId;

    @ApiModelProperty("路径")
    private String path;

    @ApiModelProperty("加入时间")
    private Long addedAt;

    @ApiModelProperty("更新时间")
    private Long updatedAt;

    @ApiModelProperty("国家地区")
    private List<String> countryList;

    @ApiModelProperty("语言")
    private List<String> languageList;

    @ApiModelProperty("类型")
    private List<String> genreList;

    @ApiModelProperty("别名")
    private List<String> akaList;

    @ApiModelProperty("标签")
    private List<String> tagList;

    @ApiModelProperty("导演")
    private List<ActorViewResp> directorList;

    @ApiModelProperty("编剧")
    private List<ActorViewResp> writerList;

    @ApiModelProperty("主演")
    private List<ActorViewResp> actorList;

    @JsonProperty
    public List<String> summaryList() {
        return Arrays.asList(StringUtils.split(summary, "\n"));
    }

}
