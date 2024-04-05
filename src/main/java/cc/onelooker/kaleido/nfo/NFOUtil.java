package cc.onelooker.kaleido.nfo;

import cc.onelooker.kaleido.enums.SourceType;
import cc.onelooker.kaleido.third.plex.Metadata;
import cc.onelooker.kaleido.third.plex.Tag;
import cc.onelooker.kaleido.third.tmm.*;
import com.google.common.collect.Lists;
import com.zjjcnt.common.util.DateTimeUtils;
import com.zjjcnt.common.util.constant.Constants;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.StringReader;
import java.io.StringWriter;
import java.lang.reflect.Proxy;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author cyetstar
 * @Date 2023-12-19 12:23:00
 * @Description TODO
 */
public class NFOUtil {

    private static Marshaller marshaller;

    private static Unmarshaller unmarshaller;

    public static ComicInfoNFO toComicInfoNFO(Comic comic) {
        ComicInfoNFO comicInfoNFO = new ComicInfoNFO();
        return toComicInfoNFO(comicInfoNFO, comic);
    }

    private static ComicInfoNFO toComicInfoNFO(ComicInfoNFO comicInfoNFO, Comic comic) {
        comicInfoNFO.setSeries(comic.getSeries());
        comicInfoNFO.setCount(comic.getVolumeCount());
        if (StringUtils.isNotEmpty(comic.getYear())) {
            comicInfoNFO.setYear(Integer.valueOf(comic.getYear()));
        }
        comicInfoNFO.setSummary(comic.getSummary());
        String writer = comic.getAuthors().stream().filter(s -> s.getRole().equals("作者")).findFirst().map(Author::getName).orElse(null);
        if (StringUtils.isEmpty(writer)) {
            writer = comic.getAuthors().stream().filter(s -> s.getRole().equals("原作")).findFirst().map(Author::getName).orElse(null);
        }
        String penciller = comic.getAuthors().stream().filter(s -> StringUtils.containsAny(s.getRole(), "作画")).findFirst().map(Author::getName).orElse(null);
        comicInfoNFO.setWriter(writer);
        comicInfoNFO.setPenciller(penciller);
        comicInfoNFO.setPublishers(comic.getPublishers());
        comicInfoNFO.setCommunityRating(comic.getAverage());
        comicInfoNFO.setOriginalSeries(comic.getOriginalSeries());
        comicInfoNFO.setTags(StringUtils.join(comic.getTags(), Constants.COMMA));
        comicInfoNFO.setWeb("https://bgm.tv/subject/" + comic.getBgmId());
        comicInfoNFO.setAkas(comic.getAkas());
        if (comic.getTags().stream().anyMatch(s -> StringUtils.equalsAny(s, "完结", "已完结", "全一卷"))) {
            comicInfoNFO.setSeriesStatus("ENDED");
        } else {
            comicInfoNFO.setSeriesStatus("ONGOING");
        }
        return comicInfoNFO;
    }

    public static MovieNFO toMovieNFO(MovieNFO movieNFO, Movie movie) {
        movieNFO.setTitle(movie.getTitle());
        movieNFO.setOriginalTitle(movie.getOriginalTitle());
        movieNFO.setYear(movie.getYear());
        List<RatingNFO> ratingNFOList = Lists.newArrayList();
        CollectionUtils.addIgnoreNull(ratingNFOList, toRatingNFO(movie.getAverage()));
        movieNFO.setRatings(ratingNFOList);
        List<UniqueidNFO> uniqueidNFOList = Lists.newArrayList();
        CollectionUtils.addIgnoreNull(uniqueidNFOList, toUniqueidNFO(SourceType.douban, movie.getDoubanId()));
        CollectionUtils.addIgnoreNull(uniqueidNFOList, toUniqueidNFO(SourceType.imdb, movie.getImdbId()));
        CollectionUtils.addIgnoreNull(uniqueidNFOList, toUniqueidNFO(SourceType.tmdb, movie.getTmdbId()));
        movieNFO.setUniqueids(uniqueidNFOList);
        movieNFO.setAkas(movie.getAkas());
        movieNFO.setPlot(movie.getPlot());
        movieNFO.setGenres(movie.getGenres());
        movieNFO.setCountries(movie.getCountries());
        movieNFO.setMpaa(movie.getMpaa());
        if (CollectionUtils.isNotEmpty(movie.getDirectors())) {
            movieNFO.setDirectors(movie.getDirectors().stream().map(s -> StringUtils.defaultString(s.getCnName(), s.getEnName())).collect(Collectors.toList()));
        }
        if (CollectionUtils.isNotEmpty(movie.getWriters())) {
            movieNFO.setCredits(movie.getWriters().stream().map(s -> StringUtils.defaultString(s.getCnName(), s.getEnName())).collect(Collectors.toList()));
        }
        movieNFO.setActors(toActorNFOs(movie.getActors()));
        return movieNFO;
    }

