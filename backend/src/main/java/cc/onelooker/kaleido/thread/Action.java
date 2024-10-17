package cc.onelooker.kaleido.thread;

/**
 * Created by cyetstar on 2021/1/10.
 */
public enum Action {

    movieUpdateSource("更新电影源"), movieSync("同步Plex电影库"), movieMatchInfo("自动匹配电影信息"), movieWriteNFO("导出电影NFO"),

    movieCollectionSyncDouban("同步豆列"), movieCollectionSyncDoubanAll("同步全部豆列"), movieCollectionCheckMovieStatus("检测豆列收藏情况"), movieAnalyze("分析电影信息"),

    musicUpdateSource("更新音乐源"), musicSync("同步Plex音乐库"), musicMatchInfo("自动匹配音乐信息"), musicWriteAudioTag("覆写音乐标签"), musicDownloadLyric("下载歌词"),

    tvshowUpdateSource("更新剧集源"), tvshowSync("同步Plex剧集库"), tvshowMatchInfo("自动匹配剧集信息"), tvshowWriteNFO("导出剧集NFO"),

    comicUpdateSource("更新漫画源"), comicSync("同步Komga漫画库"), comicMatchInfo("自动匹配漫画信息"), comicWriteComicInfo("导出ComicInfo"),

    tableOptimize("优化数据库"),
    ;

    private final String title;

    Action(String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }
}
