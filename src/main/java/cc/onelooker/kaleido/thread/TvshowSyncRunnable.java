package cc.onelooker.kaleido.thread;

import cc.onelooker.kaleido.dto.TvshowEpisodeDTO;
import cc.onelooker.kaleido.enums.ConfigKey;
import cc.onelooker.kaleido.service.TvshowEpisodeService;
import cc.onelooker.kaleido.service.TvshowManager;
import cc.onelooker.kaleido.third.plex.Metadata;
import cc.onelooker.kaleido.third.plex.PlexApiService;
import cc.onelooker.kaleido.utils.ConfigUtils;
import com.google.common.collect.Sets;
import com.zjjcnt.common.core.domain.PageResult;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.compress.utils.Lists;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by cyetstar on 2021/1/7.
 */
@Component
public class TvshowSyncRunnable extends AbstractEntityActionRunnable<Metadata> {

    private final PlexApiService plexApiService;

    private final TvshowEpisodeService tvshowEpisodeService;

    private final TvshowManager tvshowManager;

    private final List<String> plexIdList = Lists.newArrayList();

    private final Set<String> seasonIdCache = Sets.newHashSet();

    private final Set<String> showIdCache = Sets.newHashSet();

    public TvshowSyncRunnable(PlexApiService plexApiService, TvshowEpisodeService tvshowEpisodeService, TvshowManager tvshowManager) {
        this.plexApiService = plexApiService;
        this.tvshowEpisodeService = tvshowEpisodeService;
        this.tvshowManager = tvshowManager;
    }

    @Override
    public Action getAction() {
        return Action.tvshowSync;
    }

    @Override
    protected void afterRun(Map<String, String> params) {
        List<TvshowEpisodeDTO> tvshowEpisodeDTOList = tvshowEpisodeService.list(null);
        List<String> idList = tvshowEpisodeDTOList.stream().map(TvshowEpisodeDTO::getId).collect(Collectors.toList());
        Collection<String> deleteIdList = CollectionUtils.subtract(idList, plexIdList);
        if (CollectionUtils.isNotEmpty(deleteIdList)) {
            for (String deleteId : deleteIdList) {
                tvshowEpisodeService.deleteById(deleteId);
            }
        }
        seasonIdCache.clear();
        showIdCache.clear();
        plexIdList.clear();
    }

    @Override
    protected PageResult<Metadata> page(Map<String, String> params, int pageNumber, int pageSize) {
        String libraryId = ConfigUtils.getSysConfig(ConfigKey.plexTvshowLibraryId);
        PageResult<Metadata> pageResult = plexApiService.pageMetadata(libraryId, PlexApiService.TYPE_EPISODE, pageNumber, pageSize);
        plexIdList.addAll(pageResult.getRecords().stream().map(Metadata::getRatingKey).collect(Collectors.toList()));
        return pageResult;
    }

    @Override
    protected int processEntity(Map<String, String> params, Metadata metadata) throws Exception {
        TvshowEpisodeDTO tvshowEpisodeDTO = tvshowEpisodeService.findById(metadata.getRatingKey());
        if (tvshowEpisodeDTO == null || metadata.getUpdatedAt().compareTo(tvshowEpisodeDTO.getUpdatedAt()) > 0 || MapUtils.getBooleanValue(params, "force")) {
            if (showIdCache.add(metadata.getGrandparentRatingKey())) {
                tvshowManager.syncShow(plexApiService.findMetadata(metadata.getGrandparentRatingKey()));
            }
            if (seasonIdCache.add(metadata.getParentRatingKey())) {
                tvshowManager.syncSeason(plexApiService.findMetadata(metadata.getParentRatingKey()));
            }
            tvshowManager.syncEpisode(metadata);
            return SUCCESS;
        }
        return IGNORE;
    }

}
