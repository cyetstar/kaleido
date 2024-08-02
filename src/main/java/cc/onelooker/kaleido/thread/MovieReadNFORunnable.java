package cc.onelooker.kaleido.thread;

import cc.onelooker.kaleido.convert.movie.MovieBasicConvert;
import cc.onelooker.kaleido.dto.movie.MovieBasicDTO;
import cc.onelooker.kaleido.service.movie.MovieBasicService;
import cc.onelooker.kaleido.service.movie.MovieManager;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zjjcnt.common.core.domain.PageResult;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by cyetstar on 2021/1/7.
 */
@Component
public class MovieReadNFORunnable extends AbstractEntityActionRunnable<MovieBasicDTO> {

    private final MovieBasicService movieBasicService;

    private final MovieManager movieManager;

    public MovieReadNFORunnable(MovieBasicService movieBasicService, MovieManager movieManager) {
        this.movieBasicService = movieBasicService;
        this.movieManager = movieManager;
    }

    @Override
    public Action getAction() {
        return Action.movieReadNFO;
    }

    @Override
    protected PageResult<MovieBasicDTO> page(Map<String, String> params, int pageNumber, int pageSize) {
        MovieBasicDTO movieBasicDTO = MovieBasicConvert.INSTANCE.convertToDTO(params);
        return movieBasicService.page(movieBasicDTO, Page.of(pageNumber, pageSize, true));
    }

    @Override
    protected void processEntity(Map<String, String> params, MovieBasicDTO dto) throws Exception {
        movieManager.readNFO(dto.getId());
    }

}
