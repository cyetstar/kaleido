package cc.onelooker.kaleido.third.tmm;

import cc.onelooker.kaleido.utils.KaleidoUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;

/**
 * @Author cyetstar
 * @Date 2023-11-15 17:37:00
 * @Description TODO
 */
@Data
public class Song {

    @JsonProperty("netease_id")
    private String neteaseId;
    @JsonProperty("musicbrainz_id")
    private String musicbrainzId;
    private String title;
    @JsonProperty("track_index")
    private Integer trackIndex;
    private List<Artist> artists;
    private Integer duration;

    public String getArtistName() {
        if (CollectionUtils.isNotEmpty(artists)) {
            return artists.get(0).getName();
        }
        return null;
    }

    public String getSimpleName() {
        return KaleidoUtil.genSongSimpleName(title);
    }

}
