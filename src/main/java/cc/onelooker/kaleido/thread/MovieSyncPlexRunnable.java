package cc.onelooker.kaleido.thread;

import cc.onelooker.kaleido.dto.movie.MovieBasicDTO;
import cc.onelooker.kaleido.enums.ConfigKey;
import cc.onelooker.kaleido.service.movie.MovieBasicService;
import cc.onelooker.kaleido.service.movie.MovieManager;
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
public class MovieSyncPlexRunnable extends AbstractEntityActionRunnable<Metadata> {

    private final PlexApiService plexApiService;

    private final MovieBasicService movieBasicService;

    private final MovieManager movieManager;

    private String libraryId;

    private List<Metadata> metadataList;

    public MovieSyncPlexRunnable(PlexApiService plexApiService, MovieBasicService movieBasicService, MovieManager movieManager) {
        this.plexApiService = plexApiService;
        this.movieBasicService = movieBasicService;
        this.movieManager = movieManager;
    }

    @Override
    public Action getAction() {
        return Action.movieSyncPlex;
    }

    @Override
    protected void beforeRun(Map<String, String> params) {
        libraryId = ConfigUtils.getSysConfig(ConfigKey.plexMovieLibraryId);
    }

    @Override
    protected void afterRun(Map<String, String> params) {
        List<MovieBasicDTO> movieBasicDTOList = movieBasicService.list(null);
        List<Long> idList = movieBasicDTOList.stream().map(MovieBasicDTO::getId).collect(Collectors.toList());
        List<Long> plexIdList = metadataList.stream().map(Metadata::getRatingKey).collect(Collectors.toList());
        Collection<Long> deleteIdList = CollectionUtils.subtract(idList, plexIdList);
        if (CollectionUtils.isNotEmpty(deleteIdList)) {
            for (Long deleteId : deleteIdList) {
                if (isStop()) {
                    break;
                }
                movieBasicService.deleteById(deleteId);
            }
        }
    }

    @Override
    protected PageResult<Metadata> page(Map<String, String> params, int pageNumber, int pageSize) {
        PageResult<Metadata> pageResult = new PageResult<>();
        pageResult.setSearchCount(true);
        if (pageNumber < 2) {
            metadataList = plexApiService.listMovie(libraryId);
            pageResult.setTotal((long) metadataList.size());
            pageResult.setRecords(metadataList);
        }
        return pageResult;
    }

    @Override
    protected void processEntity(Map<String, String> params, Metadata metadata) throws Exception {
        MovieBasicDTO movieBasicDTO = movieBasicService.findById(metadata.getRatingKey());
        if (movieBasicDTO == null || metadata.getUpdatedAt().compareTo(movieBasicDTO.getUpdatedAt()) > 0) {
            movieManager.syncMovieAndReadNFO(metadata.getRatingKey());
        }
    }

}
