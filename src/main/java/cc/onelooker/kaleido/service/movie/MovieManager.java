package cc.onelooker.kaleido.service.movie;

import cc.onelooker.kaleido.dto.movie.*;
import cc.onelooker.kaleido.enums.ActorRole;
import cc.onelooker.kaleido.enums.SourceType;
import cc.onelooker.kaleido.nfo.ActorNFO;
import cc.onelooker.kaleido.nfo.MovieNFO;
import cc.onelooker.kaleido.nfo.RatingNFO;
import cc.onelooker.kaleido.nfo.UniqueidNFO;
import cc.onelooker.kaleido.third.douban.DoubanApiService;
import cc.onelooker.kaleido.third.douban.Movie;
import cc.onelooker.kaleido.third.plex.Metadata;
import cc.onelooker.kaleido.third.plex.PlexApiService;
import cc.onelooker.kaleido.third.plex.Tag;
import cc.onelooker.kaleido.utils.ConfigUtils;
import cc.onelooker.kaleido.utils.KaleidoUtils;
import cc.onelooker.kaleido.utils.NioFileUtils;
import cn.hutool.http.HttpUtil;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @Author cyetstar
 * @Date 2023-10-01 16:09:00
 * @Description TODO
 */
@Slf4j
@Component
public class MovieManager {

    @Autowired
    private MovieBasicService movieBasicService;

    @Autowired
    private MovieCountryService movieCountryService;

    @Autowired
    private MovieGenreService movieGenreService;

    @Autowired
    private MovieAkaService movieAkaService;

    @Autowired
    private MovieTagService movieTagService;

    @Autowired
    private MovieCollectionService movieCollectionService;

    @Autowired
    private MovieActorService movieActorService;

    @Autowired
    private MovieBasicCountryService movieBasicCountryService;

    @Autowired
    private MovieBasicGenreService movieBasicGenreService;

    @Autowired
    private MovieBasicCollectionService movieBasicCollectionService;

    @Autowired
    private MovieBasicActorService movieBasicActorService;

    @Autowired
    private MovieThreadService movieThreadService;

    @Autowired
    private PlexApiService plexApiService;

    @Autowired
    private DoubanApiService doubanApiService;

    @Transactional
    public void syncPlexMovieById(Long movieId) {
        Metadata metadata = plexApiService.findMovieById(movieId);
        syncPlexMovie(metadata);
    }

    @Transactional
    public void syncPlexMovieAndReadNFO(Long movieId) throws JAXBException {
        Metadata metadata = plexApiService.findMovieById(movieId);
        syncPlexMovie(metadata);
        readNFOById(metadata);
    }

