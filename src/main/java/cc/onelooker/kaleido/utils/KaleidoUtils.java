package cc.onelooker.kaleido.utils;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * @Author cyetstar
 * @Date 2023-12-02 21:16:00
 * @Description TODO
 */
public class KaleidoUtils {

    private static String[] noMainVideos = new String[]{"-other", "-CD2", "-CD3", "-CD4", "-CD5", "-CD6", "Part.2"};
    private static String[] videoExtensions = new String[]{"mkv", "mp4", "avi", "wmv", "rmvb", "ts"};

    public static String getMovieFolder(String path) {
        String plexMovieLibraryPath = ConfigUtils.getSysConfig("plexMovieLibraryPath");
        String movieLibraryPath = ConfigUtils.getSysConfig("movieLibraryPath");
        path = StringUtils.replace(path, plexMovieLibraryPath, movieLibraryPath);
        return path.substring(0, path.lastIndexOf("/"));
    }

    public static String getMusicFolder(String path) {
        String plexMovieLibraryPath = ConfigUtils.getSysConfig("plexMusicLibraryPath");
        String movieLibraryPath = ConfigUtils.getSysConfig("musicLibraryPath");
        path = StringUtils.replace(path, plexMovieLibraryPath, movieLibraryPath);
        return path.substring(0, path.lastIndexOf("/"));
    }

    public static boolean isVideoFile(String filename) {
        return FilenameUtils.isExtension(filename, videoExtensions);
    }
}
