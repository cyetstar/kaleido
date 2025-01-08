package cc.onelooker.kaleido.thread;

import cc.onelooker.kaleido.enums.ConfigKey;
import cc.onelooker.kaleido.service.TvshowManager;
import cc.onelooker.kaleido.third.plex.PlexApiService;
import cc.onelooker.kaleido.utils.ConfigUtils;
import cc.onelooker.kaleido.utils.KaleidoConstants;
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
 * Created by cyetstar on 2021/1/7.
 */
@Component
public class TvshowUpdateSourceRunnable extends AbstractEntityActionRunnable<Path> {

    private final TvshowManager tvshowManager;

    private final PlexApiService plexApiService;

    public TvshowUpdateSourceRunnable(TvshowManager tvshowManager, PlexApiService plexApiService) {
        this.tvshowManager = tvshowManager;
        this.plexApiService = plexApiService;
    }

    @Override
    public Action getAction() {
        return Action.tvshowUpdateSource;
    }

    @Override
    protected void afterRun(@Nullable Map<String, String> params) {
        super.afterRun(params);
        String libraryId = ConfigUtils.getSysConfig(ConfigKey.plexTvshowLibraryId);
        plexApiService.refreshLibrary(libraryId);
    }

    @Override
    protected PageResult<Path> page(Map<String, String> params, int pageNumber, int pageSize) {
        try {
            Path importPath = KaleidoUtil.getTvshowImportPath();
            PageResult<Path> pageResult = new PageResult<>();
            pageResult.setSearchCount(true);
            if (pageNumber == 1) {
                List<Path> pathList = Files.list(importPath).filter(
                                path -> Files.isDirectory(path) && Files.exists(path.resolve(KaleidoConstants.SHOW_NFO)))
                        .collect(Collectors.toList());
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
        tvshowManager.updateSource(path);
        return SUCCESS;
    }

    @Override
    protected String getMessage(Path path, Integer state) {
        return formatMessage(path.getFileName().toString(), state);
    }
}
