package cc.onelooker.kaleido.thread.movie;

import cc.onelooker.kaleido.dto.movie.MovieCollectionDTO;
import cc.onelooker.kaleido.service.movie.MovieCollectionService;
import cc.onelooker.kaleido.third.tmm.Doulist;
import cc.onelooker.kaleido.third.tmm.TmmApiService;
import cc.onelooker.kaleido.thread.AbstractEntityActionRunnable;
import cc.onelooker.kaleido.thread.Action;
import cc.onelooker.kaleido.utils.DateTimeUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.ImmutableMap;
import com.zjjcnt.common.core.domain.PageResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author xiadawei
 * @Date 2024-01-04 19:47:00
 * @Description TODO
 */
@Component
public class MovieCollectionSyncDoubanAllRunnable extends AbstractEntityActionRunnable<MovieCollectionDTO> {

    @Autowired
    private MovieCollectionSyncDoubanRunnable movieCollectionSyncDoubanRunnable;

    @Autowired
    private MovieCollectionService movieCollectionService;

    @Autowired
    private TmmApiService tmmApiService;

    @Override
    public Action getAction() {
        return Action.movieCollectionSyncDoubanAll;
    }

    @Override
    protected PageResult<MovieCollectionDTO> page(int pageNumber, int pageSize) {
        return movieCollectionService.page(null, Page.of(pageNumber, pageSize, true));
    }

    @Override
    protected void processEntity(MovieCollectionDTO movieCollectionDTO) throws Exception {
        Doulist doulist = tmmApiService.findDoulist(movieCollectionDTO.getDoubanId());
        String updated = DateTimeUtil.formatDateTime(doulist.getUpdated());
        if (StringUtils.compare(movieCollectionDTO.getUpdateTime(), updated) >= 0) {
            return;
        }
        movieCollectionSyncDoubanRunnable.setParams(ImmutableMap.of("id", movieCollectionDTO.getId()));
        movieCollectionSyncDoubanRunnable.run();
    }

}
