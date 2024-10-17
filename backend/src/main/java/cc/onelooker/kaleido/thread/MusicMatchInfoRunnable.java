package cc.onelooker.kaleido.thread;

import cc.onelooker.kaleido.convert.MusicAlbumConvert;
import cc.onelooker.kaleido.dto.MovieBasicDTO;
import cc.onelooker.kaleido.dto.MusicAlbumDTO;
import cc.onelooker.kaleido.dto.SysConfigDTO;
import cc.onelooker.kaleido.enums.ConfigKey;
import cc.onelooker.kaleido.service.*;
import cc.onelooker.kaleido.third.tmm.Album;
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
import java.util.Random;

/**
 * Created by cyetstar on 2021/1/7.
 */
@Component
public class MusicMatchInfoRunnable extends AbstractEntityActionRunnable<MusicAlbumDTO> {

    private final MusicAlbumService musicAlbumService;

    private final SysConfigService sysConfigService;

    private final MusicManager musicManager;

    private final TmmApiService tmmApiService;

    private Integer sleepSecond;

    private Long lastUpdatedAt = 0L;

    private Long maxUpdatedAt = 0L;

    public MusicMatchInfoRunnable(MusicAlbumService musicAlbumService, SysConfigService sysConfigService, MusicManager musicManager, TmmApiService tmmApiService) {
        this.musicAlbumService = musicAlbumService;
        this.sysConfigService = sysConfigService;
        this.musicManager = musicManager;
        this.tmmApiService = tmmApiService;
    }

    @Override
    public Action getAction() {
        return Action.musicMatchInfo;
    }

    @Override
    protected void beforeRun(@Nullable Map<String, String> params) {
        super.beforeRun(params);
        this.lastUpdatedAt = Long.parseLong(ConfigUtils.getSysConfig(ConfigKey.lastMusicMatchInfo, "0"));
        this.sleepSecond = Integer.valueOf(ConfigUtils.getSysConfig(ConfigKey.matchInfoSleepSecond, "0"));
    }

    @Override
    protected void finalRun(@Nullable Map<String, String> params) {
        if (MapUtils.getBooleanValue(params, "force")) {
            return;
        }
        //强制抓取匹配时，不更新时间节点
        SysConfigDTO sysConfigDTO = new SysConfigDTO();
        sysConfigDTO.setConfigKey(ConfigKey.lastMusicMatchInfo.name());
        sysConfigDTO.setConfigValue(String.valueOf(maxUpdatedAt));
        sysConfigService.save(sysConfigDTO);
    }

    @Override
    protected PageResult<MusicAlbumDTO> page(Map<String, String> params, int pageNumber, int pageSize) {
        MusicAlbumDTO musicAlbumDTO = MusicAlbumConvert.INSTANCE.convertToDTO(params);
        Page<MovieBasicDTO> page = Page.of(pageNumber, pageSize);
        page.addOrder(OrderItem.asc("updated_at"));
        return musicAlbumService.page(musicAlbumDTO, page);
    }

    @Override
    protected int processEntity(Map<String, String> params, MusicAlbumDTO dto) throws Exception {
        long updatedAt = ObjectUtils.defaultIfNull(dto.getUpdatedAt(), dto.getAddedAt());
        if (updatedAt > lastUpdatedAt || MapUtils.getBooleanValue(params, "force")) {
            Album album = tmmApiService.findAlbum(dto.getNeteaseId(), dto.getMusicbrainzId());
            if (album == null) {
                return IGNORE;
            }
            musicManager.matchInfo(dto.getId(), album);
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
