package cc.onelooker.kaleido.thread;

import cc.onelooker.kaleido.dto.MusicAlbumDTO;
import cc.onelooker.kaleido.enums.ConfigKey;
import cc.onelooker.kaleido.service.MusicAlbumService;
import cc.onelooker.kaleido.service.MusicManager;
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
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author xiadawei
 * @Date 2024-02-01 14:06:00
 * @Description TODO
 */
@Component
public class MusicSyncRunnable extends AbstractEntityActionRunnable<Metadata> {

    private final MusicAlbumService musicAlbumService;

    private final MusicManager musicManager;

    private final PlexApiService plexApiService;

    private List<String> plexIdList = Lists.newArrayList();

    public MusicSyncRunnable(MusicAlbumService musicAlbumService, MusicManager musicManager, PlexApiService plexApiService) {
        this.musicAlbumService = musicAlbumService;
        this.musicManager = musicManager;
        this.plexApiService = plexApiService;
    }

    @Override
    public Action getAction() {
        return Action.musicSync;
    }

    @Override
    protected void afterRun(@Nullable Map<String, String> params) {
        String id = MapUtils.getString(params, "id");
        if (StringUtils.isNotEmpty(id)) {
            //单条记录同步，不做后续处理
            return;
        }
        List<MusicAlbumDTO> musicAlbumDTOList = musicAlbumService.list(null);
        List<String> idList = musicAlbumDTOList.stream().map(MusicAlbumDTO::getId).collect(Collectors.toList());
        Collection<String> deleteIdList = CollectionUtils.subtract(idList, plexIdList);
        if (CollectionUtils.isNotEmpty(deleteIdList)) {
            for (String deleteId : deleteIdList) {
                musicAlbumService.deleteById(deleteId);
            }
        }
    }

    @Override
    protected PageResult<Metadata> page(Map<String, String> params, int pageNumber, int pageSize) {
        String id = MapUtils.getString(params, "id");
        PageResult<Metadata> pageResult = new PageResult<>();
        if (StringUtils.isEmpty(id)) {
            String libraryId = ConfigUtils.getSysConfig(ConfigKey.plexMusicLibraryId);
            pageResult = plexApiService.pageMetadata(libraryId, PlexApiService.TYPE_ALBUM, pageNumber, pageSize);
            plexIdList.addAll(pageResult.getRecords().stream().map(Metadata::getRatingKey).collect(Collectors.toList()));
        } else if (pageNumber == 1) {
            Metadata metadata = plexApiService.findMetadata(id);
            pageResult.setTotal(1L);
            pageResult.setSearchCount(true);
            pageResult.setRecords(Lists.newArrayList(metadata));
        }
        return pageResult;
    }

    @Override
    protected int processEntity(Map<String, String> params, Metadata metadata) throws Exception {
        MusicAlbumDTO musicAlbumDTO = musicAlbumService.findById(metadata.getRatingKey());
        if (musicAlbumDTO == null || MapUtils.getBooleanValue(params, "force")) {
            metadata = plexApiService.findMetadata(metadata.getRatingKey());
            musicManager.syncAlbum(metadata);
            return SUCCESS;
        }
        return IGNORE;
    }

}
