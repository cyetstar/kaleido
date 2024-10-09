package cc.onelooker.kaleido.nfo;

import cc.onelooker.kaleido.dto.*;
import cc.onelooker.kaleido.enums.SourceType;
import cn.hutool.core.exceptions.ExceptionUtil;
import com.google.common.collect.Lists;
import com.zjjcnt.common.util.constant.Constants;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import javax.xml.bind.JAXBContext;
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
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.lang.reflect.Proxy;
import java.nio.charset.StandardCharsets;
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

    public static ComicInfoNFO toComicInfoNFO(ComicSeriesDTO comicSeriesDTO, ComicBookDTO comicBookDTO) {
        ComicInfoNFO comicInfoNFO = new ComicInfoNFO();
        comicInfoNFO.setTitle(comicBookDTO.getTitle());
        comicInfoNFO.setNumber(comicBookDTO.getBookNumber());
        if (StringUtils.isNotEmpty(comicBookDTO.getBgmId())) {
            comicInfoNFO.setWeb("https://bgm.tv/subject/" + comicBookDTO.getBgmId());
        }
        comicInfoNFO.setSeries(comicSeriesDTO.getTitle());
        comicInfoNFO.setOriginalSeries(comicSeriesDTO.getOriginalTitle());
        comicInfoNFO.setCount(comicSeriesDTO.getBookCount());
        comicInfoNFO.setYear(comicSeriesDTO.getYear());
        comicInfoNFO.setSummary(comicSeriesDTO.getSummary());
        if (comicSeriesDTO.getWriterList() != null) {
            comicSeriesDTO.getWriterList().stream().map(AuthorDTO::getName).findFirst().ifPresent(comicInfoNFO::setWriter);
        }
        if (comicSeriesDTO.getPencillerList() != null) {
            comicSeriesDTO.getPencillerList().stream().map(AuthorDTO::getName).findFirst().ifPresent(comicInfoNFO::setPenciller);
        }
        if (StringUtils.isNotEmpty(comicSeriesDTO.getPublisher())) {
            comicInfoNFO.setPublishers(Lists.newArrayList(comicSeriesDTO.getPublisher()));
        }
        comicInfoNFO.setCommunityRating(String.valueOf(comicSeriesDTO.getRating()));
        comicInfoNFO.setTags(StringUtils.join(comicSeriesDTO.getTagList(), Constants.COMMA));
        if (CollectionUtils.isNotEmpty(comicSeriesDTO.getAlternateTitleList())) {
            comicInfoNFO.setAkas(comicSeriesDTO.getAlternateTitleList());
        }
        comicInfoNFO.setSeriesBgmId(comicSeriesDTO.getBgmId());
        comicInfoNFO.setSeriesStatus(comicSeriesDTO.getStatus());
        comicInfoNFO.setCoverPageNumber(comicBookDTO.getCoverPageNumber());
        comicInfoNFO.setCoverBoxData(comicBookDTO.getCoverBoxData());
        return comicInfoNFO;
    }

    public static MovieNFO toMovieNFO(MovieBasicDTO movieBasicDTO) {
        MovieNFO movieNFO = new MovieNFO();
        movieNFO.setDoubanId(movieBasicDTO.getDoubanId());
        movieNFO.setImdbId(movieBasicDTO.getImdbId());
        movieNFO.setTmdbId(movieBasicDTO.getTmdbId());
        movieNFO.setTitle(movieBasicDTO.getTitle());
        movieNFO.setOriginalTitle(movieBasicDTO.getOriginalTitle());
        movieNFO.setSortTitle(movieBasicDTO.getSortTitle());
        movieNFO.setYear(movieBasicDTO.getYear());
        List<RatingNFO> ratingNFOList = Lists.newArrayList();
        CollectionUtils.addIgnoreNull(ratingNFOList, toRatingNFO(movieBasicDTO.getRating()));
        if (CollectionUtils.isNotEmpty(ratingNFOList)) {
            movieNFO.setRatings(ratingNFOList);
        }
        List<UniqueidNFO> uniqueidNFOList = Lists.newArrayList();
        CollectionUtils.addIgnoreNull(uniqueidNFOList, toUniqueidNFO(SourceType.douban, movieBasicDTO.getDoubanId()));
        CollectionUtils.addIgnoreNull(uniqueidNFOList, toUniqueidNFO(SourceType.imdb, movieBasicDTO.getImdbId()));
        CollectionUtils.addIgnoreNull(uniqueidNFOList, toUniqueidNFO(SourceType.tmdb, movieBasicDTO.getTmdbId()));
        if (CollectionUtils.isNotEmpty(uniqueidNFOList)) {
            movieNFO.setUniqueids(uniqueidNFOList);
        }
        if (CollectionUtils.isNotEmpty(movieBasicDTO.getAkaList())) {
            movieNFO.setAkas(movieBasicDTO.getAkaList());
        }
        movieNFO.setPlot(movieBasicDTO.getSummary());
        if (CollectionUtils.isNotEmpty(movieBasicDTO.getGenreList())) {
            movieNFO.setGenres(movieBasicDTO.getGenreList());
        }
        if (CollectionUtils.isNotEmpty(movieBasicDTO.getCountryList())) {
            movieNFO.setCountries(movieBasicDTO.getCountryList());
        }
        if (CollectionUtils.isNotEmpty(movieBasicDTO.getLanguageList())) {
            movieNFO.setLanguages(movieBasicDTO.getLanguageList());
        }
        movieNFO.setMpaa(movieBasicDTO.getContentRating());
        if (CollectionUtils.isNotEmpty(movieBasicDTO.getDirectorList())) {
            movieNFO.setDirectors(movieBasicDTO.getDirectorList().stream().map(s -> StringUtils.defaultString(s.getName(), s.getOriginalName())).collect(Collectors.toList()));
        }
        if (CollectionUtils.isNotEmpty(movieBasicDTO.getWriterList())) {
            movieNFO.setCredits(movieBasicDTO.getWriterList().stream().map(s -> StringUtils.defaultString(s.getName(), s.getOriginalName())).collect(Collectors.toList()));
        }
        if (CollectionUtils.isNotEmpty(movieBasicDTO.getActorList())) {
            movieNFO.setActors(movieBasicDTO.getActorList().stream().map(NFOUtil::toActorNFO).collect(Collectors.toList()));
        }
        if (CollectionUtils.isNotEmpty(movieBasicDTO.getTagList())) {
            movieNFO.setTags(movieBasicDTO.getTagList());
        }
        return movieNFO;
    }

    public static TvshowNFO toTvshowNFO(TvshowShowDTO tvshowShowDTO, TvshowSeasonDTO tvshowSeasonDTO) {
        TvshowNFO tvshowNFO = new TvshowNFO();
        tvshowNFO.setDoubanId(tvshowShowDTO.getDoubanId());
        tvshowNFO.setImdbId(tvshowShowDTO.getImdbId());
        tvshowNFO.setTmdbId(tvshowShowDTO.getTmdbId());
        tvshowNFO.setTitle(tvshowShowDTO.getTitle());
        tvshowNFO.setOriginalTitle(tvshowShowDTO.getOriginalTitle());
        tvshowNFO.setSortTitle(tvshowShowDTO.getSortTitle());
        tvshowNFO.setPlot(tvshowShowDTO.getSummary());
        tvshowNFO.setYear(tvshowShowDTO.getYear());
        tvshowNFO.setPremiered(tvshowShowDTO.getOriginallyAvailableAt());
        List<RatingNFO> ratingNFOList = Lists.newArrayList();
        CollectionUtils.addIgnoreNull(ratingNFOList, toRatingNFO(tvshowShowDTO.getRating()));
        if (CollectionUtils.isNotEmpty(ratingNFOList)) {
            tvshowNFO.setRatings(ratingNFOList);
        }
        List<UniqueidNFO> uniqueidNFOList = Lists.newArrayList();
        CollectionUtils.addIgnoreNull(uniqueidNFOList, toUniqueidNFO(SourceType.douban, tvshowShowDTO.getDoubanId()));
        CollectionUtils.addIgnoreNull(uniqueidNFOList, toUniqueidNFO(SourceType.imdb, tvshowShowDTO.getImdbId()));
        CollectionUtils.addIgnoreNull(uniqueidNFOList, toUniqueidNFO(SourceType.tmdb, tvshowShowDTO.getTmdbId()));
        if (CollectionUtils.isNotEmpty(uniqueidNFOList)) {
            tvshowNFO.setUniqueids(uniqueidNFOList);
        }
        if (CollectionUtils.isNotEmpty(tvshowShowDTO.getAkaList())) {
            tvshowNFO.setAkas(tvshowShowDTO.getAkaList());
        }
        if (CollectionUtils.isNotEmpty(tvshowShowDTO.getCountryList())) {
            tvshowNFO.setCountries(tvshowShowDTO.getCountryList());
        }
        if (CollectionUtils.isNotEmpty(tvshowShowDTO.getLanguageList())) {
            tvshowNFO.setLanguages(tvshowShowDTO.getLanguageList());
        }
        if (CollectionUtils.isNotEmpty(tvshowShowDTO.getGenreList())) {
            tvshowNFO.setGenres(tvshowShowDTO.getGenreList());
        }
        tvshowNFO.setMpaa(tvshowShowDTO.getContentRating());
        if (StringUtils.isNotEmpty(tvshowShowDTO.getStudio())) {
            tvshowNFO.setStudios(Lists.newArrayList(tvshowShowDTO.getStudio()));
        }
        if (CollectionUtils.isNotEmpty(tvshowSeasonDTO.getDirectorList())) {
            tvshowNFO.setDirectors(tvshowSeasonDTO.getDirectorList().stream().map(s -> StringUtils.defaultString(s.getName(), s.getOriginalName())).collect(Collectors.toList()));
        }
        if (CollectionUtils.isNotEmpty(tvshowSeasonDTO.getWriterList())) {
            tvshowNFO.setCredits(tvshowSeasonDTO.getWriterList().stream().map(s -> StringUtils.defaultString(s.getName(), s.getOriginalName())).collect(Collectors.toList()));
        }
        if (CollectionUtils.isNotEmpty(tvshowSeasonDTO.getActorList())) {
            tvshowNFO.setActors(tvshowSeasonDTO.getActorList().stream().map(NFOUtil::toActorNFO).collect(Collectors.toList()));
        }
        return tvshowNFO;
    }

    public static SeasonNFO toSeasonNFO(TvshowShowDTO tvshowShowDTO, TvshowSeasonDTO tvshowSeasonDTO) {
        SeasonNFO seasonNFO = new SeasonNFO();
        seasonNFO.setDoubanId(tvshowSeasonDTO.getDoubanId());
        seasonNFO.setImdbId(tvshowSeasonDTO.getImdbId());
        seasonNFO.setTmdbId(tvshowSeasonDTO.getTmdbId());
        seasonNFO.setTitle(tvshowSeasonDTO.getTitle());
        seasonNFO.setOriginalTitle(tvshowSeasonDTO.getOriginalTitle());
        seasonNFO.setSortTitle(tvshowSeasonDTO.getSortTitle());
        seasonNFO.setSeasonNumber(tvshowSeasonDTO.getSeasonIndex());
        seasonNFO.setPlot(tvshowSeasonDTO.getSummary());
        seasonNFO.setYear(tvshowSeasonDTO.getYear());
        seasonNFO.setPremiered(tvshowSeasonDTO.getOriginallyAvailableAt());
        List<RatingNFO> ratingNFOList = Lists.newArrayList();
        CollectionUtils.addIgnoreNull(ratingNFOList, toRatingNFO(tvshowSeasonDTO.getRating()));
        if (CollectionUtils.isNotEmpty(ratingNFOList)) {
            seasonNFO.setRatings(ratingNFOList);
        }
        List<UniqueidNFO> uniqueidNFOList = Lists.newArrayList();
        CollectionUtils.addIgnoreNull(uniqueidNFOList, toUniqueidNFO(SourceType.douban, tvshowSeasonDTO.getDoubanId()));
        CollectionUtils.addIgnoreNull(uniqueidNFOList, toUniqueidNFO(SourceType.imdb, tvshowSeasonDTO.getImdbId()));
        CollectionUtils.addIgnoreNull(uniqueidNFOList, toUniqueidNFO(SourceType.tmdb, tvshowSeasonDTO.getTmdbId()));
        if (CollectionUtils.isNotEmpty(uniqueidNFOList)) {
            seasonNFO.setUniqueids(uniqueidNFOList);
        }
        if (CollectionUtils.isNotEmpty(tvshowSeasonDTO.getDirectorList())) {
            seasonNFO.setCredits(tvshowSeasonDTO.getDirectorList().stream().map(s -> StringUtils.defaultString(s.getName(), s.getOriginalName())).collect(Collectors.toList()));
        }
        if (CollectionUtils.isNotEmpty(tvshowSeasonDTO.getWriterList())) {
            seasonNFO.setDirectors(tvshowSeasonDTO.getWriterList().stream().map(s -> StringUtils.defaultString(s.getName(), s.getOriginalName())).collect(Collectors.toList()));
        }
        if (CollectionUtils.isNotEmpty(tvshowSeasonDTO.getActorList())) {
            seasonNFO.setActors(tvshowSeasonDTO.getActorList().stream().map(NFOUtil::toActorNFO).collect(Collectors.toList()));
        }
        if (CollectionUtils.isNotEmpty(tvshowShowDTO.getAkaList())) {
            seasonNFO.setAkas(tvshowShowDTO.getAkaList());
        }
        if (CollectionUtils.isNotEmpty(tvshowShowDTO.getCountryList())) {
            seasonNFO.setCountries(tvshowShowDTO.getCountryList());
        }
        if (CollectionUtils.isNotEmpty(tvshowShowDTO.getLanguageList())) {
            seasonNFO.setLanguages(tvshowShowDTO.getLanguageList());
        }
        if (CollectionUtils.isNotEmpty(tvshowShowDTO.getGenreList())) {
            seasonNFO.setGenres(tvshowShowDTO.getGenreList());
        }
        seasonNFO.setMpaa(tvshowShowDTO.getContentRating());
        if (StringUtils.isNotEmpty(tvshowShowDTO.getStudio())) {
            seasonNFO.setStudios(Lists.newArrayList(tvshowShowDTO.getStudio()));
        }
        return seasonNFO;
    }

    public static EpisodeNFO toEpisodeNFO(TvshowShowDTO tvshowShowDTO, TvshowSeasonDTO tvshowSeasonDTO, TvshowEpisodeDTO tvshowEpisodeDTO) {
        EpisodeNFO episodeNFO = new EpisodeNFO();
        episodeNFO.setTitle(tvshowEpisodeDTO.getTitle());
        episodeNFO.setOriginaltitle(tvshowEpisodeDTO.getOriginalTitle());
        episodeNFO.setSeasonNumber(tvshowEpisodeDTO.getSeasonIndex());
        episodeNFO.setEpisodeNumber(tvshowEpisodeDTO.getEpisodeIndex());
        episodeNFO.setPlot(tvshowEpisodeDTO.getSummary());
        episodeNFO.setRuntime(tvshowEpisodeDTO.getDuration());
        episodeNFO.setPremiered(tvshowEpisodeDTO.getOriginallyAvailableAt());
        List<RatingNFO> ratingNFOList = Lists.newArrayList();
        CollectionUtils.addIgnoreNull(ratingNFOList, toRatingNFO(tvshowEpisodeDTO.getRating()));
        if (CollectionUtils.isNotEmpty(ratingNFOList)) {
            episodeNFO.setRatings(ratingNFOList);
        }
        List<UniqueidNFO> uniqueidNFOList = Lists.newArrayList();
        CollectionUtils.addIgnoreNull(uniqueidNFOList, toUniqueidNFO(SourceType.tmdb, tvshowEpisodeDTO.getTmdbId()));
        if (CollectionUtils.isNotEmpty(uniqueidNFOList)) {
            episodeNFO.setUniqueids(uniqueidNFOList);
        }
        episodeNFO.setYear(tvshowEpisodeDTO.getYear());
        //获取season信息
        if (CollectionUtils.isNotEmpty(tvshowSeasonDTO.getDirectorList())) {
            episodeNFO.setCredits(tvshowSeasonDTO.getDirectorList().stream().map(s -> StringUtils.defaultString(s.getName(), s.getOriginalName())).collect(Collectors.toList()));
        }
        if (CollectionUtils.isNotEmpty(tvshowSeasonDTO.getWriterList())) {
            episodeNFO.setDirectors(tvshowSeasonDTO.getWriterList().stream().map(s -> StringUtils.defaultString(s.getName(), s.getOriginalName())).collect(Collectors.toList()));
        }
        if (CollectionUtils.isNotEmpty(tvshowSeasonDTO.getActorList())) {
            episodeNFO.setActors(tvshowSeasonDTO.getActorList().stream().map(NFOUtil::toActorNFO).collect(Collectors.toList()));
        }
        //获取tvshow信息
        episodeNFO.setMpaa(tvshowShowDTO.getContentRating());
        if (CollectionUtils.isNotEmpty(tvshowShowDTO.getAkaList())) {
            episodeNFO.setAkas(tvshowShowDTO.getAkaList());
        }
        if (CollectionUtils.isNotEmpty(tvshowShowDTO.getGenreList())) {
            episodeNFO.setGenres(tvshowShowDTO.getGenreList());
        }
        if (StringUtils.isNotEmpty(tvshowShowDTO.getStudio())) {
            episodeNFO.setStudios(Lists.newArrayList(tvshowShowDTO.getStudio()));
        }
        return episodeNFO;
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

    private static ActorNFO toActorNFO(ActorDTO actorDTO) {
        ActorNFO actorNFO = new ActorNFO();
        actorNFO.setName(actorDTO.getName());
        actorNFO.setOriginalName(actorDTO.getOriginalName());
        actorNFO.setRole(actorDTO.getPlayRole());
        actorNFO.setThumb(actorDTO.getThumb());
        actorNFO.setDoubanId(actorDTO.getDoubanId());
        return actorNFO;
    }

    public static ActorDTO toActorDTO(ActorNFO actorNFO) {
        ActorDTO actorDTO = new ActorDTO();
        actorDTO.setName(actorNFO.getName());
        actorDTO.setOriginalName(actorNFO.getOriginalName());
        actorDTO.setPlayRole(actorNFO.getRole());
        actorDTO.setThumb(actorNFO.getThumb());
        actorDTO.setDoubanId(actorNFO.getDoubanId());
        return actorDTO;
    }

    public static <T> void write(T object, Class<T> clazz, Path path) {
        try {
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
            Files.write(path, content.getBytes());
        } catch (Exception e) {
            throw ExceptionUtil.wrapRuntime(e);
        }
    }

    public static <T> void write(T object, Class<T> clazz, Path path, String filename) {
        write(object, clazz, path.resolve(filename));
    }

    public static <T> T read(Class<T> clazz, Path path, String filename) {
        return read(clazz, path.resolve(filename));
    }

    public static <T> T read(Class<T> clazz, Path filePath) {
        try {
            if (Files.notExists(filePath) || filePath.toFile().length() == 0) {
                return null;
            }
            JAXBContext context = JAXBContext.newInstance(clazz);
            unmarshaller = context.createUnmarshaller();
            return clazz.cast(unmarshaller.unmarshal(new InputStreamReader(Files.newInputStream(filePath.toFile().toPath()), StandardCharsets.UTF_8)));
        } catch (Exception e) {
            ExceptionUtil.wrapAndThrow(e);
        }
        return null;
    }

    private static String indentFormat(String xml) {
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
            throw ExceptionUtil.wrapRuntime(e);
        }
    }

    public static String getUniqueid(List<UniqueidNFO> uniqueids, SourceType sourceType) {
        if (CollectionUtils.isEmpty(uniqueids)) {
            return null;
        }
        return uniqueids.stream().filter(s -> StringUtils.equals(s.getType(), sourceType.name())).map(UniqueidNFO::getValue).findFirst().orElse(null);
    }

    public static Float getRating(List<RatingNFO> ratings, SourceType sourceType) {
        if (CollectionUtils.isEmpty(ratings)) {
            return null;
        }
        return ratings.stream().filter(s -> StringUtils.equals(s.getName(), sourceType.name())).findFirst().map(s -> Float.valueOf(s.getValue())).orElse(null);
    }

}



