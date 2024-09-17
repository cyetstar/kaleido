package cc.onelooker.kaleido.thread;

import cc.onelooker.kaleido.service.ComicManager;
import cc.onelooker.kaleido.utils.KaleidoUtils;
import com.zjjcnt.common.core.domain.PageResult;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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

    private final ComicManager comicManager;
    private Path importPath;

    public ComicUpdateSourceRunnable(ComicManager comicManager) {
        this.comicManager = comicManager;
    }

    @Override
    public Action getAction() {
        return Action.comicUpdateSource;
    }

    @Override
    protected void beforeRun(Map<String, String> params) {
        importPath = KaleidoUtils.getComicImportPath();
    }

    @Override
    protected PageResult<Path> page(Map<String, String> params, int pageNumber, int pageSize) {
        try {
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
        comicManager.updateSource(path);
        return SUCCESS;
    }

    @Override
    protected String getMessage(Path path, Integer state) {
        return formatMessage(path.getFileName().toString(), state);
    }

}
