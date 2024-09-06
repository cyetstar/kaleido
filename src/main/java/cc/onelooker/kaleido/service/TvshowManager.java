package cc.onelooker.kaleido.service;

import cc.onelooker.kaleido.dto.*;
import cc.onelooker.kaleido.enums.*;
import cc.onelooker.kaleido.nfo.EpisodeNFO;
import cc.onelooker.kaleido.nfo.NFOUtil;
import cc.onelooker.kaleido.nfo.SeasonNFO;
import cc.onelooker.kaleido.nfo.TvshowNFO;
import cc.onelooker.kaleido.third.plex.Metadata;
import cc.onelooker.kaleido.third.plex.PlexApiService;
import cc.onelooker.kaleido.third.plex.Tag;
import cc.onelooker.kaleido.third.tmm.*;
import cc.onelooker.kaleido.utils.ConfigUtils;
import cc.onelooker.kaleido.utils.KaleidoConstants;
import cc.onelooker.kaleido.utils.KaleidoUtils;
import cc.onelooker.kaleido.utils.NioFileUtils;
import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.http.HttpUtil;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Author cyetstar
 * @Date 2023-10-01 16:09:00
 * @Description TODO
 */
@Slf4j
@Component
public class TvshowManager {

    @Autowired
    private TvshowShowService tvshowShowService;

    @Autowired
    private TvshowSeasonService tvshowSeasonService;

    @Autowired
    private TvshowEpisodeService tvshowEpisodeService;

    @Autowired
    private AttributeService attributeService;

    @Autowired
    private AlternateTitleService alternateTitleService;

    @Autowired
    private ActorService actorService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private PlexApiService plexApiService;

    @Autowired
    private TmmApiService tmmApiService;

    private Pattern pattern = Pattern.compile("(S_?(\\d+))?EP?_?(\\d+)");

    @Transactional
    public void saveEpisode(TvshowEpisodeDTO tvshowEpisodeDTO) {
        TvshowEpisodeDTO existTvshowEpisodeDTO = tvshowEpisodeService.findById(tvshowEpisodeDTO.getId());
        if (existTvshowEpisodeDTO == null) {
            tvshowEpisodeService.insert(tvshowEpisodeDTO);
        } else {
            tvshowEpisodeService.update(tvshowEpisodeDTO);
        }
        taskService.newTask(tvshowEpisodeDTO.getId(), SubjectType.TvshowEpisode, TaskType.writeEpisodeNFO);
    }

    @Transactional
    public void saveSeason(TvshowSeasonDTO tvshowSeasonDTO) {
        actorService.updateSeasonActors(tvshowSeasonDTO.getDirectorList(), tvshowSeasonDTO.getId(), ActorRole.Director);
        actorService.updateSeasonActors(tvshowSeasonDTO.getWriterList(), tvshowSeasonDTO.getId(), ActorRole.Writer);
        actorService.updateSeasonActors(tvshowSeasonDTO.getActorList(), tvshowSeasonDTO.getId(), ActorRole.Actor);
        TvshowSeasonDTO existTvshowSeasonDTO = tvshowSeasonService.findById(tvshowSeasonDTO.getId());
        if (existTvshowSeasonDTO == null) {
            tvshowSeasonService.insert(tvshowSeasonDTO);
        } else {
            tvshowSeasonService.update(tvshowSeasonDTO);
        }
        taskService.newTask(tvshowSeasonDTO.getId(), SubjectType.TvshowSeason, TaskType.writeSeasonNFO);
        List<TvshowEpisodeDTO> tvshowEpisodeDTOList = tvshowEpisodeService.listBySeasonId(tvshowSeasonDTO.getId());
        tvshowEpisodeDTOList.forEach(e -> {
            taskService.newTask(e.getId(), SubjectType.TvshowEpisode, TaskType.writeEpisodeNFO);
        });

    }

