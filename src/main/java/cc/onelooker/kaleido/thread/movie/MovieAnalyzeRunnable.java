package cc.onelooker.kaleido.thread.movie;

import cc.onelooker.kaleido.enums.ConfigKey;
import cc.onelooker.kaleido.service.movie.MovieManager;
import cc.onelooker.kaleido.thread.AbstractEntityActionRunnable;
import cc.onelooker.kaleido.thread.Action;
import cc.onelooker.kaleido.utils.ConfigUtils;
import com.zjjcnt.common.core.domain.PageResult;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by cyetstar on 2021/1/7.
 */
@Component
public class MovieAnalyzeRunnable extends AbstractEntityActionRunnable<Path> {

    private final MovieManager movieManager;

    public MovieAnalyzeRunnable(MovieManager movieManager) {
        this.movieManager = movieManager;
    }

    @Override
    public Action getAction() {
        return Action.movieAnalyze;
    }

    @Override
    protected PageResult<Path> page(int pageNumber, int pageSize) {
        try {
            PageResult<Path> pageResult = new PageResult<>();
            pageResult.setSearchCount(true);
            if (pageNumber == 1) {
                Path libraryPath = Paths.get(ConfigUtils.getSysConfig(ConfigKey.movieLibraryPath));
                Path trashPath = Paths.get(ConfigUtils.getSysConfig(ConfigKey.movieTrashPath));
                Path downloadPath = Paths.get(ConfigUtils.getSysConfig(ConfigKey.movieDownloadPath));
                Path[] excludePaths = {libraryPath, trashPath, downloadPath};
                try (Stream<Path> stream = Files.walk(libraryPath, 2)) {
                    List<Path> pathList = stream.filter(s -> !ArrayUtils.contains(excludePaths, s.getParent())).
                            collect(Collectors.toList());
                    pageResult.setTotal((long) pathList.size());
                    pageResult.setRecords(pathList);
                }
            }
            return pageResult;
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    protected void processEntity(Path path) throws Exception {
        movieManager.analyze(path);
    }

    @Override
    protected String getMessage(Path path) {
        return path.getFileName().toString();
    }
}
