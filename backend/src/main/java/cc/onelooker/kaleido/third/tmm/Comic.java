package cc.onelooker.kaleido.third.tmm;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
import java.util.Objects;

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

    public Volume getVolume(Integer volumeNumber) {
        if (volumes == null) {
            return null;
        }
        return volumes.stream().filter(s -> Objects.equals(s.getVolumeNumber(), volumeNumber)).findFirst().orElse(null);
    }

    @Data
    public static class Volume {
        @JsonProperty("bgm_id")
        private String bgmId;
        private String title;
        @JsonProperty("original_title")
        private String originalTitle;
        private String cover;
        @JsonProperty("volume_number")
        private Integer volumeNumber;

    }
}
