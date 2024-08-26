package cc.onelooker.kaleido.thread;

import cc.onelooker.kaleido.dto.*;
import cc.onelooker.kaleido.enums.ActorRole;
import cc.onelooker.kaleido.enums.AttributeType;
import cc.onelooker.kaleido.enums.TaskType;
import cc.onelooker.kaleido.nfo.MovieNFO;
import cc.onelooker.kaleido.nfo.NFOUtil;
import cc.onelooker.kaleido.service.*;
import cc.onelooker.kaleido.utils.KaleidoConstants;
import cc.onelooker.kaleido.utils.KaleidoUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zjjcnt.common.core.domain.PageResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by cyetstar on 2021/1/7.
 */
@Slf4j
@Component
public class MovieWriteNFORunnable extends AbstractEntityActionRunnable<TaskDTO> {

    private final MovieManager movieManager;

    private final MovieBasicService movieBasicService;

    private final MovieActorService movieActorService;

    private final AttributeService attributeService;

    private final AlternateTitleService alternateTitleService;

    private final TaskService taskService;

    public MovieWriteNFORunnable(MovieManager movieManager, MovieBasicService movieBasicService, MovieActorService movieActorService, AttributeService attributeService, AlternateTitleService alternateTitleService, TaskService taskService) {
        this.movieManager = movieManager;
        this.movieBasicService = movieBasicService;
        this.movieActorService = movieActorService;
        this.attributeService = attributeService;
        this.alternateTitleService = alternateTitleService;
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
    protected void processEntity(Map<String, String> params, TaskDTO taskDTO) throws Exception {
        MovieBasicDTO movieBasicDTO = movieBasicService.findById(taskDTO.getSubjectId());
        MovieNFO movieNFO = readMovieNFO(movieBasicDTO);
        if (movieNFO == null) {
            movieManager.writeMovieNFO(movieBasicDTO);
        } else {
            List<AttributeDTO> attributeDTOList = attributeService.listBySubjectId(movieBasicDTO.getId());
            List<MovieActorDTO> movieActorDTOList = movieActorService.listByMovieId(movieBasicDTO.getId());
            List<AlternateTitleDTO> alternateTitleDTOList = alternateTitleService.listBySubjectId(movieBasicDTO.getId());
            movieBasicDTO.setDirectorList(movieActorDTOList.stream().filter(s -> StringUtils.equals(s.getRole(), ActorRole.Director.name())).collect(Collectors.toList()));
            movieBasicDTO.setWriterList(movieActorDTOList.stream().filter(s -> StringUtils.equals(s.getRole(), ActorRole.Writer.name())).collect(Collectors.toList()));
            movieBasicDTO.setActorList(movieActorDTOList.stream().filter(s -> StringUtils.equals(s.getRole(), ActorRole.Actor.name())).collect(Collectors.toList()));
            movieBasicDTO.setAkaList(alternateTitleDTOList.stream().map(AlternateTitleDTO::getTitle).collect(Collectors.toList()));
            movieBasicDTO.setLanguageList(filterAttribute(attributeDTOList, AttributeType.MovieLanguage));
            movieBasicDTO.setCountryList(filterAttribute(attributeDTOList, AttributeType.MovieCountry));
            movieBasicDTO.setGenreList(filterAttribute(attributeDTOList, AttributeType.MovieGenre));
            movieBasicDTO.setTagList(filterAttribute(attributeDTOList, AttributeType.MovieTag));
            if (isChanged(movieBasicDTO, movieNFO)) {
                movieManager.writeMovieNFO(movieBasicDTO);
            }
        }
        taskService.updateTaskStatus(taskDTO.getId(), KaleidoConstants.TASK_STATUS_DONE);
    }

    @Override
    protected void processError(Map<String, String> params, TaskDTO taskDTO, Exception e) {
        super.processError(params, taskDTO, e);
        taskService.updateTaskStatus(taskDTO.getId(), KaleidoConstants.TASK_STATUS_ERROR);
    }

    private boolean isChanged(MovieBasicDTO movieBasicDTO, MovieNFO movieNFO) {
        MovieNFO newMovieNFO = NFOUtil.toMovieNFO(movieBasicDTO);
        return !newMovieNFO.equals(movieNFO);
    }

    private List<String> filterAttribute(List<AttributeDTO> attributeDTOList, AttributeType type) {
        return attributeDTOList.stream().filter(s -> StringUtils.equals(s.getType(), type.name())).map(AttributeDTO::getValue).collect(Collectors.toList());
    }

    private MovieNFO readMovieNFO(MovieBasicDTO movieBasicDTO) {
        Path path = KaleidoUtils.getMoviePath(movieBasicDTO.getPath());
        return NFOUtil.read(MovieNFO.class, path, KaleidoConstants.MOVIE_NFO);
    }

    @Override
    protected String getMessage(TaskDTO taskDTO) {
        return taskDTO.getSubjectTitle();
    }
}
