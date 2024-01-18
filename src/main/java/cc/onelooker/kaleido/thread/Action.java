package cc.onelooker.kaleido.thread;

/**
 * Created by cyetstar on 2021/1/10.
 */
public enum Action {

    movieUpdateSource("更新文件源"),
    movieSyncPlex("同步Plex"),
    movieReadNFO("读取NFO"),
    movieExportNFO("导出NFO"),
    movieCheckThreadStatus("检测发布"),
    movieCollectionSyncDouban("同步豆列"),
    movieCollectionSyncDoubanAll("同步全部豆列"),
    movieCollectionCheckMovieStatus("检测豆列收藏情况");

    private String title;

    Action(String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }
}