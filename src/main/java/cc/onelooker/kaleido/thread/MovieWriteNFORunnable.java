package cc.onelooker.kaleido.thread;

import cc.onelooker.kaleido.dto.MovieBasicDTO;
import cc.onelooker.kaleido.dto.TaskDTO;
import cc.onelooker.kaleido.enums.TaskType;
import cc.onelooker.kaleido.nfo.MovieNFO;
import cc.onelooker.kaleido.nfo.NFOUtil;
import cc.onelooker.kaleido.service.MovieBasicService;
import cc.onelooker.kaleido.service.MovieManager;
import cc.onelooker.kaleido.service.TaskService;
import cc.onelooker.kaleido.utils.KaleidoConstants;
import cc.onelooker.kaleido.utils.KaleidoUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zjjcnt.common.core.domain.PageResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.util.Map;

/**
 * Created by cyetstar on 2021/1/7.
 */
@Slf4j
@Component
public class MovieWriteNFORunnable extends AbstractEntityActionRunnable<TaskDTO> {

    private final MovieManager movieManager;

    private final MovieBasicService movieBasicService;

    private final TaskService taskService;

    public MovieWriteNFORunnable(MovieManager movieManager, MovieBasicService movieBasicService, TaskService taskService) {
        this.movieManager = movieManager;
        this.movieBasicService = movieBasicService;
        this.taskService = taskService;
    }

    @Override
    public Action getAction() {
        return Action.movieWriteNFO;
    }

    @Override
    public boolean isNeedRun() {
        PageResult<TaskDTO> pageResult = page(null, 1, 1);
        return !pageResult.isEmpty();
    }

    @Override
    protected PageResult<TaskDTO> page(Map<String, String> params, int pageNumber, int pageSize) {
        TaskDTO param = new TaskDTO();
        param.setTaskType(TaskType.writeMovieNFO.name());
        param.setTaskStatus(KaleidoConstants.TASK_STATUS_TODO);
        return taskService.page(param, Page.of(pageNumber, pageSize));
    }

    @Override
    protected int processEntity(Map<String, String> params, TaskDTO taskDTO) throws Exception {
        MovieBasicDTO movieBasicDTO = movieBasicService.findById(taskDTO.getSubjectId());
        MovieNFO movieNFO = readMovieNFO(movieBasicDTO);
        if (movieNFO == null) {
            movieBasicDTO = movieManager.findMovieBasic(movieBasicDTO.getId());
            movieNFO = NFOUtil.toMovieNFO(movieBasicDTO);
            movieManager.writeMovieNFO(movieBasicDTO, movieNFO);
            taskService.updateTaskStatus(taskDTO.getId(), KaleidoConstants.TASK_STATUS_DONE);
            return SUCCESS;
        } else {
            movieBasicDTO = movieManager.findMovieBasic(movieBasicDTO.getId());
            MovieNFO newMovieNFO = NFOUtil.toMovieNFO(movieBasicDTO);
            if (!newMovieNFO.equals(movieNFO)) {
                movieManager.writeMovieNFO(movieBasicDTO, newMovieNFO);
                taskService.updateTaskStatus(taskDTO.getId(), KaleidoConstants.TASK_STATUS_DONE);
                return SUCCESS;
            }
        }
        taskService.updateTaskStatus(taskDTO.getId(), KaleidoConstants.TASK_STATUS_IGNORE);
        return IGNORE;
    }

    @Override
    protected void processError(Map<String, String> params, TaskDTO taskDTO, Exception e) {
        super.processError(params, taskDTO, e);
        taskService.updateTaskStatus(taskDTO.getId(), KaleidoConstants.TASK_STATUS_ERROR);
    }

    private MovieNFO readMovieNFO(MovieBasicDTO movieBasicDTO) {
        Path path = KaleidoUtils.getMoviePath(movieBasicDTO.getPath());
        return NFOUtil.read(MovieNFO.class, path, KaleidoConstants.MOVIE_NFO);
    }

    @Override
    protected String getMessage(TaskDTO taskDTO, Integer state) {
        MovieBasicDTO movieBasicDTO = movieBasicService.findById(taskDTO.getSubjectId());
        return formatMessage(movieBasicDTO.getTitle(), state);
    }
}
