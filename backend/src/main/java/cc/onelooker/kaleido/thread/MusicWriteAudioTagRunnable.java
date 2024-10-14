package cc.onelooker.kaleido.thread;

import cc.onelooker.kaleido.dto.MusicAlbumDTO;
import cc.onelooker.kaleido.dto.MusicTrackDTO;
import cc.onelooker.kaleido.dto.TaskDTO;
import cc.onelooker.kaleido.enums.SubjectType;
import cc.onelooker.kaleido.enums.TaskType;
import cc.onelooker.kaleido.service.MusicAlbumService;
import cc.onelooker.kaleido.service.MusicTrackService;
import cc.onelooker.kaleido.service.TaskService;
import cc.onelooker.kaleido.utils.AudioTagUtil;
import cc.onelooker.kaleido.utils.KaleidoConstants;
import cc.onelooker.kaleido.utils.KaleidoUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zjjcnt.common.core.domain.PageResult;
import lombok.extern.slf4j.Slf4j;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.tag.Tag;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.nio.file.Path;
import java.util.Map;

/**
 * Created by cyetstar on 2021/1/7.
 */
@Slf4j
@Component
public class MusicWriteAudioTagRunnable extends AbstractEntityActionRunnable<TaskDTO> {

    private final TaskService taskService;

    private final MusicTrackService musicTrackService;

    private final MusicAlbumService musicAlbumService;

    public MusicWriteAudioTagRunnable(TaskService taskService, MusicTrackService musicTrackService, MusicAlbumService musicAlbumService) {
        this.taskService = taskService;
        this.musicTrackService = musicTrackService;
        this.musicAlbumService = musicAlbumService;
    }

    @Override
    public Action getAction() {
        return Action.musicReadAudioTag;
    }

    @Override
    public boolean isNeedRun() {
        PageResult<TaskDTO> pageResult = page(null, 1, 1);
        return !pageResult.isEmpty();
    }

    @Override
    protected void beforeRun(@Nullable Map<String, String> params) {
        super.beforeRun(params);
        taskService.deleteNotExistRecords("music_track", SubjectType.MusicTrack);
    }

    @Override
    protected PageResult<TaskDTO> page(Map<String, String> params, int pageNumber, int pageSize) {
        TaskDTO param = new TaskDTO();
        param.setTaskType(TaskType.writeAudioTag.name());
        param.setTaskStatus(KaleidoConstants.TASK_STATUS_TODO);
        return taskService.page(param, Page.of(1, pageSize));
    }

    @Override
    protected int processEntity(Map<String, String> params, TaskDTO taskDTO) throws Exception {
        MusicTrackDTO musicTrackDTO = musicTrackService.findById(taskDTO.getSubjectId());
        MusicAlbumDTO musicAlbumDTO = musicAlbumService.findById(musicTrackDTO.getAlbumId());
        Path audioPath = KaleidoUtil.getMusicFilePath(musicAlbumDTO.getPath(), musicTrackDTO.getFilename());
        AudioFile audioFile = AudioFileIO.read(audioPath.toFile());
        Tag tag = audioFile.getTag();
        if (AudioTagUtil.isChanged(tag, musicAlbumDTO, musicTrackDTO)) {
            AudioTagUtil.toTag(musicAlbumDTO, musicTrackDTO, tag);
            audioFile.commit();
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

    @Override
    protected String getMessage(TaskDTO taskDTO, Integer state) {
        MusicTrackDTO musicTrackDTO = musicTrackService.findById(taskDTO.getSubjectId());
        MusicAlbumDTO musicAlbumDTO = musicAlbumService.findById(musicTrackDTO.getAlbumId());
        String title = String.format("%s【%s】", musicTrackDTO.getTitle(), musicAlbumDTO.getTitle());
        return formatMessage(title, state);
    }
}
