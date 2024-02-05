package cc.onelooker.kaleido.nfo;

import lombok.Data;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.List;

/**
 * @Author xiadawei
 * @Date 2024-02-02 20:03:00
 * @Description TODO
 */
@Data
@XmlRootElement(name = "season")
@XmlAccessorType(XmlAccessType.FIELD)
public class SeasonNFO {

    private String title;
    @XmlElement(name = "originaltitle")
    private String originalTitle;
    @XmlElement(name = "sorttitle")
    private String sortTitle;
    @XmlElementWrapper
    @XmlElement(name = "rating")
    private List<RatingNFO> ratings;
    @XmlJavaTypeAdapter(value = CDataAdapter.class)
    private String plot;
    private String mpaa;
    private String premiered;
    private String year;
    @XmlElement(name = "uniqueid")
    private List<UniqueidNFO> uniqueids;
    @XmlElement(name = "genre")
    private List<String> genres;
    @XmlElement(name = "country")
    private List<String> countries;
    private List<String> credits;
    @XmlElement(name = "director")
    private List<String> directors;
    @XmlElement(name = "actor")
    private List<ActorNFO> actors;
    @XmlElement(name = "aka")
    private List<String> akas;
    @XmlElement(name = "studio")
    private List<String> studios;
    @XmlElement(name = "tmdbid")
    private String tmdbId;
    @XmlElement(name = "imdbid")
    private String imdbId;
    @XmlElement(name = "doubanid")
    private String doubanId;
}
