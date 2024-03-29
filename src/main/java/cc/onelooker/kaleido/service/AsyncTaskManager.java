package cc.onelooker.kaleido.service;

import cc.onelooker.kaleido.dto.movie.MovieBasicDTO;
import cc.onelooker.kaleido.dto.movie.MovieThreadDTO;
import cc.onelooker.kaleido.dto.tvshow.TvshowEpisodeDTO;
import cc.onelooker.kaleido.enums.ConfigKey;
import cc.onelooker.kaleido.enums.ThreadStatus;
import cc.onelooker.kaleido.service.movie.MovieBasicService;
import cc.onelooker.kaleido.service.movie.MovieManager;
import cc.onelooker.kaleido.service.movie.MovieThreadService;
import cc.onelooker.kaleido.service.music.MusicAlbumService;
import cc.onelooker.kaleido.service.music.MusicManager;
import cc.onelooker.kaleido.service.tvshow.TvshowEpisodeService;
import cc.onelooker.kaleido.service.tvshow.TvshowManager;
import cc.onelooker.kaleido.third.plex.Metadata;
import cc.onelooker.kaleido.third.plex.PlexApiService;
import cc.onelooker.kaleido.utils.ConfigUtils;
import cc.onelooker.kaleido.utils.NioFileUtils;
import cn.hutool.core.exceptions.ExceptionUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zjjcnt.common.core.domain.PageResult;
import com.zjjcnt.common.core.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Duration;
import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author cyetstar
 * @Date 2023-12-03 01:31:00
 * @Description TODO
 */
@Slf4j
@Component
public class AsyncTaskManager {

    @Autowired
    private MovieBasicService movieBasicService;

    @Autowired
    private TvshowEpisodeService tvshowEpisodeService;

    @Autowired
    private MusicAlbumService musicAlbumService;

    @Autowired
    private MovieThreadService movieThreadService;

    @Autowired
    private MovieManager movieManager;

    @Autowired
    private TvshowManager tvshowManager;

    @Autowired
    private MusicManager musicManager;

    @Autowired
    private PlexApiService plexApiService;

    @Async
    public void syncPlexMovie() {
        String libraryId = ConfigUtils.getSysConfig(ConfigKey.plexMovieLibraryId);
        if (StringUtils.isBlank(libraryId)) {
            throw new ServiceException(2005, "请设置需同步资料库信息");
        }
        Instant start = Instant.now();
        try {
            log.info("同步Plex电影资料库启动。");
            int count = 0;
            List<Metadata> metadataList = plexApiService.listMovie(libraryId);
            for (Metadata metadata : metadataList) {
                try {
                    MovieBasicDTO movieBasicDTO = movieBasicService.findById(metadata.getRatingKey());
                    if (movieBasicDTO == null) {
                        movieManager.syncMovieAndReadNFO(metadata.getRatingKey());
                        count++;
                        log.debug("【{}】{} 同步成功。", metadata.getTitle(), metadata.getRatingKey());
                    } else if (metadata.getUpdatedAt().compareTo(movieBasicDTO.getUpdatedAt()) > 0) {
                        movieManager.syncMovie(metadata.getRatingKey());
                        count++;
                        log.debug("【{}】{} 同步成功。", metadata.getTitle(), metadata.getRatingKey());
                    }
                } catch (Exception e) {
                    log.error("【{}】{} 同步发生错误。{}", metadata.getTitle(), metadata.getRatingKey(), ExceptionUtil.getMessage(e));
                }
            }
            log.info("同步Plex电影资料库，更新{}条记录。", count);
            //删除plex已经不存在记录
            List<MovieBasicDTO> movieBasicDTOList = movieBasicService.list(null);
            List<Long> idList = movieBasicDTOList.stream().map(MovieBasicDTO::getId).collect(Collectors.toList());
            List<Long> plexIdList = metadataList.stream().map(Metadata::getRatingKey).collect(Collectors.toList());
            Collection<Long> deleteIdList = CollectionUtils.subtract(idList, plexIdList);
            count = 0;
            if (CollectionUtils.isNotEmpty(deleteIdList)) {
                for (Long deleteId : deleteIdList) {
                    movieBasicService.deleteById(deleteId);
                    count++;
                }
            }
            log.info("同步Plex电影资料库，删除{}条记录。", count);
        } finally {
            Instant end = Instant.now();
            log.info("同步Plex电影资料库完毕，耗时{}分钟。", Duration.between(start, end).toMinutes());
        }
    }

