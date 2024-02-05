package cc.onelooker.kaleido.third.tmm;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * @Author xiadawei
 * @Date 2024-01-04 13:57:00
 * @Description TODO
 */
@Data
public class Tvshow {
    @JsonProperty("douban_id")
    private String doubanId;
    @JsonProperty("imdb_id")
    private String imdbId;
    @JsonProperty("tmdb_id")
    private String tmdbId;
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
    private String poster;
    private String plot;
    private String year;
    private String mpaa;
    private String premiered;
    private List<String> studios;
    private Season season;

    @Override
    public String toString() {
        return title + " (" + year + ")";
    }
}