    @Transactional
    public void saveShow(TvshowShowDTO tvshowShowDTO) {
        try {
            attributeService.updateAttributes(tvshowShowDTO.getTagList(), tvshowShowDTO.getId(), AttributeType.Tag);
            attributeService.updateAttributes(tvshowShowDTO.getGenreList(), tvshowShowDTO.getId(), AttributeType.Genre);
            attributeService.updateAttributes(tvshowShowDTO.getCountryList(), tvshowShowDTO.getId(), AttributeType.Country);
            attributeService.updateAttributes(tvshowShowDTO.getLanguageList(), tvshowShowDTO.getId(), AttributeType.Language);
            alternateTitleService.updateTitles(tvshowShowDTO.getAkaList(), tvshowShowDTO.getId(), SubjectType.TvshowShow);
            TvshowShowDTO existTvshowShowDTO = tvshowShowService.findById(tvshowShowDTO.getId());
            renameDirIfChanged(tvshowShowDTO);
            if (existTvshowShowDTO == null) {
                tvshowShowService.insert(tvshowShowDTO);
            } else {
                tvshowShowService.update(tvshowShowDTO);
            }
            taskService.newTask(tvshowShowDTO.getId(), SubjectType.TvshowShow, TaskType.writeShowNFO);
            List<TvshowSeasonDTO> tvshowSeasonDTOList = tvshowSeasonService.listByShowId(tvshowShowDTO.getId());
            tvshowSeasonDTOList.forEach(s -> {
                taskService.newTask(s.getId(), SubjectType.TvshowSeason, TaskType.writeSeasonNFO);
                List<TvshowEpisodeDTO> tvshowEpisodeDTOList = tvshowEpisodeService.listBySeasonId(s.getId());
                tvshowEpisodeDTOList.forEach(e -> {
                    taskService.newTask(e.getId(), SubjectType.TvshowEpisode, TaskType.writeEpisodeNFO);
                });
            });
        } catch (Exception e) {
            ExceptionUtil.wrapAndThrow(e);
        }
    }

    private void renameDirIfChanged(TvshowShowDTO tvshowShowDTO) throws IOException {
        String newPath = KaleidoUtils.genShowFolder(tvshowShowDTO);
        if (!StringUtils.equals(newPath, tvshowShowDTO.getPath())) {
            Path tvshowPath = KaleidoUtils.getTvshowPath(newPath);
            if (Files.notExists(tvshowPath)) {
                Files.createDirectories(tvshowPath);
            }
            NioFileUtils.renameDir(KaleidoUtils.getTvshowPath(tvshowShowDTO.getPath()), tvshowPath);
            tvshowShowDTO.setPath(newPath);
        }
    }

    @Transactional
    public void syncEpisode(Metadata metadata) {
        TvshowEpisodeDTO tvshowEpisodeDTO = tvshowEpisodeService.findById(metadata.getRatingKey());
        if (tvshowEpisodeDTO == null) {
            tvshowEpisodeDTO = new TvshowEpisodeDTO();
        }
        tvshowEpisodeDTO.setId(metadata.getRatingKey());
        tvshowEpisodeDTO.setShowId(metadata.getGrandparentRatingKey());
        tvshowEpisodeDTO.setSeasonId(metadata.getParentRatingKey());
        tvshowEpisodeDTO.setTitle(metadata.getTitle());
        tvshowEpisodeDTO.setContentRating(metadata.getContentRating());
        tvshowEpisodeDTO.setSummary(metadata.getSummary());
        tvshowEpisodeDTO.setEpisodeIndex(metadata.getIndex());
        tvshowEpisodeDTO.setRating(metadata.getRating());
        tvshowEpisodeDTO.setYear(metadata.getYear());
        tvshowEpisodeDTO.setThumb(metadata.getThumb());
        tvshowEpisodeDTO.setArt(metadata.getArt());
        tvshowEpisodeDTO.setDuration(metadata.getDuration());
        tvshowEpisodeDTO.setOriginallyAvailableAt(metadata.getOriginallyAvailableAt());
        tvshowEpisodeDTO.setAddedAt(metadata.getAddedAt());
        tvshowEpisodeDTO.setUpdatedAt(metadata.getUpdatedAt());
        //无需读取nfo
        saveEpisode(tvshowEpisodeDTO);
    }

