package cc.onelooker.kaleido.thread;

import cc.onelooker.kaleido.enums.ConfigKey;
import cc.onelooker.kaleido.service.MovieManager;
import cc.onelooker.kaleido.third.plex.PlexApiService;
import cc.onelooker.kaleido.utils.ConfigUtils;
import cc.onelooker.kaleido.utils.KaleidoUtils;
import com.zjjcnt.common.core.domain.PageResult;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by cyetstar on 2021/1/7.
 */
@Component
public class MovieUpdateSourceRunnable extends AbstractEntityActionRunnable<Path> {

    private final MovieManager movieManager;

    private final PlexApiService plexApiService;

    public MovieUpdateSourceRunnable(MovieManager movieManager, PlexApiService plexApiService) {
        this.movieManager = movieManager;
        this.plexApiService = plexApiService;
    }

    @Override
    public Action getAction() {
        return Action.movieUpdateSource;
    }

    @Override
    protected void afterRun(@Nullable Map<String, String> params) {
        super.afterRun(params);
        String libraryId = ConfigUtils.getSysConfig(ConfigKey.plexMovieLibraryId);
        plexApiService.refreshLibrary(libraryId);
    }

    @Override
    protected PageResult<Path> page(Map<String, String> params, int pageNumber, int pageSize) {
        try {
            Path importPath = KaleidoUtils.getMovieImportPath();
            PageResult<Path> pageResult = new PageResult<>();
            pageResult.setSearchCount(true);
            if (pageNumber == 1) {
                List<Path> pathList = Files.list(importPath).collect(Collectors.toList());
                pageResult.setTotal((long) pathList.size());
                pageResult.setRecords(pathList);
            }
            return pageResult;
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    protected int processEntity(Map<String, String> params, Path path) throws Exception {
        movieManager.updateSource(path);
        return SUCCESS;
    }

    @Override
    protected String getMessage(Path path, Integer state) {
        return formatMessage(path.getFileName().toString(), state);
    }
}
