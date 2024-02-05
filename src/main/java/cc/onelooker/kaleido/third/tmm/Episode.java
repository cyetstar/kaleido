package cc.onelooker.kaleido.third.tmm;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @Author xiadawei
 * @Date 2024-02-02 13:53:00
 * @Description TODO
 */
@Data
public class Episode {

    @JsonProperty("tmdb_id")
    private String tmdbId;
    @JsonProperty("episode_number")
    private Integer episodeNumber;
    @JsonProperty("season_number")
    private Integer seasonNumber;
    private String title;
    private String plot;
    private String premiered;
    private String year;
    private Float average;
    private Integer votes;
    private Integer runtime;
    private String thumb;

}