    public static MovieNFO toMovieNFO(Movie movie) {
        MovieNFO movieNFO = new MovieNFO();
        return toMovieNFO(movieNFO, movie);
    }

    public static MovieNFO toMovieNFO(Metadata metadata) {
        MovieNFO movieNFO = new MovieNFO();
        movieNFO.setId(String.valueOf(metadata.getRatingKey()));
        movieNFO.setTitle(metadata.getTitle());
        movieNFO.setOriginalTitle(metadata.getOriginalTitle());
        movieNFO.setSortTitle(metadata.getTitleSort());
        movieNFO.setYear(metadata.getYear());
        movieNFO.setPlot(metadata.getSummary());
        movieNFO.setMpaa(metadata.getContentRating());
        if (metadata.getGenreList() != null) {
            movieNFO.setGenres(metadata.getGenreList().stream().map(Tag::getTag).collect(Collectors.toList()));
        }
        if (metadata.getCountryList() != null) {
            movieNFO.setCountries(metadata.getCountryList().stream().map(Tag::getTag).collect(Collectors.toList()));
        }
        if (metadata.getRating() != null) {
            RatingNFO ratingNFO = new RatingNFO();
            ratingNFO.setValue(String.valueOf(metadata.getRating()));
            movieNFO.setRatings(Lists.newArrayList(ratingNFO));
        }
        if (metadata.getDirectorList() != null) {
            movieNFO.setDirectors(metadata.getDirectorList().stream().map(Tag::getTag).collect(Collectors.toList()));
        }
        if (metadata.getWriterList() != null) {
            movieNFO.setCredits(metadata.getWriterList().stream().map(Tag::getTag).collect(Collectors.toList()));
        }
        if (metadata.getRoleList() != null) {
            movieNFO.setActors(metadata.getRoleList().stream().map(s -> toActorNFO(s.getTag(), s.getRole(), s.getThumb())).collect(Collectors.toList()));
        }
        if (metadata.getCollectionList() != null) {
            movieNFO.setSets(metadata.getCollectionList().stream().map(s -> toSetNFO(s.getTag())).collect(Collectors.toList()));
        }
        movieNFO.setDateadded(DateTimeUtils.formatDateTime(DateTimeUtils.parseSecondTimestamp(metadata.getAddedAt()), "yyyy-MM-dd HH:mm:ss"));
        return movieNFO;
    }

    public static TvshowNFO toTvshowNFO(Tvshow tvshow) {
        TvshowNFO tvshowNFO = new TvshowNFO();
        tvshowNFO.setTitle(tvshow.getTitle());
        tvshowNFO.setOriginalTitle(tvshow.getOriginalTitle());
        tvshowNFO.setPlot(tvshow.getPlot());
        tvshowNFO.setYear(tvshow.getYear());
        tvshowNFO.setPremiered(tvshow.getPremiered());
        List<RatingNFO> ratingNFOList = Lists.newArrayList();
        CollectionUtils.addIgnoreNull(ratingNFOList, toRatingNFO(tvshow.getAverage()));
        tvshowNFO.setRatings(ratingNFOList);
        List<UniqueidNFO> uniqueidNFOList = Lists.newArrayList();
        CollectionUtils.addIgnoreNull(uniqueidNFOList, toUniqueidNFO(SourceType.douban, tvshow.getDoubanId()));
        CollectionUtils.addIgnoreNull(uniqueidNFOList, toUniqueidNFO(SourceType.imdb, tvshow.getImdbId()));
        CollectionUtils.addIgnoreNull(uniqueidNFOList, toUniqueidNFO(SourceType.tmdb, tvshow.getTmdbId()));
        tvshowNFO.setUniqueids(uniqueidNFOList);
        tvshowNFO.setAkas(tvshow.getAkas());
        tvshowNFO.setGenres(tvshow.getGenres());
        tvshowNFO.setMpaa(tvshow.getMpaa());
        tvshowNFO.setStudios(tvshow.getStudios());
        if (CollectionUtils.isNotEmpty(tvshow.getDirectors())) {
            tvshowNFO.setDirectors(tvshow.getDirectors().stream().map(s -> StringUtils.defaultString(s.getCnName(), s.getEnName())).collect(Collectors.toList()));
        }
        if (CollectionUtils.isNotEmpty(tvshow.getWriters())) {
            tvshowNFO.setCredits(tvshow.getWriters().stream().map(s -> StringUtils.defaultString(s.getCnName(), s.getEnName())).collect(Collectors.toList()));
        }
        tvshowNFO.setActors(toActorNFOs(tvshow.getActors()));
        return tvshowNFO;
    }

