package cc.onelooker.kaleido.nfo;

import lombok.Data;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.List;

@Data
@XmlRootElement(name = "tvshow")
@XmlAccessorType(XmlAccessType.FIELD)
public class TvshowNFO {

    private String title;
    @XmlElement(name = "originaltitle")
    private String originalTitle;
    @XmlElement(name = "showtitle")
    private String showTitle;
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
    private String episodeguide;
    @XmlElement(name = "uniqueid")
    private List<UniqueidNFO> uniqueids;
    @XmlElement(name = "genre")
    private List<String> genres;
    @XmlElement(name = "tag")
    private List<String> tags;
    @XmlElement(name = "studio")
    private List<String> studios;
    @XmlElement(name = "aka")
    private List<String> akas;
    @XmlElement(name = "doubanid")
    private String doubanId;
    @XmlElement(name = "imdbid")
    private String imdbId;
    @XmlElement(name = "tmdbid")
    private String tmdbId;

}