    @Transactional
    public void syncPlexMovie(Metadata metadata) {
        MovieBasicDTO movieBasicDTO = movieBasicService.findById(metadata.getRatingKey());
        if (movieBasicDTO == null) {
            movieBasicDTO = new MovieBasicDTO();
            movieBasicDTO.setId(metadata.getRatingKey());
            movieBasicDTO.setStudio(metadata.getStudio());
            movieBasicDTO.setTitle(metadata.getTitle());
            movieBasicDTO.setTitleSort(metadata.getTitleSort());
            movieBasicDTO.setOriginalTitle(metadata.getOriginalTitle());
            movieBasicDTO.setContentRating(metadata.getContentRating());
            movieBasicDTO.setSummary(metadata.getSummary());
            movieBasicDTO.setRating(metadata.getRating());
            movieBasicDTO.setLastViewedAt(metadata.getLastViewedAt());
            movieBasicDTO.setYear(metadata.getYear());
            movieBasicDTO.setThumb(metadata.getThumb());
            movieBasicDTO.setArt(metadata.getArt());
            movieBasicDTO.setDuration(metadata.getDuration());
            movieBasicDTO.setOriginallyAvailableAt(metadata.getOriginallyAvailableAt());
            movieBasicDTO.setAddedAt(metadata.getAddedAt());
            movieBasicDTO.setUpdatedAt(metadata.getUpdatedAt());
            movieBasicDTO = movieBasicService.insert(movieBasicDTO);
        } else {
            movieBasicDTO.setStudio(metadata.getStudio());
            movieBasicDTO.setTitle(metadata.getTitle());
            movieBasicDTO.setTitleSort(metadata.getTitleSort());
            movieBasicDTO.setOriginalTitle(metadata.getOriginalTitle());
            movieBasicDTO.setContentRating(metadata.getContentRating());
            movieBasicDTO.setSummary(metadata.getSummary());
            movieBasicDTO.setRating(metadata.getRating());
            movieBasicDTO.setLastViewedAt(metadata.getLastViewedAt());
            movieBasicDTO.setYear(metadata.getYear());
            movieBasicDTO.setThumb(metadata.getThumb());
            movieBasicDTO.setArt(metadata.getArt());
            movieBasicDTO.setDuration(metadata.getDuration());
            movieBasicDTO.setOriginallyAvailableAt(metadata.getOriginallyAvailableAt());
            movieBasicDTO.setUpdatedAt(metadata.getUpdatedAt());
            movieBasicService.update(movieBasicDTO);
        }
        syncCountry(metadata.getCountryList(), movieBasicDTO.getId());
        syncGenre(metadata.getGenreList(), movieBasicDTO.getId());
        syncActor(metadata.getDirectorList(), movieBasicDTO.getId(), ActorRole.Director);
        syncActor(metadata.getWriterList(), movieBasicDTO.getId(), ActorRole.Writer);
        syncActor(metadata.getRoleList(), movieBasicDTO.getId(), ActorRole.Actor);
    }

    @Transactional
    public void syncPlexMovieCollectionById(Long collectionId) {
        Metadata metadata = plexApiService.findCollectionById(collectionId);
        syncPlexMovieCollection(metadata);
    }

    @Transactional
    public void syncPlexMovieCollection(Metadata metadata) {
        Long collectionId = metadata.getRatingKey();
        MovieCollectionDTO movieCollectionDTO = movieCollectionService.findById(collectionId);
        if (movieCollectionDTO == null) {
            movieCollectionDTO = new MovieCollectionDTO();
            movieCollectionDTO.setId(metadata.getRatingKey());
            movieCollectionDTO.setTitle(metadata.getTitle());
            movieCollectionDTO.setSummary(metadata.getSummary());
            movieCollectionDTO.setThumb(metadata.getThumb());
            movieCollectionDTO.setChildCount(metadata.getChildCount());
            movieCollectionDTO.setAddedAt(metadata.getAddedAt());
            movieCollectionDTO.setUpdatedAt(metadata.getUpdatedAt());
            movieCollectionService.insert(movieCollectionDTO);
        } else {
            movieCollectionDTO.setThumb(metadata.getThumb());
            movieCollectionDTO.setChildCount(metadata.getChildCount());
            movieCollectionDTO.setAddedAt(metadata.getAddedAt());
            movieCollectionDTO.setUpdatedAt(metadata.getUpdatedAt());
            movieCollectionService.update(movieCollectionDTO);
        }
        List<Metadata> metadataList = plexApiService.listMovieByCollectionId(collectionId);
        List<MovieBasicCollectionDTO> movieBasicCollectionDTOList = movieBasicCollectionService.listByCollectionId(collectionId);
        List<Long> movieIdList = movieBasicCollectionDTOList.stream().map(MovieBasicCollectionDTO::getMovieId).collect(Collectors.toList());
        List<Long> metadataIdList = metadataList.stream().map(Metadata::getRatingKey).collect(Collectors.toList());
        Collection<Long> deleteMovieIdList = CollectionUtils.subtract(movieIdList, metadataIdList);
        if (CollectionUtils.isNotEmpty(deleteMovieIdList)) {
            for (Long deleteMovieId : deleteMovieIdList) {
                movieBasicCollectionService.deleteByMovieIdAndCollectionId(deleteMovieId, collectionId);
            }
        }
        Collection<Long> addMovieIdList = CollectionUtils.subtract(metadataIdList, movieIdList);
        if (CollectionUtils.isNotEmpty(addMovieIdList)) {
            for (Long addMovieId : addMovieIdList) {
                movieBasicCollectionService.insertByMovieIdAndCollectionId(addMovieId, collectionId);
            }
        }
    }

