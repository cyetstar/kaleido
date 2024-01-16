package cc.onelooker.kaleido.nfo;

import cc.onelooker.kaleido.enums.SourceType;
import cc.onelooker.kaleido.third.plex.Metadata;
import cc.onelooker.kaleido.third.plex.Tag;
import cc.onelooker.kaleido.third.tmm.Actor;
import cc.onelooker.kaleido.third.tmm.Movie;
import com.google.common.collect.Lists;
import com.zjjcnt.common.util.DateTimeUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
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

    static {
        try {
            JAXBContext context = JAXBContext.newInstance(MovieNFO.class);
            marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, false);
            unmarshaller = context.createUnmarshaller();
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }

    public static MovieNFO toMovieNFO(MovieNFO movieNFO, Movie movie) {
        movieNFO.setTitle(movie.getTitle());
        movieNFO.setOriginaltitle(movie.getOriginalTitle());
        movieNFO.setYear(movie.getYear());
        CollectionUtils.addIgnoreNull(Lists.newArrayList(), toRatingNFO(movie));
        List<UniqueidNFO> uniqueidNFOList = Lists.newArrayList();
        CollectionUtils.addIgnoreNull(uniqueidNFOList, toUniqueidNFO(SourceType.douban, movie.getDoubanId()));
        CollectionUtils.addIgnoreNull(uniqueidNFOList, toUniqueidNFO(SourceType.imdb, movie.getImdbId()));
        CollectionUtils.addIgnoreNull(uniqueidNFOList, toUniqueidNFO(SourceType.tmdb, movie.getTmdbId()));
        movieNFO.setUniqueids(uniqueidNFOList);
        movieNFO.setAkas(movie.getAkas());
        movieNFO.setPlot(movie.getSummary());
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
        movieNFO.setOriginaltitle(metadata.getOriginalTitle());
        movieNFO.setSorttitle(metadata.getTitleSort());
        movieNFO.setYear(metadata.getYear());
        movieNFO.setPlot(metadata.getSummary());
        movieNFO.setMpaa(metadata.getContentRating());
        if (StringUtils.isNotEmpty(metadata.getStudio())) {
            movieNFO.setStudios(Lists.newArrayList(metadata.getStudio()));
        }
        if (metadata.getGenreList() != null) {
            movieNFO.setGenres(metadata.getGenreList().stream().map(Tag::getTag).collect(Collectors.toList()));
        }
        if (metadata.getCountryList() != null) {
            movieNFO.setCountries(metadata.getCountryList().stream().map(Tag::getTag).collect(Collectors.toList()));
        }
        if (metadata.getRating() != null) {
            RatingNFO ratingNFO = new RatingNFO();
            ratingNFO.setValue(String.valueOf(metadata.getRating()));
            ratingNFO.setName(SourceType.douban.name());
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

    public static UniqueidNFO toUniqueidNFO(String value, SourceType type) {
        if (StringUtils.isNotEmpty(value)) {
            UniqueidNFO uniqueidNFO = new UniqueidNFO();
            uniqueidNFO.setValue(value);
            uniqueidNFO.setType(type.name());
            return uniqueidNFO;
        }
        return null;
    }

    private static RatingNFO toRatingNFO(Movie movie) {
        if (movie.getAverage() == null) {
            return null;
        }
        RatingNFO ratingNFO = new RatingNFO();
        ratingNFO.setName(SourceType.douban.name());
        ratingNFO.setValue(String.valueOf(movie.getAverage()));
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

    public static void write(MovieNFO movieNFO, Path path, String filename) throws JAXBException {
        marshaller.marshal(movieNFO, path.resolve(filename).toFile());
    }

    public static MovieNFO read(Path path, String filename) throws JAXBException {
        return (MovieNFO) unmarshaller.unmarshal(path.resolve(filename).toFile());
    }
}



