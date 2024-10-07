package cc.onelooker.kaleido.schedule;

import cc.onelooker.kaleido.enums.ConfigKey;
import cc.onelooker.kaleido.service.MovieManager;
import cc.onelooker.kaleido.service.SysDictTypeService;
import cc.onelooker.kaleido.thread.*;
import cc.onelooker.kaleido.utils.ConfigUtils;
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
    private MovieSyncRunnable movieSyncPlexRunnable;

    @Autowired
    private MovieAnalyzeRunnable movieAnalyzeRunnable;

    @Autowired
    private MusicSyncRunnable musicSyncPlexRunnable;

    @Autowired
    private TvshowSyncRunnable tvshowSyncPlexRunnable;

    @Autowired
    private ComicSyncRunnable comicSyncRunnable;

    @Autowired
    private MovieManager movieManager;

    @Autowired
    private SysDictTypeService sysDictTypeService;

    @Autowired
    private MovieWriteNFORunnable movieWriteNFORunnable;

    @Autowired
    private TvshowWriteNFORunnable tvshowWriteNFORunnable;

    @Autowired
    private MusicWriteAudioTagRunnable musicWriteAudioTagRunnable;

    @Autowired
    private ComicWriteComicInfoRunnable comicWriteComicInfoRunnable;

    @Scheduled(cron = "0 30 2 * * ?")
    public void syncMovie() {
        if (ConfigUtils.isEnabled(ConfigKey.syncMovie)) {
            movieSyncPlexRunnable.run();
        }
    }

    @Scheduled(cron = "0 40 2 * * ?")
    public void syncTvshow() {
        if (ConfigUtils.isEnabled(ConfigKey.syncTvshow)) {
            tvshowSyncPlexRunnable.run();
        }
    }

    @Scheduled(cron = "0 50 2 * * ?")
    public void syncMusic() {
        if (ConfigUtils.isEnabled(ConfigKey.syncMusic)) {
            musicSyncPlexRunnable.run();
        }
    }

    @Scheduled(cron = "0 0 3 * * ?")
    public void syncComic() {
        if (ConfigUtils.isEnabled(ConfigKey.syncComic)) {
            comicSyncRunnable.run();
        }
    }

    @Scheduled(cron = "0 15 3 * * ?")
    public void checkThreadStatus() {
        if (ConfigUtils.isEnabled(ConfigKey.checkThreadStatus)) {
            movieCheckThreadStatusRunnable.run();
        }
    }

    @Scheduled(cron = "0 30 3 * * ?")
    public void checkMovieStatus() {
        if (ConfigUtils.isEnabled(ConfigKey.checkMovieStatus)) {
            movieCollectionCheckMovieStatusRunnable.run();
        }
    }

    /**
     * 每周二，1点15分执行
     */
    @Scheduled(cron = "0 15 1 ? * TUE")
    public void analyzeMovie() {
        if (ConfigUtils.isEnabled(ConfigKey.analyzeMovie)) {
            movieAnalyzeRunnable.run();
        }
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
    public void executeWriteMovieNFO() {
        if (ConfigUtils.isEnabled(ConfigKey.writeMovieNFO) && movieWriteNFORunnable.isNeedRun()) {
            movieWriteNFORunnable.run();
        }
    }

    @Scheduled(cron = "0 * * * * ?")
    public void executeWriteTvshowNFO() {
        if (ConfigUtils.isEnabled(ConfigKey.writeTvshowNFO) && tvshowWriteNFORunnable.isNeedRun()) {
            tvshowWriteNFORunnable.run();
        }
    }

    @Scheduled(cron = "0 * * * * ?")
    public void executeWriteAudioTag() {
        if (ConfigUtils.isEnabled(ConfigKey.writeAudioTag) && musicWriteAudioTagRunnable.isNeedRun()) {
            musicWriteAudioTagRunnable.run();
        }
    }

    @Scheduled(cron = "0 * * * * ?")
    public void executeWriteComicInfo() {
        if (ConfigUtils.isEnabled(ConfigKey.writeComicInfo) && comicWriteComicInfoRunnable.isNeedRun()) {
            comicWriteComicInfoRunnable.run();
        }
    }

}
