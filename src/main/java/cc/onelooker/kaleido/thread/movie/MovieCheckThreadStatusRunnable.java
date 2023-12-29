package cc.onelooker.kaleido.thread.movie;

import cc.onelooker.kaleido.dto.movie.MovieThreadDTO;
import cc.onelooker.kaleido.service.movie.MovieManager;
import cc.onelooker.kaleido.service.movie.MovieThreadService;
import cc.onelooker.kaleido.thread.AbstractEntityActionRunnable;
import cc.onelooker.kaleido.thread.Action;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zjjcnt.common.core.domain.PageResult;
import com.zjjcnt.common.core.utils.ApplicationContextHelper;

/**
 * Created by cyetstar on 2021/1/7.
 */
public class MovieCheckThreadStatusRunnable extends AbstractEntityActionRunnable<MovieThreadDTO> {

    private final MovieThreadService movieThreadService;

    private final MovieManager movieManager;

    public MovieCheckThreadStatusRunnable() {
        this.movieThreadService = ApplicationContextHelper.getBean(MovieThreadService.class);
        this.movieManager = ApplicationContextHelper.getBean(MovieManager.class);
    }

    @Override
    public Action getAction() {
        return Action.movieCheckThreadStatus;
    }

    @Override
    protected PageResult<MovieThreadDTO> page(int pageNumber, int pageSize) {
        return movieThreadService.page(null, Page.of(pageNumber, pageSize, true));
    }

    @Override
    protected void processEntity(MovieThreadDTO dto) throws Exception {
        movieManager.checkThreadStatus(dto);
    }

}
