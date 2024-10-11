package cc.onelooker.kaleido.nfo;

import lombok.Data;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.List;

@Data
@XmlRootElement(name = "episodedetails")
@XmlAccessorType(XmlAccessType.FIELD)
public class EpisodeNFO {

    private String title;
    private String originaltitle;
    private String showtitle;
    @XmlElementWrapper
    @XmlElement(name = "rating")
    private List<RatingNFO> ratings;
    @XmlElement(name = "season")
    private Integer seasonNumber;
    @XmlElement(name = "episode")
    private Integer episodeNumber;
    private String displayepisode;
    private String displayseason;
    @XmlJavaTypeAdapter(value = CDataAdapter.class)
    private String plot;
    private Integer runtime;
    private String mpaa;
    private String premiered;
    private String year;
    @XmlElement(name = "uniqueid")
    private List<UniqueidNFO> uniqueids;
    @XmlElement(name = "genre")
    private List<String> genres;
    private List<String> credits;
    @XmlElement(name = "director")
    private List<String> directors;
    @XmlElement(name = "studio")
    private List<String> studios;
    @XmlElement(name = "actor")
    private List<ActorNFO> actors;
    @XmlElement(name = "aka")
    private List<String> akas;
    private String tmdbid;

}
