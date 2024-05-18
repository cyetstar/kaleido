package cc.onelooker.kaleido.third.komga;

import lombok.Data;

import java.time.Instant;
import java.time.temporal.ChronoField;
import java.util.List;

/**
 * @Author xiadawei
 * @Date 2024-03-14 21:15:00
 * @Description TODO
 */
@Data
public class Book {

    private String id;
    private String seriesId;
    private String seriesTitle;
    private String libraryId;
    private String name;
    private String url;
    private Integer number;
    private String created;
    private String lastModified;
    private String fileLastModified;
    private Long sizeBytes;
    private String size;
    private String readProgress;
    private Boolean deleted;
    private String fileHash;
    private Boolean oneshot;

    private Media media;
    private Metadata metadata;

    public Long getAddedAt() {
        return Instant.parse(this.created).getLong(ChronoField.INSTANT_SECONDS);
    }

    public Long getUpdatedAt() {
        return Instant.parse(this.lastModified).getLong(ChronoField.INSTANT_SECONDS);
    }

    @Data
    public static class Media {
        private String status;
        private String mediaType;
        private Integer pagesCount;
        private String comment;
        private Boolean epubDivinaCompatible;
        private String mediaProfile;
    }

    @Data
    public static class Metadata {
        private String title;
        private Boolean titleLock;
        private String summary;
        private Boolean summaryLock;
        private Integer number;
        private Boolean numberLock;
        private Integer numberSort;
        private Boolean numberSortLock;
        private String releaseDate;
        private Boolean releaseDateLock;
        private List<Author> authors;
        private Boolean authorsLock;
        private List<String> tags;
        private Boolean tagsLock;
        private String isbn;
        private Boolean isbnLock;
        private List<Link> links;
        private String created;
        private String lastModified;
    }

}
