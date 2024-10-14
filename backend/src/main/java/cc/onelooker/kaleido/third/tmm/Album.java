package cc.onelooker.kaleido.third.tmm;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;
import java.util.Objects;

/**
 * @Author cyetstar
 * @Date 2023-11-15 17:49:00
 * @Description TODO
 */
@Data
public class Album {

    @JsonProperty("netease_id")
    private String neteaseId;

    @JsonProperty("musicbrainz_id")
    private String musicbrainzId;

    private String title;

    private String company;

    @JsonProperty("pic_url")
    private String picUrl;

    private String description;

    @JsonProperty("publish_time")
    private String publishTime;

    private List<Artist> artists;

    private List<Song> songs;

    public String getArtistName() {
        if (CollectionUtils.isNotEmpty(artists)) {
            return artists.get(0).getName();
        }
        return null;
    }

    public Song getSong(Integer trackIndex) {
        if (songs == null) {
            return null;
        }
        return songs.stream().filter(s -> Objects.equals(s.getTrackIndex(), trackIndex)).findFirst().orElse(null);
    }
}
