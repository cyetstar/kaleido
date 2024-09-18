package cc.onelooker.kaleido.thread;

import cc.onelooker.kaleido.convert.TvshowSeasonConvert;
import cc.onelooker.kaleido.dto.SysConfigDTO;
import cc.onelooker.kaleido.dto.TvshowSeasonDTO;
import cc.onelooker.kaleido.enums.ConfigKey;
import cc.onelooker.kaleido.service.SysConfigService;
import cc.onelooker.kaleido.service.TvshowManager;
import cc.onelooker.kaleido.service.TvshowSeasonService;
import cc.onelooker.kaleido.third.tmm.TmmApiService;
import cc.onelooker.kaleido.third.tmm.Tvshow;
import cc.onelooker.kaleido.utils.ConfigUtils;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zjjcnt.common.core.domain.PageResult;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.Random;

/**
 * Created by cyetstar on 2021/1/7.
 */
@Component
public class TvshowMatchInfoRunnable extends AbstractEntityActionRunnable<TvshowSeasonDTO> {

    private final TvshowSeasonService tvshowSeasonService;

    private final SysConfigService sysConfigService;

    private final TvshowManager tvshowManager;

    private final TmmApiService tmmApiService;

    private Integer sleepSecond;

    private Long lastUpdatedAt = 0L;

    private Long maxUpdatedAt = 0L;

    public TvshowMatchInfoRunnable(TvshowSeasonService tvshowSeasonService, SysConfigService sysConfigService, TvshowManager tvshowManager, TmmApiService tmmApiService) {
        this.tvshowSeasonService = tvshowSeasonService;
        this.sysConfigService = sysConfigService;
        this.tvshowManager = tvshowManager;
        this.tmmApiService = tmmApiService;
    }

    @Override
    public Action getAction() {
        return Action.tvshowMatchInfo;
    }

    @Override
    protected void beforeRun(@Nullable Map<String, String> params) {
        super.beforeRun(params);
        this.lastUpdatedAt = Long.parseLong(ConfigUtils.getSysConfig(ConfigKey.lastTvshowMatchInfo, "0"));
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
    protected PageResult<TvshowSeasonDTO> page(Map<String, String> params, int pageNumber, int pageSize) {
        TvshowSeasonDTO param = TvshowSeasonConvert.INSTANCE.convertToDTO(params);
        Page<TvshowSeasonDTO> page = Page.of(pageNumber, pageSize, true);
        page.addOrder(OrderItem.asc("updated_at"));
        return tvshowSeasonService.page(param, page);
    }

    @Override
    protected int processEntity(Map<String, String> params, TvshowSeasonDTO dto) throws Exception {
        long updatedAt = ObjectUtils.defaultIfNull(dto.getUpdatedAt(), dto.getAddedAt());
        if (updatedAt > lastUpdatedAt || MapUtils.getBooleanValue(params, "force")) {
            Tvshow tvshow = tmmApiService.findShow(dto.getDoubanId(), dto.getImdbId(), dto.getTmdbId());
            if (tvshow == null) {
                return IGNORE;
            }
            tvshowManager.matchInfo(dto.getId(), tvshow);
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
