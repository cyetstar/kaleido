package cc.onelooker.kaleido.third.tmm;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * @Author xiadawei
 * @Date 2024-03-21 21:01:00
 * @Description TODO
 */
@Data
public class Comic {

    @JsonProperty("bgm_id")
    private String bgmId;
    @JsonProperty("volume_count")
    private Integer volumeCount;
    @JsonProperty("series")
    private String series;
    @JsonProperty("original_series")
    private String originalSeries;
    private String year;
    private String summary;
    private List<String> tags;
    private List<String> akas;
    private List<String> publishers;
    private List<Author> authors;
    private String date;
    private String votes;
    private String average;
    private String cover;
    private List<Volume> volumes;

    @Data
    public static class Volume {
        @JsonProperty("bgm_id")
        private String bgmId;
        private String title;
        @JsonProperty("original_title")
        private String originalTitle;
        private String cover;

    }
}
