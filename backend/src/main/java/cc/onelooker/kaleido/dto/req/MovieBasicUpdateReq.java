package cc.onelooker.kaleido.dto.req;

import com.zjjcnt.common.core.annotation.StringDateFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 电影请求对象
 *
 * @author cyetstar
 * @date 2023-11-26 01:19:02
 */
@Data
@ApiModel("电影请求对象")
public class MovieBasicUpdateReq {

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("电影名")
    private String title;

    @ApiModelProperty("原片名")
    private String originalTitle;

    @ApiModelProperty("排序名")
    private String titleSort;

    @ApiModelProperty("首映年份")
    private String year;

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

    @ApiModelProperty("IMDb编号")
    private String imdbId;

    @ApiModelProperty("豆瓣编号")
    private String doubanId;

    @ApiModelProperty("TMDB编号")
    private String tmdbId;

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

    @ApiModelProperty("导演主键列表")
    private List<String> directorList;

    @ApiModelProperty("编剧主键列表")
    private List<String> writerList;

    @ApiModelProperty("演员主键列表")
    private List<Actor> actorList;


    @Data
    public static class Actor {

        private String id;

        private String name;

        private String playRole;

    }

}
