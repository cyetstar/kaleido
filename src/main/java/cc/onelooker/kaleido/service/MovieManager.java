package cc.onelooker.kaleido.service;

import cc.onelooker.kaleido.dto.*;
import cc.onelooker.kaleido.enums.*;
import cc.onelooker.kaleido.nfo.MovieNFO;
import cc.onelooker.kaleido.nfo.NFOUtil;
import cc.onelooker.kaleido.third.plex.Media;
import cc.onelooker.kaleido.third.plex.Metadata;
import cc.onelooker.kaleido.third.plex.PlexApiService;
import cc.onelooker.kaleido.third.plex.PlexUtil;
import cc.onelooker.kaleido.third.tmm.Doulist;
import cc.onelooker.kaleido.third.tmm.Movie;
import cc.onelooker.kaleido.third.tmm.TmmApiService;
import cc.onelooker.kaleido.third.tmm.TmmUtil;
import cc.onelooker.kaleido.utils.*;
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
            movieBasicDTO.setSortTitle(KaleidoUtils.genSortTitle(movieBasicDTO.getTitle()));
            renameDirIfChanged(movieBasicDTO);
            MovieBasicDTO existMovieBasicDTO = movieBasicService.findById(movieBasicDTO.getId());
            if (existMovieBasicDTO == null) {
                movieBasicService.insert(movieBasicDTO);
            } else {
                movieBasicService.update(movieBasicDTO);
            }
            if (ConfigUtils.isEnabled(ConfigKey.writeMovieNFO)) {
                taskService.newTask(movieBasicDTO.getId(), SubjectType.MovieBasic, TaskType.writeMovieNFO);
            }
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
    public void matchInfo(String movieId, Movie movie) {
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
     * @param movie
     */
    @Transactional
    public void matchPath(Path path, Movie movie) {
        try {
            NioFileUtils.deleteByFilter(path, "nfo");
            MovieNFO movieNFO = new MovieNFO();
            movieNFO.setDoubanId(movie.getDoubanId());
            movieNFO.setTmdbId(movie.getTmdbId());
            Path importPath = KaleidoUtils.getMovieImportPath();
            String filename = FilenameUtils.getBaseName(path.getFileName().toString());
            if (StringUtils.isNotEmpty(movie.getTitle()) && (StringUtils.isNotEmpty(movie.getDoubanId()) || StringUtils.isNotEmpty(movie.getTmdbId()))) {
                filename = movie.getTitle() + "(" + StringUtils.defaultIfEmpty(movie.getDoubanId(), movie.getTmdbId()) + ")";
            }
            Path newPath = importPath.resolve(filename);
            if (Files.isDirectory(path)) {
                if (!StringUtils.equals(newPath.toString(), path.toString())) {
                    NioFileUtils.renameDir(path, newPath, StandardCopyOption.REPLACE_EXISTING);
                }
                NFOUtil.write(movieNFO, MovieNFO.class, newPath, KaleidoConstants.MOVIE_NFO);
            } else {
                if (Files.notExists(newPath)) {
                    Files.createDirectories(newPath);
                }
                NFOUtil.write(movieNFO, MovieNFO.class, newPath, KaleidoConstants.MOVIE_NFO);
                Files.move(path, newPath.resolve(path.getFileName()), StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (Exception e) {
            ExceptionUtil.wrapAndThrow(e);
        }
    }

    @Transactional
    public void updateSource(Path path) {
        boolean isDirectory = Files.isDirectory(path);
        if (!isDirectory) {
            return;
        }
        if (Files.exists(path.resolve(KaleidoConstants.MOVIE_NFO))) {
            try {
                log.info("== 开始更新数据文件: {}", path);
                Movie movie = findTmmMovieByNFO(path);
                if (movie != null) {
                    MovieBasicDTO movieBasicDTO = TmmUtil.toMovieBasicDTO(movie);
                    log.info("== 查询到电影信息: {}", movieBasicDTO.getTitle());
                    operationPath(movieBasicDTO, path);
                } else {
                    log.info("== 未找到匹配信息，直接移动文件");
                    Path movieLibraryPath = KaleidoUtils.getMovieLibraryPath();
                    NioFileUtils.moveDir(path, movieLibraryPath, StandardCopyOption.REPLACE_EXISTING);
                }
            } catch (Exception e) {
                log.error("== 更新电影源发生错误: {}", path, e);
            } finally {
                log.info("== 完成更新数据文件: {}", path);
            }
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

    public void writeMovieNFO(MovieBasicDTO movieBasicDTO, MovieNFO movieNFO) {
        Path path = KaleidoUtils.getMoviePath(movieBasicDTO.getPath());
        NFOUtil.write(movieNFO, MovieNFO.class, path, KaleidoConstants.MOVIE_NFO);
        if (ConfigUtils.isEnabled(ConfigKey.refreshMetadata)) {
            //如果大量刷新，否则可能会给Plex带来性能灾难
            plexApiService.refreshMetadata(movieBasicDTO.getId());
        }
    }

    //FIXME 不能周五下午5点之后执行
    public void syncDoubanWeekly() {
        LocalDate today = LocalDate.now();
        int dayOfWeekValue = today.getDayOfWeek().getValue();
        int days = (dayOfWeekValue > 5 ? dayOfWeekValue : (7 + dayOfWeekValue)) - 5;
        LocalDate localDate = today.minusDays(days);
        String listingDate = localDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        List<Movie> movieList = tmmApiService.listMovieWeekly();
        List<String> doubanIdList = Lists.newArrayList();
        for (Movie movie : movieList) {
            doubanIdList.add(movie.getDoubanId());
            MovieDoubanWeeklyDTO movieDoubanWeeklyDTO = movieDoubanWeeklyService.findByDoubanId(movie.getDoubanId());
            if (movieDoubanWeeklyDTO != null) {
                movieDoubanWeeklyDTO.setTop(movie.getPos());
                movieDoubanWeeklyDTO.addListingDetail(listingDate, movie.getPos());
                movieDoubanWeeklyDTO.setStatus(Constants.YES);
                movieDoubanWeeklyService.update(movieDoubanWeeklyDTO);
            } else {
                movieDoubanWeeklyDTO = new MovieDoubanWeeklyDTO();
                movieDoubanWeeklyDTO.setDoubanId(movie.getDoubanId());
                movieDoubanWeeklyDTO.setTitle(movie.getTitle());
                movieDoubanWeeklyDTO.setOriginalTitle(movie.getOriginalTitle());
                movieDoubanWeeklyDTO.setYear(movie.getYear());
                movieDoubanWeeklyDTO.addListingDetail(listingDate, movie.getPos());
                movieDoubanWeeklyDTO.setTop(movie.getPos());
                movieDoubanWeeklyDTO.setThumb(movie.getPoster());
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
        if (StringUtils.isEmpty(movieBasicDTO.getPath())) {
            //如果path不存在，则重新从数据库中取出来
            MovieBasicDTO pathDTO = movieBasicService.findById(movieBasicDTO.getId());
            movieBasicDTO.setPath(pathDTO.getPath());
        }
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
            MovieNFO movieNFO = NFOUtil.read(MovieNFO.class, filePath, KaleidoConstants.MOVIE_NFO);
            if (movieNFO == null) {
                return;
            }
            String doubanId = NFOUtil.getUniqueid(movieNFO.getUniqueids(), SourceType.douban);
            String imdb = NFOUtil.getUniqueid(movieNFO.getUniqueids(), SourceType.imdb);
            String tmdb = NFOUtil.getUniqueid(movieNFO.getUniqueids(), SourceType.tmdb);
            //plex可能不会传递year，通过nfo文件补充该值
            movieBasicDTO.setYear(StringUtils.defaultString(movieBasicDTO.getYear(), movieNFO.getYear()));
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
            return tmmApiService.findMovie(movieNFO.getDoubanId(), movieNFO.getImdbId(), movieNFO.getTmdbId());
        } catch (Exception e) {
            log.info("== NFO文件无法读取: {}", path.resolve(KaleidoConstants.MOVIE_NFO));
        }
        return null;
    }

    private void operationPath(MovieBasicDTO movieBasicDTO, Path path) {
        try {
            //创建规范文件夹
            Path moviePath = createMoviePath(movieBasicDTO);
            moveExistingFilesToRecycleBin(moviePath);
            NioFileUtils.renameDir(path, moviePath, StandardCopyOption.REPLACE_EXISTING);
            log.info("== 移动文件夹至: {}", moviePath);
            //下载海报
            downloadPoster(movieBasicDTO, moviePath);
            //输出nfo文件
            MovieNFO movieNFO = NFOUtil.toMovieNFO(movieBasicDTO);
            NFOUtil.write(movieNFO, MovieNFO.class, moviePath, KaleidoConstants.MOVIE_NFO);
            log.info("== 输出nfo文件: {}", moviePath.resolve(KaleidoConstants.MOVIE_NFO));
        } catch (Exception e) {
            ExceptionUtil.wrapAndThrow(e);
        }
    }

    private void moveExistingFilesToRecycleBin(Path moviePath) throws IOException {
        if (Files.exists(moviePath)) {
            Path movieRecyclePath = KaleidoUtils.getMovieRecyclePath(moviePath.toString());
            KaleidoUtils.createFolderPath(movieRecyclePath);
            //直接将整个文件夹移到回收站
            NioFileUtils.renameDir(moviePath, movieRecyclePath, StandardCopyOption.REPLACE_EXISTING);
            log.info("== 原文件夹移至回收站: {}", movieRecyclePath);
        }
    }

    private Path createMoviePath(MovieBasicDTO movieBasicDTO) throws IOException {
        String folderName = KaleidoUtils.genMovieFolder(movieBasicDTO);
        Path folderPath = null;
        int i = 0;
        while (true) {
            folderPath = KaleidoUtils.getMoviePath(i == 0 ? folderName : folderName + StringUtils.SPACE + i);
            if (KaleidoUtils.createFolderPath(folderPath)) {
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
        log.info("== 创建电影文件夹: {}", folderPath);
        return folderPath;
    }

    private void downloadPoster(MovieBasicDTO movieBasicDTO, Path folderPath) {
        if (StringUtils.isEmpty(movieBasicDTO.getThumb())) {
            return;
        }
        HttpUtil.downloadFile(movieBasicDTO.getThumb(), folderPath.resolve(KaleidoConstants.POSTER).toFile());
        log.info("== 下载海报: {}", movieBasicDTO.getThumb());
    }

}
