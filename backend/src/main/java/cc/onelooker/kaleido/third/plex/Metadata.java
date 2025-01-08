package cc.onelooker.kaleido.third.plex;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.apache.commons.collections4.IterableUtils;

import java.util.List;

/**
 * @Author cyetstar
 * @Date 2023-12-15 16:22:00
 * @Description TODO
 */
@Data
public class Metadata {
    private String ratingKey;
    private String key;
    private Integer index;
    private String guid;
    private String parentRatingKey;
    private String parentGuid;
    private String parentStudio;
    private String parentKey;
    private String parentTitle;
    private Integer parentIndex;
    private String parentYear;
    private String parentThumb;
    private String grandparentRatingKey;
    private String grandparentGuid;
    private String grandparentTitle;
    private String grandparentKey;
    private String ratingCount;
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
    private String musicAnalysisVersion;
    private String loudnessAnalysisVersion;
    private String originallyAvailableAt;
    private Integer childCount;
    private Long addedAt;
    private Long updatedAt;
    @JsonProperty("Genre")
    private List<Tag> genreList;
    @JsonProperty("Style")
    private List<Tag> styleList;
    @JsonProperty("Mood")
    private List<Tag> moodList;
    @JsonProperty("Role")
    private List<Tag> roleList;
    @JsonProperty("Country")
    private List<Tag> countryList;
    @JsonProperty("Collection")
    private List<Tag> collectionList;
    @JsonProperty("Director")
    private List<Tag> directorList;
    @JsonProperty("Writer")
    private List<Tag> writerList;
    @JsonProperty("Media")
    private List<Media> mediaList;
    @JsonProperty("Location")
    private List<Location> locationList;

    public Media getMedia() {
        return IterableUtils.get(mediaList, 0);
    }

    public Location getLocation() {
        return IterableUtils.get(locationList, 0);
    }

    @Override
    public String toString() {
        return title;
    }
}
