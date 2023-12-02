package cc.onelooker.kaleido.nfo;

import lombok.Data;

import javax.xml.bind.annotation.*;
import java.util.List;

@Data
@XmlRootElement(name = "movie")
@XmlAccessorType(XmlAccessType.FIELD)
public class MovieNFO {

    private String title;
    private String originaltitle;
    private String sorttitle;
    @XmlElementWrapper
    @XmlElement(name = "rating")
    private List<RatingNFO> ratings;
    private Float userrating;
    private Integer top250;
    private String outline;
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
    // tmm meta data
    private String tmdbid;
    private String tmdbCollectionId;
    private String source;
    private String edition;
    @XmlElement(name = "original_filename")
    private String originalFilename;
    @XmlElement(name = "user_note")
    private String userNote;
    // avdc meta data
    private String num;
    // custom meta data
    private String website;
    @XmlElement(name = "language")
    private List<String> languages;
    @XmlElement(name = "aka")
    private List<String> akas;

}
