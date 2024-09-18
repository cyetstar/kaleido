package cc.onelooker.kaleido.thread;

import cc.onelooker.kaleido.dto.MovieBasicCollectionDTO;
import cc.onelooker.kaleido.dto.MovieCollectionDTO;
import cc.onelooker.kaleido.service.MovieBasicCollectionService;
import cc.onelooker.kaleido.service.MovieManager;
import cc.onelooker.kaleido.third.tmm.Movie;
import cc.onelooker.kaleido.third.tmm.TmmApiService;
import cn.hutool.core.thread.ThreadUtil;
import com.google.common.collect.Lists;
import com.zjjcnt.common.core.domain.PageResult;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * @Author xiadawei
 * @Date 2024-01-04 19:47:00
 * @Description TODO
 */
@Component
public class MovieCollectionSyncDoubanRunnable extends AbstractEntityActionRunnable<Movie> {

    private List<String> doubanIdList;

    private MovieCollectionDTO movieCollectionDTO;

    @Autowired
    private MovieBasicCollectionService movieBasicCollectionService;

    @Autowired
    private MovieManager movieManager;

    @Autowired
    private TmmApiService tmmApiService;

    @Override
    public Action getAction() {
        return Action.movieCollectionSyncDouban;
    }

    @Override
    protected void beforeRun(Map<String, String> params) {
        super.beforeRun(params);
        doubanIdList = Lists.newArrayList();
        movieCollectionDTO = movieManager.syncCollection(MapUtils.getLong(params, "id"));
    }

    @Override
    protected PageResult<Movie> page(Map<String, String> params, int pageNumber, int pageSize) {
        try {
            int start = (pageNumber - 1) * 25;
            if (start > movieCollectionDTO.getChildCount()) {
                return new PageResult<>();
            }
            List<Movie> records = tmmApiService.listMovieByDoulist(movieCollectionDTO.getDoubanId(), start);
            PageResult<Movie> pageResult = new PageResult<>();
            pageResult.setTotal((long) movieCollectionDTO.getChildCount());
            pageResult.setSearchCount(true);
            pageResult.setRecords(records);
            return pageResult;
        } finally {
            ThreadUtil.sleep(new Random().nextInt(5) * 1000L);
        }
    }

    @Override
    protected void afterRun(Map<String, String> params) {
        super.afterRun(params);
        List<MovieBasicCollectionDTO> movieBasicCollectionDTOList = movieBasicCollectionService.listByCollectionId(movieCollectionDTO.getId());
        for (MovieBasicCollectionDTO movieBasicCollectionDTO : movieBasicCollectionDTOList) {
            if (doubanIdList.contains(movieBasicCollectionDTO.getDoubanId())) {
                continue;
            }
            movieBasicCollectionService.deleteById(movieBasicCollectionDTO.getId());
        }
    }

    @Override
    protected int processEntity(Map<String, String> params, Movie movie) throws Exception {
        doubanIdList.add(movie.getDoubanId());
        movieManager.syncCollectionMovie(movieCollectionDTO.getId(), movie);
        return SUCCESS;
    }

}
