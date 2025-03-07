package cc.onelooker.kaleido.thread;

import cc.onelooker.kaleido.enums.ConfigKey;
import cc.onelooker.kaleido.service.MusicManager;
import cc.onelooker.kaleido.third.plex.PlexApiService;
import cc.onelooker.kaleido.utils.ConfigUtils;
import cc.onelooker.kaleido.utils.KaleidoUtil;
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
 * @Author xiadawei
 * @Date 2024-02-01 14:06:00
 * @Description TODO
 */
@Component
public class MusicUpdateSourceRunnable extends AbstractEntityActionRunnable<Path> {

    private final MusicManager musicManager;

    private final PlexApiService plexApiService;

    public MusicUpdateSourceRunnable(MusicManager musicManager, PlexApiService plexApiService) {
        this.musicManager = musicManager;
        this.plexApiService = plexApiService;
    }

    @Override
    public Action getAction() {
        return Action.musicUpdateSource;
    }

    @Override
    protected void afterRun(@Nullable Map<String, String> params) {
        super.afterRun(params);
        String libraryId = ConfigUtils.getSysConfig(ConfigKey.plexMusicLibraryId);
        plexApiService.refreshLibrary(libraryId);
    }

    @Override
    protected PageResult<Path> page(Map<String, String> params, int pageNumber, int pageSize) {
        try {
            Path importPath = KaleidoUtil.getMusicImportPath();
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
        musicManager.updateSource(path);
        return SUCCESS;
    }

    @Override
    protected String getMessage(Path path, Integer state) {
        return formatMessage(path.getFileName().toString(), state);
    }

}
