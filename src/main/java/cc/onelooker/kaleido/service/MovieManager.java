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
import cc.onelooker.kaleido.third.plex.Tag;
import cc.onelooker.kaleido.third.tmm.*;
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

    /**
     * 保存编辑信息
     *
     * @param movieBasicDTO
     */
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
            taskService.newTask(movieBasicDTO.getId(), SubjectType.MovieBasic, movieBasicDTO.getTitle(), TaskType.writeMovieNFO);
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
        movieBasicDTO.setId(metadata.getRatingKey());
        movieBasicDTO.setTitle(metadata.getTitle());
        movieBasicDTO.setTitleSort(metadata.getTitleSort());
        movieBasicDTO.setOriginalTitle(metadata.getOriginalTitle());
        movieBasicDTO.setContentRating(metadata.getContentRating());
        movieBasicDTO.setStudio(metadata.getStudio());
        movieBasicDTO.setSummary(metadata.getSummary());
        movieBasicDTO.setRating(metadata.getRating());
        movieBasicDTO.setYear(metadata.getYear());
        movieBasicDTO.setThumb(metadata.getThumb());
        movieBasicDTO.setArt(metadata.getArt());
        movieBasicDTO.setDuration(metadata.getDuration());
        movieBasicDTO.setLastViewedAt(metadata.getLastViewedAt());
        movieBasicDTO.setOriginallyAvailableAt(metadata.getOriginallyAvailableAt());
        Path path = KaleidoUtils.getMovieBasicPath(FilenameUtils.getFullPath(metadata.getMedia().getPart().getFile()));
        movieBasicDTO.setPath(path.toString());
        movieBasicDTO.setAddedAt(metadata.getAddedAt());
        movieBasicDTO.setUpdatedAt(metadata.getUpdatedAt());
        if (metadata.getCountryList() != null) {
            movieBasicDTO.setCountryList(metadata.getCountryList().stream().map(Tag::getTag).collect(Collectors.toList()));
        }
        if (metadata.getGenreList() != null) {
            movieBasicDTO.setGenreList(metadata.getGenreList().stream().map(Tag::getTag).collect(Collectors.toList()));
        }
        movieBasicDTO.setDirectorList(transformTag(metadata.getDirectorList(), ActorRole.Director));
        movieBasicDTO.setWriterList(transformTag(metadata.getWriterList(), ActorRole.Writer));
        movieBasicDTO.setActorList(transformTag(metadata.getRoleList(), ActorRole.Actor));
        //读取NFO文件
        readNFO(movieBasicDTO);
        saveMovie(movieBasicDTO);
    }

    @Transactional
    public void matchMovie(String movieId, Movie movie) {
        try {
            if (movie == null) {
                return;
            }
            MovieBasicDTO movieBasicDTO = movieBasicService.findById(movieId);
            movieBasicDTO.setTitle(StringUtils.defaultIfEmpty(movie.getTitle(), movieBasicDTO.getTitle()));
            movieBasicDTO.setYear(StringUtils.defaultIfEmpty(movie.getYear(), movieBasicDTO.getYear()));
            movieBasicDTO.setOriginalTitle(movie.getOriginalTitle());
            movieBasicDTO.setSummary(movie.getPlot());
            movieBasicDTO.setRating(movie.getAverage());
            movieBasicDTO.setDoubanId(movie.getDoubanId());
            movieBasicDTO.setImdbId(movie.getImdbId());
            movieBasicDTO.setWebsite(movie.getWebsite());
            movieBasicDTO.setAkaList(movie.getAkas());
            movieBasicDTO.setTagList(movie.getTags());
            movieBasicDTO.setGenreList(movie.getGenres());
            movieBasicDTO.setCountryList(movie.getCountries());
            movieBasicDTO.setLanguageList(movie.getLanguages());
            movieBasicDTO.setDirectorList(transformActor(movie.getDirectors(), ActorRole.Director));
            movieBasicDTO.setWriterList(transformActor(movie.getWriters(), ActorRole.Writer));
            movieBasicDTO.setActorList(transformActor(movie.getActors(), ActorRole.Actor));
            saveMovie(movieBasicDTO);
        } catch (Exception e) {
            log.error("匹配电影信息发生错误：{}", ExceptionUtil.getMessage(e));
        }
    }

    /**
     * 将path与信息关系记录在nfo文件中
     *
     * @param path
     * @param doubanId
     * @param tmdbId
     * @param tvdbId
     */
    @Transactional
    public void matchPath(Path path, String doubanId, String tmdbId, String tvdbId) {
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
            Movie movie = findTmmMovieByNFO(path);
            if (movie != null) {
                MovieBasicDTO movieBasicDTO = TmmUtil.toMovieBasicDTO(movie);
                log.info("== 查询到电影信息:{}", movieBasicDTO.getTitle());
                operationDir(movieBasicDTO, path);
            } else {
                Path movieLibraryPath = KaleidoUtils.getMovieLibraryPath();
                NioFileUtils.moveDir(path, movieLibraryPath, StandardCopyOption.REPLACE_EXISTING);
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
            movieBasicDTO = movieBasicService.findByImdb(movieThreadDTO.getImdb());
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
            plexApiService.refreshMetadata(movieBasicDTO.getId());
        } catch (Exception e) {
            log.error("导出NFO发生错误:{}", ExceptionUtil.getMessage(e));
            throw new RuntimeException(e);
        }
    }

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

    private List<ActorDTO> transformActor(List<Actor> actorList, ActorRole actorRole) {
        if (actorList == null) {
            return null;
        }
        return actorList.stream().map(s -> {
            ActorDTO actorDTO = null;
            if (StringUtils.isNotEmpty(s.getThumb()) && !StringUtils.endsWith(s.getThumb(), KaleidoConstants.SUFFIX_PNG)) {
                actorDTO = actorService.findByThumb(s.getThumb());
            }
            if (actorDTO == null && StringUtils.isNotEmpty(s.getDoubanId())) {
                actorDTO = actorService.findByDoubanId(s.getDoubanId());
            }
            if (actorDTO == null && StringUtils.isNotEmpty(s.getCnName())) {
                actorDTO = actorService.findByName(s.getCnName());
            }
            if (actorDTO == null) {
                actorDTO = new ActorDTO();
                actorDTO.setName(StringUtils.defaultString(s.getCnName(), s.getEnName()));
                actorDTO.setOriginalName(s.getEnName());
                actorDTO.setThumb(s.getThumb());
                actorDTO.setDoubanId(s.getDoubanId());
                actorDTO = actorService.insert(actorDTO);
            } else {
                actorDTO.setThumb(s.getThumb());
                actorDTO.setDoubanId(s.getDoubanId());
                actorService.update(actorDTO);
            }
            actorDTO.setRole(actorRole.name());
            actorDTO.setPlayRole(s.getRole());
            return actorDTO;
        }).collect(Collectors.toList());
    }

    private List<ActorDTO> transformTag(List<Tag> actorList, ActorRole actorRole) {
        if (actorList == null) {
            return null;
        }
        return actorList.stream().map(s -> {
            ActorDTO actorDTO = actorService.findByName(s.getTag());
            if (actorDTO == null) {
                actorDTO = new ActorDTO();
                actorDTO.setName(s.getTag());
                actorDTO.setOriginalName(s.getTag());
                actorDTO.setThumb(s.getThumb());
                actorDTO = actorService.insert(actorDTO);
            }
            actorDTO.setRole(actorRole.name());
            actorDTO.setPlayRole(s.getRole());
            return actorDTO;
        }).collect(Collectors.toList());
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
            log.info("NFO文件无法读取或不存在");
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

        NioFileUtils.moveDir(path, targetPath, StandardCopyOption.REPLACE_EXISTING);
        log.info("== 移动文件夹:{}", path.getFileName());
    }

    private void moveExistingFilesToRecycleBin(Path targetPath) {
        try {
            Path recyclePath = KaleidoUtils.getMovieRecyclePath();
            Files.list(targetPath).forEach(s -> {
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
            throw new RuntimeException(e);
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
        int i = 1;
        while (true) {
            folderPath = createFolderPath(folderName);
            if (folderPath == null) {
                folderName = String.format("%s (%s)", folderName, i);
                i++;
            } else {
                break;
            }
        }
        return folderPath;
    }

    private Path createFolderPath(String folderName) throws IOException {
        Path folderPath = KaleidoUtils.getMoviePath(folderName);
        if (Files.notExists(folderPath)) {
            Files.createDirectories(folderPath);
            return folderPath;
        } else {
            return null;
        }
    }

    private void downloadPoster(MovieBasicDTO movieBasicDTO, Path folderPath) {
        HttpUtil.downloadFile(movieBasicDTO.getPoster(), folderPath.resolve(KaleidoConstants.MOVIE_POSTER).toFile());
        log.info("== 下载海报");
    }

}
