package cc.onelooker.kaleido.dto.movie.resp;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zjjcnt.common.core.annotation.Dict;
import com.zjjcnt.common.core.annotation.StringDateFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.compress.utils.Lists;
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
    private Long id;

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
    private String imdb;

    @ApiModelProperty("豆瓣编号")
    private String doubanId;

    @ApiModelProperty("加入时间")
    private Long addedAt;

    @ApiModelProperty("更新时间")
    private Long updatedAt;

    private List<Country> countryList;

    private List<Language> languageList;

    private List<Genre> genreList;

    private List<Actor> directorList;

    private List<Actor> writerList;

    private List<Actor> actorList;

    private List<String> akaList;

    private List<String> tagList;

    @JsonProperty
    public List<String> summaryList() {
        return Arrays.asList(StringUtils.split(summary, "\n"));
    }

    @Data
    public static class Country {
        @Dict("movieCountry")
        private String id;

        public Country(String id) {
            this.id = id;
        }
    }

    @Data
    public static class Genre {
        @Dict("movieGenre")
        private String id;

        public Genre(String id) {
            this.id = id;
        }
    }

    @Data
    public static class Language {
        @Dict("movieLanguage")
        private String id;

        public Language(String id) {
            this.id = id;
        }
    }

    @Data
    public static class Actor {

        private String id;

        private String name;

        private String cnName;

        private String thumb;

        private String role;

        private String playRole;

    }

}
