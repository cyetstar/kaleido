package cc.onelooker.kaleido.thread;

import cc.onelooker.kaleido.convert.MusicAlbumConvert;
import cc.onelooker.kaleido.dto.MusicAlbumDTO;
import cc.onelooker.kaleido.service.MusicAlbumService;
import cc.onelooker.kaleido.service.MusicManager;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zjjcnt.common.core.domain.PageResult;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Author xiadawei
 * @Date 2024-02-01 14:06:00
 * @Description TODO
 */
@Component
public class MusicReadAudioTagRunnable extends AbstractEntityActionRunnable<MusicAlbumDTO> {

    private final MusicAlbumService musicAlbumService;

    private final MusicManager musicManager;

    public MusicReadAudioTagRunnable(MusicAlbumService musicAlbumService, MusicManager musicManager) {
        this.musicAlbumService = musicAlbumService;
        this.musicManager = musicManager;
    }

    @Override
    protected int processEntity(Map<String, String> params, MusicAlbumDTO entity) throws Exception {
        musicManager.readAudioTag(entity.getId());
        return SUCCESS;
    }

    @Override
    protected PageResult<MusicAlbumDTO> page(Map<String, String> params, int pageNumber, int pageSize) {
        MusicAlbumDTO musicAlbumDTO = MusicAlbumConvert.INSTANCE.convertToDTO(params);
        return musicAlbumService.page(musicAlbumDTO, Page.of(pageNumber, pageSize, true));
    }

    @Override
    public Action getAction() {
        return Action.musicReadAudioTag;
    }
}
