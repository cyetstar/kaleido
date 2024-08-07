package cc.onelooker.kaleido.schedule;

import cc.onelooker.kaleido.service.movie.MovieManager;
import cc.onelooker.kaleido.service.system.SysDictTypeService;
import cc.onelooker.kaleido.thread.ComicSyncRunnable;
import cc.onelooker.kaleido.thread.ComicWriteComicInfoRunnable;
import cc.onelooker.kaleido.thread.MovieAnalyzeRunnable;
import cc.onelooker.kaleido.thread.MovieCheckThreadStatusRunnable;
import cc.onelooker.kaleido.thread.MovieCollectionCheckMovieStatusRunnable;
import cc.onelooker.kaleido.thread.MovieSyncPlexRunnable;
import cc.onelooker.kaleido.thread.MusicSyncPlexRunnable;
import cc.onelooker.kaleido.thread.TvshowSyncPlexRunnable;
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
    private MusicSyncPlexRunnable musicSyncPlexRunnable;

    @Autowired
    private TvshowSyncPlexRunnable tvshowSyncPlexRunnable;

    @Autowired
    private ComicSyncRunnable comicSyncRunnable;

    @Autowired
    private MovieManager movieManager;

    @Autowired
    private SysDictTypeService sysDictTypeService;

    @Autowired
    private ComicWriteComicInfoRunnable comicWriteComicInfoRunnable;

    @Scheduled(cron = "0 0 2 * * ?")
    public void syncPlexMovie() {
        movieSyncPlexRunnable.run();
    }

    @Scheduled(cron = "0 0 3 * * ?")
    public void syncPlexTvshow() {
        tvshowSyncPlexRunnable.run();
    }

    @Scheduled(cron = "0 30 3 * * ?")
    public void syncPlexMusic() {
        musicSyncPlexRunnable.run();
    }

    @Scheduled(cron = "0 30 2 * * ?")
    public void syncComic() {
        comicSyncRunnable.run();
    }

    @Scheduled(cron = "0 15 1 * * ?")
    public void checkThreadStatus() {
        movieCheckThreadStatusRunnable.run();
    }

    @Scheduled(cron = "0 30 1 * * ?")
    public void checkMovieStatus() {
        movieCollectionCheckMovieStatusRunnable.run();
    }

    /**
     * 每周二，1点15分执行
     */
    @Scheduled(cron = "0 15 1 ? * TUE")
    public void analyzeMovie() {
        movieAnalyzeRunnable.run();
    }

    @Scheduled(cron = "0 45 2 * * ?")
    public void syncDict() {
        sysDictTypeService.syncPlex();
    }

    @Scheduled(cron = "0 10 2 * * ?")
    public void syncDoubanWeekly() {
        movieManager.syncDoubanWeekly();
    }

    @Scheduled(cron = "0 * * * * ?")
    public void executeTask() {
        if (comicWriteComicInfoRunnable.isNeedRun()) {
            comicWriteComicInfoRunnable.run();
        }
    }

}
