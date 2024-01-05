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
public class Movie {
    @JsonProperty("douban_id")
    private String doubanId;
    @JsonProperty("original_title")
    private String originalTitle;
    @JsonProperty("douban_average")
    private Float doubanAverage;
    @JsonProperty("douban_top250")
    private Integer doubanTop250;
    @JsonProperty("douban_votes")
    private Integer doubanVotes;
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
    private String mpaa;

    @Override
    public String toString() {
        return title + " (" + year + ")";
    }
}
