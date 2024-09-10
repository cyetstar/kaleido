package cc.onelooker.kaleido.thread;

import cc.onelooker.kaleido.convert.MovieBasicConvert;
import cc.onelooker.kaleido.dto.MovieBasicDTO;
import cc.onelooker.kaleido.dto.SysConfigDTO;
import cc.onelooker.kaleido.enums.ConfigKey;
import cc.onelooker.kaleido.service.MovieBasicService;
import cc.onelooker.kaleido.service.MovieManager;
import cc.onelooker.kaleido.service.SysConfigService;
import cc.onelooker.kaleido.third.tmm.Movie;
import cc.onelooker.kaleido.third.tmm.TmmApiService;
import cc.onelooker.kaleido.utils.ConfigUtils;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zjjcnt.common.core.domain.PageResult;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.Random;

/**
 * Created by cyetstar on 2021/1/7.
 */
@Component
public class MovieMatchInfoRunnable extends AbstractEntityActionRunnable<MovieBasicDTO> {

    private final MovieBasicService movieBasicService;

    private final SysConfigService sysConfigService;

    private final MovieManager movieManager;

    private final TmmApiService tmmApiService;

    private Integer sleepSecond;

    private Long lastUpdatedAt = 0L;

    private Long maxUpdatedAt = 0L;

    public MovieMatchInfoRunnable(MovieBasicService movieBasicService, SysConfigService sysConfigService, MovieManager movieManager, TmmApiService tmmApiService) {
        this.movieBasicService = movieBasicService;
        this.sysConfigService = sysConfigService;
        this.movieManager = movieManager;
        this.tmmApiService = tmmApiService;
    }

    @Override
    public Action getAction() {
        return Action.movieMatchInfo;
    }

    @Override
    protected void beforeRun(@Nullable Map<String, String> params) {
        super.beforeRun(params);
        this.lastUpdatedAt = Long.parseLong(ConfigUtils.getSysConfig(ConfigKey.lastMovieMatchInfo, "0"));
        this.sleepSecond = Integer.valueOf(ConfigUtils.getSysConfig(ConfigKey.matchInfoSleepSecond, "0"));
    }

    @Override
    protected void finalRun(@Nullable Map<String, String> params) {
        if (MapUtils.getBooleanValue(params, "force")) {
            return;
        }
        //强制抓取匹配时，不更新时间节点
        SysConfigDTO sysConfigDTO = new SysConfigDTO();
        sysConfigDTO.setConfigKey(ConfigKey.lastMovieMatchInfo.name());
        sysConfigDTO.setConfigValue(String.valueOf(maxUpdatedAt));
        sysConfigService.save(sysConfigDTO);
    }

    @Override
    protected PageResult<MovieBasicDTO> page(Map<String, String> params, int pageNumber, int pageSize) {
        MovieBasicDTO movieBasicDTO = MovieBasicConvert.INSTANCE.convertToDTO(params);
        Page<MovieBasicDTO> page = Page.of(pageNumber, pageSize, true);
        page.addOrder(OrderItem.asc("updated_at"));
        return movieBasicService.page(movieBasicDTO, page);
    }

    @Override
    protected int processEntity(Map<String, String> params, MovieBasicDTO dto) throws Exception {
        long updatedAt = dto.getUpdatedAt();
        if (updatedAt > lastUpdatedAt || MapUtils.getBooleanValue(params, "force")) {
            Movie movie = tmmApiService.findMovie(dto.getDoubanId(), dto.getImdbId(), dto.getTmdbId());
            movieManager.matchInfo(dto.getId(), movie);
            maxUpdatedAt = updatedAt > maxUpdatedAt ? updatedAt : maxUpdatedAt;
            return SUCCESS;
        }
        return IGNORE;
    }

    @Override
    public int getSleepSecond() {
        return new Random().nextInt(sleepSecond);
    }
}