    @Transactional
    public void syncShow(Metadata metadata) {
        TvshowShowDTO tvshowShowDTO = tvshowShowService.findById(metadata.getRatingKey());
        if (tvshowShowDTO == null) {
            tvshowShowDTO = new TvshowShowDTO();
        }
        tvshowShowDTO.setId(metadata.getRatingKey());
        tvshowShowDTO.setTitle(metadata.getTitle());
        tvshowShowDTO.setStudio(metadata.getStudio());
        tvshowShowDTO.setContentRating(metadata.getContentRating());
        tvshowShowDTO.setSummary(metadata.getSummary());
        tvshowShowDTO.setRating(metadata.getRating());
        tvshowShowDTO.setYear(metadata.getYear());
        tvshowShowDTO.setThumb(metadata.getThumb());
        tvshowShowDTO.setArt(metadata.getArt());
        tvshowShowDTO.setOriginallyAvailableAt(metadata.getOriginallyAvailableAt());
        tvshowShowDTO.setTotalSeasons(metadata.getChildCount());
        Path path = KaleidoUtils.getTvshowBasicPath(metadata.getLocation().getPath());
        tvshowShowDTO.setPath(path.toString());
        tvshowShowDTO.setAddedAt(metadata.getAddedAt());
        tvshowShowDTO.setUpdatedAt(metadata.getUpdatedAt());
        if (metadata.getGenreList() != null) {
            tvshowShowDTO.setGenreList(metadata.getGenreList().stream().map(Tag::getTag).collect(Collectors.toList()));
        }
        readNFO(tvshowShowDTO);
        saveShow(tvshowShowDTO);
    }

    @Transactional
    public void syncSeason(Metadata metadata) {
        TvshowSeasonDTO tvshowSeasonDTO = tvshowSeasonService.findById(metadata.getRatingKey());
        if (tvshowSeasonDTO == null) {
            tvshowSeasonDTO = new TvshowSeasonDTO();
        }
        tvshowSeasonDTO.setId(metadata.getRatingKey());
        tvshowSeasonDTO.setShowId(metadata.getParentRatingKey());
        tvshowSeasonDTO.setTitle(metadata.getTitle());
        tvshowSeasonDTO.setSummary(metadata.getSummary());
        tvshowSeasonDTO.setSeasonIndex(metadata.getIndex());
        tvshowSeasonDTO.setThumb(metadata.getThumb());
        tvshowSeasonDTO.setArt(metadata.getArt());
        tvshowSeasonDTO.setAddedAt(metadata.getAddedAt());
        tvshowSeasonDTO.setUpdatedAt(metadata.getUpdatedAt());
        readNFO(tvshowSeasonDTO);
        saveSeason(tvshowSeasonDTO);
    }

    private void readNFO(TvshowShowDTO tvshowShowDTO) {
        Path filePath = KaleidoUtils.getMoviePath(tvshowShowDTO.getPath());
        if (Files.notExists(filePath.resolve(KaleidoConstants.TVSHOW_SHOW_NFO))) {
            return;
        }
        TvshowNFO tvshowNFO = NFOUtil.read(TvshowNFO.class, filePath, KaleidoConstants.TVSHOW_SHOW_NFO);
        String doubanId = NFOUtil.getUniqueid(tvshowNFO.getUniqueids(), SourceType.douban.name());
        String imdb = NFOUtil.getUniqueid(tvshowNFO.getUniqueids(), SourceType.imdb.name());
        String tmdb = NFOUtil.getUniqueid(tvshowNFO.getUniqueids(), SourceType.tmdb.name());
        tvshowShowDTO.setDoubanId(StringUtils.defaultIfEmpty(doubanId, tvshowNFO.getDoubanId()));
        tvshowShowDTO.setImdbId(StringUtils.defaultIfEmpty(imdb, tvshowNFO.getImdbId()));
        tvshowShowDTO.setTmdbId(StringUtils.defaultIfEmpty(tmdb, tvshowNFO.getTmdbId()));
        tvshowShowDTO.setOriginalTitle(tvshowNFO.getOriginalTitle());
        tvshowShowDTO.setAkaList(tvshowNFO.getAkas());
        tvshowShowDTO.setTagList(tvshowNFO.getTags());
        tvshowShowDTO.setGenreList(tvshowNFO.getGenres());
        tvshowShowDTO.setCountryList(tvshowNFO.getCountries());
        tvshowShowDTO.setLanguageList(tvshowNFO.getLanguages());
    }

