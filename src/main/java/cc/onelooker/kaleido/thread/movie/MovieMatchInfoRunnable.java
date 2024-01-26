package cc.onelooker.kaleido.thread.movie;

import cc.onelooker.kaleido.convert.movie.MovieBasicConvert;
import cc.onelooker.kaleido.dto.movie.MovieBasicDTO;
import cc.onelooker.kaleido.service.movie.MovieBasicService;
import cc.onelooker.kaleido.service.movie.MovieManager;
import cc.onelooker.kaleido.thread.AbstractEntityActionRunnable;
import cc.onelooker.kaleido.thread.Action;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zjjcnt.common.core.domain.PageResult;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Random;

/**
 * Created by cyetstar on 2021/1/7.
 */
@Component
public class MovieMatchInfoRunnable extends AbstractEntityActionRunnable<MovieBasicDTO> {

    private final MovieBasicService movieBasicService;

    private final MovieManager movieManager;

    public MovieMatchInfoRunnable(MovieBasicService movieBasicService, MovieManager movieManager) {
        this.movieBasicService = movieBasicService;
        this.movieManager = movieManager;
    }

    @Override
    public Action getAction() {
        return Action.movieMatchInfo;
    }

    @Override
    protected PageResult<MovieBasicDTO> page(Map<String, String> params, int pageNumber, int pageSize) {
        MovieBasicDTO movieBasicDTO = MovieBasicConvert.INSTANCE.convertToDTO(params);
        return movieBasicService.page(movieBasicDTO, Page.of(pageNumber, pageSize, true));
    }

    @Override
    protected void processEntity(MovieBasicDTO dto) throws Exception {
        movieManager.matchInfo(dto.getId(), dto.getDoubanId(), dto.getImdbId(), dto.getTmdbId());
    }

    @Override
    public int getSleepSecond() {
        return new Random().nextInt(5);
    }
}
