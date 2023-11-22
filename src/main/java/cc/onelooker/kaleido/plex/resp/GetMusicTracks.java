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
        private String ratingKey;
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
        private String duration;
        private String musicAnalysisVersion;
        private Long addedAt;
        private Long updatedAt;
        private Long lastViewedAt;
        @JsonProperty("Media")
        private List<Media> mediaList;

        public Media getMedia() {
            return CollectionUtils.get(mediaList, 0);
        }

        public String getStringAddedAt() {
            return getAddedAt() == null ? null : DateTimeUtils.parseTimestamp(getAddedAt() * 1000);
        }

        public String getStringUpdatedAt() {
            return getUpdatedAt() == null ? null : DateTimeUtils.parseTimestamp(getUpdatedAt() * 1000);
        }

        public String getStringLastViewedAt() {
            return getLastViewedAt() == null ? null : DateTimeUtils.parseTimestamp(getLastViewedAt() * 1000);
        }
    }

    @Data
    public static class Media {
        private String id;
        private String duration;
        private String bitrate;
        private String audioChannels;
        private String audioCodec;
        private String container;
        @JsonProperty("Part")
        private List<Part> partList;

        public Part getPart() {
            return CollectionUtils.get(partList, 0);
        }
    }

    @Data
    public static class Part {
        private String id;
        private String key;
        private String duration;
        private String file;
        private String size;
        private String container;
    }
}
