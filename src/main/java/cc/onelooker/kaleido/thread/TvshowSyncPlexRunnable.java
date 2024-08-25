package cc.onelooker.kaleido.thread;

import cc.onelooker.kaleido.dto.TvshowEpisodeDTO;
import cc.onelooker.kaleido.enums.ConfigKey;
import cc.onelooker.kaleido.service.TvshowEpisodeService;
import cc.onelooker.kaleido.service.TvshowManager;
import cc.onelooker.kaleido.third.plex.Metadata;
import cc.onelooker.kaleido.third.plex.PlexApiService;
import cc.onelooker.kaleido.utils.ConfigUtils;
import com.zjjcnt.common.core.domain.PageResult;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by cyetstar on 2021/1/7.
 */
@Component
public class TvshowSyncPlexRunnable extends AbstractEntityActionRunnable<Metadata> {

    private final PlexApiService plexApiService;

    private final TvshowEpisodeService tvshowEpisodeService;

    private final TvshowManager tvshowManager;

    private String libraryId;

    private List<Metadata> metadataList;

    public TvshowSyncPlexRunnable(PlexApiService plexApiService, TvshowEpisodeService tvshowEpisodeService, TvshowManager tvshowManager) {
        this.plexApiService = plexApiService;
        this.tvshowEpisodeService = tvshowEpisodeService;
        this.tvshowManager = tvshowManager;
    }

    @Override
    public Action getAction() {
        return Action.tvshowSyncPlex;
    }

    @Override
    protected void beforeRun(Map<String, String> params) {
        libraryId = ConfigUtils.getSysConfig(ConfigKey.plexTvshowLibraryId);
    }

    @Override
    protected void afterRun(Map<String, String> params) {
        List<TvshowEpisodeDTO> tvshowEpisodeDTOList = tvshowEpisodeService.list(null);
        List<String> idList = tvshowEpisodeDTOList.stream().map(TvshowEpisodeDTO::getId).collect(Collectors.toList());
        List<String> plexIdList = metadataList.stream().map(Metadata::getRatingKey).collect(Collectors.toList());
        Collection<String> deleteIdList = CollectionUtils.subtract(idList, plexIdList);
        if (CollectionUtils.isNotEmpty(deleteIdList)) {
            for (String deleteId : deleteIdList) {
                tvshowEpisodeService.deleteById(deleteId);
            }
        }
    }

    @Override
    protected PageResult<Metadata> page(Map<String, String> params, int pageNumber, int pageSize) {
        PageResult<Metadata> pageResult = new PageResult<>();
        pageResult.setSearchCount(true);
        if (pageNumber < 2) {
            metadataList = plexApiService.listEpsiode(libraryId);
            pageResult.setTotal((long) metadataList.size());
            pageResult.setRecords(metadataList);
        }
        return pageResult;
    }

    @Override
    protected void processEntity(Map<String, String> params, Metadata metadata) throws Exception {
        TvshowEpisodeDTO tvshowEpisodeDTO = tvshowEpisodeService.findById(metadata.getRatingKey());
        if (tvshowEpisodeDTO == null || metadata.getUpdatedAt().compareTo(tvshowEpisodeDTO.getUpdatedAt()) > 0) {
            tvshowManager.syncPlexEpisode(metadata.getRatingKey());
        }
    }

}
