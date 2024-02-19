package cc.onelooker.kaleido.utils;

import cc.onelooker.kaleido.enums.ConfigKey;
import com.github.houbb.opencc4j.util.ZhConverterUtil;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RegExUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * @Author cyetstar
 * @Date 2023-12-02 21:16:00
 * @Description TODO
 */
public class KaleidoUtils {

    private static String[] noMainVideos = new String[]{"-other", "-CD2", "-CD3", "-CD4", "-CD5", "-CD6", "Part.2"};
    public static String[] videoExtensions = new String[]{"mkv", "mp4", "avi", "wmv", "rmvb", "ts", "m2ts"};
    public static String[] lowQualityExtensions = new String[]{"avi", "wmv", "rmvb", "mp4"};

    public static String getMovieFolder(String path) {
        String plexLibraryPath = ConfigUtils.getSysConfig(ConfigKey.plexMovieLibraryPath);
        String libraryPath = ConfigUtils.getSysConfig(ConfigKey.movieLibraryPath);
        path = StringUtils.replace(path, plexLibraryPath, libraryPath);
        return path.substring(0, path.lastIndexOf("/"));
    }

    public static String getTvshowFolder(String path) {
        String plexLibraryPath = ConfigUtils.getSysConfig(ConfigKey.plexTvshowLibraryPath);
        String libraryPath = ConfigUtils.getSysConfig(ConfigKey.tvshowLibraryPath);
        return StringUtils.replace(path, plexLibraryPath, libraryPath);
    }

    public static String getMusicFolder(String path) {
        String plexLibraryPath = ConfigUtils.getSysConfig(ConfigKey.plexMusicLibraryPath);
        String libraryPath = ConfigUtils.getSysConfig(ConfigKey.musicLibraryPath);
        path = StringUtils.replace(path, plexLibraryPath, libraryPath);
        return path.substring(0, path.lastIndexOf("/"));
    }

    public static boolean isVideoFile(String filename) {
        return FilenameUtils.isExtension(filename, videoExtensions);
    }

    public static String getSimpleName(String name){
        if (StringUtils.isEmpty(name)) {
            return name;
        }
        String simpleName = RegExUtils.removePattern(name, "'|’");
        simpleName = RegExUtils.removePattern(simpleName, "\\(.+\\)");
        simpleName = StringUtils.trim(simpleName);
        simpleName = ZhConverterUtil.toSimple(simpleName);
        return simpleName;
    }
}
