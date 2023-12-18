package cc.onelooker.kaleido.service;

import cc.onelooker.kaleido.dto.movie.MovieBasicDTO;
import cc.onelooker.kaleido.dto.movie.MovieCollectionDTO;
import cc.onelooker.kaleido.dto.music.MusicAlbumDTO;
import cc.onelooker.kaleido.dto.tvshow.TvshowEpisodeDTO;
import cc.onelooker.kaleido.service.movie.MovieBasicCollectionService;
import cc.onelooker.kaleido.service.movie.MovieCollectionService;
import cc.onelooker.kaleido.service.music.MusicAlbumService;
import cc.onelooker.kaleido.service.music.MusicManager;
import cc.onelooker.kaleido.service.music.impl.MusicAlbumServiceImpl;
import cc.onelooker.kaleido.service.tvshow.TvshowEpisodeService;
import cc.onelooker.kaleido.service.tvshow.TvshowManager;
import cc.onelooker.kaleido.third.plex.Metadata;
import cc.onelooker.kaleido.third.plex.PlexApiService;
import cc.onelooker.kaleido.service.movie.MovieBasicService;
import cc.onelooker.kaleido.service.movie.MovieManager;
import cc.onelooker.kaleido.utils.ConfigUtils;
import cc.onelooker.kaleido.utils.NioFileUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zjjcnt.common.core.domain.PageResult;
import com.zjjcnt.common.core.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author xiadawei
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
    private MovieCollectionService movieCollectionService;

    @Autowired
    private MusicAlbumService musicAlbumService;

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
        String libraryId = ConfigUtils.getSysConfig("plexMovieLibraryId");
        if (StringUtils.isBlank(libraryId)) {
            throw new ServiceException(2005, "请设置需同步资料库信息");
        }
        List<Metadata> metadataList = plexApiService.listMovie(libraryId);
        //根据更新时间，判断是否需要更新
        for (Metadata metadata : metadataList) {
            try {
                MovieBasicDTO movieBasicDTO = movieBasicService.findById(metadata.getRatingKey());
                if (movieBasicDTO == null) {
                    movieManager.syncPlexMovieAndReadNFO(metadata.getRatingKey());
                    log.info("【{}】ID:{}，同步成功", metadata.getTitle(), metadata.getRatingKey());
                } else if (metadata.getUpdatedAt().compareTo(movieBasicDTO.getUpdatedAt()) > 0) {
                    movieManager.syncPlexMovieById(metadata.getRatingKey());
                    log.info("【{}】ID:{}，同步成功", metadata.getTitle(), metadata.getRatingKey());
                }
            } catch (Exception e) {
                log.error("【{}】ID:{}，同步错误：{}", metadata.getTitle(), metadata.getRatingKey(), e.getMessage());
            }
        }
        //删除plex已经不存在记录
        List<MovieBasicDTO> movieBasicDTOList = movieBasicService.list(null);
        List<Long> idList = movieBasicDTOList.stream().map(MovieBasicDTO::getId).collect(Collectors.toList());
        List<Long> plexIdList = metadataList.stream().map(Metadata::getRatingKey).collect(Collectors.toList());
        Collection<Long> deleteIdList = CollectionUtils.subtract(idList, plexIdList);
        if (CollectionUtils.isNotEmpty(deleteIdList)) {
            for (Long deleteId : deleteIdList) {
                movieBasicService.deleteById(deleteId);
                log.info("ID:{}，删除成功", deleteId);
            }
        }
    }

    @Async
    public void syncPlexMovieCollection() {
        String libraryId = ConfigUtils.getSysConfig("plexMovieLibraryId");
        if (StringUtils.isBlank(libraryId)) {
            throw new ServiceException(2005, "请设置需同步资料库信息");
        }
        List<Metadata> metadataList = plexApiService.listCollection(libraryId);
        //根据更新时间，判断是否需要更新
        for (Metadata metadata : metadataList) {
            try {
                MovieCollectionDTO movieCollectionDTO = movieCollectionService.findById(metadata.getRatingKey());
                if (movieCollectionDTO == null) {
                    movieManager.syncPlexMovieCollection(metadata);
                    log.info("【{}】ID:{}，同步成功", metadata.getTitle(), metadata.getRatingKey());
                } else if (movieCollectionDTO.getUpdatedAt() == null || metadata.getUpdatedAt().compareTo(movieCollectionDTO.getUpdatedAt()) > 0) {
                    movieManager.syncPlexMovieCollection(metadata);
                    log.info("【{}】ID:{}，同步成功", metadata.getTitle(), metadata.getRatingKey());
                }
            } catch (Exception e) {
                log.error("【{}】ID:{}，同步错误：{}", metadata.getTitle(), metadata.getRatingKey(), e.getMessage());
            }
        }
        //删除plex已经不存在记录
        List<MovieCollectionDTO> movieCollectionDTOList = movieCollectionService.list(null);
        List<Long> idList = movieCollectionDTOList.stream().map(MovieCollectionDTO::getId).collect(Collectors.toList());
        List<Long> plexIdList = metadataList.stream().map(Metadata::getRatingKey).collect(Collectors.toList());
        Collection<Long> deleteIdList = CollectionUtils.subtract(idList, plexIdList);
        if (CollectionUtils.isNotEmpty(deleteIdList)) {
            for (Long deleteId : deleteIdList) {
                movieCollectionService.deleteById(deleteId);
                log.info("ID:{}，删除成功", deleteId);
            }
        }
    }

    @Async
    public void syncPlexTvshow() {
        String libraryId = ConfigUtils.getSysConfig("plexTvshowLibraryId");
        if (StringUtils.isBlank(libraryId)) {
            throw new ServiceException(2005, "请设置需同步资料库信息");
        }
        List<Metadata> metadataList = plexApiService.listEpsiode(libraryId);
        for (Metadata metadata : metadataList) {
            try {
                TvshowEpisodeDTO tvshowEpisodeDTO = tvshowEpisodeService.findById(metadata.getRatingKey());
                if (tvshowEpisodeDTO == null) {
                    tvshowManager.syncPlexEpisodeById(metadata.getRatingKey());
                    log.info("【{}】ID:{}，同步成功", metadata.getTitle(), metadata.getRatingKey());
                } else if (metadata.getUpdatedAt().compareTo(tvshowEpisodeDTO.getUpdatedAt()) > 0) {
                    tvshowManager.syncPlexEpisodeById(metadata.getRatingKey());
                    log.info("【{}】ID:{}，同步成功", metadata.getTitle(), metadata.getRatingKey());
                }
            } catch (Exception e) {
                log.error("【{}】ID:{}，同步错误：{}", metadata.getTitle(), metadata.getRatingKey(), e.getMessage());
            }
        }

        List<TvshowEpisodeDTO> tvshowEpisodeDTOList = tvshowEpisodeService.list(null);
        List<Long> idList = tvshowEpisodeDTOList.stream().map(TvshowEpisodeDTO::getId).collect(Collectors.toList());
        List<Long> plexIdList = metadataList.stream().map(Metadata::getRatingKey).collect(Collectors.toList());
        Collection<Long> deleteIdList = CollectionUtils.subtract(idList, plexIdList);
        if (CollectionUtils.isNotEmpty(deleteIdList)) {
            for (Long deleteId : deleteIdList) {
                tvshowEpisodeService.deleteById(deleteId);
                log.info("ID:{}，删除成功", deleteId);
            }
        }
    }

    @Async
    public void syncPlexAlbum() {
        String libraryId = ConfigUtils.getSysConfig("plexMusicLibraryId");
        if (StringUtils.isBlank(libraryId)) {
            throw new ServiceException(2005, "请设置需要同步资料库信息");
        }
        String libraryPath = plexApiService.getLibraryPath(libraryId);
        List<Metadata> metadataList = plexApiService.listAlbum(libraryId);
        for (Metadata metadata : metadataList) {
            try {
                MusicAlbumDTO musicAlbumDTO = musicAlbumService.findById(metadata.getRatingKey());
                if (musicAlbumDTO == null) {
                    musicManager.syncPlexAlbumById(libraryPath, metadata.getRatingKey());
                    log.info("【{}】ID:{}，同步成功", metadata.getTitle(), metadata.getRatingKey());
                } else if (metadata.getUpdatedAt().compareTo(musicAlbumDTO.getUpdatedAt()) > 0) {
                    musicManager.syncPlexAlbumById(libraryPath, metadata.getRatingKey());
                    log.info("【{}】ID:{}，同步成功", metadata.getTitle(), metadata.getRatingKey());
                }
            } catch (Exception e) {
                log.error("【{}】ID:{}，同步错误：{}", metadata.getTitle(), metadata.getRatingKey(), e.getMessage());
            }
        }

        List<MusicAlbumDTO> musicAlbumDTOList = musicAlbumService.list(null);
        List<Long> idList = musicAlbumDTOList.stream().map(MusicAlbumDTO::getId).collect(Collectors.toList());
        List<Long> plexIdList = metadataList.stream().map(Metadata::getRatingKey).collect(Collectors.toList());
        Collection<Long> deleteIdList = CollectionUtils.subtract(idList, plexIdList);
        if (CollectionUtils.isNotEmpty(deleteIdList)) {
            for (Long deleteId : deleteIdList) {
                musicAlbumService.deleteById(deleteId);
                log.info("ID:{}，删除成功", deleteId);
            }
        }
    }

    @Async
    public void readNFO() {
        List<Long> movieIdList = listMovieId();
        for (Long movieId : movieIdList) {
            try {
                movieManager.readNFOById(movieId);
            } catch (Exception e) {
                log.error("ID:{}，读取NFO错误：{}", movieId, e.getMessage());
            }
        }
    }

    @Async
    public void moveFile(List<String> sourcePathList, String targetPath) throws IOException {
        for (String path : sourcePathList) {
            if (Files.isDirectory(Paths.get(path))) {
                NioFileUtils.moveDir(Paths.get(path), Paths.get(targetPath));
            } else {
                Files.move(Paths.get(path), Paths.get(targetPath));
            }
        }
    }

    private List<Long> listMovieId() {
        int pageNumber = 1;
        List<Long> idList = Lists.newArrayList();
        while (true) {
            PageResult<MovieBasicDTO> page = movieBasicService.page(null, Page.of(pageNumber, 1000));
            if (page.isEmpty()) {
                break;
            }
            idList.addAll(page.getRecords().stream().map(MovieBasicDTO::getId).collect(Collectors.toList()));
            pageNumber++;
        }
        return idList;
    }
}
