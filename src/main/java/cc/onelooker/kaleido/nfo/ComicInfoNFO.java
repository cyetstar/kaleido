package cc.onelooker.kaleido.nfo;

import com.google.common.collect.Sets;
import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.List;
import java.util.Set;

/**
 * @Author xiadawei
 * @Date 2024-03-24 21:42:00
 * @Description TODO
 */
@Data
@XmlRootElement(name = "ComicInfo")
@XmlAccessorType(XmlAccessType.FIELD)
public class ComicInfoNFO {
    @XmlElement(name = "Title")
    private String title;
    @XmlElement(name = "Series")
    private String series;
    @XmlElement(name = "Number")
    private String number;
    @XmlElement(name = "Count")
    private Integer count;
    @XmlElement(name = "Volume")
    private Integer volume;
    @XmlElement(name = "AlternateSeries")
    private String alternateSeries;
    @XmlElement(name = "AlternateNumber")
    private String alternateNumber;
    @XmlElement(name = "AlternateCount")
    private String alternateCount;
    @XmlJavaTypeAdapter(value = CDataAdapter.class)
    @XmlElement(name = "Summary")
    private String summary;
    @XmlElement(name = "Notes")
    private String notes;
    @XmlElement(name = "Year")
    private String year;
    @XmlElement(name = "Month")
    private String month;
    @XmlElement(name = "Day")
    private String day;
    @XmlElement(name = "Writer")
    private String writer;
    @XmlElement(name = "Penciller")
    private String penciller;
    @XmlElement(name = "Inker")
    private String inker;
    @XmlElement(name = "Colorist")
    private String colorist;
    @XmlElement(name = "Letterer")
    private String letterer;
    @XmlElement(name = "CoverArtist")
    private String coverArtist;
    @XmlElement(name = "Editor")
    private String editor;
    @XmlElement(name = "Publisher")
    private List<String> publishers;
    @XmlElement(name = "Imprint")
    private String imprint;
    @XmlElement(name = "Genre")
    private String genre;
    @XmlElement(name = "Web")
    private String web;
    @XmlElement(name = "PageCount")
    private Integer pageCount;
    @XmlElement(name = "LanguageISO")
    private String languageISO;
    @XmlElement(name = "Format")
    private String format;
    @XmlElement(name = "BlackAndWhite")
    private String blackAndWhite;
    @XmlElement(name = "Manga")
    private String manga;
    @XmlElement(name = "Characters")
    private String characters;
    @XmlElement(name = "Teams")
    private String teams;
    @XmlElement(name = "Locations")
    private String locations;
    @XmlElement(name = "ScanInformation")
    private String scanInformation;
    @XmlElement(name = "StoryArc")
    private String storyArc;
    @XmlElement(name = "SeriesGroup")
    private String seriesGroup;
    @XmlElement(name = "AgeRating")
    private String ageRating;
    @XmlElement(name = "Pages")
    private String pages;
    @XmlElement(name = "CommunityRating")
    private String communityRating;
    @XmlElement(name = "MainCharacterOrTeam")
    private String mainCharacterOrTeam;
    @XmlElement(name = "Review")
    private String review;
    @XmlElement(name = "OriginalSeries")
    private String originalSeries;
    @XmlElement(name = "Tags")
    private String tags;
    @XmlElement(name = "Aka")
    private List<String> akas;
    @XmlElement(name = "SeriesBgmId")
    private String seriesBgmId;
    @XmlElement(name = "SeriesStatus")
    private String seriesStatus;

    public String getAuthors() {
        Set<String> authors = Sets.newLinkedHashSet();
        CollectionUtils.addIgnoreNull(authors, StringUtils.defaultIfEmpty(writer, null));
        CollectionUtils.addIgnoreNull(authors, StringUtils.defaultIfEmpty(penciller, null));
        return StringUtils.join(authors, "×");
    }
}
