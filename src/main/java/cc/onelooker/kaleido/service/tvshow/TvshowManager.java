package cc.onelooker.kaleido.service.tvshow;

import cc.onelooker.kaleido.dto.tvshow.*;
import cc.onelooker.kaleido.enums.ActorRole;
import cc.onelooker.kaleido.enums.ConfigKey;
import cc.onelooker.kaleido.enums.SourceType;
import cc.onelooker.kaleido.nfo.EpisodeNFO;
import cc.onelooker.kaleido.nfo.NFOUtil;
import cc.onelooker.kaleido.nfo.SeasonNFO;
import cc.onelooker.kaleido.nfo.TvshowNFO;
import cc.onelooker.kaleido.third.plex.Metadata;
import cc.onelooker.kaleido.third.plex.PlexApiService;
import cc.onelooker.kaleido.third.plex.Tag;
import cc.onelooker.kaleido.third.tmm.Episode;
import cc.onelooker.kaleido.third.tmm.Season;
import cc.onelooker.kaleido.third.tmm.TmmApiService;
import cc.onelooker.kaleido.third.tmm.Tvshow;
import cc.onelooker.kaleido.utils.ConfigUtils;
import cc.onelooker.kaleido.utils.KaleidoUtils;
import cc.onelooker.kaleido.utils.NioFileUtils;
import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.http.HttpUtil;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.io.FilenameUtils;
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
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
    private TvshowGenreService tvshowGenreService;

    @Autowired
    private TvshowActorService tvshowActorService;

    @Autowired
    private TvshowShowGenreService tvshowShowGenreService;

    @Autowired
    private TvshowShowActorService tvshowShowActorService;

    @Autowired
    private TvshowEpisodeActorService tvshowEpisodeActorService;

    @Autowired
    private PlexApiService plexApiService;

    @Autowired
    private TmmApiService tmmApiService;

    private Pattern pattern = Pattern.compile("([sS]_?(\\d+))?[eE][pP]?_?(\\d+)");

    @Transactional
    public void syncPlexEpisode(Long episodeId) {
        Metadata metadata = plexApiService.findEpisodeById(episodeId);
        syncPlexEpisode(metadata);
    }

    @Transactional
    public void syncPlex(Long tvshowId) {
        syncTvshowShow(tvshowId);
        List<Metadata> children = plexApiService.listMetadataChildren(tvshowId);
        for (Metadata child : children) {
            syncTvshowSeason(child.getRatingKey());
            List<Metadata> children2 = plexApiService.listMetadataChildren(child.getRatingKey());
            for (Metadata child2 : children2) {
                syncTvshowEpisode(child2.getRatingKey());
            }
        }
    }

    private void syncTvshowShow(Long tvshowId) {
        Metadata metadata = plexApiService.findMetadata(tvshowId);
        TvshowShowDTO tvshowShowDTO = tvshowShowService.findById(tvshowId);
        if (tvshowShowDTO == null) {
            tvshowShowDTO = new TvshowShowDTO();
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
            tvshowShowDTO.setAddedAt(metadata.getAddedAt());
            tvshowShowDTO.setUpdatedAt(metadata.getUpdatedAt());
            tvshowShowService.insert(tvshowShowDTO);
        } else {
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
            tvshowShowDTO.setAddedAt(metadata.getAddedAt());
            tvshowShowDTO.setUpdatedAt(metadata.getUpdatedAt());
            tvshowShowService.update(tvshowShowDTO);
        }
        syncPlexGenre(metadata.getGenreList(), tvshowShowDTO.getId());
        syncPlexTvshowActor(metadata.getRoleList(), tvshowShowDTO.getId(), ActorRole.Actor);
    }

    private void syncTvshowSeason(Long seasonId) {
        Metadata metadata = plexApiService.findMetadata(seasonId);
        TvshowSeasonDTO tvshowSeasonDTO = tvshowSeasonService.findById(seasonId);
        if (tvshowSeasonDTO == null) {
            tvshowSeasonDTO = new TvshowSeasonDTO();
            tvshowSeasonDTO.setId(metadata.getRatingKey());
            tvshowSeasonDTO.setShowId(metadata.getParentRatingKey());
            tvshowSeasonDTO.setTitle(metadata.getTitle());
            tvshowSeasonDTO.setSummary(metadata.getSummary());
            tvshowSeasonDTO.setSeasonIndex(metadata.getIndex());
            tvshowSeasonDTO.setThumb(metadata.getThumb());
            tvshowSeasonDTO.setArt(metadata.getArt());
            tvshowSeasonDTO.setAddedAt(metadata.getAddedAt());
            tvshowSeasonDTO.setUpdatedAt(metadata.getUpdatedAt());
            tvshowSeasonService.insert(tvshowSeasonDTO);
        } else {
            tvshowSeasonDTO.setTitle(metadata.getTitle());
            tvshowSeasonDTO.setSummary(metadata.getSummary());
            tvshowSeasonDTO.setSeasonIndex(metadata.getIndex());
            tvshowSeasonDTO.setThumb(metadata.getThumb());
            tvshowSeasonDTO.setArt(metadata.getArt());
            tvshowSeasonDTO.setAddedAt(metadata.getAddedAt());
            tvshowSeasonDTO.setUpdatedAt(metadata.getUpdatedAt());
            tvshowSeasonService.update(tvshowSeasonDTO);
        }
    }

    private void syncTvshowEpisode(Long episodeId) {
        Metadata metadata = plexApiService.findMetadata(episodeId);
        TvshowEpisodeDTO tvshowEpisodeDTO = tvshowEpisodeService.findById(episodeId);
        if (tvshowEpisodeDTO == null) {
            tvshowEpisodeDTO = new TvshowEpisodeDTO();
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
            tvshowEpisodeDTO = tvshowEpisodeService.insert(tvshowEpisodeDTO);
        } else {
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
            tvshowEpisodeService.update(tvshowEpisodeDTO);
        }
        syncPlexEpisodeActor(metadata.getDirectorList(), tvshowEpisodeDTO.getId(), ActorRole.Director);
        syncPlexEpisodeActor(metadata.getWriterList(), tvshowEpisodeDTO.getId(), ActorRole.Writer);
    }

    @Transactional
    public void readNFO(Long tvshowId) throws Exception {
        Metadata metadata = plexApiService.findMetadata(tvshowId);
        Path folderPath = Paths.get(KaleidoUtils.getTvshowFolder(metadata.getLocation().getPath()));
        readTvshowNFO(folderPath, tvshowId);
        Files.walk(folderPath, 2).forEach(s -> {
            try {
                String filename = s.getFileName().toString();
                if (StringUtils.equals(filename, "season.nfo")) {
                    readSeasonNFO(s, tvshowId);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

    }

    @Transactional
    public void readTvshowNFO(Path path, Long tvshowId) throws Exception {
        TvshowNFO tvshowNFO = NFOUtil.read(TvshowNFO.class, path, "tvshow.nfo");
        String doubanId = NFOUtil.getUniqueid(tvshowNFO.getUniqueids(), SourceType.douban.name());
        String imdb = NFOUtil.getUniqueid(tvshowNFO.getUniqueids(), SourceType.imdb.name());
        String tmdb = NFOUtil.getUniqueid(tvshowNFO.getUniqueids(), SourceType.tmdb.name());
        TvshowShowDTO tvshowShowDTO = tvshowShowService.findById(tvshowId);
        tvshowShowDTO.setDoubanId(StringUtils.defaultIfEmpty(doubanId, tvshowNFO.getDoubanId()));
        tvshowShowDTO.setImdbId(StringUtils.defaultIfEmpty(imdb, tvshowNFO.getImdbId()));
        tvshowShowDTO.setTmdbId(StringUtils.defaultIfEmpty(tmdb, tvshowNFO.getTmdbId()));
        tvshowShowDTO.setOriginalTitle(tvshowNFO.getOriginalTitle());
        tvshowShowService.update(tvshowShowDTO);
    }

    private void readSeasonNFO(Path path, Long tvshowId) throws Exception {
        SeasonNFO seasonNFO = NFOUtil.read(SeasonNFO.class, path.getParent(), path.getFileName().toString());
        String doubanId = NFOUtil.getUniqueid(seasonNFO.getUniqueids(), SourceType.douban.name());
        String imdb = NFOUtil.getUniqueid(seasonNFO.getUniqueids(), SourceType.imdb.name());
        String tmdb = NFOUtil.getUniqueid(seasonNFO.getUniqueids(), SourceType.tmdb.name());
        TvshowSeasonDTO param = new TvshowSeasonDTO();
        param.setShowId(tvshowId);
        param.setSeasonIndex(seasonNFO.getSeasonNumber());
        TvshowSeasonDTO tvshowSeasonDTO = tvshowSeasonService.find(param);
        tvshowSeasonDTO.setDoubanId(StringUtils.defaultIfEmpty(doubanId, seasonNFO.getDoubanId()));
        tvshowSeasonDTO.setImdbId(StringUtils.defaultIfEmpty(imdb, seasonNFO.getImdbId()));
        tvshowSeasonDTO.setTmdbId(StringUtils.defaultIfEmpty(tmdb, seasonNFO.getTmdbId()));
    }

    public void updateSource(Path path) {
        try {
            log.info("=========== 开始更新数据文件 ==========");
            if (!Files.isDirectory(path)) {
                return;
            }
            log.info("== 源文件信息:{}", path);
            Tvshow tvshow = findTvshowByNFO(path);
            if (tvshow == null) {
                log.info("== 找不到剧集信息");
                return;
            }
            log.info("== 查询到剧集信息:{}", tvshow.getTitle());
            operationFolder(tvshow, path);
        } catch (Exception e) {
            log.error("{}, 更新电影源发生错误", path, e);
        } finally {
            log.info("=========== 完成更新数据文件 ==========");
        }
    }

    private Tvshow findTvshowByNFO(Path path) throws Exception {
        Stream<Path> stream = Files.list(path);
        Path nfoPath = stream.filter(s -> FilenameUtils.isExtension(s.getFileName().toString(), "nfo")).findFirst().orElse(null);
        if (nfoPath != null) {
            SeasonNFO seasonNFO = NFOUtil.read(SeasonNFO.class, nfoPath);
            return tmmApiService.findTvshow(seasonNFO.getDoubanId(), seasonNFO.getImdbId(), seasonNFO.getTmdbId());
        }
        return null;
    }

    private void operationFolder(Tvshow tvshow, Path path) throws Exception {
        Path libraryPath = Paths.get(ConfigUtils.getSysConfig(ConfigKey.tvshowLibraryPath));
        Path downloadPath = Paths.get(ConfigUtils.getSysConfig(ConfigKey.tvshowDownloadPath));
        Path targetPath = createFolderPath(tvshow, libraryPath);
        Map<Integer, Boolean> seasonContext = Maps.newHashMap();
        Files.list(path).forEach(s -> {
            try {
                File file = s.toFile();
                String fileName = file.getName();
                if (!KaleidoUtils.isVideoFile(fileName)) {
                    log.info("== 非视频文件保持不动: {}", fileName);
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
                if (file.isDirectory() && StringUtils.equals(fileName, "Other")) {
                    //直接移动文件夹
                    NioFileUtils.moveDir(s, seasonPath, StandardCopyOption.REPLACE_EXISTING);
                    log.info("== 移动文件目录:{}", fileName);
                    return;
                }
                //删除已经存在的剧集视频文件
                deleteExistEpisodeVideoFile(seasonPath, seasonNumber, episodeNumber);
                //移动视频文件
                Path videoPath = seasonPath.resolve(fileName);
                Files.move(s, videoPath, StandardCopyOption.REPLACE_EXISTING);
                log.info("== 移动视频文件: {}", fileName);
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
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        //下载剧集图片
        downloadTvshowPoster(tvshow, targetPath);
        //生成剧集nfo文件
        exportTvshowNFO(tvshow, targetPath);
        if (!path.equals(downloadPath) && Files.list(path).noneMatch(s -> KaleidoUtils.isVideoFile(s.getFileName().toString()))) {
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
        Path trashPath = Paths.get(ConfigUtils.getSysConfig(ConfigKey.tvshowTrashPath));
        Files.list(targetPath).forEach(s -> {
            try {
                String fileName = s.getFileName().toString();
                Map<String, Integer> numberResult = getSeasonEpisodeNumber(fileName);
                if (seasonNumber != null && Objects.equals(seasonNumber, numberResult.getOrDefault("seasonNumber", -1)) && Objects.equals(episodeNumber, numberResult.getOrDefault("episodeNumber", -1))) {
                    Files.move(s, trashPath.resolve(s.getFileName()), StandardCopyOption.REPLACE_EXISTING);
                    log.info("== 移除原剧集视频文件: {}", fileName);
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

    private void syncPlexEpisode(Metadata metadata) {
        TvshowEpisodeDTO tvshowEpisodeDTO = tvshowEpisodeService.findById(metadata.getRatingKey());
        if (tvshowEpisodeDTO == null) {
            tvshowEpisodeDTO = new TvshowEpisodeDTO();
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
            tvshowEpisodeDTO = tvshowEpisodeService.insert(tvshowEpisodeDTO);
        } else {
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
            tvshowEpisodeService.update(tvshowEpisodeDTO);
        }
        syncPlexEpisodeActor(metadata.getDirectorList(), tvshowEpisodeDTO.getId(), ActorRole.Director);
        syncPlexEpisodeActor(metadata.getWriterList(), tvshowEpisodeDTO.getId(), ActorRole.Writer);

        syncPlexSeason(metadata.getParentRatingKey());
    }

    private void syncPlexShow(Long tvshowId) {
        Metadata metadata = plexApiService.findTvshowById(tvshowId);
        TvshowShowDTO tvshowShowDTO = tvshowShowService.findById(tvshowId);
        if (tvshowShowDTO == null) {
            tvshowShowDTO = new TvshowShowDTO();
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
            tvshowShowDTO.setAddedAt(metadata.getAddedAt());
            tvshowShowDTO.setUpdatedAt(metadata.getUpdatedAt());
            tvshowShowService.insert(tvshowShowDTO);
        } else {
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
            tvshowShowDTO.setAddedAt(metadata.getAddedAt());
            tvshowShowDTO.setUpdatedAt(metadata.getUpdatedAt());
            tvshowShowService.update(tvshowShowDTO);
        }
        syncPlexGenre(metadata.getGenreList(), tvshowShowDTO.getId());
        syncPlexTvshowActor(metadata.getRoleList(), tvshowShowDTO.getId(), ActorRole.Actor);
    }

    private void syncPlexSeason(Long seasonId) {
        Metadata metadata = plexApiService.findSeasonById(seasonId);
        TvshowSeasonDTO tvshowSeasonDTO = tvshowSeasonService.findById(seasonId);
        if (tvshowSeasonDTO == null) {
            tvshowSeasonDTO = new TvshowSeasonDTO();
            tvshowSeasonDTO.setId(metadata.getRatingKey());
            tvshowSeasonDTO.setShowId(metadata.getParentRatingKey());
            tvshowSeasonDTO.setTitle(metadata.getTitle());
            tvshowSeasonDTO.setSummary(metadata.getSummary());
            tvshowSeasonDTO.setSeasonIndex(metadata.getIndex());
            tvshowSeasonDTO.setThumb(metadata.getThumb());
            tvshowSeasonDTO.setArt(metadata.getArt());
            tvshowSeasonDTO.setAddedAt(metadata.getAddedAt());
            tvshowSeasonDTO.setUpdatedAt(metadata.getUpdatedAt());
            tvshowSeasonService.insert(tvshowSeasonDTO);
        } else {
            tvshowSeasonDTO.setTitle(metadata.getTitle());
            tvshowSeasonDTO.setSummary(metadata.getSummary());
            tvshowSeasonDTO.setSeasonIndex(metadata.getIndex());
            tvshowSeasonDTO.setThumb(metadata.getThumb());
            tvshowSeasonDTO.setArt(metadata.getArt());
            tvshowSeasonDTO.setAddedAt(metadata.getAddedAt());
            tvshowSeasonDTO.setUpdatedAt(metadata.getUpdatedAt());
            tvshowSeasonService.update(tvshowSeasonDTO);
        }
        syncPlexShow(metadata.getParentRatingKey());
    }

    private void syncPlexEpisodeActor(List<Tag> actorList, Long episodeId, ActorRole actorRole) {
        tvshowEpisodeActorService.deleteByEpisodeIdAndRole(episodeId, actorRole.name());
        if (actorList == null) {
            return;
        }
        for (Tag tag : actorList) {
            TvshowActorDTO tvshowActorDTO = saveActor(tag);
            TvshowEpisodeActorDTO tvshowEpisodeActorDTO = tvshowEpisodeActorService.findByEpisodeIdAndActorId(episodeId, tvshowActorDTO.getId());
            if (tvshowEpisodeActorDTO == null) {
                tvshowEpisodeActorDTO = new TvshowEpisodeActorDTO();
                tvshowEpisodeActorDTO.setEpisodeId(episodeId);
                tvshowEpisodeActorDTO.setActorId(tvshowActorDTO.getId());
                tvshowEpisodeActorDTO.setRole(actorRole.name());
                tvshowEpisodeActorDTO.setPlayRole(tag.getRole());
                tvshowEpisodeActorService.insert(tvshowEpisodeActorDTO);
            }
        }

    }

    private void syncPlexTvshowActor(List<Tag> actorList, Long showId, ActorRole actorRole) {
        tvshowShowActorService.deleteByShowIdAndRole(showId, actorRole.name());
        if (actorList == null) {
            return;
        }
        for (Tag tag : actorList) {
            TvshowActorDTO tvshowActorDTO = saveActor(tag);
            TvshowShowActorDTO tvshowShowActorDTO = tvshowShowActorService.findByShowIdAndActorId(showId, tvshowActorDTO.getId());
            if (tvshowShowActorDTO == null) {
                tvshowShowActorDTO = new TvshowShowActorDTO();
                tvshowShowActorDTO.setShowId(showId);
                tvshowShowActorDTO.setActorId(tvshowActorDTO.getId());
                tvshowShowActorDTO.setRole(actorRole.name());
                tvshowShowActorDTO.setPlayRole(tag.getRole());
                tvshowShowActorService.insert(tvshowShowActorDTO);
            }
        }
    }

    private TvshowActorDTO saveActor(Tag tag) {
        TvshowActorDTO tvshowActorDTO = tvshowActorService.findById(tag.getId());
        if (tvshowActorDTO == null) {
            tvshowActorDTO = new TvshowActorDTO();
            tvshowActorDTO.setId(tag.getId());
            tvshowActorDTO.setName(tag.getTag());
            tvshowActorDTO.setOriginalName(tag.getTag());
            tvshowActorDTO.setThumb(tag.getThumb());
            tvshowActorDTO = tvshowActorService.insert(tvshowActorDTO);
        } else if (StringUtils.isNotEmpty(tag.getThumb())) {
            tvshowActorDTO.setThumb(tag.getThumb());
            tvshowActorService.update(tvshowActorDTO);
        }
        return tvshowActorDTO;
    }

    private void syncPlexGenre(List<Tag> genreList, Long showId) {
        tvshowShowGenreService.deleteByShowId(showId);
        if (genreList == null) {
            return;
        }
        for (Tag tag : genreList) {
            TvshowGenreDTO tvshowGenreDTO = tvshowGenreService.findById(tag.getId());
            if (tvshowGenreDTO == null) {
                tvshowGenreDTO = tvshowGenreService.insert(tag.getId(), tag.getTag());
            }
            TvshowShowGenreDTO tvshowShowGenreDTO = tvshowShowGenreService.findByShowIdAndGenreId(showId, tvshowGenreDTO.getId());
            if (tvshowShowGenreDTO == null) {
                tvshowShowGenreService.insert(showId, tvshowGenreDTO.getId());
            }
        }
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
        Path downloadPath = Paths.get(ConfigUtils.getSysConfig(ConfigKey.tvshowDownloadPath));
        try {
            SeasonNFO seasonNFO = new SeasonNFO();
            seasonNFO.setDoubanId(doubanId);
            Path path = pathList.get(0);
            if (path.toFile().isDirectory()) {
                NFOUtil.write(seasonNFO, SeasonNFO.class, path, "season.nfo");
                NioFileUtils.moveDir(path, downloadPath, StandardCopyOption.REPLACE_EXISTING);
            } else {
                String baseName = FilenameUtils.getBaseName(path.getFileName().toString());
                Path newPath = downloadPath.resolve(baseName);
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
    public void matchInfo(Long id, String doubanId, String imdbId, String tmdbId) {
        try {
            Tvshow tvshow = tmmApiService.findTvshow(doubanId, imdbId, tmdbId);
            Metadata metadata = plexApiService.findMetadata(id);
            Path folderPath = Paths.get(KaleidoUtils.getTvshowFolder(metadata.getLocation().getPath()));
            Files.list(folderPath).forEach(s -> {
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
            exportTvshowNFO(tvshow, folderPath);
            plexApiService.refreshMetadata(id);
            //如果模版信息发生变动，则重新移动文件，而后续由定时任务重新获取新的信息
            TvshowShowDTO tvshowShowDTO = tvshowShowService.findById(id);
            if (!StringUtils.equals(tvshow.getTitle(), tvshowShowDTO.getTitle()) || !StringUtils.equals(tvshow.getYear(), tvshowShowDTO.getYear())) {
                String libraryPath = ConfigUtils.getSysConfig(ConfigKey.tvshowLibraryPath);
                Path newFolderPath = createFolderPath(tvshow, Paths.get(libraryPath));
                NioFileUtils.renameDir(folderPath, newFolderPath);
            }
        } catch (Exception e) {
            log.error("匹配剧集信息发生错误：{}", ExceptionUtil.getMessage(e));
        }
    }
}
