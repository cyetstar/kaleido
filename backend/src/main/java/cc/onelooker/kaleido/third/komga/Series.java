package cc.onelooker.kaleido.third.komga;

import lombok.Data;

import java.time.Instant;
import java.time.temporal.ChronoField;
import java.util.List;

/**
 * @Author xiadawei
 * @Date 2024-03-14 00:37:00
 * @Description TODO
 */
@Data
public class Series {

    private String id;
    private String libraryId;
    private String name;
    private String url;
    private String created;
    private String lastModified;
    private String fileLastModified;
    private Integer booksCount;
    private Integer booksReadCount;
    private Integer booksUnreadCount;
    private Integer booksInProgressCount;
    private Boolean deleted;
    private Boolean oneshot;
    private Metadata metadata;
    private BooksMetadata booksMetadata;

    public Long getAddedAt() {
        return Instant.parse(this.created).getLong(ChronoField.INSTANT_SECONDS);
    }

    public Long getUpdatedAt() {
        return Instant.parse(this.lastModified).getLong(ChronoField.INSTANT_SECONDS);
    }

    @Data
    public static class Metadata {
        private String status;
        private Boolean statusLock;
        private String title;
        private Boolean titleLock;
        private String titleSort;
        private Boolean titleSortLock;
        private String summary;
        private Boolean summaryLock;
        private String readingDirection;
        private Boolean readingDirectionLock;
        private String publisher;
        private Boolean publisherLock;
        private String ageRating;
        private Boolean ageRatingLock;
        private String language;
        private Boolean languageLock;
        private List<String> genres;
        private Boolean genresLock;
        private List<String> tags;
        private Boolean tagsLock;
        private Integer totalBookCount;
        private Boolean totalBookCountLock;
        private List<String> sharingLabels;
        private Boolean sharingLabelsLock;
        private List<Link> links;
        private Boolean linksLock;
        private List<AlternateTitle> alternateTitles;
        private Boolean alternateTitlesLock;
        private String created;
        private String lastModified;
    }

    @Data
    public static class BooksMetadata {
        private List<Author> authors;
        private List<String> tags;
        private String releaseDate;
        private String summary;
        private String summaryNumber;
        private String created;
        private String lastModified;
    }
}
