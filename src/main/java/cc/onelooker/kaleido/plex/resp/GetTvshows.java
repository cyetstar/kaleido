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
public class GetTvshows {

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

        public GetTvshows.Metadata getMetadata() {
            return CollectionUtils.get(metadataList, 0);
        }

    }

    @Data
    public static class Metadata {
        private Long ratingKey;
        private String key;
        private String guid;
        private String studio;
        private String type;
        private String title;
        private String contentRating;
        private String summary;
        private Float rating;
        private String year;
        private Integer viewCount;
        private Long lastViewedAt;
        private String thumb;
        private String art;
        private Integer duration;
        private String originallyAvailableAt;
        private Integer childCount;
        private Long addedAt;
        private Long updatedAt;
        @JsonProperty("Genre")
        private List<Tag> genreList;
        @JsonProperty("Role")
        private List<Tag> roleList;
    }

}
