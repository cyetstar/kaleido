package cc.onelooker.kaleido.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * @Author xiadawei
 * @Date 2023-12-02 21:16:00
 * @Description TODO
 */
public class PlexUtils {

    public static String getMovieFolder(String path) {
        String plexMovieLibraryPath = ConfigUtils.getSysConfig("plexMovieLibraryPath");
        String movieLibraryPath = ConfigUtils.getSysConfig("movieLibraryPath");
        path = StringUtils.replace(path, plexMovieLibraryPath, movieLibraryPath);
        return path.substring(0, path.lastIndexOf("/"));
    }
}
