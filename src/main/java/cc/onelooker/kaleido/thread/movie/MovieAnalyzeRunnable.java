package cc.onelooker.kaleido.thread.movie;

import cc.onelooker.kaleido.dto.system.SysConfigDTO;
import cc.onelooker.kaleido.enums.ConfigKey;
import cc.onelooker.kaleido.service.movie.MovieManager;
import cc.onelooker.kaleido.service.system.SysConfigService;
import cc.onelooker.kaleido.third.plex.Metadata;
import cc.onelooker.kaleido.third.plex.PlexApiService;
import cc.onelooker.kaleido.thread.AbstractEntityActionRunnable;
import cc.onelooker.kaleido.thread.Action;
import cc.onelooker.kaleido.utils.ConfigUtils;
import com.zjjcnt.common.core.domain.PageResult;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

/**
 * Created by cyetstar on 2021/1/7.
 */
@Component
public class MovieAnalyzeRunnable extends AbstractEntityActionRunnable<Metadata> {

    private final MovieManager movieManager;

    private final PlexApiService plexApiService;

    private final SysConfigService sysConfigService;

    private Long lastAnalyzeTime = 0L;

    private Long maxUpdatedAt = 0L;

    public MovieAnalyzeRunnable(MovieManager movieManager, PlexApiService plexApiService, SysConfigService sysConfigService) {
        this.movieManager = movieManager;
        this.plexApiService = plexApiService;
        this.sysConfigService = sysConfigService;
    }

    @Override
    public Action getAction() {
        return Action.movieAnalyze;
    }

    @Override
    protected void beforeRun(@Nullable Map<String, Object> params) {
        super.beforeRun(params);
        lastAnalyzeTime = Long.parseLong(ConfigUtils.getSysConfig(ConfigKey.lastMovieAnalyzeTime, "0"));
    }

    @Override
    protected void afterRun(@Nullable Map<String, Object> params) {
        super.afterRun(params);
        SysConfigDTO sysConfigDTO = new SysConfigDTO();
        sysConfigDTO.setConfigKey(ConfigKey.lastMovieAnalyzeTime.name());
        sysConfigDTO.setConfigValue(String.valueOf(maxUpdatedAt));
        sysConfigService.save(sysConfigDTO);
    }

    @Override
    protected PageResult<Metadata> page(int pageNumber, int pageSize) {
        PageResult<Metadata> pageResult = new PageResult<>();
        pageResult.setSearchCount(true);
        if (pageNumber == 1) {
            String movieLibraryId = ConfigUtils.getSysConfig(ConfigKey.plexMovieLibraryId);
            List<Metadata> metadataList = plexApiService.listMovieByUpdatedAt(movieLibraryId, lastAnalyzeTime);
            pageResult.setTotal((long) metadataList.size());
            pageResult.setRecords(metadataList);
        }
        return pageResult;
    }

    @Override
    protected void processEntity(Metadata metadata) throws Exception {
        long lastUpdatedAt = metadata.getUpdatedAt();
        if (lastUpdatedAt > lastAnalyzeTime) {
            movieManager.analyze(metadata);
        }
        maxUpdatedAt = lastUpdatedAt > maxUpdatedAt ? lastUpdatedAt : maxUpdatedAt;
    }

}