    private void readNFO(TvshowSeasonDTO tvshowSeasonDTO) {
        if (tvshowSeasonDTO.getSeasonIndex() == null || tvshowSeasonDTO.getSeasonIndex() == 0) {
            return;
        }
        String seasonFolder = KaleidoUtils.genSeasonFolder(tvshowSeasonDTO.getSeasonIndex());
        TvshowShowDTO tvshowShowDTO = tvshowShowService.findById(tvshowSeasonDTO.getShowId());
        Path folderPath = KaleidoUtils.getTvshowPath(tvshowShowDTO.getPath());
        Path seasonPath = folderPath.resolve(seasonFolder);
        if (Files.notExists(seasonPath.resolve(KaleidoConstants.TVSHOW_SEASON_NFO))) {
            return;
        }
        SeasonNFO seasonNFO = NFOUtil.read(SeasonNFO.class, seasonPath, KaleidoConstants.TVSHOW_SEASON_NFO);
        String doubanId = NFOUtil.getUniqueid(seasonNFO.getUniqueids(), SourceType.douban.name());
        String imdbId = NFOUtil.getUniqueid(seasonNFO.getUniqueids(), SourceType.imdb.name());
        String tmdbId = NFOUtil.getUniqueid(seasonNFO.getUniqueids(), SourceType.tmdb.name());
        tvshowSeasonDTO.setDoubanId(StringUtils.defaultIfEmpty(doubanId, seasonNFO.getDoubanId()));
        tvshowSeasonDTO.setImdbId(StringUtils.defaultIfEmpty(imdbId, seasonNFO.getImdbId()));
        tvshowSeasonDTO.setTmdbId(StringUtils.defaultIfEmpty(tmdbId, seasonNFO.getTmdbId()));
        tvshowSeasonDTO.setOriginalTitle(seasonNFO.getOriginalTitle());
        tvshowSeasonDTO.setSummary(seasonNFO.getPlot());
        tvshowSeasonDTO.setYear(seasonNFO.getYear());
        tvshowSeasonDTO.setOriginallyAvailableAt(seasonNFO.getPremiered());
        if (CollectionUtils.isNotEmpty(seasonNFO.getRatings())) {
            tvshowSeasonDTO.setRating(Float.valueOf(seasonNFO.getRatings().get(0).getValue()));
        }
        if (seasonNFO.getDirectors() != null) {
            tvshowSeasonDTO.setDirectorList(seasonNFO.getDirectors().stream().map(ActorDTO::new).collect(Collectors.toList()));
        }
        if (seasonNFO.getCredits() != null) {
            tvshowSeasonDTO.setWriterList(seasonNFO.getCredits().stream().map(ActorDTO::new).collect(Collectors.toList()));
        }
        if (seasonNFO.getActors() != null) {
            tvshowSeasonDTO.setActorList(seasonNFO.getActors().stream().map(NFOUtil::toActorDTO).collect(Collectors.toList()));
        }
    }

    public void updateSource(Path path) {
        try {
            log.info("=========== 开始更新数据文件 ==========");
            if (!Files.isDirectory(path)) {
                return;
            }
            log.info("== 源文件信息:{}", path);
            Tvshow tvshow = findTvshowByNFO(path);
            if (tvshow != null) {
                log.info("== 查询到剧集信息:{}", tvshow.getTitle());
                TvshowShowDTO tvshowShowDTO = TmmUtil.toTvshowShow(tvshow);
                operationFolder(tvshow, path);
            } else {

            }

        } catch (Exception e) {
            log.error("{}, 更新电影源发生错误", path, e);
        } finally {
            log.info("=========== 完成更新数据文件 ==========");
        }
    }

