package cc.onelooker.kaleido.dto.resp;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zjjcnt.common.core.annotation.StringDateFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;

/**
 * 电影响应对象
 *
 * @author cyetstar
 * @date 2023-11-26 01:19:02
 */
@Data
@ApiModel("电影响应对象")
public class MovieBasicViewResp {

    @ApiModelProperty("主键")
    private String id;

    @ApiModelProperty("电影名")
    private String title;

    @ApiModelProperty("原片名")
    private String originalTitle;

    @ApiModelProperty("排序名")
    private String titleSort;

    @ApiModelProperty("首映年份")
    private String year;

    @ApiModelProperty("海报")
    private String thumb;

    @ApiModelProperty("艺术图")
    private String art;

    @ApiModelProperty("用户评分")
    private Integer userRating;

    @ApiModelProperty("简介")
    private String summary;

    @ApiModelProperty("片长(秒)")
    private Integer duration;

    @ApiModelProperty("电影评级")
    private String contentRating;

    @StringDateFormat
    @ApiModelProperty("首映日期")
    private String originallyAvailableAt;

    @ApiModelProperty("电影公司")
    private String studio;

    @ApiModelProperty("评分")
    private Float rating;

    @ApiModelProperty("观看时间")
    private Long lastViewedAt;

    @ApiModelProperty("观看次数")
    private Integer viewCount;

    @ApiModelProperty("电影网站")
    private String website;

    @ApiModelProperty("IMDb编号")
    private String imdbId;

    @ApiModelProperty("豆瓣编号")
    private String doubanId;

    @ApiModelProperty("TMDB编号")
    private String tmdbId;

    @ApiModelProperty("多文件")
    private String multipleFiles;

    @ApiModelProperty("无字幕")
    private String noSubtitle;

    @ApiModelProperty("国语配音")
    private String mandarin;

    @ApiModelProperty("低质量")
    private String lowQuality;

    @ApiModelProperty("路径")
    private String path;

    @ApiModelProperty("文件名")
    private String filename;

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
