package cc.onelooker.kaleido.thread;

import cc.onelooker.kaleido.dto.MovieBasicDTO;
import cc.onelooker.kaleido.enums.ConfigKey;
import cc.onelooker.kaleido.enums.SubjectType;
import cc.onelooker.kaleido.enums.TaskType;
import cc.onelooker.kaleido.service.MovieBasicService;
import cc.onelooker.kaleido.service.MovieManager;
import cc.onelooker.kaleido.service.TaskService;
import cc.onelooker.kaleido.third.plex.Metadata;
import cc.onelooker.kaleido.third.plex.PlexApiService;
import cc.onelooker.kaleido.utils.ConfigUtils;
import com.google.common.collect.Lists;
import com.zjjcnt.common.core.domain.PageResult;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by cyetstar on 2021/1/7.
 */
@Component
public class MovieSyncRunnable extends AbstractEntityActionRunnable<Metadata> {

    private final PlexApiService plexApiService;

    private final MovieBasicService movieBasicService;

    private final MovieManager movieManager;

    private String libraryId;

    private List<Metadata> metadataList;

    public MovieSyncRunnable(PlexApiService plexApiService, MovieManager movieManager, MovieBasicService movieBasicService) {
        this.plexApiService = plexApiService;
        this.movieManager = movieManager;
        this.movieBasicService = movieBasicService;
    }

    @Override
    public Action getAction() {
        return Action.movieSync;
    }

    @Override
    protected void beforeRun(Map<String, String> params) {
        libraryId = ConfigUtils.getSysConfig(ConfigKey.plexMovieLibraryId);
    }

    @Override
    protected void afterRun(Map<String, String> params) {
        String id = MapUtils.getString(params, "id");
        if (StringUtils.isNotEmpty(id)) {
            //单条记录同步，不做后续处理
            return;
        }
        List<MovieBasicDTO> movieBasicDTOList = movieBasicService.list(null);
        List<String> idList = movieBasicDTOList.stream().map(MovieBasicDTO::getId).collect(Collectors.toList());
        List<String> plexIdList = metadataList.stream().map(Metadata::getRatingKey).collect(Collectors.toList());
        Collection<String> deleteIdList = CollectionUtils.subtract(idList, plexIdList);
        if (CollectionUtils.isNotEmpty(deleteIdList)) {
            for (String deleteId : deleteIdList) {
                if (isStop()) {
                    break;
                }
                movieBasicService.deleteById(deleteId);
            }
        }
    }

    @Override
    protected PageResult<Metadata> page(Map<String, String> params, int pageNumber, int pageSize) {
        String id = MapUtils.getString(params, "id");
        PageResult<Metadata> pageResult = new PageResult<>();
        pageResult.setSearchCount(true);
        if (pageNumber == 1) {
            if (StringUtils.isNotEmpty(id)) {
                Metadata metadata = plexApiService.findMetadata(id);
                if (metadata != null) {
                    metadataList = Lists.newArrayList(metadata);
                }
            } else {
                metadataList = plexApiService.listMovie(libraryId);
            }
            pageResult.setTotal((long) metadataList.size());
            pageResult.setRecords(metadataList);
        }
        return pageResult;
    }

    @Override
    protected void processEntity(Map<String, String> params, Metadata metadata) throws Exception {
        MovieBasicDTO movieBasicDTO = movieBasicService.findById(metadata.getRatingKey());
        if (movieBasicDTO == null || metadata.getUpdatedAt().compareTo(movieBasicDTO.getUpdatedAt()) > 0 || MapUtils.getBooleanValue(params, "force")) {
            movieManager.syncMovie(metadata);
        }
    }

}
