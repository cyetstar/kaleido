package cc.onelooker.kaleido.schedule;

import cc.onelooker.kaleido.service.AsyncTaskManager;
import cc.onelooker.kaleido.service.movie.MovieManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @Author xiadawei
 * @Date 2023-12-14 00:20:00
 * @Description TODO
 */
@Slf4j
@Component
public class JobManager {

    @Autowired
    private AsyncTaskManager taskManager;

    @Autowired
    private MovieManager movieManager;

    @Scheduled(cron = "0 0 2 * * ?")
    public void syncPlexMovie() {
        taskManager.syncPlexMovie();
    }

    @Scheduled(cron = "0 30 2 * * ?")
    public void syncPlexMovieCollection() {
        taskManager.syncPlexMovieCollection();
    }

    @Scheduled(cron = "0 0 3 * * ?")
    public void syncPlexTvshow() {
        taskManager.syncPlexTvshow();
    }

    @Scheduled(cron = "0 30 3 * * ?")
    public void syncPlexMusic() {
        taskManager.syncPlexAlbum();
    }

    @Scheduled(cron = "0 5 0 * * ?")
    public void checkThreadStatus() {
        taskManager.checkThreadStatus();
    }

    public void syncPlexMovieAttribute() {

    }

    @Scheduled(cron = "0 0 21 ? * FRI")
    public void syncDoubanWeekly() {
        movieManager.syncDoubanWeekly();
    }
}
