package cc.onelooker.kaleido.thread.movie;

import cc.onelooker.kaleido.dto.movie.MovieBasicDTO;
import cc.onelooker.kaleido.service.movie.MovieBasicService;
import cc.onelooker.kaleido.service.movie.MovieManager;
import cc.onelooker.kaleido.third.plex.Metadata;
import cc.onelooker.kaleido.third.plex.PlexApiService;
import cc.onelooker.kaleido.thread.AbstractEntityActionRunnable;
import cc.onelooker.kaleido.thread.Action;
import cc.onelooker.kaleido.utils.ConfigUtils;
import com.zjjcnt.common.core.domain.PageResult;
import com.zjjcnt.common.core.utils.ApplicationContextHelper;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by cyetstar on 2021/1/7.
 */
public class MovieSyncPlexRunnable extends AbstractEntityActionRunnable<Metadata> {

    private final PlexApiService plexApiService;

    private final MovieBasicService movieBasicService;

    private final MovieManager movieManager;

    private String libraryId;

    private List<Metadata> records;

    public MovieSyncPlexRunnable() {
        this.plexApiService = ApplicationContextHelper.getBean(PlexApiService.class);
        this.movieBasicService = ApplicationContextHelper.getBean(MovieBasicService.class);
        this.movieManager = ApplicationContextHelper.getBean(MovieManager.class);
    }

    @Override
    public Action getAction() {
        return Action.movieSyncPlex;
    }

    @Override
    protected void beforeRun() {
        libraryId = ConfigUtils.getSysConfig("plexMovieLibraryId");
    }

    @Override
    protected void afterRun() {
        List<MovieBasicDTO> movieBasicDTOList = movieBasicService.list(null);
        List<Long> idList = movieBasicDTOList.stream().map(MovieBasicDTO::getId).collect(Collectors.toList());
        List<Long> plexIdList = records.stream().map(Metadata::getRatingKey).collect(Collectors.toList());
        Collection<Long> deleteIdList = CollectionUtils.subtract(idList, plexIdList);
        if (CollectionUtils.isNotEmpty(deleteIdList)) {
            for (Long deleteId : deleteIdList) {
                movieBasicService.deleteById(deleteId);
            }
        }
    }

    @Override
    protected PageResult<Metadata> page(int pageNumber, int pageSize) {
        records = plexApiService.listMovie(libraryId);
        PageResult<Metadata> pageResult = new PageResult<>();
        pageResult.setTotal((long) records.size());
        pageResult.setSearchCount(true);
        pageResult.setRecords(records);
        return pageResult;
    }

    @Override
    protected void processEntity(Metadata metadata) throws Exception {
        movieManager.syncPlexMovie(metadata);
    }

}
