package cc.onelooker.kaleido.third.plex;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class GetMovies {

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

        @JsonIgnore
        public GetMovies.Metadata getMetadata() {
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
        private String titleSort;
        private String originalTitle;
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
        private Long addedAt;
        private Long updatedAt;
        @JsonProperty("Media")
        private List<Media> mediaList;
        @JsonProperty("Genre")
        private List<Tag> genreList;
        @JsonProperty("Country")
        private List<Tag> countryList;
        @JsonProperty("Collection")
        private List<Tag> collectionList;
        @JsonProperty("Director")
        private List<Tag> directorList;
        @JsonProperty("Writer")
        private List<Tag> writerList;
        @JsonProperty("Role")
        private List<Tag> roleList;

        @JsonIgnore
        public Media getMedia() {
            return CollectionUtils.get(mediaList, 0);
        }

    }

}
