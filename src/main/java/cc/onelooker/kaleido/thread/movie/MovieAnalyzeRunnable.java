package cc.onelooker.kaleido.thread.movie;

import cc.onelooker.kaleido.convert.movie.MovieBasicConvert;
import cc.onelooker.kaleido.dto.movie.MovieBasicDTO;
import cc.onelooker.kaleido.dto.system.SysConfigDTO;
import cc.onelooker.kaleido.enums.ConfigKey;
import cc.onelooker.kaleido.service.movie.MovieBasicService;
import cc.onelooker.kaleido.service.movie.MovieManager;
import cc.onelooker.kaleido.service.system.SysConfigService;
import cc.onelooker.kaleido.third.plex.Metadata;
import cc.onelooker.kaleido.third.plex.PlexApiService;
import cc.onelooker.kaleido.thread.AbstractEntityActionRunnable;
import cc.onelooker.kaleido.thread.Action;
import cc.onelooker.kaleido.utils.ConfigUtils;
import com.zjjcnt.common.core.domain.PageResult;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by cyetstar on 2021/1/7.
 */
@Component
public class MovieAnalyzeRunnable extends AbstractEntityActionRunnable<Metadata> {

    private final MovieManager movieManager;

    private final PlexApiService plexApiService;

    private final SysConfigService sysConfigService;

    private final MovieBasicService movieBasicService;

    private Long lastAnalyzeTime = 0L;

    private Long maxUpdatedAt = 0L;

    private List<Long> idList;

    public MovieAnalyzeRunnable(MovieManager movieManager, PlexApiService plexApiService, SysConfigService sysConfigService, MovieBasicService movieBasicService) {
        this.movieManager = movieManager;
        this.plexApiService = plexApiService;
        this.sysConfigService = sysConfigService;
        this.movieBasicService = movieBasicService;
    }

    @Override
    public Action getAction() {
        return Action.movieAnalyze;
    }

    @Override
    protected void beforeRun(@Nullable Map<String, String> params) {
        super.beforeRun(params);
        lastAnalyzeTime = Long.parseLong(ConfigUtils.getSysConfig(ConfigKey.lastMovieAnalyzeTime, "0"));
        if (MapUtils.isNotEmpty(params)) {
            MovieBasicDTO movieBasicDTO = MovieBasicConvert.INSTANCE.convertToDTO(params);
            List<MovieBasicDTO> movieBasicDTOList = movieBasicService.list(movieBasicDTO);
            idList = movieBasicDTOList.stream().map(MovieBasicDTO::getId).collect(Collectors.toList());
        }
    }

    @Override
    protected void afterRun(@Nullable Map<String, String> params) {
        super.afterRun(params);
        if (CollectionUtils.isEmpty(idList)) {
            SysConfigDTO sysConfigDTO = new SysConfigDTO();
            sysConfigDTO.setConfigKey(ConfigKey.lastMovieAnalyzeTime.name());
            sysConfigDTO.setConfigValue(String.valueOf(maxUpdatedAt));
            sysConfigService.save(sysConfigDTO);
        }
    }

    @Override
    protected PageResult<Metadata> page(Map<String, String> params, int pageNumber, int pageSize) {
        PageResult<Metadata> pageResult = new PageResult<>();
        pageResult.setSearchCount(true);
        if (pageNumber == 1) {
            String movieLibraryId = ConfigUtils.getSysConfig(ConfigKey.plexMovieLibraryId);
            List<Metadata> metadataList = plexApiService.listMovie(movieLibraryId);
            pageResult.setTotal((long) metadataList.size());
            pageResult.setRecords(metadataList);
        }
        return pageResult;
    }

    @Override
    protected void processEntity(Metadata metadata) throws Exception {
        if (CollectionUtils.isNotEmpty(idList) && idList.contains(metadata.getRatingKey())) {
            movieManager.analyze(metadata);
        } else {
            long lastUpdatedAt = metadata.getUpdatedAt();
            if (lastUpdatedAt > lastAnalyzeTime) {
                movieManager.analyze(metadata);
            }
            maxUpdatedAt = lastUpdatedAt > maxUpdatedAt ? lastUpdatedAt : maxUpdatedAt;
        }
    }

}
