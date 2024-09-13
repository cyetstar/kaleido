package cc.onelooker.kaleido.thread;

import cc.onelooker.kaleido.dto.ComicBookDTO;
import cc.onelooker.kaleido.dto.ComicSeriesDTO;
import cc.onelooker.kaleido.dto.TaskDTO;
import cc.onelooker.kaleido.enums.TaskType;
import cc.onelooker.kaleido.nfo.ComicInfoNFO;
import cc.onelooker.kaleido.nfo.NFOUtil;
import cc.onelooker.kaleido.service.ComicBookService;
import cc.onelooker.kaleido.service.ComicManager;
import cc.onelooker.kaleido.service.ComicSeriesService;
import cc.onelooker.kaleido.service.TaskService;
import cc.onelooker.kaleido.utils.KaleidoConstants;
import cc.onelooker.kaleido.utils.KaleidoUtils;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.extra.compress.CompressUtil;
import cn.hutool.extra.compress.extractor.Extractor;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zjjcnt.common.core.domain.PageResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Objects;

/**
 * Created by cyetstar on 2021/1/7.
 */
@Slf4j
@Component
public class ComicWriteComicInfoRunnable extends AbstractEntityActionRunnable<TaskDTO> {

    private final TaskService taskService;

    private final ComicManager comicManager;

    private final ComicBookService comicBookService;

    private final ComicSeriesService comicSeriesService;

    public ComicWriteComicInfoRunnable(TaskService taskService, ComicManager comicManager, ComicBookService comicBookService, ComicSeriesService comicSeriesService) {
        this.taskService = taskService;
        this.comicManager = comicManager;
        this.comicBookService = comicBookService;
        this.comicSeriesService = comicSeriesService;
    }

    @Override
    public Action getAction() {
        return Action.comicWriteComicInfo;
    }

    @Override
    public boolean isNeedRun() {
        PageResult<TaskDTO> pageResult = page(null, 1, 1);
        return !pageResult.isEmpty();
    }

    @Override
    protected PageResult<TaskDTO> page(Map<String, String> params, int pageNumber, int pageSize) {
        TaskDTO param = new TaskDTO();
        param.setTaskType(TaskType.writeComicInfo.name());
        param.setTaskStatus(KaleidoConstants.TASK_STATUS_TODO);
        return taskService.page(param, Page.of(pageNumber, pageSize));
    }

    @Override
    protected int processEntity(Map<String, String> params, TaskDTO taskDTO) throws Exception {
        ComicBookDTO comicBookDTO = comicBookService.findById(taskDTO.getSubjectId());
        ComicSeriesDTO comicSeriesDTO = comicManager.findSeriesById(comicBookDTO.getSeriesId());
        ComicInfoNFO comicInfoNFO = readComicInfoNFO(comicBookDTO);
        ComicInfoNFO newComicInfoNFO = NFOUtil.toComicInfoNFO(comicSeriesDTO, comicBookDTO);
        if (comicInfoNFO == null || !Objects.equals(comicInfoNFO, newComicInfoNFO)) {
            comicManager.writeComicInfo(comicBookDTO, newComicInfoNFO);
            taskService.updateTaskStatus(taskDTO.getId(), KaleidoConstants.TASK_STATUS_DONE);
            return SUCCESS;
        }
        taskService.updateTaskStatus(taskDTO.getId(), KaleidoConstants.TASK_STATUS_IGNORE);
        return IGNORE;
    }

    @Override
    protected void processError(Map<String, String> params, TaskDTO taskDTO, Exception e) {
        super.processError(params, taskDTO, e);
        taskService.updateTaskStatus(taskDTO.getId(), KaleidoConstants.TASK_STATUS_ERROR);
    }

    private ComicInfoNFO readComicInfoNFO(ComicBookDTO comicBookDTO) {
        Path path = KaleidoUtils.getComicPath(comicBookDTO.getPath());
        Extractor extractor = CompressUtil.createExtractor(CharsetUtil.defaultCharset(), path.toFile());
        Path tempPath = Paths.get(System.getProperty("java.io.tmpdir"), "kaleido");
        extractor.extract(tempPath.toFile(), f -> StringUtils.equals(f.getName(), KaleidoConstants.COMIC_INFO));
        extractor.close();
        return NFOUtil.read(ComicInfoNFO.class, tempPath, KaleidoConstants.COMIC_INFO);
    }

    @Override
    protected String getMessage(TaskDTO taskDTO, Integer state) {
        ComicBookDTO comicBookDTO = comicBookService.findById(taskDTO.getSubjectId());
        ComicSeriesDTO comicSeriesDTO = comicSeriesService.findById(comicBookDTO.getSeriesId());
        return String.format("%s【%s】<%s>", comicBookDTO.getTitle(), comicSeriesDTO.getTitle(), getStateMessage(state));
    }
}
