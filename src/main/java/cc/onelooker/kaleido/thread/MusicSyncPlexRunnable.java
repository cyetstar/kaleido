package cc.onelooker.kaleido.thread;

import cc.onelooker.kaleido.dto.MusicAlbumDTO;
import cc.onelooker.kaleido.enums.ConfigKey;
import cc.onelooker.kaleido.service.MusicAlbumService;
import cc.onelooker.kaleido.service.MusicManager;
import cc.onelooker.kaleido.third.plex.Metadata;
import cc.onelooker.kaleido.third.plex.PlexApiService;
import cc.onelooker.kaleido.utils.ConfigUtils;
import com.zjjcnt.common.core.domain.PageResult;
import org.apache.commons.collections4.CollectionUtils;
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
public class MusicSyncPlexRunnable extends AbstractEntityActionRunnable<Metadata> {

    private final MusicAlbumService musicAlbumService;

    private final MusicManager musicManager;

    private final PlexApiService plexApiService;

    private String libraryId;

    private String libraryPath;

    private List<Metadata> metadataList;

    public MusicSyncPlexRunnable(MusicAlbumService musicAlbumService, MusicManager musicManager, PlexApiService plexApiService) {
        this.musicAlbumService = musicAlbumService;
        this.musicManager = musicManager;
        this.plexApiService = plexApiService;
    }

    @Override
    public Action getAction() {
        return Action.musicSyncPlex;
    }

    @Override
    protected void beforeRun(Map<String, String> params) {
        libraryId = ConfigUtils.getSysConfig(ConfigKey.plexMusicLibraryId);
        libraryPath = ConfigUtils.getSysConfig(ConfigKey.plexMusicLibraryPath);
    }

    @Override
    protected void afterRun(@Nullable Map<String, String> params) {
        List<MusicAlbumDTO> musicAlbumDTOList = musicAlbumService.list(null);
        List<String> idList = musicAlbumDTOList.stream().map(MusicAlbumDTO::getId).collect(Collectors.toList());
        List<String> plexIdList = metadataList.stream().map(Metadata::getRatingKey).collect(Collectors.toList());
        Collection<String> deleteIdList = CollectionUtils.subtract(idList, plexIdList);
        if (CollectionUtils.isNotEmpty(deleteIdList)) {
            for (String deleteId : deleteIdList) {
                musicAlbumService.deleteById(deleteId);
            }
        }
    }

    @Override
    protected PageResult<Metadata> page(Map<String, String> params, int pageNumber, int pageSize) {
        PageResult<Metadata> pageResult = new PageResult<>();
        pageResult.setSearchCount(true);
        if (pageNumber < 2) {
            metadataList = plexApiService.listAlbum(libraryId);
            pageResult.setTotal((long) metadataList.size());
            pageResult.setRecords(metadataList);
        }
        return pageResult;
    }

    @Override
    protected void processEntity(Map<String, String> params, Metadata metadata) throws Exception {
        MusicAlbumDTO musicAlbumDTO = musicAlbumService.findById(metadata.getRatingKey());
        if (musicAlbumDTO == null || metadata.getUpdatedAt().compareTo(musicAlbumDTO.getUpdatedAt()) > 0) {
            musicManager.syncPlexAlbumAndReadAudioTag(libraryPath, metadata.getRatingKey());
        }
    }

}
