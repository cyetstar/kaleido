package cc.onelooker.kaleido.thread;

import cc.onelooker.kaleido.dto.TaskDTO;
import cc.onelooker.kaleido.dto.TvshowEpisodeDTO;
import cc.onelooker.kaleido.dto.TvshowSeasonDTO;
import cc.onelooker.kaleido.dto.TvshowShowDTO;
import cc.onelooker.kaleido.enums.ConfigKey;
import cc.onelooker.kaleido.enums.SubjectType;
import cc.onelooker.kaleido.enums.TaskType;
import cc.onelooker.kaleido.nfo.EpisodeNFO;
import cc.onelooker.kaleido.nfo.NFOUtil;
import cc.onelooker.kaleido.nfo.SeasonNFO;
import cc.onelooker.kaleido.nfo.TvshowNFO;
import cc.onelooker.kaleido.service.*;
import cc.onelooker.kaleido.third.plex.PlexApiService;
import cc.onelooker.kaleido.utils.ConfigUtils;
import cc.onelooker.kaleido.utils.KaleidoConstants;
import cc.onelooker.kaleido.utils.KaleidoUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zjjcnt.common.core.domain.PageResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.util.Map;
import java.util.Objects;

/**
 * Created by cyetstar on 2021/1/7.
 */
@Slf4j
@Component
public class TvshowWriteNFORunnable extends AbstractEntityActionRunnable<TaskDTO> {

    private final TvshowManager tvshowManager;

    private final TvshowShowService tvshowShowService;

    private final TvshowSeasonService tvshowSeasonService;

    private final TvshowEpisodeService tvshowEpisodeService;

    private final TaskService taskService;

    private final PlexApiService plexApiService;

    public TvshowWriteNFORunnable(TvshowManager tvshowManager, TvshowShowService tvshowShowService, TvshowSeasonService tvshowSeasonService, TvshowEpisodeService tvshowEpisodeService, TaskService taskService, PlexApiService plexApiService) {
        this.tvshowManager = tvshowManager;
        this.tvshowShowService = tvshowShowService;
        this.tvshowSeasonService = tvshowSeasonService;
        this.tvshowEpisodeService = tvshowEpisodeService;
        this.taskService = taskService;
        this.plexApiService = plexApiService;
    }

    @Override
    public Action getAction() {
        return Action.tvshowWriteNFO;
    }

    @Override
    public boolean isNeedRun() {
        PageResult<TaskDTO> pageResult = page(null, 1, 1);
        return !pageResult.isEmpty();
    }

    @Override
    protected PageResult<TaskDTO> page(Map<String, String> params, int pageNumber, int pageSize) {
        TaskDTO param = new TaskDTO();
        param.setTaskType(TaskType.writeTvshowNFO.name());
        param.setTaskStatus(KaleidoConstants.TASK_STATUS_TODO);
        return taskService.page(param, Page.of(1, pageSize));
    }

