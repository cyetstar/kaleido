package cc.onelooker.kaleido.third.tmm;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * @Author xiadawei
 * @Date 2024-01-04 13:57:00
 * @Description TODO
 */
@Data
public class Movie {
    @JsonProperty("douban_id")
    private String doubanId;
    @JsonProperty("tmdb_id")
    private String tmdbId;
    @JsonProperty("original_title")
    private String originalTitle;
    private Integer votes;
    private Float average;
    private List<String> genres;
    private List<String> countries;
    private List<String> languages;
    private List<String> akas;
    private List<Actor> directors;
    private List<Actor> writers;
    private List<Actor> actors;
    @JsonProperty("release_date")
    private String releaseDate;
    private List<String> runtimes;
    private String poster;
    @JsonProperty("imdb_id")
    private String imdbId;
    private List<Doulist> doulists;
    private String summary;
    private List<String> tags;
    private String title;
    private String website;
    private String year;
    @JsonProperty("certification")
    private String mpaa;
    @JsonProperty("douban_top250")
    private Integer doubanTop250;
    private String comment;
    private Integer pos;
    private String source;

    @Override
    public String toString() {
        return title + " (" + year + ")";
    }

    public String getDecade() {
        return StringUtils.substring(year, 0, 3) + "0s";
    }
}