    private void syncActor(List<Tag> actorList, Long movieId, ActorRole actorRole) {
        if (actorList == null) {
            return;
        }
        for (Tag tag : actorList) {
            MovieActorDTO movieActorDTO = movieActorService.findById(tag.getId());
            if (movieActorDTO == null) {
                movieActorDTO = new MovieActorDTO();
                movieActorDTO.setId(tag.getId());
                movieActorDTO.setName(tag.getTag());
                movieActorDTO.setOriginalName(tag.getTag());
                movieActorDTO.setThumb(tag.getThumb());
                movieActorDTO = movieActorService.insert(movieActorDTO);
            } else if (StringUtils.isNotEmpty(tag.getThumb())) {
                movieActorDTO.setThumb(tag.getThumb());
                movieActorService.update(movieActorDTO);
            }
            MovieBasicActorDTO movieBasicActorDTO = movieBasicActorService.findByMovieIdAndActorId(movieId, movieActorDTO.getId());
            if (movieBasicActorDTO == null) {
                movieBasicActorDTO = new MovieBasicActorDTO();
                movieBasicActorDTO.setMovieId(movieId);
                movieBasicActorDTO.setActorId(movieActorDTO.getId());
                movieBasicActorDTO.setRole(actorRole.name());
                movieBasicActorDTO.setPlayRole(tag.getRole());
                movieBasicActorService.insert(movieBasicActorDTO);
            }
        }
    }

    private void syncCountry(List<Tag> countryList, Long movieId) {
        if (countryList == null) {
            return;
        }
        for (Tag tag : countryList) {
            MovieCountryDTO movieCountryDTO = movieCountryService.findById(tag.getId());
            if (movieCountryDTO == null) {
                movieCountryDTO = movieCountryService.insert(tag.getId(), tag.getTag());
            }
            MovieBasicCountryDTO movieBasicCountryDTO = movieBasicCountryService.findByMovieIdAndCountryId(movieId, movieCountryDTO.getId());
            if (movieBasicCountryDTO == null) {
                movieBasicCountryService.insertByMovieIdAndCountryId(movieId, movieCountryDTO.getId());
            }
        }
    }

    private void syncGenre(List<Tag> genreList, Long movieId) {
        if (genreList == null) {
            return;
        }
        for (Tag tag : genreList) {
            MovieGenreDTO movieGenreDTO = movieGenreService.findById(tag.getId());
            if (movieGenreDTO == null) {
                movieGenreDTO = movieGenreService.insert(tag.getId(), tag.getTag());
            }
            MovieBasicGenreDTO movieBasicGenreDTO = movieBasicGenreService.findByMovieIdAndGenreId(movieId, movieGenreDTO.getId());
            if (movieBasicGenreDTO == null) {
                movieBasicGenreService.insertByMovieIdAndGenreId(movieId, movieGenreDTO.getId());
            }
        }
    }

    @Transactional
    public void readNFOById(Long movieId) throws JAXBException {
        Metadata metadata = plexApiService.findMovieById(movieId);
        readNFOById(metadata);
    }

    @Transactional
    public void readNFOById(Metadata metadata) throws JAXBException {
        Long movieId = metadata.getRatingKey();
        String movieFolder = KaleidoUtils.getMovieFolder(metadata.getMedia().getPart().getFile());
        File file = Paths.get(movieFolder, "movie.nfo").toFile();
        MovieNFO movieNFO = parseNFO(file);
        String doubanId = getUniqueid(movieNFO.getUniqueids(), SourceType.douban.name());
        String imdb = getUniqueid(movieNFO.getUniqueids(), SourceType.imdb.name());
        MovieBasicDTO movieBasicDTO = movieBasicService.findById(movieId);
        movieBasicDTO.setDoubanId(doubanId);
        movieBasicDTO.setImdb(imdb);
        movieBasicDTO.setWebsite(movieNFO.getWebsite());
        movieBasicService.update(movieBasicDTO);

        updateAkas(movieNFO.getAkas(), movieId);
        updateTags(movieNFO.getTags(), movieId);
    }