    @Override
    protected int processEntity(Map<String, String> params, TaskDTO taskDTO) throws Exception {
        String taskStatus = KaleidoConstants.TASK_STATUS_IGNORE;
        if (StringUtils.equals(SubjectType.TvshowShow.name(), taskDTO.getSubjectType())) {
            TvshowShowDTO tvshowShowDTO = tvshowManager.findTvshowShow(taskDTO.getSubjectId());
            Path path = KaleidoUtil.getTvshowPath(tvshowShowDTO.getPath());
            TvshowSeasonDTO tvshowSeasonDTO = tvshowManager.findTvshowSeason(tvshowShowDTO.getFirstSeason().getId());
            TvshowNFO tvshowNFO = NFOUtil.read(TvshowNFO.class, path, KaleidoConstants.SHOW_NFO);
            TvshowNFO newTvshowNFO = NFOUtil.toTvshowNFO(tvshowShowDTO, tvshowSeasonDTO);
            if (tvshowNFO == null || !Objects.equals(tvshowNFO, newTvshowNFO)) {
                NFOUtil.write(newTvshowNFO, TvshowNFO.class, path, KaleidoConstants.SHOW_NFO);
                if (ConfigUtils.isEnabled(ConfigKey.refreshMetadata)) {
                    //如果大量刷新，否则可能会给Plex带来性能灾难
                    plexApiService.refreshMetadata(tvshowShowDTO.getId());
                }
                taskStatus = KaleidoConstants.TASK_STATUS_DONE;
            }
        } else if (StringUtils.equals(SubjectType.TvshowSeason.name(), taskDTO.getSubjectType())) {
            TvshowSeasonDTO tvshowSeasonDTO = tvshowManager.findTvshowSeason(taskDTO.getSubjectId());
            TvshowShowDTO tvshowShowDTO = tvshowManager.findTvshowShow(tvshowSeasonDTO.getShowId());
            Path path = KaleidoUtil.getTvshowPath(tvshowShowDTO.getPath());
            String seasonFolder = KaleidoUtil.genSeasonFolder(tvshowSeasonDTO.getSeasonIndex());
            Path seasonPath = path.resolve(seasonFolder);
            SeasonNFO seasonNFO = NFOUtil.read(SeasonNFO.class, seasonPath, KaleidoConstants.SEASON_NFO);
            SeasonNFO newSeasonNFO = NFOUtil.toSeasonNFO(tvshowShowDTO, tvshowSeasonDTO);
            if (seasonNFO == null || !Objects.equals(seasonNFO, newSeasonNFO)) {
                NFOUtil.write(newSeasonNFO, SeasonNFO.class, seasonPath, KaleidoConstants.SEASON_NFO);
                if (ConfigUtils.isEnabled(ConfigKey.refreshMetadata)) {
                    //如果大量刷新，否则可能会给Plex带来性能灾难
                    plexApiService.refreshMetadata(tvshowSeasonDTO.getId());
                }
                taskStatus = KaleidoConstants.TASK_STATUS_DONE;
            }
        } else if (StringUtils.equals(SubjectType.TvshowEpisode.name(), taskDTO.getSubjectType())) {
            TvshowEpisodeDTO tvshowEpisodeDTO = tvshowEpisodeService.findById(taskDTO.getSubjectId());
            TvshowSeasonDTO tvshowSeasonDTO = tvshowManager.findTvshowSeason(tvshowEpisodeDTO.getSeasonId());
            TvshowShowDTO tvshowShowDTO = tvshowManager.findTvshowShow(tvshowSeasonDTO.getShowId());
            Path path = KaleidoUtil.getTvshowPath(tvshowShowDTO.getPath());
            String seasonFolder = KaleidoUtil.genSeasonFolder(tvshowSeasonDTO.getSeasonIndex());
            Path seasonPath = path.resolve(seasonFolder);
            String filename = KaleidoUtil.genEpisodeNfoFilename(tvshowEpisodeDTO.getFilename());
            EpisodeNFO episodeNFO = NFOUtil.read(EpisodeNFO.class, seasonPath, filename);
            EpisodeNFO newEpisodeNFO = NFOUtil.toEpisodeNFO(tvshowShowDTO, tvshowSeasonDTO, tvshowEpisodeDTO);
            if (episodeNFO == null || !Objects.equals(episodeNFO, newEpisodeNFO)) {
                NFOUtil.write(newEpisodeNFO, EpisodeNFO.class, seasonPath, filename);
                if (ConfigUtils.isEnabled(ConfigKey.refreshMetadata)) {
                    //如果大量刷新，否则可能会给Plex带来性能灾难
                    plexApiService.refreshMetadata(tvshowEpisodeDTO.getId());
                }
                taskStatus = KaleidoConstants.TASK_STATUS_DONE;
            }
        }
        taskService.updateTaskStatus(taskDTO.getId(), taskStatus);
        return StringUtils.equals(taskStatus, KaleidoConstants.TASK_STATUS_DONE) ? SUCCESS : IGNORE;
    }

    @Override
    protected void processError(Map<String, String> params, TaskDTO taskDTO, Exception e) {
        super.processError(params, taskDTO, e);
        taskService.updateTaskStatus(taskDTO.getId(), KaleidoConstants.TASK_STATUS_ERROR);
    }

    @Override
    protected String getMessage(TaskDTO taskDTO, Integer state) {
        String title = null;
        if (StringUtils.equals(SubjectType.TvshowShow.name(), taskDTO.getSubjectType())) {
            title = tvshowShowService.findById(taskDTO.getSubjectId()).getTitle();
        } else if (StringUtils.equals(SubjectType.TvshowSeason.name(), taskDTO.getSubjectType())) {
            title = tvshowSeasonService.findById(taskDTO.getSubjectId()).getTitle();
        } else if (StringUtils.equals(SubjectType.TvshowEpisode.name(), taskDTO.getSubjectType())) {
            TvshowEpisodeDTO tvshowEpisodeDTO = tvshowEpisodeService.findById(taskDTO.getSubjectId());
            TvshowSeasonDTO tvshowSeasonDTO = tvshowSeasonService.findById(tvshowEpisodeDTO.getSeasonId());
            title = String.format("第%s集【%s】", tvshowEpisodeDTO.getEpisodeIndex(), tvshowSeasonDTO.getTitle());
        }
        return formatMessage(title, state);
    }
}
