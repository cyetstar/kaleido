package cc.onelooker.kaleido.third.plex;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;

/**
 * @Author cyetstar
 * @Date 2023-09-25 22:44:00
 * @Description TODO
 */
@Data
public class GetLibraries {

    @JsonProperty("MediaContainer")
    private MediaContainer mediaContainer;

    @Data
    public static class MediaContainer {
        private Integer size;
        private String allowSync;
        @JsonProperty("Directory")
        private List<Directory> directoryList;
    }

}
