package cc.onelooker.kaleido.third.plex;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @Author cyetstar
 * @Date 2023-12-15 16:33:00
 * @Description TODO
 */
@Data
public class PlexResult {

    @JsonProperty("MediaContainer")
    private MediaContainer mediaContainer;

}
