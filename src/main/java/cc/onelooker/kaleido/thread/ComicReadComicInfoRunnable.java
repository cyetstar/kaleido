package cc.onelooker.kaleido.thread;

import cc.onelooker.kaleido.convert.comic.ComicSeriesConvert;
import cc.onelooker.kaleido.dto.comic.ComicSeriesDTO;
import cc.onelooker.kaleido.service.ComicManager;
import cc.onelooker.kaleido.service.comic.ComicSeriesService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zjjcnt.common.core.domain.PageResult;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by cyetstar on 2021/1/7.
 */
@Component
public class ComicReadComicInfoRunnable extends AbstractEntityActionRunnable<ComicSeriesDTO> {

    private final ComicManager comicManager;

    private final ComicSeriesService comicSeriesService;

    public ComicReadComicInfoRunnable(ComicManager comicManager, ComicSeriesService comicSeriesService) {
        this.comicManager = comicManager;
        this.comicSeriesService = comicSeriesService;
    }

    @Override
    public Action getAction() {
        return Action.comicReadComicInfo;
    }

    @Override
    protected PageResult<ComicSeriesDTO> page(Map<String, String> params, int pageNumber, int pageSize) {
        ComicSeriesDTO comicSeriesDTO = ComicSeriesConvert.INSTANCE.convertToDTO(params);
        return comicSeriesService.page(comicSeriesDTO, Page.of(pageNumber, pageSize));
    }

    @Override
    protected void processEntity(ComicSeriesDTO comicSeriesDTO) throws Exception {
        comicManager.readComicInfo(comicSeriesDTO.getId());
    }

    @Override
    protected String getMessage(ComicSeriesDTO comicSeriesDTO) {
        return comicSeriesDTO.getTitle();
    }
}
