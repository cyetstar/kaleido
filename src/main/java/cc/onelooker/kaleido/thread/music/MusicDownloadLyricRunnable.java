package cc.onelooker.kaleido.thread.music;

import cc.onelooker.kaleido.convert.music.MusicAlbumConvert;
import cc.onelooker.kaleido.dto.music.MusicAlbumDTO;
import cc.onelooker.kaleido.enums.ConfigKey;
import cc.onelooker.kaleido.service.music.MusicAlbumService;
import cc.onelooker.kaleido.service.music.MusicManager;
import cc.onelooker.kaleido.thread.AbstractEntityActionRunnable;
import cc.onelooker.kaleido.thread.Action;
import cc.onelooker.kaleido.utils.ConfigUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zjjcnt.common.core.domain.PageResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.Random;

/**
 * @Author xiadawei
 * @Date 2024-02-01 14:06:00
 * @Description TODO
 */
@Component
public class MusicDownloadLyricRunnable extends AbstractEntityActionRunnable<MusicAlbumDTO> {

    private final MusicAlbumService musicAlbumService;

    private final MusicManager musicManager;

    private Integer sleepSecond;

    public MusicDownloadLyricRunnable(MusicAlbumService musicAlbumService, MusicManager musicManager) {
        this.musicAlbumService = musicAlbumService;
        this.musicManager = musicManager;
    }

    @Override
    protected void beforeRun(@Nullable Map<String, String> params) {
        super.beforeRun(params);
        this.sleepSecond = Integer.valueOf(ConfigUtils.getSysConfig(ConfigKey.downloadLyricSleepSecond, "0"));
    }

    @Override
    protected void processEntity(MusicAlbumDTO entity) throws Exception {
        if (StringUtils.isNotEmpty(entity.getNeteaseId())) {
            musicManager.downloadLyric(entity.getId());
        }
    }

    @Override
    protected PageResult<MusicAlbumDTO> page(Map<String, String> params, int pageNumber, int pageSize) {
        MusicAlbumDTO musicAlbumDTO = MusicAlbumConvert.INSTANCE.convertToDTO(params);
        return musicAlbumService.page(musicAlbumDTO, Page.of(pageNumber, pageSize, false));
    }

    @Override
    public Action getAction() {
        return Action.musicDownloadLyric;
    }

    @Override
    public int getSleepSecond() {
        return new Random().nextInt(sleepSecond);
    }
}
