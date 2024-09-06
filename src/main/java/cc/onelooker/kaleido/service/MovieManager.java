package cc.onelooker.kaleido.service;

import cc.onelooker.kaleido.dto.*;
import cc.onelooker.kaleido.enums.*;
import cc.onelooker.kaleido.nfo.MovieNFO;
import cc.onelooker.kaleido.nfo.NFOUtil;
import cc.onelooker.kaleido.third.douban.DoubanApiService;
import cc.onelooker.kaleido.third.douban.Subject;
import cc.onelooker.kaleido.third.plex.Media;
import cc.onelooker.kaleido.third.plex.Metadata;
import cc.onelooker.kaleido.third.plex.PlexApiService;
import cc.onelooker.kaleido.third.plex.PlexUtil;
import cc.onelooker.kaleido.third.tmm.Doulist;
import cc.onelooker.kaleido.third.tmm.Movie;
import cc.onelooker.kaleido.third.tmm.TmmApiService;
import cc.onelooker.kaleido.third.tmm.TmmUtil;
import cc.onelooker.kaleido.utils.DateTimeUtil;
import cc.onelooker.kaleido.utils.KaleidoConstants;
import cc.onelooker.kaleido.utils.KaleidoUtils;
import cc.onelooker.kaleido.utils.NioFileUtils;
import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.http.HttpUtil;
import com.baomidou.dynamic.datasource.annotation.DSTransactional;
import com.google.common.collect.Lists;
import com.zjjcnt.common.util.constant.Constants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    private MovieCollectionService movieCollectionService;

    @Autowired
    private ActorService actorService;

    @Autowired
    private AttributeService attributeService;

    @Autowired
    private AlternateTitleService alternateTitleService;

    @Autowired
    private MovieBasicCollectionService movieBasicCollectionService;

    @Autowired
    private MovieThreadService movieThreadService;

    @Autowired
    private MovieThreadFilenameService movieThreadFilenameService;

    @Autowired
    private MovieDoubanWeeklyService movieDoubanWeeklyService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private PlexApiService plexApiService;

    @Autowired
    private DoubanApiService doubanApiService;

    @Autowired
    private TmmApiService tmmApiService;

    @Transactional
    public void saveMovie(MovieBasicDTO movieBasicDTO) {
        try {
            attributeService.updateAttributes(movieBasicDTO.getCountryList(), movieBasicDTO.getId(), AttributeType.Country);
            attributeService.updateAttributes(movieBasicDTO.getLanguageList(), movieBasicDTO.getId(), AttributeType.Language);
            attributeService.updateAttributes(movieBasicDTO.getGenreList(), movieBasicDTO.getId(), AttributeType.Genre);
            attributeService.updateAttributes(movieBasicDTO.getTagList(), movieBasicDTO.getId(), AttributeType.Tag);
            alternateTitleService.updateTitles(movieBasicDTO.getAkaList(), movieBasicDTO.getId(), SubjectType.MovieBasic);
            actorService.updateMovieActors(movieBasicDTO.getDirectorList(), movieBasicDTO.getId(), ActorRole.Director);
            actorService.updateMovieActors(movieBasicDTO.getWriterList(), movieBasicDTO.getId(), ActorRole.Writer);
            actorService.updateMovieActors(movieBasicDTO.getActorList(), movieBasicDTO.getId(), ActorRole.Actor);
            movieBasicDTO.setTitleSort(KaleidoUtils.genTitleSort(movieBasicDTO.getTitle()));
            renameDirIfChanged(movieBasicDTO);
            MovieBasicDTO existMovieBasicDTO = movieBasicService.findById(movieBasicDTO.getId());
            if (existMovieBasicDTO == null) {
                movieBasicService.insert(movieBasicDTO);
            } else {
                movieBasicService.update(movieBasicDTO);
            }
            taskService.newTask(movieBasicDTO.getId(), SubjectType.MovieBasic, TaskType.writeMovieNFO);
        } catch (IOException e) {
            ExceptionUtil.wrapAndThrow(e);
        }

    }

    @Transactional
    public void syncMovie(Metadata metadata) {
        MovieBasicDTO movieBasicDTO = movieBasicService.findById(metadata.getRatingKey());
        if (movieBasicDTO == null) {
            movieBasicDTO = new MovieBasicDTO();
        }
        PlexUtil.toMovieBasicDTO(movieBasicDTO, metadata);
        //读取NFO文件
        readNFO(movieBasicDTO);
        saveMovie(movieBasicDTO);
    }

    @Transactional
    public void matchMovie(String movieId, Movie movie) {
        if (movie == null) {
            return;
        }
        MovieBasicDTO movieBasicDTO = movieBasicService.findById(movieId);
        TmmUtil.toMovieBasicDTO(movieBasicDTO, movie);
        saveMovie(movieBasicDTO);
    }

    /**
     * 将path与信息关系记录在nfo文件中
     *
     * @param path
     * @param doubanId
     * @param tmdbId
     */
    @Transactional
    public void matchPath(Path path, String doubanId, String tmdbId) {
        Path movieImportPath = KaleidoUtils.getMovieImportPath();
        try {
            MovieNFO movieNFO = new MovieNFO();
            movieNFO.setDoubanId(doubanId);
            movieNFO.setTmdbId(tmdbId);
            if (Files.isDirectory(path)) {
                NFOUtil.write(movieNFO, MovieNFO.class, path, KaleidoConstants.MOVIE_NFO);
                NioFileUtils.moveDir(path, movieImportPath, StandardCopyOption.REPLACE_EXISTING);
            } else {
                String baseName = FilenameUtils.getBaseName(path.getFileName().toString());
                Path newPath = movieImportPath.resolve(baseName);
                if (!Files.exists(newPath)) {
                    newPath.toFile().mkdir();
                }
                NFOUtil.write(movieNFO, MovieNFO.class, newPath, KaleidoConstants.MOVIE_NFO);
                if (!Files.isSameFile(path, newPath)) {
                    Files.move(path, newPath.resolve(path.getFileName()), StandardCopyOption.REPLACE_EXISTING);
                }
            }
        } catch (Exception e) {
            log.error("文件夹匹配信息发生错误", e);
        }
    }

    @Transactional
    public void updateSource(Path path) {
        try {
            log.info("=========== 开始更新数据文件 ==========");
            log.info("== 源文件信息:{}", path);
            boolean isDirectory = Files.isDirectory(path);
            if (!isDirectory) {
                log.info("== 忽略不是文件夹的文件:{}", path);
                return;
            }
            if (Files.exists(path.resolve(KaleidoConstants.MOVIE_NFO))) {
                Movie movie = findTmmMovieByNFO(path);
                if (movie != null) {
                    MovieBasicDTO movieBasicDTO = TmmUtil.toMovieBasicDTO(movie);
                    log.info("== 查询到电影信息:{}", movieBasicDTO.getTitle());
                    operationDir(movieBasicDTO, path);
                } else {
                    Path movieLibraryPath = KaleidoUtils.getMovieLibraryPath();
                    NioFileUtils.moveDir(path, movieLibraryPath, StandardCopyOption.REPLACE_EXISTING);
                }
            } else {
                //如果不存在movie.nfo，则不做任何操作
            }
        } catch (Exception e) {
            log.error("{}, 更新电影源发生错误", path, e);
        } finally {
            log.info("=========== 完成更新数据文件 ==========");
        }
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
            movieBasicDTO = movieBasicService.findByImdbId(movieThreadDTO.getImdb());
        }
        if (movieBasicDTO == null) {
            return;
        }
        Metadata metadata = plexApiService.findMovieById(movieBasicDTO.getId());
        String filename = StringUtils.lowerCase(StringUtils.substringAfterLast(metadata.getMedia().getPart().getFile(), Constants.SLASH));
        List<String> filenameList = movieThreadFilenameDTOList.stream().map(s -> StringUtils.lowerCase(s.getValue())).collect(Collectors.toList());
        if (filenameList.contains(filename)) {
            movieThreadDTO.setStatus(ThreadStatus.done.ordinal());
            movieThreadService.update(movieThreadDTO);
        }
    }

    public void writeMovieNFO(MovieBasicDTO movieBasicDTO) {
        try {
            Path path = KaleidoUtils.getMoviePath(movieBasicDTO.getPath());
            MovieNFO movieNFO = NFOUtil.toMovieNFO(movieBasicDTO);
            NFOUtil.write(movieNFO, MovieNFO.class, path, KaleidoConstants.MOVIE_NFO);
            //不刷新，否则可能会带来性能灾难
//            plexApiService.refreshMetadata(movieBasicDTO.getId());
        } catch (Exception e) {
            log.error("导出NFO发生错误:{}", ExceptionUtil.getMessage(e));
            ExceptionUtil.wrapAndThrow(e);
        }
    }

    //FIXME 不能周五下午5点之后执行
    public void syncDoubanWeekly() {
        LocalDate today = LocalDate.now();
        int dayOfWeekValue = today.getDayOfWeek().getValue();
        int days = (dayOfWeekValue > 5 ? dayOfWeekValue : (7 + dayOfWeekValue)) - 5;
        LocalDate localDate = today.minusDays(days);
        String listingDate = localDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        List<Subject> subjectList = doubanApiService.listMovieWeekly();
        List<String> doubanIdList = Lists.newArrayList();
        for (Subject subject : subjectList) {
            cc.onelooker.kaleido.third.douban.Movie movie = subject.getMovie();
            doubanIdList.add(movie.getId());
            MovieDoubanWeeklyDTO movieDoubanWeeklyDTO = movieDoubanWeeklyService.findByDoubanId(movie.getId());
            if (movieDoubanWeeklyDTO != null) {
                movieDoubanWeeklyDTO.setTop(subject.getRank());
                movieDoubanWeeklyDTO.addListingDetail(listingDate, subject.getRank());
                movieDoubanWeeklyDTO.setStatus(Constants.YES);
                movieDoubanWeeklyService.update(movieDoubanWeeklyDTO);
            } else {
                movieDoubanWeeklyDTO = new MovieDoubanWeeklyDTO();
                movieDoubanWeeklyDTO.setDoubanId(movie.getId());
                movieDoubanWeeklyDTO.setTitle(movie.getTitle());
                movieDoubanWeeklyDTO.setOriginalTitle(movie.getOriginalTitle());
                movieDoubanWeeklyDTO.setYear(movie.getYear());
                movieDoubanWeeklyDTO.addListingDetail(listingDate, subject.getRank());
                movieDoubanWeeklyDTO.setTop(subject.getRank());
                movieDoubanWeeklyDTO.setThumb(movie.getImages().getSmall());
                movieDoubanWeeklyDTO.setStatus(Constants.YES);
                movieDoubanWeeklyService.insert(movieDoubanWeeklyDTO);
            }
        }
        List<MovieDoubanWeeklyDTO> movieDoubanWeeklyDTOList = movieDoubanWeeklyService.list(null);
        for (MovieDoubanWeeklyDTO movieDoubanWeeklyDTO : movieDoubanWeeklyDTOList) {
            if (StringUtils.equals(movieDoubanWeeklyDTO.getStatus(), Constants.YES) && !doubanIdList.contains(movieDoubanWeeklyDTO.getDoubanId())) {
                //未下榜，但不在本期榜单上
                movieDoubanWeeklyDTO.setTop(movieDoubanWeeklyDTO.getBestTop());
                movieDoubanWeeklyDTO.setStatus(Constants.NO);
                movieDoubanWeeklyService.update(movieDoubanWeeklyDTO);
            }
        }
    }

    public void syncDoubanWeekly(LocalDate localDate, String filePath) {
        int dayOfWeekValue = localDate.getDayOfWeek().getValue();
        int days = (dayOfWeekValue > 5 ? dayOfWeekValue : (7 + dayOfWeekValue)) - 5;
        LocalDate minusLocalDate = localDate.minusDays(days);
        String listingDate = minusLocalDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        List<Subject> subjectList = doubanApiService.listMovieWeeklyFromJSON(filePath);
        List<String> doubanIdList = Lists.newArrayList();
        for (Subject subject : subjectList) {
            cc.onelooker.kaleido.third.douban.Movie movie = subject.getMovie();
            doubanIdList.add(movie.getId());
            MovieDoubanWeeklyDTO movieDoubanWeeklyDTO = movieDoubanWeeklyService.findByDoubanId(movie.getId());
            if (movieDoubanWeeklyDTO != null) {
                movieDoubanWeeklyDTO.setTop(subject.getRank());
                movieDoubanWeeklyDTO.addListingDetail(listingDate, subject.getRank());
                movieDoubanWeeklyDTO.setStatus(Constants.YES);
                movieDoubanWeeklyService.update(movieDoubanWeeklyDTO);
            } else {
                movieDoubanWeeklyDTO = new MovieDoubanWeeklyDTO();
                movieDoubanWeeklyDTO.setDoubanId(movie.getId());
                movieDoubanWeeklyDTO.setTitle(movie.getTitle());
                movieDoubanWeeklyDTO.setOriginalTitle(movie.getOriginalTitle());
                movieDoubanWeeklyDTO.setYear(movie.getYear());
                movieDoubanWeeklyDTO.addListingDetail(listingDate, subject.getRank());
                movieDoubanWeeklyDTO.setTop(subject.getRank());
                movieDoubanWeeklyDTO.setThumb(movie.getImages().getSmall());
                movieDoubanWeeklyDTO.setStatus(Constants.YES);
                movieDoubanWeeklyService.insert(movieDoubanWeeklyDTO);
            }
        }
        List<MovieDoubanWeeklyDTO> movieDoubanWeeklyDTOList = movieDoubanWeeklyService.list(null);
        for (MovieDoubanWeeklyDTO movieDoubanWeeklyDTO : movieDoubanWeeklyDTOList) {
            if (StringUtils.equals(movieDoubanWeeklyDTO.getStatus(), Constants.YES) && !doubanIdList.contains(movieDoubanWeeklyDTO.getDoubanId())) {
                //未下榜，但不在本期榜单上
                movieDoubanWeeklyDTO.setTop(movieDoubanWeeklyDTO.getBestTop());
                movieDoubanWeeklyDTO.setStatus(Constants.NO);
                movieDoubanWeeklyService.update(movieDoubanWeeklyDTO);
            }
        }
    }

    @Transactional
    public void deleteMovieBasic(String movieId) {
        plexApiService.deleteMetadata(movieId);
        movieBasicService.deleteById(movieId);
    }

    public MovieBasicDTO findMovieBasic(String movieId) {
        MovieBasicDTO movieBasicDTO = movieBasicService.findById(movieId);
        List<AttributeDTO> attributeDTOList = attributeService.listBySubjectId(movieBasicDTO.getId());
        List<ActorDTO> actorDTOList = actorService.listByMovieId(movieBasicDTO.getId());
        List<AlternateTitleDTO> alternateTitleDTOList = alternateTitleService.listBySubjectId(movieBasicDTO.getId());
        movieBasicDTO.setDirectorList(actorDTOList.stream().filter(s -> StringUtils.equals(s.getRole(), ActorRole.Director.name())).collect(Collectors.toList()));
        movieBasicDTO.setWriterList(actorDTOList.stream().filter(s -> StringUtils.equals(s.getRole(), ActorRole.Writer.name())).collect(Collectors.toList()));
        movieBasicDTO.setActorList(actorDTOList.stream().filter(s -> StringUtils.equals(s.getRole(), ActorRole.Actor.name())).collect(Collectors.toList()));
        movieBasicDTO.setAkaList(alternateTitleDTOList.stream().map(AlternateTitleDTO::getTitle).collect(Collectors.toList()));
        movieBasicDTO.setLanguageList(attributeDTOList.stream().filter(s -> StringUtils.equals(s.getType(), AttributeType.Language.name())).map(AttributeDTO::getValue).collect(Collectors.toList()));
        movieBasicDTO.setCountryList(attributeDTOList.stream().filter(s -> StringUtils.equals(s.getType(), AttributeType.Country.name())).map(AttributeDTO::getValue).collect(Collectors.toList()));
        movieBasicDTO.setGenreList(attributeDTOList.stream().filter(s -> StringUtils.equals(s.getType(), AttributeType.Genre.name())).map(AttributeDTO::getValue).collect(Collectors.toList()));
        movieBasicDTO.setTagList(attributeDTOList.stream().filter(s -> StringUtils.equals(s.getType(), AttributeType.Tag.name())).map(AttributeDTO::getValue).collect(Collectors.toList()));
        return movieBasicDTO;
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
    public void analyze(Metadata metadata) {
        try {
            MovieBasicDTO movieBasicDTO = movieBasicService.findById(metadata.getRatingKey());
            Path filePath = KaleidoUtils.getMoviePath(movieBasicDTO.getPath());
            try (Stream<Path> stream = Files.list(filePath)) {
                List<Path> videoPathList = stream.filter(s -> KaleidoUtils.isVideoFile(s.getFileName().toString())).collect(Collectors.toList());
                if (CollectionUtils.size(videoPathList) > 1) {
                    movieBasicDTO.setMultipleFiles(Constants.YES);
                } else {
                    movieBasicDTO.setMultipleFiles(Constants.NO);
                }
                if (videoPathList.stream().anyMatch(s -> FilenameUtils.isExtension(s.getFileName().toString(), KaleidoUtils.lowQualityExtensions))) {
                    movieBasicDTO.setLowQuality(Constants.YES);
                } else {
                    movieBasicDTO.setLowQuality(Constants.NO);
                }
                Media media = metadata.getMedia();
                List<Media.Stream> streamList = media.getPart().getStreamList();
                if (streamList != null && streamList.stream().anyMatch(s -> s.getStreamType() == 2 && KaleidoUtils.isChineseStream(s))) {
                    movieBasicDTO.setMandarin(Constants.YES);
                } else {
                    movieBasicDTO.setMandarin(Constants.NO);
                }
                if (streamList != null && streamList.stream().anyMatch(s -> s.getStreamType() == 3 && KaleidoUtils.isChineseStream(s))) {
                    movieBasicDTO.setNoSubtitle(Constants.NO);
                } else {
                    movieBasicDTO.setNoSubtitle(Constants.YES);
                }
                movieBasicService.update(movieBasicDTO);
            }
        } catch (Exception e) {
            ExceptionUtil.wrapAndThrow(e);
        }
    }

    @Transactional
    public MovieBasicCollectionDTO syncCollectionMovie(String collectionId, Movie movie) {
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
            movieBasicCollectionDTO.setComment(movie.getComment());
            movieBasicCollectionDTO.setPos(movie.getPos());
            movieBasicCollectionDTO.setStatus(Constants.NO);
            MovieBasicDTO movieBasicDTO = movieBasicService.findByDoubanId(movie.getDoubanId());
            if (movieBasicDTO != null) {
                movieBasicCollectionDTO.setStatus(Constants.YES);
                movieBasicCollectionDTO.setMovieId(movieBasicDTO.getId());
            }
            movieBasicCollectionService.insert(movieBasicCollectionDTO);
        } else {
            movieBasicCollectionDTO.setComment(movie.getComment());
            movieBasicCollectionDTO.setPos(movie.getPos());
            MovieBasicDTO movieBasicDTO = movieBasicService.findByDoubanId(movie.getDoubanId());
            if (movieBasicDTO != null) {
                movieBasicCollectionDTO.setStatus(Constants.YES);
                movieBasicCollectionDTO.setMovieId(movieBasicDTO.getId());
            }
            movieBasicCollectionService.update(movieBasicCollectionDTO);
        }
        return movieBasicCollectionDTO;

    }

    private void renameDirIfChanged(MovieBasicDTO movieBasicDTO) throws IOException {
        String newPath = KaleidoUtils.genMovieFolder(movieBasicDTO);
        if (!StringUtils.equals(newPath, movieBasicDTO.getPath())) {
            Path moviePath = KaleidoUtils.getMoviePath(newPath);
            if (Files.notExists(moviePath)) {
                Files.createDirectories(moviePath);
            }
            NioFileUtils.renameDir(KaleidoUtils.getMoviePath(movieBasicDTO.getPath()), moviePath);
            movieBasicDTO.setPath(newPath);
        }
    }

    private void readNFO(MovieBasicDTO movieBasicDTO) {
        try {
            Path filePath = KaleidoUtils.getMoviePath(movieBasicDTO.getPath());
            if (Files.notExists(filePath.resolve(KaleidoConstants.MOVIE_NFO))) {
                return;
            }
            MovieNFO movieNFO = NFOUtil.read(MovieNFO.class, filePath, KaleidoConstants.MOVIE_NFO);
            String doubanId = NFOUtil.getUniqueid(movieNFO.getUniqueids(), SourceType.douban.name());
            String imdb = NFOUtil.getUniqueid(movieNFO.getUniqueids(), SourceType.imdb.name());
            String tmdb = NFOUtil.getUniqueid(movieNFO.getUniqueids(), SourceType.tmdb.name());
            movieBasicDTO.setDoubanId(StringUtils.defaultIfEmpty(doubanId, movieNFO.getDoubanId()));
            movieBasicDTO.setImdbId(StringUtils.defaultIfEmpty(imdb, movieNFO.getImdbId()));
            movieBasicDTO.setTmdbId(StringUtils.defaultIfEmpty(tmdb, movieNFO.getTmdbId()));
            movieBasicDTO.setWebsite(movieNFO.getWebsite());
            movieBasicDTO.setAkaList(movieNFO.getAkas());
            movieBasicDTO.setTagList(movieNFO.getTags());
        } catch (Exception e) {
            ExceptionUtil.wrapAndThrow(e);
        }
    }

    private Movie findTmmMovieByNFO(Path path) {
        try {
            MovieNFO movieNFO = NFOUtil.read(MovieNFO.class, path, KaleidoConstants.MOVIE_NFO);
            return findTmmMovie(movieNFO.getDoubanId(), movieNFO.getImdbId(), movieNFO.getTmdbId());
        } catch (Exception e) {
            log.info("NFO文件无法读取");
        }
        return null;
    }

    private void operationDir(MovieBasicDTO movieBasicDTO, Path path) throws Exception {
        //创建规范文件夹
        Path targetPath = createFolderPath(movieBasicDTO);
        moveExistingFilesToRecycleBin(targetPath);

        //下载海报
        downloadPoster(movieBasicDTO, targetPath);

        //输出nfo文件
        MovieNFO movieNFO = NFOUtil.toMovieNFO(movieBasicDTO);
        NFOUtil.write(movieNFO, MovieNFO.class, targetPath, KaleidoConstants.MOVIE_NFO);
        log.info("== 输出nfo文件");

        NioFileUtils.renameDir(path, targetPath, StandardCopyOption.REPLACE_EXISTING);
        log.info("== 移动文件夹:{}", path.getFileName());
    }

    private void moveExistingFilesToRecycleBin(Path targetPath) {
        try {
            Path recyclePath = KaleidoUtils.getMovieRecyclePath();
            Files.list(targetPath.resolveSibling(targetPath)).forEach(s -> {
                try {
                    if (KaleidoUtils.isVideoFile(s.getFileName().toString())) {
                        Files.move(s, recyclePath.resolve(s.getFileName()), StandardCopyOption.REPLACE_EXISTING);
                        log.info("== 移除原视频文件:{}", s.getFileName());
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (IOException e) {
            ExceptionUtil.wrapAndThrow(e);
        }
    }

    private Movie findTmmMovie(String doubanId, String imdbId, String tmdbId) {
        if (StringUtils.isBlank(doubanId) && StringUtils.isBlank(imdbId) && StringUtils.isBlank(tmdbId)) {
            return null;
        }
        return tmmApiService.findMovie(doubanId, imdbId, tmdbId);
    }

    private Path createFolderPath(MovieBasicDTO movieBasicDTO) throws IOException {
        String folderName = KaleidoUtils.genMovieFolder(movieBasicDTO);
        Path folderPath = null;
        int i = 0;
        while (true) {
            folderPath = KaleidoUtils.getMoviePath(i == 0 ? folderName : folderName + " " + i);
            if (createFolderPath(folderPath)) {
                break;
            } else {
                MovieNFO movieNFO = NFOUtil.read(MovieNFO.class, folderPath, KaleidoConstants.MOVIE_NFO);
                if (KaleidoUtils.isSameMovie(movieBasicDTO, movieNFO)) {
                    break;
                } else {
                    i++;
                }
            }
        }
        return folderPath;
    }

    private Boolean createFolderPath(Path folderPath) throws IOException {
        if (Files.notExists(folderPath)) {
            Files.createDirectories(folderPath);
            return true;
        } else {
            return false;
        }
    }

    private void downloadPoster(MovieBasicDTO movieBasicDTO, Path folderPath) {
        HttpUtil.downloadFile(movieBasicDTO.getPoster(), folderPath.resolve(KaleidoConstants.MOVIE_POSTER).toFile());
        log.info("== 下载海报");
    }

}
