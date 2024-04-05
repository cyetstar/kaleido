package cc.onelooker.kaleido.thread;

import cc.onelooker.kaleido.enums.ConfigKey;
import cc.onelooker.kaleido.service.ComicManager;
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
 * @Author xiadawei
 * @Date 2024-03-24 19:13:00
 * @Description TODO
 */
@Component
public class ComicUpdateSourceRunnable extends AbstractEntityActionRunnable<Path> {

    private String downloadPath;

    private final ComicManager comicManager;

    public ComicUpdateSourceRunnable(ComicManager comicManager) {
        this.comicManager = comicManager;
    }

    @Override
    public Action getAction() {
        return Action.comicUpdateSource;
    }

    @Override
    protected void beforeRun(Map<String, String> params) {
        downloadPath = ConfigUtils.getSysConfig(ConfigKey.comicDownloadPath);
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
        comicManager.updateSource(path);
    }

    @Override
    protected String getMessage(Path path) {
        return path.getFileName().toString();
    }

}
