package cc.onelooker.kaleido.plex.resp;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zjjcnt.common.util.DateTimeUtils;
import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;

/**
 * @Author xiadawei
 * @Date 2023-09-26 23:00:00
 * @Description TODO
 */
@Data
public class GetMusicArtists {

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

        public GetMusicArtists.Metadata getMetadata() {
            return CollectionUtils.get(metadataList, 0);
        }
    }

    @Data
    public static class Metadata {
        private String ratingKey;
        private String key;
        private String parentRatingKey;
        private String guid;
        private String parentGuid;
        private String studio;
        private String type;
        private String title;
        private String parentKey;
        private String parentTitle;
        private String summary;
        private String rating;
        private String year;
        private String thumb;
        private String art;
        private String parentThumb;
        private String loudnessAnalysisVersion;
        private String musicAnalysisVersion;
        private Long lastViewedAt;
        private Long originallyAvailableAt;
        private Long addedAt;
        private Long updatedAt;

        public String getStringAddedAt() {
            return getAddedAt() == null ? null : DateTimeUtils.parseTimestamp(getAddedAt() * 1000);
        }

        public String getStringUpdatedAt() {
            return getUpdatedAt() == null ? null : DateTimeUtils.parseTimestamp(getUpdatedAt() * 1000);
        }
    }
}