    private MovieNFO parseNFO(File file) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(MovieNFO.class);
        Unmarshaller movieUnmarshaller = context.createUnmarshaller();
        return (MovieNFO) movieUnmarshaller.unmarshal(file);
    }

    private String getUniqueid(List<UniqueidNFO> uniqueids, String type) {
        return uniqueids.stream().filter(s -> StringUtils.equals(s.getType(), type)).map(s -> s.getValue()).findFirst().orElse(null);
    }

    private void updateAkas(List<String> akas, Long movieId) {
        if (akas == null) {
            return;
        }
        for (String aka : akas) {
            MovieAkaDTO movieAkaDTO = movieAkaService.findByTitleAndMovieId(aka, movieId);
            if (movieAkaDTO == null) {
                movieAkaDTO = new MovieAkaDTO();
                movieAkaDTO.setTitle(aka);
                movieAkaDTO.setMovieId(movieId);
                movieAkaService.insert(movieAkaDTO);
            }
        }
    }

    private void updateTags(List<String> tags, Long movieId) {
        if (tags == null) {
            return;
        }
        for (String tag : tags) {
            MovieTagDTO movieTagDTO = movieTagService.findByTagAndMovieId(tag, movieId);
            if (movieTagDTO == null) {
                movieTagDTO = new MovieTagDTO();
                movieTagDTO.setTag(tag);
                movieTagDTO.setMovieId(movieId);
                movieTagService.insert(movieTagDTO);
            }
        }
    }

    @Transactional
    public void matchDouban(Long id, String doubanId) {
        Movie doubanMovie = doubanApiService.findMovieById(doubanId);
        MovieBasicDTO movieBasicDTO = movieBasicService.findById(id);
        movieBasicDTO.setDoubanId(doubanMovie.getId());
        movieBasicDTO.setSummary(doubanMovie.getSummary());
        movieBasicDTO.setRating(doubanMovie.getRating().getAverage());
        movieBasicDTO.setOriginalTitle(doubanMovie.getOriginalTitle());
        movieBasicService.update(movieBasicDTO);
        updateAkas(doubanMovie.getAka(), id);
    }

    @Transactional
    public void deleteMovieCollection(Long[] idArray) {
        for (Long id : idArray) {
            plexApiService.deleteCollection(id);
            movieCollectionService.deleteById(id);
        }
    }

    public void updateMovieSource(Path path) {
        try {
            String movieLibraryPath = ConfigUtils.getSysConfig("movieLibraryPath");
            if (Files.isDirectory(path)) {
                Optional<Path> optionalNFOPath = Files.list(path).filter(s -> FilenameUtils.isExtension(s.getFileName().toString(), "nfo")).findFirst();
                if (optionalNFOPath.isPresent()) {
                    MovieNFO movieNFO = parseNFO(optionalNFOPath.get().toFile());
                    Movie movie = null;
                    if (StringUtils.isNotEmpty(movieNFO.getDoubanid())) {
                        movie = doubanApiService.findMovieById(movieNFO.getDoubanid());
                    } else if (StringUtils.isNotEmpty(movieNFO.getImdbid())) {
                        movie = doubanApiService.findMovieByImdbId(movieNFO.getImdbid());
                    }
                    if (movie != null) {
                        //存在豆瓣信息，则删除老NFO文件，生成新NFO文件
                        movie.setImdb(movieNFO.getImdbid());
                        Files.delete(optionalNFOPath.get());
                        generateNFO(movieNFO, movie, path);
                    } else {
                        //重命名
                        Files.move(optionalNFOPath.get(), optionalNFOPath.get().resolve("movie.nfo"));
                    }
                    NioFileUtils.moveDir(path, Paths.get(movieLibraryPath));
                } else {
                    Files.list(path).forEach(this::updateMovieSource);
                }
            } else {
                String filename = path.getFileName().toString();
                if (!KaleidoUtils.isVideoFile(filename)) {
                    return;
                }
                MovieThreadDTO movieThreadDTO = movieThreadService.findByFilename(filename);
                if (movieThreadDTO == null) {
                    return;
                }
                Movie movie = null;
                if (StringUtils.isNotEmpty(movieThreadDTO.getDoubanId())) {
                    movie = doubanApiService.findMovieById(movieThreadDTO.getDoubanId());
                } else if (StringUtils.isNotEmpty(movieThreadDTO.getImdb())) {
                    movie = doubanApiService.findMovieByImdbId(movieThreadDTO.getImdb());
                }
                if (movie == null) {
                    return;
                }
                movie.setImdb(movieThreadDTO.getImdb());
                Path folderPath = createFolderPath(movie, path);
                Files.move(path, folderPath.resolve(path.getFileName()));
                downloadPoster(movie, folderPath);
                generateNFO(null, movie, folderPath);
                NioFileUtils.moveDir(folderPath, Paths.get(movieLibraryPath));
            }
        } catch (Exception e) {
            log.error("更新电影源发生错误", e);
        }
    }

    private Path createFolderPath(Movie movie, Path path) throws IOException {
        String folderName = movie.getTitle() + " (" + movie.getYear() + ")";
        return Files.createDirectory(path.getParent().resolve(folderName));
    }

    private void downloadPoster(Movie movie, Path folderPath) {
        Movie.Thumb thumb = movie.getImages();
        String large = thumb.getLarge();
        HttpUtil.downloadFile(large, folderPath.resolve("poster.jpg").toFile());
    }

    private void generateNFO(MovieNFO movieNFO, Movie movie, Path folderPath) throws JAXBException {
        if (movieNFO == null) {
            movieNFO = new MovieNFO();
        }
        movieNFO.setTitle(movie.getTitle());
        movieNFO.setOriginaltitle(movie.getOriginalTitle());
        movieNFO.setYear(movie.getYear());
        movieNFO.setRatings(Lists.newArrayList(toRatingNFO(movie.getRating())));
        movieNFO.setUniqueids(Lists.newArrayList(toUniqueidNFO(SourceType.douban, movie.getId()), toUniqueidNFO(SourceType.imdb, movie.getImdb())));
        movieNFO.setAkas(movie.getAka());
        movieNFO.setPlot(movie.getSummary());
        movieNFO.setGenres(movie.getGenres());
        movieNFO.setCountries(movie.getCountries());
        if (CollectionUtils.isNotEmpty(movie.getDirectors())) {
            movieNFO.setDirectors(movie.getDirectors().stream().map(Movie.Cast::getName).collect(Collectors.toList()));
        }
        movieNFO.setActors(toActorNFOs(movie.getCasts()));
        JAXBContext context = JAXBContext.newInstance(MovieNFO.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.marshal(movieNFO, folderPath.resolve("movie.nfo").toFile());
    }

    private RatingNFO toRatingNFO(Movie.Rating rating) {
        RatingNFO ratingNFO = new RatingNFO();
        ratingNFO.setName(SourceType.douban.name());
        ratingNFO.setValue(String.valueOf(rating.getAverage()));
        return ratingNFO;
    }

    private UniqueidNFO toUniqueidNFO(SourceType type, String value) {
        UniqueidNFO uniqueidNFO = new UniqueidNFO();
        uniqueidNFO.setType(type.name());
        uniqueidNFO.setValue(value);
        return uniqueidNFO;
    }

    private List<ActorNFO> toActorNFOs(List<Movie.Cast> castList) {
        List<ActorNFO> actorNFOList = Lists.newArrayList();
        if (castList != null) {
            for (Movie.Cast cast : castList) {
                ActorNFO actorNFO = new ActorNFO();
                actorNFO.setName(cast.getName());
                actorNFO.setThumb(cast.getAvatars().getLarge());
                actorNFOList.add(actorNFO);
            }
        }
        return actorNFOList;
    }
}
