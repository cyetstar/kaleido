package cc.onelooker.kaleido.schedule;

import cc.onelooker.kaleido.service.AsyncTaskManager;
import cc.onelooker.kaleido.service.movie.MovieManager;
import cc.onelooker.kaleido.service.system.SysDictTypeService;
import cc.onelooker.kaleido.thread.movie.MovieAnalyzeRunnable;
import cc.onelooker.kaleido.thread.movie.MovieCheckThreadStatusRunnable;
import cc.onelooker.kaleido.thread.movie.MovieCollectionCheckMovieStatusRunnable;
import cc.onelooker.kaleido.thread.movie.MovieSyncPlexRunnable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @Author cyetstar
 * @Date 2023-12-14 00:20:00
 * @Description TODO
 */
@Slf4j
@Component
public class JobManager {

    @Autowired
    private MovieCheckThreadStatusRunnable movieCheckThreadStatusRunnable;

    @Autowired
    private MovieCollectionCheckMovieStatusRunnable movieCollectionCheckMovieStatusRunnable;

    @Autowired
    private MovieSyncPlexRunnable movieSyncPlexRunnable;

    @Autowired
    private MovieAnalyzeRunnable movieAnalyzeRunnable;

    @Autowired
    private AsyncTaskManager taskManager;

    @Autowired
    private MovieManager movieManager;

    @Autowired
    private SysDictTypeService sysDictTypeService;

    @Scheduled(cron = "0 0 2 * * ?")
    public void syncPlexMovie() {
        movieSyncPlexRunnable.run();
    }

    @Scheduled(cron = "0 0 3 * * ?")
    public void syncPlexTvshow() {
        taskManager.syncPlexTvshow();
    }

    @Scheduled(cron = "0 30 3 * * ?")
    public void syncPlexMusic() {
        taskManager.syncPlexAlbum();
    }

    @Scheduled(cron = "0 15 1 * * ?")
    public void checkThreadStatus() {
        movieCheckThreadStatusRunnable.run();
    }

    @Scheduled(cron = "0 30 1 * * ?")
    public void checkMovieStatus() {
        movieCollectionCheckMovieStatusRunnable.run();
    }

    @Scheduled(cron = "0 40 1 * * ?")
    public void analyzeMovie() {
        movieAnalyzeRunnable.run();
    }

    @Scheduled(cron = "0 0 2 * * ?")
    public void syncDict() {
        sysDictTypeService.syncPlex();
    }

    @Scheduled(cron = "0 10 2 * * ?")
    public void syncDoubanWeekly() {
        movieManager.syncDoubanWeekly();
    }
}
