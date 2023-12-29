package cc.onelooker.kaleido.thread.movie;

import cc.onelooker.kaleido.service.movie.MovieManager;
import cc.onelooker.kaleido.thread.AbstractEntityActionRunnable;
import cc.onelooker.kaleido.thread.Action;
import cc.onelooker.kaleido.utils.ConfigUtils;
import com.zjjcnt.common.core.domain.PageResult;
import com.zjjcnt.common.core.utils.ApplicationContextHelper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by cyetstar on 2021/1/7.
 */
public class MovieUpdateSourceRunnable extends AbstractEntityActionRunnable<Path> {

    private final MovieManager movieManager;

    private String movieDownloadPath;

    public MovieUpdateSourceRunnable() {
        this.movieManager = ApplicationContextHelper.getBean(MovieManager.class);
    }

    @Override
    public Action getAction() {
        return Action.movieUpdateSource;
    }

    @Override
    protected void beforeRun() {
        movieDownloadPath = ConfigUtils.getSysConfig("movieDownloadPath");
    }

    @Override
    protected PageResult<Path> page(int pageNumber, int pageSize) {
        try {
            PageResult<Path> pageResult = new PageResult<>();
            pageResult.setSearchCount(true);
            if (pageNumber == 1) {
                List<Path> pathList = Files.list(Paths.get(movieDownloadPath)).collect(Collectors.toList());
                pageResult.setTotal((long) pathList.size());
                pageResult.setRecords(pathList);
            }
            return pageResult;
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    protected void processEntity(Path path) throws Exception {
        movieManager.updateMovieSource(path);
    }

}
