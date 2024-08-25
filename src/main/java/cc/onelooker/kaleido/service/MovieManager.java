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
import cc.onelooker.kaleido.third.tmm.Actor;
import cc.onelooker.kaleido.third.tmm.Doulist;
import cc.onelooker.kaleido.third.tmm.Movie;
import cc.onelooker.kaleido.third.tmm.TmmApiService;
import cc.onelooker.kaleido.utils.DateTimeUtil;
import cc.onelooker.kaleido.utils.KaleidoConstants;
import cc.onelooker.kaleido.utils.KaleidoUtils;
import cc.onelooker.kaleido.utils.NioFileUtils;
import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.extra.pinyin.PinyinUtil;
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
    private MovieActorService movieActorService;

    @Autowired
    private SubjectAttributeService subjectAttributeService;

    @Autowired
    private AlternateTitleService alternateTitleService;

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
            subjectAttributeService.updateAttribute(movieBasicDTO.getCountryList(), movieBasicDTO.getId(), AttributeType.MovieCountry);
            subjectAttributeService.updateAttribute(movieBasicDTO.getLanguageList(), movieBasicDTO.getId(), AttributeType.MovieLanguage);
            subjectAttributeService.updateAttribute(movieBasicDTO.getGenreList(), movieBasicDTO.getId(), AttributeType.MovieGenre);
            subjectAttributeService.updateAttribute(movieBasicDTO.getTagList(), movieBasicDTO.getId(), AttributeType.MovieTag);
            updateActor(movieBasicDTO.getDirectorList(), movieBasicDTO.getId(), ActorRole.Director);
            updateActor(movieBasicDTO.getWriterList(), movieBasicDTO.getId(), ActorRole.Writer);
            updateActor(movieBasicDTO.getActorList(), movieBasicDTO.getId(), ActorRole.Actor);
            updateAlternateTitle(movieBasicDTO.getAkaList(), movieBasicDTO.getId());
            setTitleSort(movieBasicDTO);
            MovieBasicDTO existMovieBasicDTO = movieBasicService.findById(movieBasicDTO.getId());
            if (existMovieBasicDTO == null) {
                movieBasicService.insert(movieBasicDTO);
            } else {
                movieBasicService.update(movieBasicDTO);
            }
            renameDirIfChanged(movieBasicDTO);
            taskService.newTask(movieBasicDTO.getId(), SubjectType.MovieBasic, TaskType.writeMovieNFO);
        } catch (IOException e) {
            throw new RuntimeException(e);
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

    public void updateSource(Path path) {
        try {
            log.info("=========== 开始更新数据文件 ==========");
            log.info("== 源文件信息:{}", path);
            Movie movie;
            boolean isDirectory = Files.isDirectory(path);
            String filename = path.getFileName().toString();
            if (isDirectory) {
                movie = findTmmMovieByNFO(path);
            } else {
                movie = findTmmMovieByFilename(filename);
            }
            if (movie != null) {
                log.info("== 查询到电影信息:{}", movie.getTitle());
                operationFolder(movie, path, isDirectory);
            } else {
                Path movieLibraryPath = KaleidoUtils.getMovieLibraryPath();
                if (isDirectory) {
                    NioFileUtils.moveDir(path, movieLibraryPath, StandardCopyOption.REPLACE_EXISTING);
                } else {
                    Path target = movieLibraryPath.resolve(FilenameUtils.getBaseName(filename));
                    Files.move(path, target, StandardCopyOption.REPLACE_EXISTING);
                }
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
    public void deleteMovie(String id) {
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
                if (streamList.stream().anyMatch(s -> s.getStreamType() == 2 && StringUtils.equals(s.getLanguageTag(), "zh"))) {
                    movieBasicDTO.setMandarin(Constants.YES);
                } else {
                    movieBasicDTO.setMandarin(Constants.NO);
                }
                if (streamList.stream().anyMatch(s -> s.getStreamType() == 3 && (StringUtils.equals(s.getLanguageTag(), "zh") || StringUtils.containsAnyIgnoreCase(s.getTitle(), "中", "简", "chs", "cht")))) {
                    movieBasicDTO.setNoSubtitle(Constants.NO);
                } else {
                    movieBasicDTO.setNoSubtitle(Constants.YES);
                }
                movieBasicService.update(movieBasicDTO);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
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

    private void setTitleSort(MovieBasicDTO movieBasicDTO) {
        if (StringUtils.isEmpty(movieBasicDTO.getTitleSort())) {
            movieBasicDTO.setTitleSort(PinyinUtil.getFirstLetter(movieBasicDTO.getTitle(), StringUtils.EMPTY));
        }
    }

    private List<MovieActorDTO> transformActor(List<Actor> actorList, ActorRole actorRole) {
        if (actorList == null) {
            return null;
        }
        return actorList.stream().map(s -> {
            MovieActorDTO movieActorDTO = null;
            if (StringUtils.isNotEmpty(s.getThumb()) && !StringUtils.endsWith(s.getThumb(), "png")) {
                movieActorDTO = movieActorService.findByThumb(s.getThumb());
            }
            if (movieActorDTO == null && StringUtils.isNotEmpty(s.getDoubanId())) {
                movieActorDTO = movieActorService.findByDoubanId(s.getDoubanId());
            }
            if (movieActorDTO == null && StringUtils.isNotEmpty(s.getCnName())) {
                movieActorDTO = movieActorService.findByName(s.getCnName());
            }
            if (movieActorDTO == null) {
                movieActorDTO = new MovieActorDTO();
                movieActorDTO.setName(StringUtils.defaultString(s.getCnName(), s.getEnName()));
                movieActorDTO.setOriginalName(s.getEnName());
                movieActorDTO.setThumb(s.getThumb());
                movieActorDTO.setDoubanId(s.getDoubanId());
                movieActorDTO = movieActorService.insert(movieActorDTO);
            }
            movieActorDTO.setRole(actorRole.name());
            movieActorDTO.setPlayRole(s.getRole());
            return movieActorDTO;
        }).collect(Collectors.toList());
    }

    private List<MovieActorDTO> transformTag(List<Tag> actorList, ActorRole actorRole) {
        if (actorList == null) {
            return null;
        }
        return actorList.stream().map(s -> {
            MovieActorDTO movieActorDTO = movieActorService.findByName(s.getTag());
            if (movieActorDTO == null) {
                movieActorDTO = new MovieActorDTO();
                movieActorDTO.setName(s.getTag());
                movieActorDTO.setOriginalName(s.getTag());
                movieActorDTO.setThumb(s.getThumb());
                movieActorDTO = movieActorService.insert(movieActorDTO);
            }
            movieActorDTO.setRole(actorRole.name());
            movieActorDTO.setPlayRole(s.getRole());
            return movieActorDTO;
        }).collect(Collectors.toList());
    }

    private void updateAlternateTitle(List<String> alternateTitleList, String subjectId) {
        if (alternateTitleList == null) {
            return;
        }
        alternateTitleService.deleteBySubjectId(subjectId);
        alternateTitleList.forEach(s -> {
            AlternateTitleDTO alternateTitleDTO = new AlternateTitleDTO();
            alternateTitleDTO.setTitle(s);
            alternateTitleDTO.setSubjectId(subjectId);
            alternateTitleDTO.setSubjectType(SubjectType.MovieBasic.name());
            alternateTitleService.insert(alternateTitleDTO);
        });
    }

    private void updateActor(List<MovieActorDTO> movieActorDTOList, String movieId, ActorRole actorRole) {
        if (movieActorDTOList == null) {
            return;
        }
        movieBasicActorService.deleteByMovieIdAndRole(movieId, actorRole);
        movieActorDTOList.stream().forEach(s -> {
            MovieBasicActorDTO movieBasicActorDTO = new MovieBasicActorDTO();
            movieBasicActorDTO.setMovieId(movieId);
            movieBasicActorDTO.setActorId(s.getId());
            movieBasicActorDTO.setRole(actorRole.name());
            movieBasicActorDTO.setPlayRole(s.getPlayRole());
            movieBasicActorService.insert(movieBasicActorDTO);
        });
    }

    private void renameDirIfChanged(MovieBasicDTO movieBasicDTO) throws IOException {
        String path = KaleidoUtils.genMoviePath(movieBasicDTO);
        if (!StringUtils.equals(path, movieBasicDTO.getPath())) {
            Path newPath = KaleidoUtils.getMoviePath(path);
            if (Files.notExists(newPath)) {
                Files.createDirectory(newPath);
            }
            NioFileUtils.renameDir(KaleidoUtils.getMoviePath(movieBasicDTO.getPath()), newPath);
        }
    }

    private void readNFO(MovieBasicDTO movieBasicDTO) {
        try {
            Path filePath = KaleidoUtils.getMoviePath(movieBasicDTO.getPath());
            if (Files.exists(filePath.resolve(KaleidoConstants.MOVIE_NFO))) {
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
            }
        } catch (Exception e) {
            log.error("读取NFO发生错误:{}", ExceptionUtil.getMessage(e));
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

    private Movie findTmmMovieByFilename(String filename) {
        if (!KaleidoUtils.isVideoFile(filename)) {
            return null;
        }
        MovieThreadDTO movieThreadDTO = movieThreadService.findByFilename(filename);
        if (movieThreadDTO != null) {
            return findTmmMovie(movieThreadDTO.getDoubanId(), movieThreadDTO.getImdb(), null);
        }
        return null;
    }

    private void operationFolder(Movie movie, Path path, boolean isDirectory) throws Exception {
        Path movieLibraryPath = KaleidoUtils.getMovieLibraryPath();
        Path importPath = KaleidoUtils.getMovieImportPath();

        //创建规范文件夹
        Path targetPath = createFolderPath(movie, movieLibraryPath);
        moveExistingFilesToRecycleBin(targetPath);

        if (isDirectory) {
            Files.list(path).forEach(s -> {
                try {
                    if (Files.isDirectory(s)) {
                        NioFileUtils.moveDir(s, targetPath, StandardCopyOption.REPLACE_EXISTING);
                        log.info("== 移动文件目录:{}", s.getFileName());
                    } else {
                        Files.move(s, targetPath.resolve(s.getFileName()), StandardCopyOption.REPLACE_EXISTING);
                        log.info("== 移动文件:{}", s.getFileName());
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            if (!path.equals(importPath)) {
                NioFileUtils.deleteIfExists(path);
                log.info("== 删除源文件夹:{}", path.getFileName());
            }
        } else {
            //将主文件移动到文件夹
            Files.move(path, targetPath.resolve(path.getFileName()), StandardCopyOption.REPLACE_EXISTING);
            log.info("== 移动文件:{}", path.getFileName());
            //如果存在额外文件夹也移动
            Path extraPath = path.getParent().resolve("Other");
            if (Files.exists(extraPath)) {
                NioFileUtils.moveDir(extraPath, targetPath, StandardCopyOption.REPLACE_EXISTING);
                log.info("== 移动文件目录:{}", extraPath.getFileName());
            }
        }
        //下载海报
        downloadPoster(movie, targetPath);
        log.info("== 下载海报");
        //输出nfo文件
        MovieNFO movieNFO = NFOUtil.toMovieNFO(movie);
        NFOUtil.write(movieNFO, MovieNFO.class, targetPath, KaleidoConstants.MOVIE_NFO);
        log.info("== 输出nfo文件");
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

    private Path createFolderPath(Movie movie, Path path) throws IOException {
        String decade = movie.getDecade();
        String title = movie.getTitle();
        String year = movie.getYear();
        MovieBasicDTO param = new MovieBasicDTO();
        param.setTitle(title);
        param.setYear(year);
        List<MovieBasicDTO> movieBasicDTOList = movieBasicService.list(param);
        if (movieBasicDTOList.stream().anyMatch(s -> (StringUtils.isNotEmpty(movie.getDoubanId()) && !StringUtils.equals(movie.getDoubanId(), s.getDoubanId()) || (StringUtils.isNotEmpty(movie.getImdbId()) && !StringUtils.equals(movie.getImdbId(), s.getImdbId()))))) {
            if (CollectionUtils.isNotEmpty(movie.getAkas())) {
                title = movie.getAkas().get(0);
            } else {
                title = movie.getTitle() + " (" + movieBasicDTOList.size() + ")";
            }
        }
        title = StringUtils.replace(title, "?", "");
        title = StringUtils.replace(title, "!", "");
        String folderName = decade + "/" + title + " (" + year + ")";
        Path folderPath = path.resolve(folderName);
        if (Files.notExists(folderPath)) {
            Files.createDirectory(folderPath);
        }
        return folderPath;
    }

    private void downloadPoster(Movie movie, Path folderPath) {
        HttpUtil.downloadFile(movie.getPoster(), folderPath.resolve(KaleidoConstants.MOVIE_POSTER).toFile());
    }
}
