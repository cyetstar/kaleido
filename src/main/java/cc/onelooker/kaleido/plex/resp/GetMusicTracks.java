package cc.onelooker.kaleido.plex.resp;

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
public class GetMusicTracks {

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

        public Metadata getMetadata() {
            return CollectionUtils.get(metadataList, 0);
        }
    }

    @Data
    public static class Metadata {
        private Long ratingKey;
        private String key;
        private String parentRatingKey;
        private String grandparentRatingKey;
        private String guid;
        private String parentGuid;
        private String grandparentGuid;
        private String grandparentTitle;
        private String parentTitle;
        private String type;
        private String title;
        private String grandparentKey;
        private String parentKey;
        private String summary;
        private String ratingCount;
        private String parentYear;
        private String thumb;
        private String art;
        private String index;
        private Integer duration;
        private String musicAnalysisVersion;
        private Long addedAt;
        private Long updatedAt;
        private Long lastViewedAt;
        @JsonProperty("Media")
        private List<Media> mediaList;

        public Media getMedia() {
            return CollectionUtils.get(mediaList, 0);
        }

    }

}
