package cc.onelooker.kaleido.thread;

import cc.onelooker.kaleido.convert.ComicSeriesConvert;
import cc.onelooker.kaleido.dto.ComicSeriesDTO;
import cc.onelooker.kaleido.dto.SysConfigDTO;
import cc.onelooker.kaleido.enums.ConfigKey;
import cc.onelooker.kaleido.service.ComicManager;
import cc.onelooker.kaleido.service.ComicSeriesService;
import cc.onelooker.kaleido.service.SysConfigService;
import cc.onelooker.kaleido.third.tmm.Comic;
import cc.onelooker.kaleido.third.tmm.TmmApiService;
import cc.onelooker.kaleido.utils.ConfigUtils;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zjjcnt.common.core.domain.PageResult;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.Map;

/**
 * Created by cyetstar on 2021/1/7.
 */
@Component
public class ComicMatchInfoRunnable extends AbstractEntityActionRunnable<ComicSeriesDTO> {

    private final ComicSeriesService comicSeriesService;

    private final SysConfigService sysConfigService;

    private final ComicManager comicManager;

    private final TmmApiService tmmApiService;

    private Long lastUpdatedAt = 0L;

    private Long maxUpdatedAt = 0L;

    public ComicMatchInfoRunnable(ComicSeriesService comicSeriesService, SysConfigService sysConfigService, ComicManager comicManager, TmmApiService tmmApiService) {
        this.comicSeriesService = comicSeriesService;
        this.sysConfigService = sysConfigService;
        this.comicManager = comicManager;
        this.tmmApiService = tmmApiService;
    }

    @Override
    public Action getAction() {
        return Action.comicMatchInfo;
    }

    @Override
    protected void beforeRun(@Nullable Map<String, String> params) {
        super.beforeRun(params);
        this.lastUpdatedAt = Long.parseLong(ConfigUtils.getSysConfig(ConfigKey.lastComicMatchInfo, "0"));
    }

    @Override
    protected void finalRun(@Nullable Map<String, String> params) {
        if (MapUtils.getBooleanValue(params, "force")) {
            return;
        }
        //强制抓取匹配时，不更新时间节点
        SysConfigDTO sysConfigDTO = new SysConfigDTO();
        sysConfigDTO.setConfigKey(ConfigKey.lastComicMatchInfo.name());
        sysConfigDTO.setConfigValue(String.valueOf(maxUpdatedAt));
        sysConfigService.save(sysConfigDTO);
    }

    @Override
    protected PageResult<ComicSeriesDTO> page(Map<String, String> params, int pageNumber, int pageSize) {
        ComicSeriesDTO param = ComicSeriesConvert.INSTANCE.convertToDTO(params);
        Page<ComicSeriesDTO> page = Page.of(pageNumber, pageSize, true);
        page.addOrder(OrderItem.asc("updated_at"));
        return comicSeriesService.page(param, page);
    }

    @Override
    protected int processEntity(Map<String, String> params, ComicSeriesDTO dto) throws Exception {
        long updatedAt = ObjectUtils.defaultIfNull(dto.getUpdatedAt(), dto.getAddedAt());
        if (updatedAt > lastUpdatedAt || MapUtils.getBooleanValue(params, "force")) {
            Comic comic = tmmApiService.findComic(dto.getBgmId());
            if (comic == null) {
                return IGNORE;
            }
            comicManager.matchInfo(dto.getId(), comic);
            maxUpdatedAt = updatedAt > maxUpdatedAt ? updatedAt : maxUpdatedAt;
            return SUCCESS;
        }
        return IGNORE;
    }

}
