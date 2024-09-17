package cc.onelooker.kaleido.service;

import cc.onelooker.kaleido.dto.*;
import cc.onelooker.kaleido.enums.*;
import cc.onelooker.kaleido.nfo.EpisodeNFO;
import cc.onelooker.kaleido.nfo.NFOUtil;
import cc.onelooker.kaleido.nfo.SeasonNFO;
import cc.onelooker.kaleido.nfo.TvshowNFO;
import cc.onelooker.kaleido.third.plex.Metadata;
import cc.onelooker.kaleido.third.plex.PlexUtil;
import cc.onelooker.kaleido.third.tmm.*;
import cc.onelooker.kaleido.utils.ConfigUtils;
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
    public void saveShow(TvshowShowDTO tvshowShowDTO) {
        try {
            attributeService.updateAttributes(tvshowShowDTO.getTagList(), tvshowShowDTO.getId(), AttributeType.Tag);
            attributeService.updateAttributes(tvshowShowDTO.getGenreList(), tvshowShowDTO.getId(), AttributeType.Genre);
            attributeService.updateAttributes(tvshowShowDTO.getCountryList(), tvshowShowDTO.getId(), AttributeType.Country);
            attributeService.updateAttributes(tvshowShowDTO.getLanguageList(), tvshowShowDTO.getId(), AttributeType.Language);
            alternateTitleService.updateTitles(tvshowShowDTO.getAkaList(), tvshowShowDTO.getId(), SubjectType.TvshowShow);
            tvshowShowDTO.setSortTitle(KaleidoUtils.genSortTitle(tvshowShowDTO.getTitle()));
            renameDirIfChanged(tvshowShowDTO);
            TvshowShowDTO existTvshowShowDTO = tvshowShowService.findById(tvshowShowDTO.getId());
            if (existTvshowShowDTO == null) {
                tvshowShowService.insert(tvshowShowDTO);
            } else {
                tvshowShowService.update(tvshowShowDTO);
            }
            if (ConfigUtils.isEnabled(ConfigKey.writeTvshowNFO)) {
                taskService.newTask(tvshowShowDTO.getId(), SubjectType.TvshowShow, TaskType.writeTvshowNFO);
                List<TvshowSeasonDTO> tvshowSeasonDTOList = tvshowSeasonService.listByShowId(tvshowShowDTO.getId());
                tvshowSeasonDTOList.forEach(this::writeTvshowNFO);
            }
        } catch (Exception e) {
            ExceptionUtil.wrapAndThrow(e);
        }
    }

    @Transactional
    public void saveSeason(TvshowSeasonDTO tvshowSeasonDTO) {
        actorService.updateSeasonActors(tvshowSeasonDTO.getDirectorList(), tvshowSeasonDTO.getId(), ActorRole.Director);
        actorService.updateSeasonActors(tvshowSeasonDTO.getWriterList(), tvshowSeasonDTO.getId(), ActorRole.Writer);
        actorService.updateSeasonActors(tvshowSeasonDTO.getActorList(), tvshowSeasonDTO.getId(), ActorRole.Actor);
        tvshowSeasonDTO.setSortTitle(KaleidoUtils.genSortTitle(tvshowSeasonDTO.getTitle()));
        TvshowSeasonDTO existTvshowSeasonDTO = tvshowSeasonService.findById(tvshowSeasonDTO.getId());
        if (existTvshowSeasonDTO == null) {
            tvshowSeasonService.insert(tvshowSeasonDTO);
        } else {
            tvshowSeasonService.update(tvshowSeasonDTO);
        }
        writeTvshowNFO(tvshowSeasonDTO);
    }

    @Transactional
    public void saveEpisode(TvshowEpisodeDTO tvshowEpisodeDTO) {
        TvshowEpisodeDTO existTvshowEpisodeDTO = tvshowEpisodeService.findById(tvshowEpisodeDTO.getId());
        if (existTvshowEpisodeDTO == null) {
            tvshowEpisodeService.insert(tvshowEpisodeDTO);
        } else {
            tvshowEpisodeService.update(tvshowEpisodeDTO);
        }
        if (ConfigUtils.isEnabled(ConfigKey.writeTvshowNFO)) {
            taskService.newTask(tvshowEpisodeDTO.getId(), SubjectType.TvshowEpisode, TaskType.writeTvshowNFO);
        }
    }

    @Transactional
    public void syncShow(Metadata metadata) {
        TvshowShowDTO tvshowShowDTO = tvshowShowService.findById(metadata.getRatingKey());
        if (tvshowShowDTO == null) {
            tvshowShowDTO = new TvshowShowDTO();
        }
        PlexUtil.toTvshowShowDTO(tvshowShowDTO, metadata);
        readNFO(tvshowShowDTO);
        saveShow(tvshowShowDTO);
    }

    @Transactional
    public void syncSeason(Metadata metadata) {
        TvshowSeasonDTO tvshowSeasonDTO = tvshowSeasonService.findById(metadata.getRatingKey());
        if (tvshowSeasonDTO == null) {
            tvshowSeasonDTO = new TvshowSeasonDTO();
        }
        PlexUtil.toTvshowSeasonDTO(tvshowSeasonDTO, metadata);
        readNFO(tvshowSeasonDTO);
        saveSeason(tvshowSeasonDTO);
    }

    @Transactional
    public void syncEpisode(Metadata metadata) {
        TvshowEpisodeDTO tvshowEpisodeDTO = tvshowEpisodeService.findById(metadata.getRatingKey());
        if (tvshowEpisodeDTO == null) {
            tvshowEpisodeDTO = new TvshowEpisodeDTO();
        }
        PlexUtil.toTvshowEpisodeDTO(tvshowEpisodeDTO, metadata);
        //无需读取nfo
        saveEpisode(tvshowEpisodeDTO);
    }

    @Transactional
    public void matchPath(Path path, Tvshow tvshow) {
        try {
            NioFileUtils.deleteByFilter(path, "nfo");
            TvshowNFO tvshowNFO = new TvshowNFO();
            tvshowNFO.setDoubanId(tvshow.getDoubanId());
            Path importPath = KaleidoUtils.getTvshowImportPath();
            String filename = tvshow.getTitle() + "(" + StringUtils.defaultIfEmpty(tvshow.getDoubanId(), tvshow.getTmdbId()) + ")";
            Path newPath = importPath.resolve(StringUtils.defaultIfEmpty(filename, FilenameUtils.getBaseName(path.getFileName().toString())));
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
                    log.info("== 未找到匹配信息，直接移动文件");
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
        List<AlternateTitleDTO> alternateTitleDTOList = alternateTitleService.listBySubjectId(tvshowShowDTO.getId());
        tvshowShowDTO.setAkaList(alternateTitleDTOList.stream().map(AlternateTitleDTO::getTitle).collect(Collectors.toList()));
        tvshowShowDTO.setLanguageList(attributeDTOList.stream().filter(s -> StringUtils.equals(s.getType(), AttributeType.Language.name())).map(AttributeDTO::getValue).collect(Collectors.toList()));
        tvshowShowDTO.setCountryList(attributeDTOList.stream().filter(s -> StringUtils.equals(s.getType(), AttributeType.Country.name())).map(AttributeDTO::getValue).collect(Collectors.toList()));
        tvshowShowDTO.setGenreList(attributeDTOList.stream().filter(s -> StringUtils.equals(s.getType(), AttributeType.Genre.name())).map(AttributeDTO::getValue).collect(Collectors.toList()));
        tvshowShowDTO.setTagList(attributeDTOList.stream().filter(s -> StringUtils.equals(s.getType(), AttributeType.Tag.name())).map(AttributeDTO::getValue).collect(Collectors.toList()));
        return tvshowShowDTO;
    }

    public TvshowSeasonDTO findTvshowSeason(String seasonId) {
        TvshowSeasonDTO tvshowSeasonDTO = tvshowSeasonService.findById(seasonId);
        List<ActorDTO> actorDTOList = actorService.listBySeasonId(seasonId);
        tvshowSeasonDTO.setDirectorList(actorDTOList.stream().filter(s -> StringUtils.equals(s.getRole(), ActorRole.Director.name())).collect(Collectors.toList()));
        tvshowSeasonDTO.setWriterList(actorDTOList.stream().filter(s -> StringUtils.equals(s.getRole(), ActorRole.Writer.name())).collect(Collectors.toList()));
        tvshowSeasonDTO.setActorList(actorDTOList.stream().filter(s -> StringUtils.equals(s.getRole(), ActorRole.Actor.name())).collect(Collectors.toList()));
        return tvshowSeasonDTO;
    }

    private void writeTvshowNFO(TvshowSeasonDTO tvshowSeasonDTO) {
        if (ConfigUtils.isEnabled(ConfigKey.writeTvshowNFO)) {
            taskService.newTask(tvshowSeasonDTO.getId(), SubjectType.TvshowSeason, TaskType.writeTvshowNFO);
            List<TvshowEpisodeDTO> tvshowEpisodeDTOList = tvshowEpisodeService.listBySeasonId(tvshowSeasonDTO.getId());
            tvshowEpisodeDTOList.forEach(e -> {
                taskService.newTask(e.getId(), SubjectType.TvshowEpisode, TaskType.writeTvshowNFO);
            });
        }
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
        Path filePath = KaleidoUtils.getTvshowPath(tvshowShowDTO.getPath());
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
        try {
            Path showPath = createShowPath(tvshowShowDTO);
            Set<Integer> seasonIndexSet = Sets.newHashSet();
            Files.list(path).forEach(s -> {
                try {
                    String fileName = s.getFileName().toString();
                    Integer episodeIndex = KaleidoUtils.parseEpisodeIndex(fileName);
                    if (episodeIndex == null) {
                        log.info("== 无法确定集号信息，保持不动: {}", fileName);
                        return;
                    }
                    Integer seasonIndex = KaleidoUtils.parseSeasonIndex(fileName, 1);
                    Path seasonPath = createSeasonPath(showPath, seasonIndex);
                    moveExistingFilesToRecycleBin(seasonPath, episodeIndex);
                    if (!KaleidoUtils.isVideoFile(fileName)) {
                        Files.move(s, seasonPath.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
                        log.info("== 移动其他文件: {}", fileName);
                        return;
                    }

                    Path episodePath = seasonPath.resolve(fileName);
                    TvshowSeasonDTO tvshowSeasonDTO = tvshowShowDTO.getSeason(seasonIndex);
                    if (tvshowSeasonDTO == null) {
                        log.info("== 无法找到季号信息，不再处理: {}", fileName);
                    }
                    TvshowEpisodeDTO tvshowEpisodeDTO = tvshowSeasonDTO.getEpisode(episodeIndex);
                    if (tvshowEpisodeDTO == null) {
                        tvshowEpisodeDTO = new TvshowEpisodeDTO();
                        tvshowEpisodeDTO.setTitle("第 " + episodeIndex + " 集");
                        tvshowEpisodeDTO.setEpisodeIndex(episodeIndex);
                        tvshowEpisodeDTO.setSeasonIndex(seasonIndex);
                    }
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
            //下载剧集图片
            downloadTvshowPoster(tvshowShowDTO, showPath);
            //生成剧集nfo文件
            exportTvshowNFO(tvshowShowDTO, showPath);
            //删除空文件夹
            deletePathIfEmpty(path);
        } catch (IOException e) {
            ExceptionUtil.wrapAndThrow(e);
        }

    }

    private void deletePathIfEmpty(Path path) throws IOException {
        if (Files.list(path).noneMatch(s -> KaleidoUtils.isVideoFile(s.getFileName().toString()))) {
            NioFileUtils.deleteIfExists(path);
            log.info("== 源目录已空，进行删除: {}", path);
        }
    }

    private void moveExistingFilesToRecycleBin(Path seasonPath, Integer episodeIndex) throws IOException {
        Files.list(seasonPath).forEach(s -> {
            try {
                String fileName = s.getFileName().toString();
                Integer epIndex = KaleidoUtils.parseEpisodeIndex(fileName);
                if (Objects.equals(episodeIndex, epIndex)) {
                    Path recycleSeasonPath = KaleidoUtils.getTvshowRecyclePath(seasonPath.toString());
                    KaleidoUtils.createFolderPath(recycleSeasonPath);
                    Files.move(s, recycleSeasonPath.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
                    log.info("== 原剧集文件移至回收站: {}", s);
                }
            } catch (IOException e) {
                ExceptionUtil.wrapAndThrow(e);
            }
        });
    }

    private void downloadTvshowPoster(TvshowShowDTO tvshowShowDTO, Path tvshowPath) {
        if (StringUtils.isEmpty(tvshowShowDTO.getThumb())) {
            return;
        }
        HttpUtil.downloadFile(tvshowShowDTO.getThumb(), tvshowPath.resolve("poster.jpg").toFile());
        log.info("== 下载剧集海报: {}", tvshowShowDTO.getThumb());
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
        if (tvshowEpisodeDTO != null && StringUtils.isEmpty(tvshowEpisodeDTO.getThumb())) {
            return;
        }
        String fileName = videoPath.getFileName().toString();
        String thumbFileName = FilenameUtils.getBaseName(fileName) + "-thumb.jpg";
        HttpUtil.downloadFile(tvshowEpisodeDTO.getThumb(), videoPath.resolveSibling(thumbFileName).toFile());
        log.info("== 下载单集图片: {}", tvshowEpisodeDTO.getThumb());
    }

    private void exportTvshowNFO(TvshowShowDTO tvshowShowDTO, Path tvshowPath) {
        TvshowSeasonDTO firstSeason = tvshowShowDTO.getFirstSeason();
        TvshowNFO tvshowNFO = NFOUtil.toTvshowNFO(tvshowShowDTO, firstSeason);
        NFOUtil.write(tvshowNFO, TvshowNFO.class, tvshowPath, KaleidoConstants.TVSHOW_SHOW_NFO);
        log.info("== 生成剧集NFO文件: {}", tvshowPath.resolve(KaleidoConstants.TVSHOW_SHOW_NFO));
    }

    private void exportSeasonNFO(TvshowShowDTO tvshowShowDTO, TvshowSeasonDTO tvshowSeasonDTO, Path seasonPath) {
        SeasonNFO seasonNFO = NFOUtil.toSeasonNFO(tvshowShowDTO, tvshowSeasonDTO);
        NFOUtil.write(seasonNFO, SeasonNFO.class, seasonPath, KaleidoConstants.TVSHOW_SEASON_NFO);
        log.info("== 生成单季NFO文件: {}", seasonPath.resolve(KaleidoConstants.TVSHOW_SEASON_NFO));
    }

    private void exportEpisodeNFO(TvshowShowDTO tvshowShowDTO, TvshowSeasonDTO tvshowSeasonDTO, TvshowEpisodeDTO tvshowEpisodeDTO, Path videoPath) {
        String fileName = videoPath.getFileName().toString();
        EpisodeNFO episodeNFO = NFOUtil.toEpisodeNFO(tvshowShowDTO, tvshowSeasonDTO, tvshowEpisodeDTO);
        String nfoFileName = FilenameUtils.getBaseName(fileName) + ".nfo";
        NFOUtil.write(episodeNFO, EpisodeNFO.class, videoPath.getParent(), nfoFileName);
        log.info("== 生成单集NFO文件: {}", videoPath.resolveSibling(nfoFileName));
    }

    private Path createShowPath(TvshowShowDTO tvshowShowDTO) throws IOException {
        String folderName = KaleidoUtils.genTvshowFolder(tvshowShowDTO);
        Path folderPath = KaleidoUtils.getTvshowPath(folderName);
        KaleidoUtils.createFolderPath(folderPath);
        log.info("== 创建剧集文件夹: {}", folderPath);
        return folderPath;
    }

    private Path createSeasonPath(Path showPath, Integer seasonIndex) throws IOException {
        String folderName = KaleidoUtils.genSeasonFolder(seasonIndex);
        Path folderPath = showPath.resolve(folderName);
        KaleidoUtils.createFolderPath(folderPath);
        log.info("== 创建季文件夹: {}", folderPath);
        return folderPath;
    }

}
