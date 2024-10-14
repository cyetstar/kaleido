package cc.onelooker.kaleido.thread;

import cc.onelooker.kaleido.dto.MovieBasicDTO;
import cc.onelooker.kaleido.enums.ConfigKey;
import cc.onelooker.kaleido.service.MovieBasicService;
import cc.onelooker.kaleido.service.MovieManager;
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

    private final List<String> plexIdList = Lists.newArrayList();

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
    protected void afterRun(Map<String, String> params) {
        String id = MapUtils.getString(params, "id");
        if (StringUtils.isNotEmpty(id)) {
            //单条记录同步，不做后续处理
            return;
        }
        List<MovieBasicDTO> movieBasicDTOList = movieBasicService.list(null);
        List<String> idList = movieBasicDTOList.stream().map(MovieBasicDTO::getId).collect(Collectors.toList());
        Collection<String> deleteIdList = CollectionUtils.subtract(idList, plexIdList);
        if (CollectionUtils.isNotEmpty(deleteIdList)) {
            deleteIdList.forEach(movieBasicService::deleteById);
        }
        plexIdList.clear();
    }

    @Override
    protected PageResult<Metadata> page(Map<String, String> params, int pageNumber, int pageSize) {
        String id = MapUtils.getString(params, "id");
        PageResult<Metadata> pageResult = new PageResult<>();
        if (StringUtils.isEmpty(id)) {
            String libraryId = ConfigUtils.getSysConfig(ConfigKey.plexMovieLibraryId);
            pageResult = plexApiService.pageMetadata(libraryId, PlexApiService.TYPE_MOVIE, pageNumber, pageSize);
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
        MovieBasicDTO movieBasicDTO = movieBasicService.findById(metadata.getRatingKey());
        //FIXME 已经存在，会存在部分无法及时更新的情况
        if (movieBasicDTO == null || MapUtils.getBooleanValue(params, "force")) {
            movieManager.syncMovie(plexApiService.findMetadata(metadata.getRatingKey()));
            return SUCCESS;
        }
        return IGNORE;
    }

}
