package cc.onelooker.kaleido.thread;

import cc.onelooker.kaleido.dto.movie.MovieBasicCollectionDTO;
import cc.onelooker.kaleido.dto.movie.MovieBasicDTO;
import cc.onelooker.kaleido.service.movie.MovieBasicCollectionService;
import cc.onelooker.kaleido.service.movie.MovieBasicService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zjjcnt.common.core.domain.PageResult;
import com.zjjcnt.common.util.constant.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Author xiadawei
 * @Date 2024-01-05 14:04:00
 * @Description TODO
 */
@Component
public class MovieCollectionCheckMovieStatusRunnable extends AbstractEntityActionRunnable<MovieBasicCollectionDTO> {

    @Autowired
    private MovieBasicCollectionService movieBasicCollectionService;

    @Autowired
    private MovieBasicService movieBasicService;

    @Override
    public Action getAction() {
        return Action.movieCollectionCheckMovieStatus;
    }

    @Override
    protected PageResult<MovieBasicCollectionDTO> page(Map<String, String> params, int pageNumber, int pageSize) {
        MovieBasicCollectionDTO param = new MovieBasicCollectionDTO();
        param.setStatus(Constants.NO);
        return movieBasicCollectionService.page(param, Page.of(pageNumber, pageNumber, true));
    }

    @Override
    protected void processEntity(Map<String, String> params, MovieBasicCollectionDTO dto) throws Exception {
        MovieBasicDTO movieBasicDTO = movieBasicService.findByDoubanId(dto.getDoubanId());
        if (movieBasicDTO != null) {
            dto.setMovieId(movieBasicDTO.getId());
            dto.setStatus(Constants.YES);
            movieBasicCollectionService.update(dto);
        }
    }

}