    @Async
    public void syncPlexTvshow() {
        String libraryId = ConfigUtils.getSysConfig(ConfigKey.plexTvshowLibraryId);
        if (StringUtils.isBlank(libraryId)) {
            throw new ServiceException(2005, "请设置需同步资料库信息");
        }
        Instant start = Instant.now();
        try {
            log.info("同步Plex剧集资料库启动。");
            int count = 0;
            List<Metadata> metadataList = plexApiService.listEpsiode(libraryId);
            for (Metadata metadata : metadataList) {
                try {
                    TvshowEpisodeDTO tvshowEpisodeDTO = tvshowEpisodeService.findById(metadata.getRatingKey());
                    if (tvshowEpisodeDTO == null) {
                        tvshowManager.syncPlexEpisode(metadata.getRatingKey());
                        count++;
                        log.debug("【{}】{} 同步成功。", metadata.getTitle(), metadata.getRatingKey());
                    } else if (metadata.getUpdatedAt().compareTo(tvshowEpisodeDTO.getUpdatedAt()) > 0) {
                        tvshowManager.syncPlexEpisode(metadata.getRatingKey());
                        count++;
                        log.debug("【{}】{} 同步成功。", metadata.getTitle(), metadata.getRatingKey());
                    }
                } catch (Exception e) {
                    log.error("【{}】{} 同步发生错误。{}", metadata.getTitle(), metadata.getRatingKey(), ExceptionUtil.getMessage(e));
                }
            }
            log.info("同步Plex剧集资料库，更新{}条记录。", count);
            count = 0;
            List<TvshowEpisodeDTO> tvshowEpisodeDTOList = tvshowEpisodeService.list(null);
            List<Long> idList = tvshowEpisodeDTOList.stream().map(TvshowEpisodeDTO::getId).collect(Collectors.toList());
            List<Long> plexIdList = metadataList.stream().map(Metadata::getRatingKey).collect(Collectors.toList());
            Collection<Long> deleteIdList = CollectionUtils.subtract(idList, plexIdList);
            if (CollectionUtils.isNotEmpty(deleteIdList)) {
                for (Long deleteId : deleteIdList) {
                    tvshowEpisodeService.deleteById(deleteId);
                    count++;
                }
            }
            log.info("同步Plex剧集资料库，删除{}条记录。", count);
        } finally {
            Instant end = Instant.now();
            log.info("同步Plex剧集资料库完毕，耗时{}分钟。", Duration.between(start, end).toMinutes());
        }
    }

    @Async
    public void readNFO() {
        Instant start = Instant.now();
        try {
            log.info("读取NFO启动");
            List<MovieBasicDTO> movieBasicDTOList = movieBasicService.list(null);
            for (MovieBasicDTO movieBasicDTO : movieBasicDTOList) {
                try {
                    movieManager.readNFO(movieBasicDTO.getId());
                    log.debug("【{}】{} 读取NFO成功。", movieBasicDTO.getTitle(), movieBasicDTO.getId());
                } catch (Exception e) {
                    log.error("【{}】{} 读取NFO发生错误。{}", movieBasicDTO.getTitle(), movieBasicDTO.getId(), ExceptionUtil.getMessage(e));
                }
            }
        } finally {
            Instant end = Instant.now();
            log.info("读取NFO完毕，耗时{}分钟", Duration.between(start, end).toMinutes());
        }

    }

    @Async
    public void moveFile(List<String> sourcePathList, String targetPath) throws IOException {
        for (String path : sourcePathList) {
            if (Files.isDirectory(Paths.get(path))) {
                NioFileUtils.moveDir(Paths.get(path), Paths.get(targetPath), StandardCopyOption.REPLACE_EXISTING);
            } else {
                Files.move(Paths.get(path), Paths.get(targetPath), StandardCopyOption.REPLACE_EXISTING);
            }
        }
    }

    @Async
    public void checkThreadStatus() {
        Instant start = Instant.now();
        try {
            log.info("检测发布信息状态启动。");
            MovieThreadDTO param = new MovieThreadDTO();
            param.setStatus(ThreadStatus.todo.ordinal());
            int pageNumber = 1;
            while (true) {
                PageResult<MovieThreadDTO> page = movieThreadService.page(param, Page.of(pageNumber, 1000));
                if (page.isEmpty()) {
                    break;
                }
                List<MovieThreadDTO> records = page.getRecords();
                for (MovieThreadDTO movieThreadDTO : records) {
                    try {
                        movieManager.checkThreadStatus(movieThreadDTO);
                        log.debug("【{}】检测完成。", movieThreadDTO.getTitle());
                    } catch (Exception e) {
                        log.error("【{}】检测发生错误。{}", movieThreadDTO.getTitle(), ExceptionUtil.getMessage(e));
                    }
                }
                pageNumber++;
            }
        } finally {
            Instant end = Instant.now();
            log.info("检测发布信息状态完毕，耗时{}分钟。", Duration.between(start, end).toMinutes());
        }
    }

}
