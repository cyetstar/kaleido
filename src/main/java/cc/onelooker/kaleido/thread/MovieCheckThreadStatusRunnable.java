package cc.onelooker.kaleido.thread;

import cc.onelooker.kaleido.dto.ThreadDTO;
import cc.onelooker.kaleido.enums.ThreadStatus;
import cc.onelooker.kaleido.service.MovieManager;
import cc.onelooker.kaleido.service.ThreadService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zjjcnt.common.core.domain.PageResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by cyetstar on 2021/1/7.
 */
@Component
public class MovieCheckThreadStatusRunnable extends AbstractEntityActionRunnable<ThreadDTO> {

    private final ThreadService movieThreadService;

    private final MovieManager movieManager;

    public MovieCheckThreadStatusRunnable(ThreadService movieThreadService, MovieManager movieManager) {
        this.movieThreadService = movieThreadService;
        this.movieManager = movieManager;
    }

    @Override
    public Action getAction() {
        return Action.movieCheckThreadStatus;
    }

    @Override
    protected PageResult<ThreadDTO> page(Map<String, String> params, int pageNumber, int pageSize) {
        return movieThreadService.page(null, Page.of(pageNumber, pageSize));
    }

    @Override
    protected int processEntity(Map<String, String> params, ThreadDTO dto) throws Exception {
        if (StringUtils.equals(dto.getStatus(), ThreadStatus.todo.name())) {
            movieManager.checkThreadStatus(dto);
            return SUCCESS;
        }
        return IGNORE;
    }

}
