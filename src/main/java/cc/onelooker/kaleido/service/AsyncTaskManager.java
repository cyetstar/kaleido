package cc.onelooker.kaleido.service;

import cc.onelooker.kaleido.service.tvshow.TvshowEpisodeService;
import cc.onelooker.kaleido.service.tvshow.TvshowManager;
import cc.onelooker.kaleido.third.plex.GetEpisodes;
import cc.onelooker.kaleido.third.plex.PlexApiService;
import cc.onelooker.kaleido.third.plex.GetMovies;
import cc.onelooker.kaleido.service.movie.MovieBasicService;
import cc.onelooker.kaleido.service.movie.MovieManager;
import cc.onelooker.kaleido.utils.ConfigUtils;
import cc.onelooker.kaleido.utils.NioFileUtils;
import com.zjjcnt.common.core.domain.CommonResult;
import com.zjjcnt.common.core.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;

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
    private MovieManager movieManager;

    @Autowired
    private TvshowManager tvshowManager;

    @Autowired
    private PlexApiService plexApiService;

    @Async
    public void syncPlexMovie() {
        String libraryId = ConfigUtils.getSysConfig("plexMovieLibraryId");
        if (StringUtils.isBlank(libraryId)) {
            throw new ServiceException(2005, "请设置需同步资料库信息");
        }
        //获取最后修改时间
        Long maxUpdatedAt = movieBasicService.findMaxUpdatedAt();
        List<GetMovies.Metadata> metadataList = maxUpdatedAt == null ? plexApiService.listMovie(libraryId) : plexApiService.listMovieByUpdatedAt(libraryId, maxUpdatedAt);
        metadataList.sort(Comparator.comparing(GetMovies.Metadata::getUpdatedAt, Comparator.nullsLast(Comparator.naturalOrder())));
        for (GetMovies.Metadata metadata : metadataList) {
            try {
                movieManager.syncPlexMovieById(metadata.getRatingKey());
                log.info("【{}】ID:{}，同步成功", metadata.getTitle(), metadata.getRatingKey());
            } catch (Exception e) {
                log.error("【{}】ID:{}，同步错误：{}", metadata.getTitle(), metadata.getRatingKey(), e.getMessage());
            }
        }
    }

    @Async
    public void syncPlexTvshow() {
        String libraryId = ConfigUtils.getSysConfig("plexTvshowLibraryId");
        if (StringUtils.isBlank(libraryId)) {
            throw new ServiceException(2005, "请设置需同步资料库信息");
        }
        //获取最后修改时间
        Long maxUpdatedAt = tvshowEpisodeService.findMaxUpdatedAt();
        List<GetEpisodes.Metadata> metadataList = maxUpdatedAt == null ? plexApiService.listEpsiode(libraryId) : plexApiService.listEpsiodeByUpdatedAt(libraryId, maxUpdatedAt);
        metadataList.sort(Comparator.comparing(GetEpisodes.Metadata::getUpdatedAt, Comparator.nullsLast(Comparator.naturalOrder())));
        for (GetEpisodes.Metadata metadata : metadataList) {
            try {
                tvshowManager.syncPlexEpisodeById(metadata.getRatingKey());
                log.info("【{}】ID:{}，同步成功", metadata.getTitle(), metadata.getRatingKey());
            } catch (Exception e) {
                log.error("【{}】ID:{}，同步错误：{}", metadata.getTitle(), metadata.getRatingKey(), e.getMessage());
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
}
