package cc.onelooker.kaleido.third.douban;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * @Author xiadawei
 * @Date 2023-12-03 17:21:00
 * @Description TODO
 */
@Data
public class Movie {

    private String id;
    private Rating rating;
    private List<String> genres;
    private String title;
    private List<Cast> casts;
    @JsonProperty("collect_count")
    private Integer collectCount;
    @JsonProperty("original_title")
    private String originalTitle;
    private String subtype;
    private List<Cast> directors;
    private String year;
    private Thumb images;
    private String alt;
    @JsonProperty("reviews_count")
    private Integer reviewsCount;
    @JsonProperty("wish_count")
    private Integer wishCount;
    @JsonProperty("douban_site")
    private String doubanSite;
    @JsonProperty("mobile_url")
    private String mobileUrl;
    @JsonProperty("do_count")
    private Integer doCount;
    @JsonProperty("share_url")
    private String shareUrl;
    @JsonProperty("seasons_count")
    private Integer seasonsCount;
    @JsonProperty("schedule_url")
    private String scheduleUrl;
    @JsonProperty("episodes_count")
    private Integer episodesCount;
    private List<String> countries;
    @JsonProperty("current_season")
    private String currentSeason;
    private String summary;
    @JsonProperty("comments_count")
    private Integer commentsCount;
    @JsonProperty("ratings_count")
    private Integer ratingsCount;
    private List<String> aka;
    private String imdb;

    @Data
    public static class Rating {
        private Integer max;
        private Float average;
        private String stars;
        private Integer min;
    }

    @Data
    public static class Cast {
        private String id;
        private String name;
        private String alt;
        private Thumb avatars;
    }

    @Data
    public static class Thumb {
        private String small;
        private String large;
        private String medium;

        @JsonProperty
        public String getMedium() {
            return medium.replace("s_ratio_poster", "m_ratio_poster");
        }

        @JsonProperty
        public String getLarge() {
            return large.replace("s_ratio_poster", "l_ratio_poster");
        }
    }

}