    private Tvshow findTvshowByNFO(Path path) throws Exception {
        Stream<Path> stream = Files.list(path);
        Path nfoPath = stream.filter(s -> StringUtils.equals(s.getFileName().toString(), "season.nfo")).findFirst().orElse(null);
        if (nfoPath != null) {
            SeasonNFO seasonNFO = NFOUtil.read(SeasonNFO.class, nfoPath);
            seasonNFO.setDoubanId(ObjectUtils.defaultIfNull(seasonNFO.getDoubanId(), NFOUtil.getUniqueid(seasonNFO.getUniqueids(), SourceType.douban.name())));
            seasonNFO.setImdbId(ObjectUtils.defaultIfNull(seasonNFO.getImdbId(), NFOUtil.getUniqueid(seasonNFO.getUniqueids(), SourceType.imdb.name())));
            seasonNFO.setTmdbId(ObjectUtils.defaultIfNull(seasonNFO.getTmdbId(), NFOUtil.getUniqueid(seasonNFO.getUniqueids(), SourceType.tmdb.name())));
            return tmmApiService.findTvshow(seasonNFO.getDoubanId(), seasonNFO.getImdbId(), seasonNFO.getTmdbId());
        }
        return null;
    }

    private void operationFolder(Tvshow tvshow, Path path) throws Exception {
        Path libraryPath = KaleidoUtils.getTvshowLibraryPath();
        Path importPath = KaleidoUtils.getTvshowImportPath();
        Path targetPath = createFolderPath(tvshow, libraryPath);
        Map<Integer, Boolean> seasonContext = Maps.newHashMap();
        Files.list(path).forEach(s -> {
            try {
                File file = s.toFile();
                String fileName = file.getName();
                if (file.isDirectory() && StringUtils.equals(fileName, "Season 00")) {
                    NioFileUtils.moveDir(s, targetPath, StandardCopyOption.REPLACE_EXISTING);
                    log.info("== 移动特别季文件夹: {}", fileName);
                    return;
                }
                Map<String, Integer> numberResult = getSeasonEpisodeNumber(fileName);
                Integer seasonNumber = MapUtils.getInteger(numberResult, "seasonNumber", 1);
                Integer episodeNumber = MapUtils.getInteger(numberResult, "episodeNumber");
                if (episodeNumber == null) {
                    log.info("== 无法确定集号信息，保持不动: {}", fileName);
                    return;
                }
                //创建规范文件夹
                Path seasonPath = createSeasonPath(targetPath, seasonNumber);
                if (!KaleidoUtils.isVideoFile(fileName)) {
                    Files.move(s, seasonPath.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
                    log.info("== 移动其他文件: {}", fileName);
                    return;
                }
                Path videoPath = seasonPath.resolve(fileName);
                Episode episode = findEpisode(tvshow, seasonNumber, episodeNumber);
                if (episode != null) {
                    //下载episode图片
                    downloadEpisodePoster(episode, videoPath);
                    //生成episode的nfo文件
                    exportEpisodeNFO(episode, tvshow, videoPath);
                } else {
                    log.info("== 无法找到单集信息: {}", fileName);
                }
                if (seasonContext.get(seasonNumber) == null) {
                    //下载单季图片
                    downloadSeasonPoster(tvshow.getSeason(seasonNumber), seasonPath);
                    //生成单季nfo文件
                    exportSeasonNFO(tvshow.getSeason(seasonNumber), tvshow, seasonPath);
                    seasonContext.put(seasonNumber, true);
                }
                //如果当前文件为视频文件，则删除已经存在的剧集视频文件
                deleteExistEpisodeVideoFile(seasonPath, seasonNumber, episodeNumber);
                //移动视频文件
                Files.move(s, videoPath, StandardCopyOption.REPLACE_EXISTING);
                log.info("== 移动视频文件: {}", fileName);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        //下载剧集图片
        downloadTvshowPoster(tvshow, targetPath);
        //生成剧集nfo文件
        exportTvshowNFO(tvshow, targetPath);
        if (!path.equals(importPath) && Files.list(path).noneMatch(s -> KaleidoUtils.isVideoFile(s.getFileName().toString()))) {
            NioFileUtils.deleteIfExists(path);
            log.info("== 删除源文件夹:{}", path.getFileName());
        }
    }

    private Episode findEpisode(Tvshow tvshow, Integer seasonNumber, Integer episodeNumber) {
        Optional<Season> optionalSeason = tvshow.getSeasons().stream().filter(s -> Objects.equals(s.getSeasonNumber(), seasonNumber)).findFirst();
        return optionalSeason.map(season -> season.getEpisodes() != null ? season.getEpisodes().stream().filter(s -> Objects.equals(s.getEpisodeNumber(), episodeNumber)).findFirst().orElse(null) : null).orElse(null);
    }

    private void downloadTvshowPoster(Tvshow tvshow, Path tvshowPath) {
        if (tvshow == null || StringUtils.isEmpty(tvshow.getPoster())) {
            return;
        }
        HttpUtil.downloadFile(tvshow.getPoster(), tvshowPath.resolve("poster.jpg").toFile());
        log.info("== 下载剧集海报");
    }

    private void downloadSeasonPoster(Season season, Path seasonPath) {
        if (season == null || StringUtils.isEmpty(season.getPoster())) {
            return;
        }
        String fileName = StringUtils.joinWith("-", "season", season.getSeasonNumberStr(), "poster.jpg");
        HttpUtil.downloadFile(season.getPoster(), seasonPath.resolveSibling(fileName).toFile());
        log.info("== 下载单季海报");
    }

    private void downloadEpisodePoster(Episode episode, Path videoPath) {
        if (episode == null || StringUtils.isEmpty(episode.getThumb())) {
            return;
        }
        String fileName = videoPath.getFileName().toString();
        String thumbFileName = FilenameUtils.getBaseName(fileName) + "-thumb.jpg";
        HttpUtil.downloadFile(episode.getThumb(), videoPath.resolveSibling(thumbFileName).toFile());
        log.info("== 下载单集图片: {}", thumbFileName);
    }

    private void exportTvshowNFO(Tvshow tvshow, Path tvshowPath) {
        try {
            if (tvshow == null) {
                return;
            }
            TvshowNFO tvshowNFO = NFOUtil.toTvshowNFO(tvshow);
            NFOUtil.write(tvshowNFO, TvshowNFO.class, tvshowPath, "tvshow.nfo");
            log.info("== 生成剧集NFO文件");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void exportSeasonNFO(Season season, Tvshow tvshow, Path seasonPath) {
        try {
            if (season == null) {
                return;
            }
            SeasonNFO seasonNFO = NFOUtil.toSeasonNFO(season, tvshow);
            NFOUtil.write(seasonNFO, SeasonNFO.class, seasonPath, "season.nfo");
            log.info("== 生成单季NFO文件");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void exportEpisodeNFO(Episode episode, Tvshow tvshow, Path videoPath) {
        try {
            if (episode == null) {
                return;
            }
            String fileName = videoPath.getFileName().toString();
            EpisodeNFO episodeNFO = NFOUtil.toEpisodeNFO(episode, tvshow.getSeason(episode.getSeasonNumber()), tvshow);
            String nfoFileName = FilenameUtils.getBaseName(fileName) + ".nfo";
            NFOUtil.write(episodeNFO, EpisodeNFO.class, videoPath.getParent(), nfoFileName);
            log.info("== 生成单集NFO文件: {}", nfoFileName);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void deleteExistEpisodeVideoFile(Path targetPath, Integer seasonNumber, Integer episodeNumber) throws IOException {
        Path recyclePath = KaleidoUtils.getTvshowRecyclePath();
        Files.list(targetPath).forEach(s -> {
            try {
                String fileName = s.getFileName().toString();
                Map<String, Integer> numberResult = getSeasonEpisodeNumber(fileName);
                if (KaleidoUtils.isVideoFile(fileName) && seasonNumber != null && Objects.equals(seasonNumber, numberResult.getOrDefault("seasonNumber", -1)) && Objects.equals(episodeNumber, numberResult.getOrDefault("episodeNumber", -1))) {
                    Files.move(s, recyclePath.resolve(s.getFileName()), StandardCopyOption.REPLACE_EXISTING);
                    log.info("== 删除原剧集视频文件: {}", fileName);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

    }

    private Map<String, Integer> getSeasonEpisodeNumber(String fileName) {
        Map<String, Integer> result = Maps.newHashMap();
        Matcher matcher = pattern.matcher(fileName);
        if (matcher.find()) {
            result.put("seasonNumber", matcher.group(2) != null ? Integer.valueOf(matcher.group(2)) : 1);
            if (matcher.group(3) != null) {
                result.put("episodeNumber", Integer.valueOf(matcher.group(3)));
            }

        }
        return result;
    }

    private Path createFolderPath(Tvshow tvshow, Path path) throws IOException {
        String title = tvshow.getTitle();
        String year = tvshow.getYear();
        title = StringUtils.replace(title, "?", "");
        title = StringUtils.replace(title, "!", "");
        String folderName = title + " (" + year + ")";
        Path folderPath = path.resolve(folderName);
        if (Files.notExists(folderPath)) {
            Files.createDirectory(folderPath);
        }
        return folderPath;
    }

    private Path createSeasonPath(Path targetPath, Integer seasonNumber) throws IOException {
        String folderName = "Season " + StringUtils.leftPad(String.valueOf(seasonNumber), 2, "0");
        Path folderPath = targetPath.resolve(folderName);
        if (Files.notExists(folderPath)) {
            Files.createDirectory(folderPath);
        }
        return folderPath;
    }

    /**
     * 将path与信息关系记录在nfo文件中
     *
     * @param pathList
     * @param doubanId
     */
    @Transactional
    public void matchPath(List<Path> pathList, String doubanId) {
        if (CollectionUtils.isEmpty(pathList)) {
            return;
        }
        if (pathList.stream().filter(s -> s.toFile().isDirectory()).count() > 1) {
            throw new RuntimeException("不能同时匹配多个文件夹");
        }
        Path importPath = KaleidoUtils.getTvshowImportPath();
        try {
            SeasonNFO seasonNFO = new SeasonNFO();
            seasonNFO.setDoubanId(doubanId);
            Path path = pathList.get(0);
            if (path.toFile().isDirectory()) {
                NFOUtil.write(seasonNFO, SeasonNFO.class, path, "season.nfo");
                NioFileUtils.moveDir(path, importPath, StandardCopyOption.REPLACE_EXISTING);
            } else {
                String baseName = FilenameUtils.getBaseName(path.getFileName().toString());
                Path newPath = importPath.resolve(baseName);
                if (!Files.exists(newPath)) {
                    newPath.toFile().mkdir();
                }
                NFOUtil.write(seasonNFO, SeasonNFO.class, newPath, "season.nfo");
                for (Path p : pathList) {
                    Files.move(p, newPath.resolve(p.getFileName()), StandardCopyOption.REPLACE_EXISTING);
                }
            }
        } catch (Exception e) {
            log.error("文件夹匹配信息发生错误", e);
        }
    }

    @Transactional
    public void matchInfo(String id, Tvshow tvshow) {
        try {
            TvshowShowDTO tvshowShowDTO = tvshowShowService.findById(id);
            Path path = KaleidoUtils.getTvshowPath(tvshowShowDTO.getPath());
            Files.list(path).forEach(s -> {
                String directoryName = s.getFileName().toString();
                if (!StringUtils.startsWith(directoryName, "Season")) {
                    return;
                }
                Integer seasonNumber = Integer.parseInt(StringUtils.removeStart(directoryName, "Season").trim());
                Season season = tvshow.getSeason(seasonNumber);
                exportSeasonNFO(season, tvshow, s);
                try {
                    Files.list(s).forEach(file -> {
                        String fileName = file.getFileName().toString();
                        if (KaleidoUtils.isVideoFile(fileName)) {
                            Map<String, Integer> numberResult = getSeasonEpisodeNumber(fileName);
                            Episode episode = season.getEpisode(MapUtils.getInteger(numberResult, "episodeNumber"));
                            exportEpisodeNFO(episode, tvshow, file);
                        }
                    });
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
            exportTvshowNFO(tvshow, path);
            plexApiService.refreshMetadata(id);
            //如果模版信息发生变动，则重新移动文件，而后续由定时任务重新获取新的信息
            if (!StringUtils.equals(tvshow.getTitle(), tvshowShowDTO.getTitle()) || !StringUtils.equals(tvshow.getYear(), tvshowShowDTO.getYear())) {
                String libraryPath = ConfigUtils.getSysConfig(ConfigKey.tvshowLibraryPath);
                Path newFolderPath = createFolderPath(tvshow, Paths.get(libraryPath));
                NioFileUtils.renameDir(path, newFolderPath);
            }
        } catch (Exception e) {
            log.error("匹配剧集信息发生错误：{}", ExceptionUtil.getMessage(e));
        }
    }

    public TvshowShowDTO findTvshowShow(String showId) {
        TvshowShowDTO tvshowShowDTO = tvshowShowService.findById(showId);
        List<AttributeDTO> attributeDTOList = attributeService.listBySubjectId(tvshowShowDTO.getId());
        Set<ActorDTO> actorDTOSet = Sets.newHashSet();
        List<TvshowSeasonDTO> tvshowSeasonDTOList = tvshowSeasonService.listByShowId(tvshowShowDTO.getId());
        tvshowSeasonDTOList.forEach(s -> {
            actorDTOSet.addAll(actorService.listBySeasonId(s.getId()));
        });
        List<ActorDTO> actorDTOList = new ArrayList<>(actorDTOSet);
        List<AlternateTitleDTO> alternateTitleDTOList = alternateTitleService.listBySubjectId(tvshowShowDTO.getId());
        tvshowShowDTO.setDirectorList(actorDTOList.stream().filter(s -> StringUtils.equals(s.getRole(), ActorRole.Director.name())).collect(Collectors.toList()));
        tvshowShowDTO.setWriterList(actorDTOList.stream().filter(s -> StringUtils.equals(s.getRole(), ActorRole.Writer.name())).collect(Collectors.toList()));
        tvshowShowDTO.setActorList(actorDTOList.stream().filter(s -> StringUtils.equals(s.getRole(), ActorRole.Actor.name())).collect(Collectors.toList()));
        tvshowShowDTO.setAkaList(alternateTitleDTOList.stream().map(AlternateTitleDTO::getTitle).collect(Collectors.toList()));
        tvshowShowDTO.setLanguageList(attributeDTOList.stream().filter(s -> StringUtils.equals(s.getType(), AttributeType.Language.name())).map(AttributeDTO::getValue).collect(Collectors.toList()));
        tvshowShowDTO.setCountryList(attributeDTOList.stream().filter(s -> StringUtils.equals(s.getType(), AttributeType.Country.name())).map(AttributeDTO::getValue).collect(Collectors.toList()));
        tvshowShowDTO.setGenreList(attributeDTOList.stream().filter(s -> StringUtils.equals(s.getType(), AttributeType.Genre.name())).map(AttributeDTO::getValue).collect(Collectors.toList()));
        tvshowShowDTO.setTagList(attributeDTOList.stream().filter(s -> StringUtils.equals(s.getType(), AttributeType.Tag.name())).map(AttributeDTO::getValue).collect(Collectors.toList()));
        return tvshowShowDTO;
    }
}
