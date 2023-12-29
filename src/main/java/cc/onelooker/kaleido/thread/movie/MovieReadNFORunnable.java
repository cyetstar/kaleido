package cc.onelooker.kaleido.thread.movie;

import cc.onelooker.kaleido.dto.movie.MovieBasicDTO;
import cc.onelooker.kaleido.service.movie.MovieBasicService;
import cc.onelooker.kaleido.service.movie.MovieManager;
import cc.onelooker.kaleido.thread.AbstractEntityActionRunnable;
import cc.onelooker.kaleido.thread.Action;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zjjcnt.common.core.domain.PageResult;
import com.zjjcnt.common.core.utils.ApplicationContextHelper;

/**
 * Created by cyetstar on 2021/1/7.
 */
public class MovieReadNFORunnable extends AbstractEntityActionRunnable<MovieBasicDTO> {

    private final MovieBasicService movieBasicService;

    private final MovieManager movieManager;

    public MovieReadNFORunnable() {
        this.movieBasicService = ApplicationContextHelper.getBean(MovieBasicService.class);
        this.movieManager = ApplicationContextHelper.getBean(MovieManager.class);
    }

    @Override
    public Action getAction() {
        return Action.movieReadNFO;
    }

    @Override
    protected PageResult<MovieBasicDTO> page(int pageNumber, int pageSize) {
        return movieBasicService.page(null, Page.of(pageNumber, pageSize, true));
    }

    @Override
    protected void processEntity(MovieBasicDTO dto) throws Exception {
        movieManager.readNFO(dto.getId());
    }

}
