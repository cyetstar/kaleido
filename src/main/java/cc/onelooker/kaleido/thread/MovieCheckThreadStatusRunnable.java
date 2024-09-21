package cc.onelooker.kaleido.thread;

import cc.onelooker.kaleido.dto.MovieThreadDTO;
import cc.onelooker.kaleido.enums.ThreadStatus;
import cc.onelooker.kaleido.service.MovieManager;
import cc.onelooker.kaleido.service.MovieThreadService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zjjcnt.common.core.domain.PageResult;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by cyetstar on 2021/1/7.
 */
@Component
public class MovieCheckThreadStatusRunnable extends AbstractEntityActionRunnable<MovieThreadDTO> {

    private final MovieThreadService movieThreadService;

    private final MovieManager movieManager;

    public MovieCheckThreadStatusRunnable(MovieThreadService movieThreadService, MovieManager movieManager) {
        this.movieThreadService = movieThreadService;
        this.movieManager = movieManager;
    }

    @Override
    public Action getAction() {
        return Action.movieCheckThreadStatus;
    }

    @Override
    protected PageResult<MovieThreadDTO> page(Map<String, String> params, int pageNumber, int pageSize) {
        return movieThreadService.page(null, Page.of(pageNumber, pageSize));
    }

    @Override
    protected int processEntity(Map<String, String> params, MovieThreadDTO dto) throws Exception {
        if (dto.getStatus() == ThreadStatus.todo.ordinal()) {
            movieManager.checkThreadStatus(dto);
            return SUCCESS;
        }
        return IGNORE;
    }

}
