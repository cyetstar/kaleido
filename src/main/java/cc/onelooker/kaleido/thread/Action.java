package cc.onelooker.kaleido.thread;

/**
 * Created by cyetstar on 2021/1/10.
 */
public enum Action {

    movieUpdateSource("更新电影源"),
    movieSync("同步Plex电影库"),
    movieMatchInfo("匹配信息"),
    movieReadNFO("读取NFO"),
    movieExportNFO("导出NFO"),
    movieCheckThreadStatus("检测发布"),
    movieCollectionSyncDouban("同步豆列"),
    movieCollectionSyncDoubanAll("同步全部豆列"),
    movieCollectionCheckMovieStatus("检测豆列收藏情况"),
    movieAnalyze("分析电影信息"),
    movieWriteNFO("写入NFO"),

    musicSyncPlex("同步Plex音乐库"),
    musicReadAudioTag("读取音乐标签"),
    musicDownloadLyric("下载歌词"),

    tvshowSyncPlex("同步Plex剧集库"),
    tvshowUpdateSource("更新剧集源"),

    comicSync("漫画同步"),
    comicUpdateSource("漫画更新源"),
    comicReadComicInfo("读取ComicInfo"),
    comicWriteComicInfo("写入ComicInfo");

    private String title;

    Action(String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }
}
