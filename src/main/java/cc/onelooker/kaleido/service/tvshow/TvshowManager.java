package cc.onelooker.kaleido.service.tvshow;

import cc.onelooker.kaleido.dto.tvshow.*;
import cc.onelooker.kaleido.enums.ActorRole;
import cc.onelooker.kaleido.enums.ConfigKey;
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
import cn.hutool.http.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
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

    private Pattern pattern = Pattern.compile("S([0-9]?[1-9])E([0-9]?[1-9])");

    @Transactional
    public void syncPlexEpisode(Long tvshowId) {
        Metadata metadata = plexApiService.findEpisodeById(tvshowId);
        syncPlexEpisode(metadata);
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
        //创建规范文件夹
        Path targetPath = createFolderPath(tvshow, libraryPath);
        Season season = tvshow.getSeason();
        Files.list(path).forEach(s -> {
            try {
                File file = s.toFile();
                String fileName = file.getName();
                if (file.isDirectory() && StringUtils.equals(fileName, "Other")) {
                    //直接移动文件夹
                    NioFileUtils.moveDir(s, targetPath, StandardCopyOption.REPLACE_EXISTING);
                    log.info("== 移动文件目录:{}", fileName);
                    return;
                }
                if (!KaleidoUtils.isVideoFile(fileName)) {
                    //直接移动非视频类文件
                    Files.move(s, targetPath.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
                    log.info("== 移动文件: {}", fileName);
                    return;
                }
                Integer episodeNumber = getEpisodeNumber(fileName);
                if (episodeNumber == null) {
                    //如果无法确定集号，则保留不动
                    log.info("== 无法确定集号，保持不动: {}", fileName);
                    return;
                }
                //删除已经存在的剧集视频文件
                deleteExistEpisodeVideoFile(targetPath, episodeNumber);
                //移动视频文件
                Path videoPath = targetPath.resolve(fileName);
                Files.move(s, videoPath, StandardCopyOption.REPLACE_EXISTING);
                log.info("== 移动视频文件: {}", fileName);
                if (CollectionUtils.isNotEmpty(season.getEpisodes())) {
                    Optional<Episode> episodeOptional = season.getEpisodes().stream().filter(episode -> Objects.equals(episode.getEpisodeNumber(), episodeNumber)).findFirst();
                    if (episodeOptional.isPresent()) {
                        Episode episode = episodeOptional.get();
                        //下载episode图片
                        downloadEpisodePoster(episode, videoPath);
                        //生成episode的nfo文件
                        exportEpisodeNFO(episode, tvshow, videoPath);
                    } else {
                        log.info("== 无法找到单集信息: {}", fileName);
                    }
                } else {
                    log.info("== 无法获取单集信息，忽略单集后续信息处理: {}", fileName);
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

    private void exportTvshowNFO(Tvshow tvshow, Path targetPath) throws Exception {
        TvshowNFO tvshowNFO = NFOUtil.toTvshowNFO(tvshow);
        NFOUtil.write(tvshowNFO, TvshowNFO.class, targetPath.getParent(), "tvshow.nfo");
        log.info("== 生成剧集NFO文件");
        Season season = tvshow.getSeason();
        SeasonNFO seasonNFO = NFOUtil.toSeasonNFO(season, tvshow);
        NFOUtil.write(seasonNFO, SeasonNFO.class, targetPath, "season.nfo");
        log.info("== 生成单季NFO文件");
    }

    private void downloadTvshowPoster(Tvshow tvshow, Path targetPath) {
        HttpUtil.downloadFile(tvshow.getPoster(), targetPath.resolveSibling("poster.jpg").toFile());
        log.info("== 下载剧集海报");
        Season season = tvshow.getSeason();
        String fileName = StringUtils.joinWith("-", "season", season.getSeasonNumberStr(), "poster.jpg");
        HttpUtil.downloadFile(season.getPoster(), targetPath.resolveSibling(fileName).toFile());
        log.info("== 下载单季海报");
    }

    private void exportEpisodeNFO(Episode episode, Tvshow tvshow, Path videoPath) throws Exception {
        String fileName = videoPath.getFileName().toString();
        EpisodeNFO episodeNFO = NFOUtil.toEpisodeNFO(episode, tvshow.getSeason(), tvshow);
        String nfoFileName = FilenameUtils.getBaseName(fileName) + ".nfo";
        NFOUtil.write(episodeNFO, EpisodeNFO.class, videoPath.getParent(), nfoFileName);
        log.info("== 生成单集NFO文件: {}", nfoFileName);
    }

    private void downloadEpisodePoster(Episode episode, Path videoPath) {
        String fileName = videoPath.getFileName().toString();
        String thumbFileName = FilenameUtils.getBaseName(fileName) + "-thumb.jpg";
        HttpUtil.downloadFile(episode.getThumb(), videoPath.resolveSibling(thumbFileName).toFile());
        log.info("== 下载单集图片: {}", thumbFileName);
    }

    private void deleteExistEpisodeVideoFile(Path targetPath, Integer episodeNumber) throws IOException {
        Path trashPath = Paths.get(ConfigUtils.getSysConfig(ConfigKey.tvshowTrashPath));
        Files.list(targetPath).forEach(s -> {
            try {
                String fileName = s.getFileName().toString();
                Integer episode = getEpisodeNumber(fileName);
                if (episode != null && episode.equals(episodeNumber)) {
                    Files.move(s, trashPath.resolve(s.getFileName()), StandardCopyOption.REPLACE_EXISTING);
                    log.info("== 移除原剧集视频文件: {}", fileName);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

    }

    private Integer getEpisodeNumber(String fileName) {
        Matcher matcher = pattern.matcher(fileName);
        if (matcher.find()) {
            return Integer.valueOf(matcher.group(2));
        }
        return null;
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
        folderName = "Season " + tvshow.getSeason().getSeasonNumberStr();
        folderPath = folderPath.resolve(folderName);
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
        syncPlexActor(metadata.getDirectorList(), null, tvshowEpisodeDTO.getId(), ActorRole.Director);
        syncPlexActor(metadata.getWriterList(), null, tvshowEpisodeDTO.getId(), ActorRole.Writer);

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
        syncPlexActor(metadata.getRoleList(), tvshowShowDTO.getId(), null, ActorRole.Actor);
    }

    public void syncPlexSeason(Long seasonId) {
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

    private void syncPlexActor(List<Tag> directorList, Long showId, Long episodeId, ActorRole actorRole) {
        if (directorList == null) {
            return;
        }
        for (Tag tag : directorList) {
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
            if (showId != null) {
                TvshowShowActorDTO tvshowShowActorDTO = tvshowShowActorService.findByShowIdAndActorId(showId, tvshowActorDTO.getId());
                if (tvshowShowActorDTO == null) {
                    tvshowShowActorDTO = new TvshowShowActorDTO();
                    tvshowShowActorDTO.setShowId(showId);
                    tvshowShowActorDTO.setActorId(tvshowActorDTO.getId());
                    tvshowShowActorDTO.setRole(actorRole.name());
                    tvshowShowActorDTO.setPlayRole(tag.getRole());
                    tvshowShowActorService.insert(tvshowShowActorDTO);
                }
            } else if (episodeId != null) {
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
    }

    private void syncPlexGenre(List<Tag> genreList, Long tvshowId) {
        if (genreList == null) {
            return;
        }
        for (Tag tag : genreList) {
            TvshowGenreDTO tvshowGenreDTO = tvshowGenreService.findById(tag.getId());
            if (tvshowGenreDTO == null) {
                tvshowGenreDTO = tvshowGenreService.insert(tag.getId(), tag.getTag());
            }
            TvshowShowGenreDTO tvshowShowGenreDTO = tvshowShowGenreService.findByShowIdAndGenreId(tvshowId, tvshowGenreDTO.getId());
            if (tvshowShowGenreDTO == null) {
                tvshowShowGenreService.insert(tvshowId, tvshowGenreDTO.getId());
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

}
