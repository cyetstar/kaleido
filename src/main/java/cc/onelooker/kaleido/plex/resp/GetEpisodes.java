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
public class GetEpisodes {

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

        public GetEpisodes.Metadata getMetadata() {
            return CollectionUtils.get(metadataList, 0);
        }

    }

    @Data
    public static class Metadata {
        private Long ratingKey;
        private String key;
        private Long parentRatingKey;
        private Long grandparentRatingKey;
        private String guid;
        private String parentGuid;
        private String grandparentGuid;
        private String type;
        private String title;
        private String titleSort;
        private String parentKey;
        private String grandparentKey;
        private String parentTitle;
        private String grandparentTitle;
        private String contentRating;
        private String summary;
        private Integer index;
        private Integer parentIndex;
        private Float rating;
        private String year;
        private String thumb;
        private String art;
        private Integer duration;
        private String originallyAvailableAt;
        private Long addedAt;
        private Long updatedAt;

        @JsonProperty("Director")
        private List<Tag> directorList;
        @JsonProperty("Writer")
        private List<Tag> writerList;
    }



}