    public static SeasonNFO toSeasonNFO(Season season, Tvshow tvshow) {
        SeasonNFO seasonNFO = new SeasonNFO();
        seasonNFO.setTitle(season.getTitle());
        seasonNFO.setOriginalTitle(season.getOriginalTitle());
        seasonNFO.setSeasonNumber(season.getSeasonNumber());
        seasonNFO.setPlot(season.getPlot());
        seasonNFO.setYear(season.getYear());
        seasonNFO.setPremiered(season.getPremiered());
        List<RatingNFO> ratingNFOList = Lists.newArrayList();
        CollectionUtils.addIgnoreNull(ratingNFOList, toRatingNFO(season.getAverage()));
        seasonNFO.setRatings(ratingNFOList);
        List<UniqueidNFO> uniqueidNFOList = Lists.newArrayList();
        CollectionUtils.addIgnoreNull(uniqueidNFOList, toUniqueidNFO(SourceType.douban, season.getDoubanId()));
        CollectionUtils.addIgnoreNull(uniqueidNFOList, toUniqueidNFO(SourceType.imdb, season.getImdbId()));
        CollectionUtils.addIgnoreNull(uniqueidNFOList, toUniqueidNFO(SourceType.tmdb, season.getTmdbId()));
        seasonNFO.setUniqueids(uniqueidNFOList);
        if (CollectionUtils.isNotEmpty(season.getCredits())) {
            seasonNFO.setCredits(season.getCredits().stream().map(s -> StringUtils.defaultString(s.getCnName(), s.getEnName())).collect(Collectors.toList()));
        }
        if (CollectionUtils.isNotEmpty(season.getDirectors())) {
            seasonNFO.setDirectors(season.getDirectors().stream().map(s -> StringUtils.defaultString(s.getCnName(), s.getEnName())).collect(Collectors.toList()));
        }
        seasonNFO.setActors(toActorNFOs(season.getActors()));
        seasonNFO.setAkas(tvshow.getAkas());
        seasonNFO.setGenres(tvshow.getGenres());
        seasonNFO.setMpaa(tvshow.getMpaa());
        seasonNFO.setStudios(tvshow.getStudios());
        return seasonNFO;
    }

    private static EpisodeNFO toEpisodeNFO(EpisodeNFO episodeNFO, Episode episode, Season season, Tvshow tvshow) {
        episodeNFO.setTitle(episode.getTitle());
        episodeNFO.setOriginaltitle(tvshow.getOriginalTitle());
        episodeNFO.setSeasonNumber(episode.getSeasonNumber());
        episodeNFO.setEpisodeNumber(episode.getEpisodeNumber());
        episodeNFO.setPlot(episode.getPlot());
        episodeNFO.setRuntime(episode.getRuntime());
        episodeNFO.setPremiered(episode.getPremiered());
        List<RatingNFO> ratingNFOList = Lists.newArrayList();
        CollectionUtils.addIgnoreNull(ratingNFOList, toRatingNFO(episode.getAverage()));
        episodeNFO.setRatings(ratingNFOList);
        List<UniqueidNFO> uniqueidNFOList = Lists.newArrayList();
        CollectionUtils.addIgnoreNull(uniqueidNFOList, toUniqueidNFO(SourceType.tmdb, episode.getTmdbId()));
        episodeNFO.setUniqueids(uniqueidNFOList);
        //获取season信息
        episodeNFO.setYear(season.getYear());
        if (CollectionUtils.isNotEmpty(season.getCredits())) {
            episodeNFO.setCredits(season.getCredits().stream().map(s -> StringUtils.defaultString(s.getCnName(), s.getEnName())).collect(Collectors.toList()));
        }
        if (CollectionUtils.isNotEmpty(season.getDirectors())) {
            episodeNFO.setDirectors(season.getDirectors().stream().map(s -> StringUtils.defaultString(s.getCnName(), s.getEnName())).collect(Collectors.toList()));
        }
        episodeNFO.setActors(toActorNFOs(season.getActors()));
        //获取tvshow信息
        episodeNFO.setMpaa(tvshow.getMpaa());
        episodeNFO.setGenres(tvshow.getGenres());
        episodeNFO.setStudios(tvshow.getStudios());
        return episodeNFO;
    }

