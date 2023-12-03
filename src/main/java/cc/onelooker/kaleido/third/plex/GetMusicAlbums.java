package cc.onelooker.kaleido.third.plex;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;

/**
 * @Author cyetstar
 * @Date 2023-09-26 23:00:00
 * @Description TODO
 */
@Data
public class GetMusicAlbums {

    @JsonProperty("MediaContainer")
    private MediaContainer mediaContainer;

    @Data
    public static class MediaContainer {
        private Integer size;
        private String allowSync;
        private String identifier;
        private String librarySectionID;
        private String librarySectionTitle;
        @JsonProperty("Metadata")
        private List<Metadata> metadataList;

        public GetMusicAlbums.Metadata getMetadata() {
            return CollectionUtils.get(metadataList, 0);
        }

    }

    @Data
    public static class Metadata {
        private Long ratingKey;
        private String key;
        private Long parentRatingKey;
        private String guid;
        private String parentGuid;
        private String studio;
        private String type;
        private String title;
        private String parentKey;
        private String parentTitle;
        private String summary;
        private String year;
        private String thumb;
        private String parentThumb;
        private String originallyAvailableAt;
        private Long lastViewedAt;
        private Long addedAt;
        private Long updatedAt;

    }
}
