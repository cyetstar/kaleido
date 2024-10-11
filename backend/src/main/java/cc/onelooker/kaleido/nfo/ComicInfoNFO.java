package cc.onelooker.kaleido.nfo;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.List;
import java.util.Objects;

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
    private Integer number;
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
    @XmlElement(name = "CoverPageNumber")
    private Integer coverPageNumber;
    @XmlElement(name = "CoverBoxData")
    private String coverBoxData;

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        ComicInfoNFO that = (ComicInfoNFO) object;
        return Objects.equals(title, that.title) && Objects.equals(series, that.series) && Objects.equals(number, that.number) && Objects.equals(count, that.count) && Objects.equals(volume, that.volume) && Objects.equals(alternateSeries, that.alternateSeries) && Objects.equals(alternateNumber, that.alternateNumber) && Objects.equals(alternateCount, that.alternateCount) && Objects.equals(summary, that.summary) && Objects.equals(notes, that.notes) && Objects.equals(year, that.year) && Objects.equals(month, that.month) && Objects.equals(day, that.day) && Objects.equals(writer, that.writer) && Objects.equals(penciller, that.penciller) && Objects.equals(inker, that.inker) && Objects.equals(colorist, that.colorist) && Objects.equals(letterer, that.letterer) && Objects.equals(coverArtist, that.coverArtist) && Objects.equals(editor, that.editor) && Objects.equals(publishers, that.publishers) && Objects.equals(imprint, that.imprint) && Objects.equals(genre, that.genre) && Objects.equals(web, that.web) && Objects.equals(pageCount, that.pageCount) && Objects.equals(languageISO, that.languageISO) && Objects.equals(format, that.format) && Objects.equals(blackAndWhite, that.blackAndWhite) && Objects.equals(manga, that.manga) && Objects.equals(characters, that.characters) && Objects.equals(teams, that.teams) && Objects.equals(locations, that.locations) && Objects.equals(scanInformation, that.scanInformation) && Objects.equals(storyArc, that.storyArc) && Objects.equals(seriesGroup, that.seriesGroup) && Objects.equals(ageRating, that.ageRating) && Objects.equals(pages, that.pages) && Objects.equals(communityRating, that.communityRating) && Objects.equals(mainCharacterOrTeam, that.mainCharacterOrTeam) && Objects.equals(review, that.review) && Objects.equals(originalSeries, that.originalSeries) && Objects.equals(tags, that.tags) && Objects.equals(akas, that.akas) && Objects.equals(seriesBgmId, that.seriesBgmId) && Objects.equals(seriesStatus, that.seriesStatus) && Objects.equals(coverPageNumber, that.coverPageNumber) && Objects.equals(coverBoxData, that.coverBoxData);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, series, number, count, volume, alternateSeries, alternateNumber, alternateCount, summary, notes, year, month, day, writer, penciller, inker, colorist, letterer, coverArtist, editor, publishers, imprint, genre, web, pageCount, languageISO, format, blackAndWhite, manga, characters, teams, locations, scanInformation, storyArc, seriesGroup, ageRating, pages, communityRating, mainCharacterOrTeam, review, originalSeries, tags, akas, seriesBgmId, seriesStatus, coverPageNumber, coverBoxData);
    }
}
