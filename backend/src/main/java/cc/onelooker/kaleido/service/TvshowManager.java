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
import cc.onelooker.kaleido.utils.KaleidoUtil;
import cc.onelooker.kaleido.utils.NioFileUtil;
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
            tvshowShowDTO.setSortTitle(KaleidoUtil.genSortTitle(tvshowShowDTO.getTitle()));
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
        tvshowSeasonDTO.setSortTitle(KaleidoUtil.genSortTitle(tvshowSeasonDTO.getTitle()));
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
            NioFileUtil.deleteByFilter(path, "nfo");
            TvshowNFO tvshowNFO = new TvshowNFO();
            tvshowNFO.setDoubanId(tvshow.getDoubanId());
            Path importPath = KaleidoUtil.getTvshowImportPath();
            String filename = FilenameUtils.getBaseName(path.getFileName().toString());
            if (StringUtils.isNotEmpty(tvshow.getTitle()) && (StringUtils.isNotEmpty(tvshow.getDoubanId()) || StringUtils.isNotEmpty(tvshow.getTmdbId()))) {
                filename = tvshow.getTitle() + "(" + StringUtils.defaultIfEmpty(tvshow.getDoubanId(), tvshow.getTmdbId()) + ")";
            }
            Path newPath = importPath.resolve(filename);
            if (Files.isDirectory(path)) {
                //如果是文件夹，则写入nfo再改名
                if (!StringUtils.equals(newPath.toString(), path.toString())) {
                    NioFileUtil.renameDir(path, newPath, StandardCopyOption.REPLACE_EXISTING);
                }
                NFOUtil.write(tvshowNFO, TvshowNFO.class, newPath, KaleidoConstants.SHOW_NFO);
            } else {
                //新建文件夹，写入nfo再移入文件夹
                if (Files.notExists(newPath)) {
                    Files.createDirectories(newPath);
                }
                NFOUtil.write(tvshowNFO, TvshowNFO.class, newPath, KaleidoConstants.SHOW_NFO);
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
        if (Files.exists(path.resolve(KaleidoConstants.SHOW_NFO))) {
            try {
                log.info("== 开始更新数据文件: {}", path);
                Tvshow tvshow = findTmmTvshowByNFO(path);
                if (tvshow != null) {
                    log.info("== 查询到剧集信息: {}", tvshow.getTitle());
                    TvshowShowDTO tvshowShowDTO = TmmUtil.toTvshowShow(tvshow);
                    operationPath(tvshowShowDTO, path);
                } else {
                    log.info("== 未找到匹配信息，直接移动文件");
                    Path tvshowLibraryPath = KaleidoUtil.getTvshowLibraryPath();
                    Path targetPath = tvshowLibraryPath.resolve(path.getFileName());
                    if (Files.notExists(targetPath)) {
                        Files.createDirectories(targetPath);
                    }
                    //默认第1季
                    Path seasonPath = createSeasonPath(targetPath, 1);
                    NioFileUtil.renameDir(path, seasonPath, StandardCopyOption.REPLACE_EXISTING);
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
            if (tvshow == null) {
                return;
            }
            TvshowSeasonDTO tvshowSeasonDTO = tvshowSeasonService.findById(seasonId);
            TvshowShowDTO tvshowShowDTO = tvshowShowService.findById(tvshowSeasonDTO.getShowId());
            String oldPath = tvshowShowDTO.getPath();
            boolean isSame = KaleidoUtil.isSame(tvshowShowDTO, tvshow);
            TmmUtil.toTvshowShow(tvshowShowDTO, tvshow);
            saveShow(tvshowShowDTO);
            Season season = tvshow.getSeason(tvshowSeasonDTO.getSeasonIndex());
            Validate.notNull(season);
            List<TvshowEpisodeDTO> tvshowEpisodeDTOList = tvshowEpisodeService.listBySeasonId(seasonId);
            TmmUtil.toTvshowSeason(tvshowSeasonDTO, season);
            saveSeason(tvshowSeasonDTO);
            for (TvshowEpisodeDTO tvshowEpisodeDTO : tvshowEpisodeDTOList) {
                Episode episode = season.getEpisode(tvshowEpisodeDTO.getEpisodeIndex());
                TmmUtil.toTvshowEpisode(tvshowEpisodeDTO, episode);
                saveEpisode(tvshowEpisodeDTO);
            }
            if (!isSame) {
                Path path = KaleidoUtil.getTvshowPath(oldPath);
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
        String newPath = KaleidoUtil.genTvshowFolder(tvshowShowDTO);
        if (StringUtils.isEmpty(tvshowShowDTO.getPath())) {
            TvshowShowDTO existTvshowShowDTO = tvshowShowService.findById(tvshowShowDTO.getId());
            tvshowShowDTO.setPath(existTvshowShowDTO.getPath());
        }
        if (!StringUtils.equals(newPath, tvshowShowDTO.getPath())) {
            Path tvshowPath = KaleidoUtil.getTvshowPath(newPath);
            if (Files.notExists(tvshowPath)) {
                Files.createDirectories(tvshowPath);
            }
            NioFileUtil.renameDir(KaleidoUtil.getTvshowPath(tvshowShowDTO.getPath()), tvshowPath);
            tvshowShowDTO.setPath(newPath);
        }
    }

    private void readNFO(TvshowShowDTO tvshowShowDTO) {
        Path filePath = KaleidoUtil.getTvshowPath(tvshowShowDTO.getPath());
        TvshowNFO tvshowNFO = NFOUtil.read(TvshowNFO.class, filePath, KaleidoConstants.SHOW_NFO);
        if (tvshowNFO == null) {
            return;
        }
        String doubanId = NFOUtil.getUniqueid(tvshowNFO.getUniqueids(), SourceType.douban);
        String imdb = NFOUtil.getUniqueid(tvshowNFO.getUniqueids(), SourceType.imdb);
        String tmdb = NFOUtil.getUniqueid(tvshowNFO.getUniqueids(), SourceType.tmdb);
        //plex可能不会传递year，通过nfo文件补充该值
        tvshowShowDTO.setYear(StringUtils.defaultString(tvshowShowDTO.getYear(), tvshowNFO.getYear()));
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
        String seasonFolder = KaleidoUtil.genSeasonFolder(tvshowSeasonDTO.getSeasonIndex());
        TvshowShowDTO tvshowShowDTO = tvshowShowService.findById(tvshowSeasonDTO.getShowId());
        Path folderPath = KaleidoUtil.getTvshowPath(tvshowShowDTO.getPath());
        Path seasonPath = folderPath.resolve(seasonFolder);
        SeasonNFO seasonNFO = NFOUtil.read(SeasonNFO.class, seasonPath, KaleidoConstants.SEASON_NFO);
        if (seasonNFO == null) {
            return;
        }
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
            TvshowNFO tvshowNFO = NFOUtil.read(TvshowNFO.class, path, KaleidoConstants.SHOW_NFO);
            return tmmApiService.findShow(tvshowNFO.getDoubanId(), tvshowNFO.getImdbId(), tvshowNFO.getTmdbId());
        } catch (Exception e) {
            log.info("== NFO文件无法读取: {}", path.resolve(KaleidoConstants.SHOW_NFO));
        }
        return null;
    }

    private void operationPath(TvshowShowDTO tvshowShowDTO, Path path) {
        try {
            Path showPath = createStandardPath(tvshowShowDTO);
            Set<Integer> seasonIndexSet = Sets.newHashSet();
            Files.list(path).forEach(s -> {
                try {
                    String fileName = s.getFileName().toString();
                    Integer episodeIndex = KaleidoUtil.parseEpisodeIndex(fileName);
                    if (episodeIndex == null) {
                        log.info("== 无法确定集号信息，保持不动: {}", fileName);
                        return;
                    }
                    Integer seasonIndex = KaleidoUtil.parseSeasonIndex(fileName, 1);
                    Path seasonPath = createSeasonPath(showPath, seasonIndex);
                    if (!KaleidoUtil.isVideoFile(fileName)) {
                        Files.move(s, seasonPath.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
                        log.info("== 移动其他文件: {}", fileName);
                        return;
                    } else {
                        //只有视频文件时才检查是否已经存在，防止多次检查
                        moveExistingFilesToRecycleBin(seasonPath, episodeIndex);
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
        if (Files.list(path).noneMatch(s -> KaleidoUtil.isVideoFile(s.getFileName().toString()))) {
            NioFileUtil.deleteIfExists(path);
            log.info("== 源目录已空，进行删除: {}", path);
        }
    }

    private void moveExistingFilesToRecycleBin(Path seasonPath, Integer episodeIndex) throws IOException {
        Files.list(seasonPath).forEach(s -> {
            try {
                String fileName = s.getFileName().toString();
                Integer epIndex = KaleidoUtil.parseEpisodeIndex(fileName);
                if (Objects.equals(episodeIndex, epIndex)) {
                    Path recycleSeasonPath = KaleidoUtil.getTvshowRecyclePath(seasonPath.toString());
                    KaleidoUtil.createFolderPath(recycleSeasonPath);
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
        HttpUtil.downloadFile(tvshowShowDTO.getThumb(), tvshowPath.resolve(KaleidoConstants.POSTER).toFile());
        log.info("== 下载剧集海报: {}", tvshowShowDTO.getThumb());
    }

    private void downloadSeasonPoster(TvshowSeasonDTO tvshowSeasonDTO, Path seasonPath) {
        if (StringUtils.isEmpty(tvshowSeasonDTO.getThumb())) {
            return;
        }
        String filename = KaleidoUtil.genSeasonPosterFilename(tvshowSeasonDTO.getSeasonIndex());
        HttpUtil.downloadFile(tvshowSeasonDTO.getThumb(), seasonPath.resolveSibling(filename).toFile());
        log.info("== 下载单季海报: {}", tvshowSeasonDTO.getThumb());
    }

    private void downloadEpisodePoster(TvshowEpisodeDTO tvshowEpisodeDTO, Path videoPath) {
        if (tvshowEpisodeDTO != null && StringUtils.isEmpty(tvshowEpisodeDTO.getThumb())) {
            return;
        }
        String filename = KaleidoUtil.genEpisodeThumbFilename(videoPath.getFileName().toString());
        HttpUtil.downloadFile(tvshowEpisodeDTO.getThumb(), videoPath.resolveSibling(filename).toFile());
        log.info("== 下载单集图片: {}", tvshowEpisodeDTO.getThumb());
    }

    private void exportTvshowNFO(TvshowShowDTO tvshowShowDTO, Path tvshowPath) {
        TvshowSeasonDTO firstSeason = tvshowShowDTO.getFirstSeason();
        TvshowNFO tvshowNFO = NFOUtil.toTvshowNFO(tvshowShowDTO, firstSeason);
        NFOUtil.write(tvshowNFO, TvshowNFO.class, tvshowPath, KaleidoConstants.SHOW_NFO);
        log.info("== 生成剧集NFO文件: {}", tvshowPath.resolve(KaleidoConstants.SHOW_NFO));
    }

    private void exportSeasonNFO(TvshowShowDTO tvshowShowDTO, TvshowSeasonDTO tvshowSeasonDTO, Path seasonPath) {
        SeasonNFO seasonNFO = NFOUtil.toSeasonNFO(tvshowShowDTO, tvshowSeasonDTO);
        NFOUtil.write(seasonNFO, SeasonNFO.class, seasonPath, KaleidoConstants.SEASON_NFO);
        log.info("== 生成单季NFO文件: {}", seasonPath.resolve(KaleidoConstants.SEASON_NFO));
    }

    private void exportEpisodeNFO(TvshowShowDTO tvshowShowDTO, TvshowSeasonDTO tvshowSeasonDTO, TvshowEpisodeDTO tvshowEpisodeDTO, Path videoPath) {
        String filename = videoPath.getFileName().toString();
        EpisodeNFO episodeNFO = NFOUtil.toEpisodeNFO(tvshowShowDTO, tvshowSeasonDTO, tvshowEpisodeDTO);
        filename = KaleidoUtil.genEpisodeNfoFilename(filename);
        NFOUtil.write(episodeNFO, EpisodeNFO.class, videoPath.getParent(), filename);
        log.info("== 生成单集NFO文件: {}", videoPath.resolveSibling(filename));
    }

    private Path createStandardPath(TvshowShowDTO tvshowShowDTO) throws IOException {
        String folderName = KaleidoUtil.genTvshowFolder(tvshowShowDTO);
        Path folderPath = KaleidoUtil.getTvshowPath(folderName);
        KaleidoUtil.createFolderPath(folderPath);
        log.info("== 创建剧集文件夹: {}", folderPath);
        return folderPath;
    }

    private Path createSeasonPath(Path showPath, Integer seasonIndex) throws IOException {
        String folderName = KaleidoUtil.genSeasonFolder(seasonIndex);
        Path folderPath = showPath.resolve(folderName);
        if (KaleidoUtil.createFolderPath(folderPath)) {
            log.info("== 创建季文件夹: {}", folderPath);
        }
        return folderPath;
    }

}
