package cc.onelooker.kaleido.thread.tvshow;

import cc.onelooker.kaleido.enums.ConfigKey;
import cc.onelooker.kaleido.service.tvshow.TvshowManager;
import cc.onelooker.kaleido.thread.AbstractEntityActionRunnable;
import cc.onelooker.kaleido.thread.Action;
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
public class TvshowUpdateSourceRunnable extends AbstractEntityActionRunnable<Path> {

    private final TvshowManager tvshowManager;

    private String downloadPath;

    public TvshowUpdateSourceRunnable(TvshowManager tvshowManager) {
        this.tvshowManager = tvshowManager;
    }

    @Override
    public Action getAction() {
        return Action.tvshowUpdateSource;
    }

    @Override
    protected void beforeRun(Map<String, String> params) {
        downloadPath = ConfigUtils.getSysConfig(ConfigKey.tvshowDownloadPath);
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
    protected void processEntity(Path path) throws Exception {
        tvshowManager.updateSource(path);
    }

    @Override
    protected String getMessage(Path path) {
        return path.getFileName().toString();
    }
}