    public static EpisodeNFO toEpisodeNFO(Episode episode, Season season, Tvshow tvshow) {
        EpisodeNFO episodeNFO = new EpisodeNFO();
        return toEpisodeNFO(episodeNFO, episode, season, tvshow);
    }

    public static UniqueidNFO toUniqueidNFO(String value, SourceType type) {
        if (StringUtils.isNotEmpty(value)) {
            UniqueidNFO uniqueidNFO = new UniqueidNFO();
            uniqueidNFO.setValue(value);
            uniqueidNFO.setType(type.name());
            return uniqueidNFO;
        }
        return null;
    }

    private static RatingNFO toRatingNFO(Float average) {
        if (average == null) {
            return null;
        }
        RatingNFO ratingNFO = new RatingNFO();
        ratingNFO.setValue(String.valueOf(average));
        return ratingNFO;
    }

    private static UniqueidNFO toUniqueidNFO(SourceType type, String value) {
        if (StringUtils.isEmpty(value)) {
            return null;
        }
        UniqueidNFO uniqueidNFO = new UniqueidNFO();
        uniqueidNFO.setType(type.name());
        uniqueidNFO.setValue(value);
        return uniqueidNFO;
    }

    private static List<ActorNFO> toActorNFOs(List<Actor> actorList) {
        List<ActorNFO> actorNFOList = Lists.newArrayList();
        if (actorList != null) {
            for (Actor actor : actorList) {
                ActorNFO actorNFO = new ActorNFO();
                actorNFO.setName(StringUtils.defaultString(actor.getCnName(), actor.getEnName()));
                actorNFO.setRole(actor.getRole());
                actorNFO.setThumb(actor.getThumb());
                actorNFOList.add(actorNFO);
            }
        }
        return actorNFOList;
    }

    private static ActorNFO toActorNFO(String name, String role, String thumb) {
        ActorNFO actorNFO = new ActorNFO();
        actorNFO.setName(name);
        actorNFO.setRole(role);
        actorNFO.setThumb(thumb);
        return actorNFO;
    }

    private static SetNFO toSetNFO(String name) {
        SetNFO setNFO = new SetNFO();
        setNFO.setName(name);
        return setNFO;
    }

    public static <T> void write(T object, Class<T> clazz, Path path, String filename) throws Exception {
        StringWriter writer = new StringWriter();
        XMLStreamWriter streamWriter = XMLOutputFactory.newInstance().createXMLStreamWriter(writer);
        XMLStreamWriter cdataStreamWriter = (XMLStreamWriter) Proxy.newProxyInstance(streamWriter.getClass().getClassLoader(), streamWriter.getClass().getInterfaces(), new CDataHandler(streamWriter));
        JAXBContext context = JAXBContext.newInstance(clazz);
        marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
        marshaller.setProperty(Marshaller.JAXB_FRAGMENT, false);
        marshaller.marshal(object, cdataStreamWriter);
        String content = indentFormat(writer.toString());
        Files.write(path.resolve(filename), content.getBytes());
    }

    public static <T> T read(Class<T> clazz, Path path, String filename) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(clazz);
        unmarshaller = context.createUnmarshaller();
        return clazz.cast(unmarshaller.unmarshal(path.resolve(filename).toFile()));
    }

    public static <T> T read(Class<T> clazz, Path filePath) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(clazz);
        unmarshaller = context.createUnmarshaller();
        return clazz.cast(unmarshaller.unmarshal(filePath.toFile()));
    }

    public static String indentFormat(String xml) {
        try {
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer();
            // *) 打开对齐开关
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            // *) 忽略掉xml声明头信息
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

            StringWriter formattedStringWriter = new StringWriter();
            transformer.transform(new StreamSource(new StringReader(xml)), new StreamResult(formattedStringWriter));

            return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + formattedStringWriter;
        } catch (TransformerException e) {
        }
        return null;
    }

    public static String getUniqueid(List<UniqueidNFO> uniqueids, String type) {
        if (CollectionUtils.isEmpty(uniqueids)) {
            return null;
        }
        return uniqueids.stream().filter(s -> StringUtils.equals(s.getType(), type)).map(s -> s.getValue()).findFirst().orElse(null);
    }

    public static Float getRating(List<RatingNFO> ratings, String type) {
        if (CollectionUtils.isEmpty(ratings)) {
            return null;
        }
        return ratings.stream().filter(s -> StringUtils.equals(s.getName(), type)).findFirst().map(s -> Float.valueOf(s.getValue())).orElse(null);
    }

}



