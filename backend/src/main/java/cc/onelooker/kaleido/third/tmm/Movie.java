package cc.onelooker.kaleido.third.tmm;

import cc.onelooker.kaleido.dto.IUnique;
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
public class Movie implements IUnique {
    @JsonProperty("douban_id")
    private String doubanId;
    @JsonProperty("tmdb_id")
    private String tmdbId;
    @JsonProperty("imdb_id")
    private String imdbId;
    private String title;
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
    private String premiered;
    private String year;
    private List<Doulist> doulists;
    private String plot;
    private List<String> tags;
    private String website;
    private String mpaa;
    @JsonProperty("douban_top250")
    private Integer doubanTop250;
    private String poster;
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
