package cc.onelooker.kaleido.dto.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 剧集请求对象
 *
 * @author cyetstar
 * @date 2023-11-27 22:51:36
 */
@Data
@ApiModel("剧集请求对象")
public class TvshowShowUpdateReq {

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("剧集名")
    private String title;

    @ApiModelProperty("原剧集名")
    private String originalTitle;

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

    @ApiModelProperty("别名列表")
    private List<String> akaList;

    @ApiModelProperty("类型列表")
    private List<String> genreList;

    @ApiModelProperty("语言列表")
    private List<String> languageList;

    @ApiModelProperty("国家地区列表")
    private List<String> countryList;

    @ApiModelProperty("标签列表")
    private List<String> tagList;

}
