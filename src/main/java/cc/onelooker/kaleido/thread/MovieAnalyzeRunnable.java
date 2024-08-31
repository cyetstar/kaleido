package cc.onelooker.kaleido.thread;

import cc.onelooker.kaleido.convert.MovieBasicConvert;
import cc.onelooker.kaleido.dto.MovieBasicDTO;
import cc.onelooker.kaleido.dto.SysConfigDTO;
import cc.onelooker.kaleido.enums.ConfigKey;
import cc.onelooker.kaleido.service.MovieBasicService;
import cc.onelooker.kaleido.service.MovieManager;
import cc.onelooker.kaleido.service.SysConfigService;
import cc.onelooker.kaleido.third.plex.Metadata;
import cc.onelooker.kaleido.third.plex.PlexApiService;
import cc.onelooker.kaleido.utils.ConfigUtils;
import com.google.common.collect.Lists;
import com.zjjcnt.common.core.domain.PageResult;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
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

    private List<String> idList;

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
        String id = MapUtils.getString(params, "id");
        PageResult<Metadata> pageResult = new PageResult<>();
        pageResult.setSearchCount(true);
        if (StringUtils.isEmpty(id)) {
            String libraryId = ConfigUtils.getSysConfig(ConfigKey.plexMovieLibraryId);
            pageResult = plexApiService.pageMovie(libraryId, pageNumber, pageSize);
        } else if (pageNumber == 1) {
            Metadata metadata = plexApiService.findMetadata(id);
            pageResult.setTotal(1L);
            pageResult.setRecords(Lists.newArrayList(metadata));
        }
        return pageResult;
    }

    @Override
    protected int processEntity(Map<String, String> params, Metadata metadata) throws Exception {
        if (CollectionUtils.isNotEmpty(idList)) {
            if (idList.contains(metadata.getRatingKey())) {
                //查出所有数据后，匹配上的才进行分析
                movieManager.analyze(metadata);
                return SUCCESS;
            }
        } else {
            long lastUpdatedAt = metadata.getUpdatedAt();
            if (lastUpdatedAt > lastAnalyzeTime) {
                movieManager.analyze(metadata);
                return SUCCESS;
            }
            maxUpdatedAt = lastUpdatedAt > maxUpdatedAt ? lastUpdatedAt : maxUpdatedAt;
        }
        return IGNORE;
    }

}
