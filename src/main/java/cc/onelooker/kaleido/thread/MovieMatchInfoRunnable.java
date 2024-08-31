package cc.onelooker.kaleido.thread;

import cc.onelooker.kaleido.convert.MovieBasicConvert;
import cc.onelooker.kaleido.dto.MovieBasicDTO;
import cc.onelooker.kaleido.enums.ConfigKey;
import cc.onelooker.kaleido.service.MovieBasicService;
import cc.onelooker.kaleido.service.MovieManager;
import cc.onelooker.kaleido.third.tmm.Movie;
import cc.onelooker.kaleido.third.tmm.TmmApiService;
import cc.onelooker.kaleido.utils.ConfigUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zjjcnt.common.core.domain.PageResult;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.Random;

/**
 * Created by cyetstar on 2021/1/7.
 */
@Component
public class MovieMatchInfoRunnable extends AbstractEntityActionRunnable<MovieBasicDTO> {

    private final MovieBasicService movieBasicService;

    private final MovieManager movieManager;

    private final TmmApiService tmmApiService;

    private Integer sleepSecond;

    public MovieMatchInfoRunnable(MovieBasicService movieBasicService, MovieManager movieManager, TmmApiService tmmApiService) {
        this.movieBasicService = movieBasicService;
        this.movieManager = movieManager;
        this.tmmApiService = tmmApiService;
    }

    @Override
    public Action getAction() {
        return Action.movieMatchInfo;
    }

    @Override
    protected void beforeRun(@Nullable Map<String, String> params) {
        super.beforeRun(params);
        this.sleepSecond = Integer.valueOf(ConfigUtils.getSysConfig(ConfigKey.matchInfoSleepSecond, "0"));
    }

    @Override
    protected PageResult<MovieBasicDTO> page(Map<String, String> params, int pageNumber, int pageSize) {
        MovieBasicDTO movieBasicDTO = MovieBasicConvert.INSTANCE.convertToDTO(params);
        return movieBasicService.page(movieBasicDTO, Page.of(pageNumber, pageSize, true));
    }

    @Override
    protected int processEntity(Map<String, String> params, MovieBasicDTO dto) throws Exception {
        Movie movie = tmmApiService.findMovie(dto.getDoubanId(), dto.getImdbId(), dto.getTmdbId());
        movieManager.matchMovie(dto.getId(), movie);
        return SUCCESS;
    }

    @Override
    public int getSleepSecond() {
        return new Random().nextInt(sleepSecond);
    }
}
