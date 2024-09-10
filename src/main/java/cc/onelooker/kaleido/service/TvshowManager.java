package cc.onelooker.kaleido.service;

import cc.onelooker.kaleido.dto.*;
import cc.onelooker.kaleido.enums.*;
import cc.onelooker.kaleido.nfo.EpisodeNFO;
import cc.onelooker.kaleido.nfo.NFOUtil;
import cc.onelooker.kaleido.nfo.SeasonNFO;
import cc.onelooker.kaleido.nfo.TvshowNFO;
import cc.onelooker.kaleido.third.plex.Metadata;
import cc.onelooker.kaleido.third.plex.Tag;
import cc.onelooker.kaleido.third.tmm.*;
import cc.onelooker.kaleido.utils.KaleidoConstants;
import cc.onelooker.kaleido.utils.KaleidoUtils;
import cc.onelooker.kaleido.utils.NioFileUtils;
import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.http.HttpUtil;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

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
    private TmmApiService tmmApiService;

    @Transactional
    public void saveEpisode(TvshowEpisodeDTO tvshowEpisodeDTO) {
        TvshowEpisodeDTO existTvshowEpisodeDTO = tvshowEpisodeService.findById(tvshowEpisodeDTO.getId());
        if (existTvshowEpisodeDTO == null) {
            tvshowEpisodeService.insert(tvshowEpisodeDTO);
        } else {
            tvshowEpisodeService.update(tvshowEpisodeDTO);
        }
        taskService.newTask(tvshowEpisodeDTO.getId(), SubjectType.TvshowEpisode, TaskType.writeTvshowNFO);
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
        taskService.newTask(tvshowSeasonDTO.getId(), SubjectType.TvshowSeason, TaskType.writeTvshowNFO);
        List<TvshowEpisodeDTO> tvshowEpisodeDTOList = tvshowEpisodeService.listBySeasonId(tvshowSeasonDTO.getId());
        tvshowEpisodeDTOList.forEach(e -> {
            taskService.newTask(e.getId(), SubjectType.TvshowEpisode, TaskType.writeTvshowNFO);
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
            renameDirIfChanged(tvshowShowDTO);
            TvshowShowDTO existTvshowShowDTO = tvshowShowService.findById(tvshowShowDTO.getId());
            if (existTvshowShowDTO == null) {
                tvshowShowService.insert(tvshowShowDTO);
            } else {
                tvshowShowService.update(tvshowShowDTO);
            }
            taskService.newTask(tvshowShowDTO.getId(), SubjectType.TvshowShow, TaskType.writeTvshowNFO);
            List<TvshowSeasonDTO> tvshowSeasonDTOList = tvshowSeasonService.listByShowId(tvshowShowDTO.getId());
            tvshowSeasonDTOList.forEach(s -> {
                taskService.newTask(s.getId(), SubjectType.TvshowSeason, TaskType.writeTvshowNFO);
                List<TvshowEpisodeDTO> tvshowEpisodeDTOList = tvshowEpisodeService.listBySeasonId(s.getId());
                tvshowEpisodeDTOList.forEach(e -> {
                    taskService.newTask(e.getId(), SubjectType.TvshowEpisode, TaskType.writeTvshowNFO);
                });
            });
        } catch (Exception e) {
            ExceptionUtil.wrapAndThrow(e);
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
        tvshowEpisodeDTO.setSeasonIndex(metadata.getParentIndex());
        tvshowEpisodeDTO.setEpisodeIndex(metadata.getIndex());
        tvshowEpisodeDTO.setRating(metadata.getRating());
        tvshowEpisodeDTO.setYear(metadata.getYear());
        tvshowEpisodeDTO.setThumb(metadata.getThumb());
        tvshowEpisodeDTO.setArt(metadata.getArt());
        String filename = FilenameUtils.getName(metadata.getMedia().getPart().getFile());
        tvshowEpisodeDTO.setFilename(filename);
        tvshowEpisodeDTO.setDuration(metadata.getDuration());
        tvshowEpisodeDTO.setOriginallyAvailableAt(metadata.getOriginallyAvailableAt());
        tvshowEpisodeDTO.setAddedAt(metadata.getAddedAt());
        tvshowEpisodeDTO.setUpdatedAt(metadata.getUpdatedAt());
        //无需读取nfo
        saveEpisode(tvshowEpisodeDTO);
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
    public void matchPath(Path path, String doubanId) {
        try {
            NioFileUtils.deleteByFilter(path, "nfo");
            TvshowNFO tvshowNFO = new TvshowNFO();
            tvshowNFO.setDoubanId(doubanId);
            String filename = FilenameUtils.getBaseName(path.getFileName().toString());
            Path importPath = KaleidoUtils.getTvshowImportPath();
            Path newPath = importPath.resolve(StringUtils.defaultIfEmpty(doubanId, filename));
            if (Files.isDirectory(path)) {
                //如果是文件夹，则写入nfo再改名
                if (!StringUtils.equals(newPath.toString(), path.toString())) {
                    NioFileUtils.renameDir(path, newPath, StandardCopyOption.REPLACE_EXISTING);
                }
                NFOUtil.write(tvshowNFO, TvshowNFO.class, newPath, KaleidoConstants.TVSHOW_SHOW_NFO);
            } else {
                //新建文件夹，写入nfo再移入文件夹
                if (Files.notExists(newPath)) {
                    Files.createDirectories(newPath);
                }
                NFOUtil.write(tvshowNFO, TvshowNFO.class, newPath, KaleidoConstants.TVSHOW_SHOW_NFO);
                Files.move(path, newPath.resolve(path.getFileName()), StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (Exception e) {
            ExceptionUtil.wrapAndThrow(e);
        }
    }

    @Transactional
    public void updateSource(Path path) {
        if (!Files.isDirectory(path)) {
            return;
        }
        if (Files.exists(path.resolve(KaleidoConstants.TVSHOW_SHOW_NFO))) {
            try {
                log.info("== 开始更新数据文件: {}", path);
                Tvshow tvshow = findTmmTvshowByNFO(path);
                if (tvshow != null) {
                    log.info("== 查询到剧集信息: {}", tvshow.getTitle());
                    TvshowShowDTO tvshowShowDTO = TmmUtil.toTvshowShow(tvshow);
                    operationPath(tvshowShowDTO, path);
                } else {
                    Path tvshowLibraryPath = KaleidoUtils.getTvshowLibraryPath();
                    Path targetPath = tvshowLibraryPath.resolve(path.getFileName());
                    if (Files.notExists(targetPath)) {
                        Files.createDirectories(targetPath);
                    }
                    //默认第1季
                    Path seasonPath = createSeasonPath(targetPath, 1);
                    NioFileUtils.renameDir(path, seasonPath, StandardCopyOption.REPLACE_EXISTING);
                }
            } catch (Exception e) {
                log.error("== 更新电影源发生错误: {}", path, e);
            } finally {
                log.info("== 完成更新数据文件: {}", path);
            }
        }
    }

    @Transactional
    public void matchInfo(String seasonId, Tvshow tvshow) {
        try {
            TvshowSeasonDTO tvshowSeasonDTO = tvshowSeasonService.findById(seasonId);
            TvshowShowDTO tvshowShowDTO = tvshowShowService.findById(tvshowSeasonDTO.getShowId());
            TmmUtil.toTvshowShow(tvshowShowDTO, tvshow);
            if (KaleidoUtils.isSameTvshow(tvshowShowDTO, tvshow)) {
                //如为相同的剧集，则直接更新
                saveShow(tvshowShowDTO);
                Season season = tvshow.getSeason(tvshowSeasonDTO.getSeasonIndex());
                Validate.notNull(season);
                List<TvshowEpisodeDTO> tvshowEpisodeDTOList = tvshowEpisodeService.listBySeasonId(seasonId);
                TmmUtil.toTvshowSeason(tvshowSeasonDTO, season);
                saveSeason(tvshowSeasonDTO);
                for (TvshowEpisodeDTO tvshowEpisodeDTO : tvshowEpisodeDTOList) {
                    Episode episode = null;
                    if (season.getEpisodes() != null) {
                        episode = season.getEpisodes().stream().filter(s -> Objects.equals(s.getEpisodeNumber(), tvshowEpisodeDTO.getEpisodeIndex())).findFirst().orElse(null);
                    }
                    TmmUtil.toTvshowEpisode(tvshowEpisodeDTO, episode);
                    saveEpisode(tvshowEpisodeDTO);
                }
            } else {
                //重新处理文件夹
                Path path = KaleidoUtils.getTvshowPath(tvshowShowDTO.getPath());
                operationPath(tvshowShowDTO, path);
            }
        } catch (Exception e) {
            ExceptionUtil.wrapAndThrow(e);
        }
    }

    public TvshowShowDTO findTvshowShow(String showId) {
        TvshowShowDTO tvshowShowDTO = tvshowShowService.findById(showId);
        List<AttributeDTO> attributeDTOList = attributeService.listBySubjectId(tvshowShowDTO.getId());
        List<TvshowSeasonDTO> tvshowSeasonDTOList = tvshowSeasonService.listByShowId(tvshowShowDTO.getId());
        tvshowShowDTO.setSeasonList(tvshowSeasonDTOList);
        TvshowSeasonDTO firstSeasonDTO = tvshowShowDTO.getFirstSeason();
        List<ActorDTO> actorDTOList = actorService.listBySeasonId(firstSeasonDTO.getId());
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

    private void renameDirIfChanged(TvshowShowDTO tvshowShowDTO) throws IOException {
        String newPath = KaleidoUtils.genTvshowFolder(tvshowShowDTO);
        if (!StringUtils.equals(newPath, tvshowShowDTO.getPath())) {
            Path tvshowPath = KaleidoUtils.getTvshowPath(newPath);
            if (Files.notExists(tvshowPath)) {
                Files.createDirectories(tvshowPath);
            }
            NioFileUtils.renameDir(KaleidoUtils.getTvshowPath(tvshowShowDTO.getPath()), tvshowPath);
            tvshowShowDTO.setPath(newPath);
        }
    }

    private void readNFO(TvshowShowDTO tvshowShowDTO) {
        Path filePath = KaleidoUtils.getMoviePath(tvshowShowDTO.getPath());
        if (Files.notExists(filePath.resolve(KaleidoConstants.TVSHOW_SHOW_NFO))) {
            return;
        }
        TvshowNFO tvshowNFO = NFOUtil.read(TvshowNFO.class, filePath, KaleidoConstants.TVSHOW_SHOW_NFO);
        String doubanId = NFOUtil.getUniqueid(tvshowNFO.getUniqueids(), SourceType.douban);
        String imdb = NFOUtil.getUniqueid(tvshowNFO.getUniqueids(), SourceType.imdb);
        String tmdb = NFOUtil.getUniqueid(tvshowNFO.getUniqueids(), SourceType.tmdb);
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
        String doubanId = NFOUtil.getUniqueid(seasonNFO.getUniqueids(), SourceType.douban);
        String imdbId = NFOUtil.getUniqueid(seasonNFO.getUniqueids(), SourceType.imdb);
        String tmdbId = NFOUtil.getUniqueid(seasonNFO.getUniqueids(), SourceType.tmdb);
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

    private Tvshow findTmmTvshowByNFO(Path path) {
        try {
            TvshowNFO tvshowNFO = NFOUtil.read(TvshowNFO.class, path, KaleidoConstants.TVSHOW_SHOW_NFO);
            return tmmApiService.findTvshow(tvshowNFO.getDoubanId(), tvshowNFO.getImdbId(), tvshowNFO.getTmdbId());
        } catch (Exception e) {
            log.info("== NFO文件无法读取: {}", path.resolve(KaleidoConstants.TVSHOW_SHOW_NFO).toString());
        }
        return null;
    }

    private void operationPath(TvshowShowDTO tvshowShowDTO, Path path) {
        Path targetPath = createFolderPath(tvshowShowDTO);
        Set<Integer> seasonIndexSet = Sets.newHashSet();
        try {
            Files.list(path).forEach(s -> {
                try {
                    String fileName = s.getFileName().toString();
                    Integer episodeIndex = KaleidoUtils.parseEpisodeIndex(fileName);
                    if (episodeIndex == null) {
                        log.info("== 无法确定集号信息，保持不动: {}", fileName);
                        return;
                    }
                    Integer seasonIndex = KaleidoUtils.parseSeasonIndex(fileName, 1);
                    Path seasonPath = createSeasonPath(targetPath, seasonIndex);
                    moveExistingFilesToRecycleBin(seasonPath, episodeIndex);
                    if (!KaleidoUtils.isVideoFile(fileName)) {
                        Files.move(s, seasonPath.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
                        log.info("== 移动其他文件: {}", fileName);
                        return;
                    }

                    Path episodePath = seasonPath.resolve(fileName);
                    TvshowSeasonDTO tvshowSeasonDTO = tvshowShowDTO.getSeason(seasonIndex);
                    TvshowEpisodeDTO tvshowEpisodeDTO = tvshowSeasonDTO.getEpisode(episodeIndex);
                    downloadEpisodePoster(tvshowEpisodeDTO, episodePath);
                    exportEpisodeNFO(tvshowShowDTO, tvshowSeasonDTO, tvshowEpisodeDTO, episodePath);
                    if (seasonIndexSet.add(seasonIndex)) {
                        //下载单季图片
                        downloadSeasonPoster(tvshowSeasonDTO, seasonPath);
                        //生成单季nfo文件
                        exportSeasonNFO(tvshowShowDTO, tvshowSeasonDTO, seasonPath);
                    }
                    Files.move(s, episodePath, StandardCopyOption.REPLACE_EXISTING);
                    log.info("== 移动视频文件: {}", fileName);
                } catch (Exception e) {
                    ExceptionUtil.wrapAndThrow(e);
                }
            });
        } catch (IOException e) {
            ExceptionUtil.wrapAndThrow(e);
        }
        //下载剧集图片
        downloadTvshowPoster(tvshowShowDTO, targetPath);
        //生成剧集nfo文件
        exportTvshowNFO(tvshowShowDTO, targetPath);
        //删除空文件夹
        deletePathIfEmpty(path);
    }

    private void deletePathIfEmpty(Path path) {
        try {
            if (Files.list(path).noneMatch(s -> KaleidoUtils.isVideoFile(s.getFileName().toString()))) {
                NioFileUtils.deleteIfExists(path);
                log.info("== 删除源文件夹: {}", path.getFileName());
            }
        } catch (IOException e) {
            ExceptionUtil.wrapAndThrow(e);
        }
    }

    private void moveExistingFilesToRecycleBin(Path seasonPath, Integer episodeIndex) {
        Path recyclePath = KaleidoUtils.getTvshowRecyclePath();
        try {
            Files.list(seasonPath).forEach(s -> {
                try {
                    String fileName = s.getFileName().toString();
                    Integer epIndex = KaleidoUtils.parseEpisodeIndex(s.getFileName().toString());
                    if (KaleidoUtils.isVideoFile(fileName) && Objects.equals(episodeIndex, epIndex)) {
                        Files.move(s, recyclePath.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
                        log.info("== 删除原剧集视频文件: {}", fileName);
                    }
                } catch (IOException e) {
                    ExceptionUtil.wrapAndThrow(e);
                }
            });
        } catch (IOException e) {
            ExceptionUtil.wrapAndThrow(e);
        }
    }

    private void downloadTvshowPoster(TvshowShowDTO tvshowShowDTO, Path tvshowPath) {
        if (StringUtils.isEmpty(tvshowShowDTO.getThumb())) {
            return;
        }
        HttpUtil.downloadFile(tvshowShowDTO.getThumb(), tvshowPath.resolve("poster.jpg").toFile());
        log.info("== 下载海报: {}", tvshowShowDTO.getThumb());
    }

    private void downloadSeasonPoster(TvshowSeasonDTO tvshowSeasonDTO, Path seasonPath) {
        if (StringUtils.isEmpty(tvshowSeasonDTO.getThumb())) {
            return;
        }
        String fileName = StringUtils.joinWith("-", "season", tvshowSeasonDTO.getSeasonIndex(), "poster.jpg");
        HttpUtil.downloadFile(tvshowSeasonDTO.getThumb(), seasonPath.resolveSibling(fileName).toFile());
        log.info("== 下载单季海报: {}", tvshowSeasonDTO.getThumb());
    }

    private void downloadEpisodePoster(TvshowEpisodeDTO tvshowEpisodeDTO, Path videoPath) {
        if (StringUtils.isEmpty(tvshowEpisodeDTO.getThumb())) {
            return;
        }
        String fileName = videoPath.getFileName().toString();
        String thumbFileName = FilenameUtils.getBaseName(fileName) + "-thumb.jpg";
        HttpUtil.downloadFile(tvshowEpisodeDTO.getThumb(), videoPath.resolveSibling(thumbFileName).toFile());
        log.info("== 下载单集图片: {}", tvshowEpisodeDTO.getThumb());
    }

    private void exportTvshowNFO(TvshowShowDTO tvshowShowDTO, Path tvshowPath) {
        try {
            TvshowNFO tvshowNFO = NFOUtil.toTvshowNFO(tvshowShowDTO);
            NFOUtil.write(tvshowNFO, TvshowNFO.class, tvshowPath, KaleidoConstants.TVSHOW_SHOW_NFO);
            log.info("== 生成剧集NFO文件: {}", tvshowPath.resolve(KaleidoConstants.TVSHOW_SHOW_NFO));
        } catch (Exception e) {
            ExceptionUtil.wrapAndThrow(e);
        }
    }

    private void exportSeasonNFO(TvshowShowDTO tvshowShowDTO, TvshowSeasonDTO tvshowSeasonDTO, Path seasonPath) {
        try {
            SeasonNFO seasonNFO = NFOUtil.toSeasonNFO(tvshowShowDTO, tvshowSeasonDTO);
            NFOUtil.write(seasonNFO, SeasonNFO.class, seasonPath, KaleidoConstants.TVSHOW_SEASON_NFO);
            log.info("== 生成单季NFO文件: {}", seasonPath.resolve(KaleidoConstants.TVSHOW_SEASON_NFO));
        } catch (Exception e) {
            ExceptionUtil.wrapAndThrow(e);
        }
    }

    private void exportEpisodeNFO(TvshowShowDTO tvshowShowDTO, TvshowSeasonDTO tvshowSeasonDTO, TvshowEpisodeDTO tvshowEpisodeDTO, Path videoPath) {
        try {
            if (tvshowEpisodeDTO == null) {
                return;
            }
            String fileName = videoPath.getFileName().toString();
            EpisodeNFO episodeNFO = NFOUtil.toEpisodeNFO(tvshowShowDTO, tvshowSeasonDTO, tvshowEpisodeDTO);
            String nfoFileName = FilenameUtils.getBaseName(fileName) + ".nfo";
            NFOUtil.write(episodeNFO, EpisodeNFO.class, videoPath.getParent(), nfoFileName);
            log.info("== 生成单集NFO文件: {}", videoPath.resolveSibling(nfoFileName));
        } catch (Exception e) {
            ExceptionUtil.wrapAndThrow(e);
        }
    }

    private Path createFolderPath(TvshowShowDTO tvshowShowDTO) {
        try {
            String folderName = KaleidoUtils.genTvshowFolder(tvshowShowDTO);
            Path folderPath = KaleidoUtils.getTvshowPath(folderName);
            if (Files.notExists(folderPath)) {
                Files.createDirectories(folderPath);
            }
            return folderPath;
        } catch (IOException e) {
            ExceptionUtil.wrapAndThrow(e);
        }
        return null;
    }

    private Path createSeasonPath(Path targetPath, Integer seasonIndex) throws IOException {
        String folderName = KaleidoUtils.genSeasonFolder(seasonIndex);
        Path folderPath = targetPath.resolve(folderName);
        if (Files.notExists(folderPath)) {
            Files.createDirectory(folderPath);
        }
        return folderPath;
    }

}
