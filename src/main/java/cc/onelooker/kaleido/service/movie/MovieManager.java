package cc.onelooker.kaleido.service.movie;

import cc.onelooker.kaleido.dto.movie.*;
import cc.onelooker.kaleido.enums.ActorRole;
import cc.onelooker.kaleido.enums.ConfigKey;
import cc.onelooker.kaleido.enums.SourceType;
import cc.onelooker.kaleido.enums.ThreadStatus;
import cc.onelooker.kaleido.nfo.MovieNFO;
import cc.onelooker.kaleido.nfo.NFOUtil;
import cc.onelooker.kaleido.nfo.UniqueidNFO;
import cc.onelooker.kaleido.third.douban.DoubanApiService;
import cc.onelooker.kaleido.third.douban.Subject;
import cc.onelooker.kaleido.third.plex.Metadata;
import cc.onelooker.kaleido.third.plex.PlexApiService;
import cc.onelooker.kaleido.third.plex.Tag;
import cc.onelooker.kaleido.third.tmm.Doulist;
import cc.onelooker.kaleido.third.tmm.Movie;
import cc.onelooker.kaleido.third.tmm.TmmApiService;
import cc.onelooker.kaleido.utils.ConfigUtils;
import cc.onelooker.kaleido.utils.DateTimeUtil;
import cc.onelooker.kaleido.utils.KaleidoUtils;
import cc.onelooker.kaleido.utils.NioFileUtils;
import cn.hutool.http.HttpUtil;
import com.baomidou.dynamic.datasource.annotation.DSTransactional;
import com.google.common.collect.Lists;
import com.zjjcnt.common.util.DateTimeUtils;
import com.zjjcnt.common.util.constant.Constants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
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
    private MovieThreadFilenameService movieThreadFilenameService;

    @Autowired
    private MovieDoubanWeeklyService movieDoubanWeeklyService;

    @Autowired
    private PlexApiService plexApiService;

    @Autowired
    private DoubanApiService doubanApiService;

    @Autowired
    private TmmApiService tmmApiService;

    @Transactional
    public void syncPlexMovie(Long movieId) {
        Metadata metadata = plexApiService.findMovieById(movieId);
        syncPlexMovie(metadata);
    }

    @Transactional
    public void syncPlexMovieAndReadNFO(Long movieId) throws JAXBException {
        Metadata metadata = plexApiService.findMovieById(movieId);
        syncPlexMovie(metadata);
        readNFO(metadata);
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
    public void readNFO(Long movieId) throws JAXBException {
        Metadata metadata = plexApiService.findMovieById(movieId);
        readNFO(metadata);
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
        Movie doubanMovie = tmmApiService.findMovie(doubanId);
        MovieBasicDTO movieBasicDTO = movieBasicService.findById(id);
        movieBasicDTO.setDoubanId(doubanMovie.getDoubanId());
        movieBasicDTO.setSummary(doubanMovie.getSummary());
        movieBasicDTO.setRating(doubanMovie.getDoubanAverage());
        movieBasicDTO.setOriginalTitle(doubanMovie.getOriginalTitle());
        movieBasicService.update(movieBasicDTO);
        updateAkas(doubanMovie.getAkas(), id);
    }

    /**
     * 将path与doubanId关系记录在nfo文件中
     *
     * @param path
     * @param doubanId
     */
    @Transactional
    public void matchPath(Path path, String doubanId) {
        Path movieDownloadPath = Paths.get(ConfigUtils.getSysConfig(ConfigKey.movieDownloadPath));
        try {
            MovieNFO movieNFO = new MovieNFO();
            movieNFO.setDoubanid(doubanId);
            if (Files.isDirectory(path)) {
                NFOUtil.write(movieNFO, path, "movie.nfo");
                NioFileUtils.moveDir(path, movieDownloadPath, StandardCopyOption.REPLACE_EXISTING);
            } else {
                String baseName = FilenameUtils.getBaseName(path.getFileName().toString());
                Path newPath = movieDownloadPath.resolve(baseName);
                if (!Files.exists(newPath)) {
                    newPath.toFile().mkdir();
                }
                NFOUtil.write(movieNFO, newPath, "movie.nfo");
                if (!Files.isSameFile(path, newPath)) {
                    Files.move(path, newPath.resolve(path.getFileName()), StandardCopyOption.REPLACE_EXISTING);
                }
            }
        } catch (Exception e) {
            log.error("文件夹匹配豆瓣信息发生错误", e);
        }
    }

    public void updateMovieSource(Path path) {
        try {
            Path movieLibraryPath = Paths.get(ConfigUtils.getSysConfig(ConfigKey.movieLibraryPath));
            Path movieDownloadPath = Paths.get(ConfigUtils.getSysConfig(ConfigKey.movieDownloadPath));
            Movie movie;
            boolean isDirectory = Files.isDirectory(path);
            if (isDirectory) {
                movie = findDoubanMovieByNFO(path);
            } else {
                movie = findDoubanMovieByFilename(path.getFileName().toString());
            }
            if (movie == null) {
                return;
            }
            operationFolder(movie, path, isDirectory, movieLibraryPath, movieDownloadPath);
        } catch (Exception e) {
            log.error("更新电影源发生错误", e);
        }
    }

    private Movie findDoubanMovieByNFO(Path path) throws IOException {
        Path nfoPath = Files.list(path).filter(s -> FilenameUtils.isExtension(s.getFileName().toString(), "nfo")).findFirst().orElse(null);
        if (nfoPath != null) {
            try {
                MovieNFO movieNFO = NFOUtil.read(path, nfoPath.getFileName().toString());
                return findDoubanMovie(movieNFO.getDoubanid(), movieNFO.getImdbid());
            } catch (Exception e) {
                log.warn("无法读取nfo文件");
            }
        }
        return null;
    }

    private Movie findDoubanMovieByFilename(String filename) {
        if (!KaleidoUtils.isVideoFile(filename)) {
            return null;
        }
        MovieThreadDTO movieThreadDTO = movieThreadService.findByFilename(filename);
        if (movieThreadDTO != null) {
            return findDoubanMovie(movieThreadDTO.getDoubanId(), movieThreadDTO.getImdb());
        }
        return null;
    }

    private void operationFolder(Movie movie, Path path, boolean isDirectory, Path targetPath, Path sourcePath) throws Exception {
        //创建规范文件夹
        Path folderPath = createFolderPath(movie, targetPath);
        if (isDirectory) {
            Files.list(path).forEach(s -> {
                try {
                    if (Files.isDirectory(s)) {
                        NioFileUtils.moveDir(s, folderPath, StandardCopyOption.REPLACE_EXISTING);
                    } else {
                        Files.move(s, folderPath.resolve(s.getFileName()), StandardCopyOption.REPLACE_EXISTING);
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            if (!path.equals(sourcePath)) {
                NioFileUtils.deleteIfExists(path);
            }
        } else {
            //将主文件移动到文件夹
            Files.move(path, folderPath.resolve(path.getFileName()));
            //如果存在额外文件夹也移动
            Path extraPath = path.getParent().resolve("Other");
            if (Files.exists(extraPath)) {
                NioFileUtils.moveDir(extraPath, folderPath, StandardCopyOption.REPLACE_EXISTING);
            }
        }
        //下载海报
        downloadPoster(movie, folderPath);
        //输出nfo文件
        MovieNFO movieNFO = NFOUtil.toMovieNFO(movie);
        NFOUtil.write(movieNFO, folderPath, "movie.nfo");
    }

    private Movie findDoubanMovie(String doubanId, String imdbId) {
        Movie movie = null;
        if (StringUtils.isNotEmpty(doubanId)) {
            movie = tmmApiService.findMovie(doubanId);
        } else if (StringUtils.isNotEmpty(imdbId)) {
            movie = tmmApiService.findMovie(imdbId);
        }
        return movie;
    }

    private Path createFolderPath(Movie movie, Path path) throws IOException {
        String folderName = movie.getTitle() + " (" + movie.getYear() + ")";
        Path folderPath = path.resolve(folderName);
        if (Files.notExists(folderPath)) {
            Files.createDirectory(folderPath);
        }
        return folderPath;
    }

    private void downloadPoster(Movie movie, Path folderPath) {
        HttpUtil.downloadFile(movie.getPoster(), folderPath.resolve("poster.jpg").toFile());
    }

    @DSTransactional
    public void checkThreadStatus(MovieThreadDTO movieThreadDTO) {
        List<MovieThreadFilenameDTO> movieThreadFilenameDTOList = movieThreadFilenameService.listByThreadId(movieThreadDTO.getId());
        if (CollectionUtils.isEmpty(movieThreadFilenameDTOList)) {
            return;
        }
        MovieBasicDTO movieBasicDTO = null;
        if (StringUtils.isNotEmpty(movieThreadDTO.getDoubanId())) {
            movieBasicDTO = movieBasicService.findByDoubanId(movieThreadDTO.getDoubanId());
        } else if (StringUtils.isNotEmpty(movieThreadDTO.getImdb())) {
            movieBasicDTO = movieBasicService.findByImdb(movieThreadDTO.getImdb());
        }
        if (movieBasicDTO == null) {
            return;
        }
        Metadata metadata = plexApiService.findMovieById(movieBasicDTO.getId());
        String filename = StringUtils.substringAfterLast(metadata.getMedia().getPart().getFile(), Constants.SLASH);
        List<String> filenameList = movieThreadFilenameDTOList.stream().map(MovieThreadFilenameDTO::getValue).collect(Collectors.toList());
        if (filenameList.contains(filename)) {
            movieThreadDTO.setStatus(ThreadStatus.done.ordinal());
            movieThreadService.update(movieThreadDTO);
        }
    }

    @Transactional
    public void readNFO(Metadata metadata) throws JAXBException {
        Long movieId = metadata.getRatingKey();
        String movieFolder = KaleidoUtils.getMovieFolder(metadata.getMedia().getPart().getFile());
        MovieNFO movieNFO = NFOUtil.read(Paths.get(movieFolder), "movie.nfo");
        String doubanId = getUniqueid(movieNFO.getUniqueids(), SourceType.douban.name());
        String imdb = getUniqueid(movieNFO.getUniqueids(), SourceType.imdb.name());
        String tmdb = getUniqueid(movieNFO.getUniqueids(), SourceType.tmdb.name());
        MovieBasicDTO movieBasicDTO = movieBasicService.findById(movieId);
        movieBasicDTO.setDoubanId(StringUtils.defaultIfEmpty(doubanId, movieNFO.getDoubanid()));
        movieBasicDTO.setImdb(StringUtils.defaultIfEmpty(imdb, movieNFO.getImdbid()));
        movieBasicDTO.setTmdb(StringUtils.defaultIfEmpty(tmdb, movieNFO.getTmdbid()));
        movieBasicDTO.setWebsite(movieNFO.getWebsite());
        movieBasicService.update(movieBasicDTO);
        updateAkas(movieNFO.getAkas(), movieId);
        updateTags(movieNFO.getTags(), movieId);
    }

    public void exportNFO(MovieBasicDTO dto) throws JAXBException {
        Metadata metadata = plexApiService.findMovieById(dto.getId());
        String movieFolder = KaleidoUtils.getMovieFolder(metadata.getMedia().getPart().getFile());
        MovieNFO movieNFO = NFOUtil.toMovieNFO(metadata);
        movieNFO.setDoubanid(dto.getDoubanId());
        movieNFO.setImdbid(dto.getImdb());
        movieNFO.setTmdbid(dto.getTmdb());
        List<UniqueidNFO> uniqueidNFOList = Lists.newArrayList();
        CollectionUtils.addIgnoreNull(uniqueidNFOList, NFOUtil.toUniqueidNFO(dto.getDoubanId(), SourceType.douban));
        CollectionUtils.addIgnoreNull(uniqueidNFOList, NFOUtil.toUniqueidNFO(dto.getImdb(), SourceType.imdb));
        CollectionUtils.addIgnoreNull(uniqueidNFOList, NFOUtil.toUniqueidNFO(dto.getTmdb(), SourceType.tmdb));
        movieNFO.setUniqueids(uniqueidNFOList);
        List<MovieAkaDTO> movieAkaDTOList = movieAkaService.listByMovieId(dto.getId());
        if (movieAkaDTOList != null) {
            movieNFO.setAkas(movieAkaDTOList.stream().map(MovieAkaDTO::getTitle).collect(Collectors.toList()));
        }
        NFOUtil.write(movieNFO, Paths.get(movieFolder), "movie.nfo");
    }

    public void syncDoubanWeekly() {
        LocalDate today = LocalDate.of(2023, 12, 30);
        DayOfWeek dayOfWeek = today.getDayOfWeek();
        int dayOfWeekValue = dayOfWeek.getValue();
        String listingDate = DateTimeUtils.addDays("20231230", 5 - (dayOfWeekValue > 5 ? dayOfWeekValue : (7 + dayOfWeekValue)));
        List<Subject> subjectList = doubanApiService.listMovieWeekly();
        List<Long> idList = Lists.newArrayList();
        for (Subject subject : subjectList) {
            cc.onelooker.kaleido.third.douban.Movie movie = subject.getMovie();
            Long id = Long.parseLong(movie.getId());
            idList.add(id);
            MovieDoubanWeeklyDTO movieDoubanWeeklyDTO = movieDoubanWeeklyService.findById(id);
            if (movieDoubanWeeklyDTO != null && StringUtils.equals(movieDoubanWeeklyDTO.getDelistingDate(), "99999999") && movieDoubanWeeklyDTO.getTop().compareTo(subject.getRank()) < 0) {
                //在榜且排名高于之前
                movieDoubanWeeklyDTO.setTop(subject.getRank());
                movieDoubanWeeklyService.update(movieDoubanWeeklyDTO);
            } else if (movieDoubanWeeklyDTO == null) {
                movieDoubanWeeklyDTO = new MovieDoubanWeeklyDTO();
                movieDoubanWeeklyDTO.setId(id);
                movieDoubanWeeklyDTO.setTitle(movie.getTitle());
                movieDoubanWeeklyDTO.setOriginalTitle(movie.getOriginalTitle());
                movieDoubanWeeklyDTO.setYear(movie.getYear());
                movieDoubanWeeklyDTO.setListingDate(listingDate);
                movieDoubanWeeklyDTO.setDelistingDate("99999999");
                movieDoubanWeeklyDTO.setTop(subject.getRank());
                movieDoubanWeeklyDTO.setThumb(movie.getImages().getSmall());
                movieDoubanWeeklyService.insert(movieDoubanWeeklyDTO);
            }
        }
        List<MovieDoubanWeeklyDTO> movieDoubanWeeklyDTOList = movieDoubanWeeklyService.list(null);
        for (MovieDoubanWeeklyDTO movieDoubanWeeklyDTO : movieDoubanWeeklyDTOList) {
            if (StringUtils.equals(movieDoubanWeeklyDTO.getDelistingDate(), "99999999") && !idList.contains(movieDoubanWeeklyDTO.getId())) {
                //未下榜，但不在本期榜单上
                movieDoubanWeeklyDTO.setDelistingDate(listingDate);
                movieDoubanWeeklyService.update(movieDoubanWeeklyDTO);
            }
        }
    }

    @Transactional
    public void deleteMovie(Long id) {
        plexApiService.deleteMetadata(id);
        movieBasicService.deleteById(id);
    }

    @Transactional
    public void createCollection(String doubanId) {
        Doulist doulist = tmmApiService.findDoulist(doubanId);
        MovieCollectionDTO movieCollectionDTO = new MovieCollectionDTO();
        movieCollectionDTO.setDoubanId(doulist.getDoubanId());
        movieCollectionDTO.setTitle(doulist.getTitle());
        movieCollectionDTO.setSummary(doulist.getAbout());
        movieCollectionDTO.setThumb(doulist.getThumb());
        movieCollectionDTO.setChildCount(doulist.getTotal());
        movieCollectionDTO.setUpdateTime(DateTimeUtil.formatDateTime(doulist.getUpdated()));
        movieCollectionService.insert(movieCollectionDTO);
    }

    @Transactional
    public MovieCollectionDTO syncCollection(Long id) {
        MovieCollectionDTO movieCollectionDTO = movieCollectionService.findById(id);
        Doulist doulist = tmmApiService.findDoulist(movieCollectionDTO.getDoubanId());
        movieCollectionDTO.setChildCount(doulist.getTotal());
        movieCollectionDTO.setSummary(doulist.getAbout());
        movieCollectionDTO.setUpdateTime(DateTimeUtil.formatDateTime(doulist.getUpdated()));
        movieCollectionService.update(movieCollectionDTO);
        return movieCollectionDTO;
    }

    @Transactional
    public MovieBasicCollectionDTO syncCollectionMovie(Long collectionId, cc.onelooker.kaleido.third.tmm.Movie movie) {
        String doubanId = movie.getDoubanId();
        MovieBasicCollectionDTO movieBasicCollectionDTO = movieBasicCollectionService.findByCollectionIdAndDoubanId(collectionId, doubanId);
        if (movieBasicCollectionDTO == null) {
            movieBasicCollectionDTO = new MovieBasicCollectionDTO();
            movieBasicCollectionDTO.setCollectionId(collectionId);
            movieBasicCollectionDTO.setYear(movie.getYear());
            movieBasicCollectionDTO.setThumb(movie.getPoster());
            movieBasicCollectionDTO.setTitle(movie.getTitle());
            movieBasicCollectionDTO.setOriginalTitle(movie.getOriginalTitle());
            movieBasicCollectionDTO.setDoubanId(movie.getDoubanId());
            movieBasicCollectionDTO.setStatus(Constants.NO);
            MovieBasicDTO movieBasicDTO = movieBasicService.findByDoubanId(movie.getDoubanId());
            if (movieBasicDTO != null) {
                movieBasicCollectionDTO.setStatus(Constants.YES);
                movieBasicCollectionDTO.setMovieId(movieBasicDTO.getId());
            }
            movieBasicCollectionService.insert(movieBasicCollectionDTO);
        } else {
            MovieBasicDTO movieBasicDTO = movieBasicService.findByDoubanId(movie.getDoubanId());
            if (movieBasicDTO != null) {
                movieBasicCollectionDTO.setStatus(Constants.YES);
                movieBasicCollectionDTO.setMovieId(movieBasicDTO.getId());
                movieBasicCollectionService.update(movieBasicCollectionDTO);
            }
        }
        return movieBasicCollectionDTO;

    }

}
