package cc.onelooker.kaleido.third.tmm;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Objects;

/**
 * @Author xiadawei
 * @Date 2024-02-02 11:39:00
 * @Description TODO
 */
@Data
public class Season {

    @JsonProperty("douban_id")
    private String doubanId;
    @JsonProperty("tmdb_id")
    private String tmdbId;
    @JsonProperty("imdb_id")
    private String imdbId;
    private String title;
    @JsonProperty("original_title")
    private String originalTitle;
    private Float average;
    private Integer votes;
    private String plot;
    private String year;
    private String premiered;
    @JsonProperty("season_number")
    private Integer seasonNumber;
    private List<Actor> credits;
    private List<Actor> directors;
    private List<Actor> actors;
    private List<Episode> episodes;
    private String poster;

    public String getSeasonNumberStr() {
        return StringUtils.leftPad(String.valueOf(seasonNumber), 2, "0");
    }

    public Episode getEpisode(Integer episodeNumber) {
        if (episodes == null) {
            return null;
        }
        return episodes.stream().filter(s -> Objects.equals(s.getEpisodeNumber(), episodeNumber)).findFirst().orElse(null);
    }
}
