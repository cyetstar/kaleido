package cc.onelooker.kaleido.thread;

import cc.onelooker.kaleido.enums.ConfigKey;
import cc.onelooker.kaleido.service.movie.MovieManager;
import cc.onelooker.kaleido.utils.ConfigUtils;
import com.zjjcnt.common.core.domain.PageResult;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by cyetstar on 2021/1/7.
 */
@Component
public class MovieUpdateSourceRunnable extends AbstractEntityActionRunnable<Path> {

    private final MovieManager movieManager;

    private String downloadPath;

    public MovieUpdateSourceRunnable(MovieManager movieManager) {
        this.movieManager = movieManager;
    }

    @Override
    public Action getAction() {
        return Action.movieUpdateSource;
    }

    @Override
    protected void beforeRun(Map<String, String> params) {
        downloadPath = ConfigUtils.getSysConfig(ConfigKey.movieDownloadPath);
    }

    @Override
    protected PageResult<Path> page(Map<String, String> params, int pageNumber, int pageSize) {
        try {
            PageResult<Path> pageResult = new PageResult<>();
            pageResult.setSearchCount(true);
            if (pageNumber == 1) {
                List<Path> pathList = Files.list(Paths.get(downloadPath)).collect(Collectors.toList());
                pageResult.setTotal((long) pathList.size());
                pageResult.setRecords(pathList);
            }
            return pageResult;
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    protected void processEntity(Map<String, String> params, Path path) throws Exception {
        movieManager.updateSource(path);
    }

    @Override
    protected String getMessage(Path path) {
        return path.getFileName().toString();
    }
}
