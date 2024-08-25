package cc.onelooker.kaleido.nfo;

import lombok.Data;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.List;
import java.util.Objects;

@Data
@XmlRootElement(name = "movie")
@XmlAccessorType(XmlAccessType.FIELD)
public class MovieNFO {

    private String title;
    @XmlElement(name = "originaltitle")
    private String originalTitle;
    @XmlElement(name = "sorttitle")
    private String sortTitle;
    @XmlElementWrapper
    @XmlElement(name = "rating")
    private List<RatingNFO> ratings;
    private Float userrating;
    private Integer top250;
    private String outline;
    @XmlJavaTypeAdapter(value = CDataAdapter.class)
    private String plot;
    private String tagline;
    private Integer runtime;
    @XmlElement(name = "thumb")
    private List<ThumbNFO> thumbs;
    private FanartNFO fanart;
    private String mpaa;
    private Integer playcount;
    private String lastplayed;
    //Kodi id
    private String id;
    @XmlElement(name = "uniqueid")
    private List<UniqueidNFO> uniqueids;
    @XmlElement(name = "genre")
    private List<String> genres;
    @XmlElement(name = "country")
    private List<String> countries;
    //Kodi only contain a single value
    @XmlElement(name = "set")
    private List<SetNFO> sets;
    @XmlElement(name = "tag")
    private List<String> tags;
    private List<String> credits;
    @XmlElement(name = "director")
    private List<String> directors;
    private String premiered;
    private String year;
    private String status;
    private String code;
    private String aired;
    @XmlElement(name = "studio")
    private List<String> studios;
    private String trailer;
    private FileinfoNFO fileinfo;
    @XmlElement(name = "actor")
    private List<ActorNFO> actors;
    private String showlink;
    private ResumeNFO resume;
    private String dateadded;
    // plex mata data
    private String epbookmark;
    private String certification;
    @XmlElement(name = "producer")
    private List<ActorNFO> producers;
    private Boolean watched;
    private String source;
    private String edition;
    private String website;
    @XmlElement(name = "language")
    private List<String> languages;
    @XmlElement(name = "aka")
    private List<String> akas;
    @XmlElement(name = "tmdbid")
    private String tmdbId;
    @XmlElement(name = "imdbid")
    private String imdbId;
    @XmlElement(name = "doubanid")
    private String doubanId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MovieNFO movieNFO = (MovieNFO) o;
        return Objects.equals(title, movieNFO.title) && Objects.equals(originalTitle, movieNFO.originalTitle) && Objects.equals(sortTitle, movieNFO.sortTitle) && Objects.equals(ratings, movieNFO.ratings) && Objects.equals(userrating, movieNFO.userrating) && Objects.equals(top250, movieNFO.top250) && Objects.equals(outline, movieNFO.outline) && Objects.equals(plot, movieNFO.plot) && Objects.equals(tagline, movieNFO.tagline) && Objects.equals(runtime, movieNFO.runtime) && Objects.equals(thumbs, movieNFO.thumbs) && Objects.equals(fanart, movieNFO.fanart) && Objects.equals(mpaa, movieNFO.mpaa) && Objects.equals(playcount, movieNFO.playcount) && Objects.equals(lastplayed, movieNFO.lastplayed) && Objects.equals(id, movieNFO.id) && Objects.equals(uniqueids, movieNFO.uniqueids) && Objects.equals(genres, movieNFO.genres) && Objects.equals(countries, movieNFO.countries) && Objects.equals(sets, movieNFO.sets) && Objects.equals(tags, movieNFO.tags) && Objects.equals(credits, movieNFO.credits) && Objects.equals(directors, movieNFO.directors) && Objects.equals(premiered, movieNFO.premiered) && Objects.equals(year, movieNFO.year) && Objects.equals(status, movieNFO.status) && Objects.equals(code, movieNFO.code) && Objects.equals(aired, movieNFO.aired) && Objects.equals(studios, movieNFO.studios) && Objects.equals(trailer, movieNFO.trailer) && Objects.equals(fileinfo, movieNFO.fileinfo) && Objects.equals(actors, movieNFO.actors) && Objects.equals(showlink, movieNFO.showlink) && Objects.equals(resume, movieNFO.resume) && Objects.equals(dateadded, movieNFO.dateadded) && Objects.equals(epbookmark, movieNFO.epbookmark) && Objects.equals(certification, movieNFO.certification) && Objects.equals(producers, movieNFO.producers) && Objects.equals(watched, movieNFO.watched) && Objects.equals(source, movieNFO.source) && Objects.equals(edition, movieNFO.edition) && Objects.equals(website, movieNFO.website) && Objects.equals(languages, movieNFO.languages) && Objects.equals(akas, movieNFO.akas) && Objects.equals(tmdbId, movieNFO.tmdbId) && Objects.equals(imdbId, movieNFO.imdbId) && Objects.equals(doubanId, movieNFO.doubanId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, originalTitle, sortTitle, ratings, userrating, top250, outline, plot, tagline, runtime, thumbs, fanart, mpaa, playcount, lastplayed, id, uniqueids, genres, countries, sets, tags, credits, directors, premiered, year, status, code, aired, studios, trailer, fileinfo, actors, showlink, resume, dateadded, epbookmark, certification, producers, watched, source, edition, website, languages, akas, tmdbId, imdbId, doubanId);
    }
}
