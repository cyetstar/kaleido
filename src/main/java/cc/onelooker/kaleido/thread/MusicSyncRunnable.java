package cc.onelooker.kaleido.thread;

import cc.onelooker.kaleido.dto.MusicTrackDTO;
import cc.onelooker.kaleido.enums.ConfigKey;
import cc.onelooker.kaleido.service.MusicManager;
import cc.onelooker.kaleido.service.MusicTrackService;
import cc.onelooker.kaleido.third.plex.Metadata;
import cc.onelooker.kaleido.third.plex.PlexApiService;
import cc.onelooker.kaleido.utils.ConfigUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.zjjcnt.common.core.domain.PageResult;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Author xiadawei
 * @Date 2024-02-01 14:06:00
 * @Description TODO
 */
@Component
public class MusicSyncRunnable extends AbstractEntityActionRunnable<Metadata> {

    private final MusicManager musicManager;

    private final MusicTrackService musicTrackService;

    private final PlexApiService plexApiService;

    private List<String> plexIdList = Lists.newArrayList();

    private final Set<String> artistIdCache = Sets.newHashSet();

    private final Set<String> albumIdCache = Sets.newHashSet();

    public MusicSyncRunnable(MusicManager musicManager, MusicTrackService musicTrackService, PlexApiService plexApiService) {
        this.musicManager = musicManager;
        this.musicTrackService = musicTrackService;
        this.plexApiService = plexApiService;
    }

    @Override
    public Action getAction() {
        return Action.musicSync;
    }

    @Override
    protected void afterRun(@Nullable Map<String, String> params) {
        String albumId = MapUtils.getString(params, "albumId");
        if (StringUtils.isEmpty(albumId)) {
            List<MusicTrackDTO> musicTrackDTOList = musicTrackService.list(null);
            List<String> idList = musicTrackDTOList.stream().map(MusicTrackDTO::getId).collect(Collectors.toList());
            Collection<String> deleteIdList = CollectionUtils.subtract(idList, plexIdList);
            if (CollectionUtils.isNotEmpty(deleteIdList)) {
                for (String deleteId : deleteIdList) {
                    musicTrackService.deleteById(deleteId);
                }
            }
        }
        artistIdCache.clear();
        albumIdCache.clear();
        plexIdList.clear();
    }

    @Override
    protected PageResult<Metadata> page(Map<String, String> params, int pageNumber, int pageSize) {
        String albumId = MapUtils.getString(params, "albumId");
        PageResult<Metadata> pageResult = new PageResult<>();
        if (StringUtils.isEmpty(albumId)) {
            String libraryId = ConfigUtils.getSysConfig(ConfigKey.plexMusicLibraryId);
            pageResult = plexApiService.pageMetadata(libraryId, PlexApiService.TYPE_TRACK, pageNumber, pageSize);
            plexIdList.addAll(pageResult.getRecords().stream().map(Metadata::getRatingKey).collect(Collectors.toList()));
        } else if (pageNumber == 1) {
            List<Metadata> records = plexApiService.listMetadataChildren(albumId);
            pageResult.setTotal((long) CollectionUtils.size(records));
            pageResult.setSearchCount(true);
            pageResult.setRecords(records);
        }
        return pageResult;
    }

    @Override
    protected int processEntity(Map<String, String> params, Metadata metadata) throws Exception {
        MusicTrackDTO musicTrackDTO = musicTrackService.findById(metadata.getRatingKey());
        if (musicTrackDTO == null || MapUtils.getBooleanValue(params, "force")) {
            if (artistIdCache.add(metadata.getGrandparentRatingKey())) {
                musicManager.syncArtist(plexApiService.findMetadata(metadata.getGrandparentRatingKey()));
            }
            if (albumIdCache.add(metadata.getParentRatingKey())) {
                musicManager.syncAlbum(plexApiService.findMetadata(metadata.getParentRatingKey()), plexApiService.findMetadata(metadata.getRatingKey()));
            }
            musicManager.syncTrack(plexApiService.findMetadata(metadata.getRatingKey()));
            return SUCCESS;
        }
        return IGNORE;
    }

}
