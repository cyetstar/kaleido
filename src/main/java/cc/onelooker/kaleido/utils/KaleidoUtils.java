package cc.onelooker.kaleido.utils;

import cc.onelooker.kaleido.enums.ConfigKey;
import com.github.houbb.opencc4j.util.ZhConverterUtil;
import com.google.common.collect.Sets;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RegExUtils;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

/**
 * @Author cyetstar
 * @Date 2023-12-02 21:16:00
 * @Description TODO
 */
public class KaleidoUtils {

    private static String[] noMainVideos = new String[]{"-other", "-CD2", "-CD3", "-CD4", "-CD5", "-CD6", "Part.2"};
    public static String[] videoExtensions = new String[]{"mkv", "mp4", "avi", "wmv", "rmvb", "ts", "m2ts"};
    public static String[] lowQualityExtensions = new String[]{"avi", "wmv", "rmvb", "mp4"};

    private static final String IMPORT = "import";
    private static final String RECYCLE = "#recycle";

    public static Path getMoviePath(String path) {
        String plexLibraryPath = ConfigUtils.getSysConfig(ConfigKey.plexMovieLibraryPath);
        String libraryPath = ConfigUtils.getSysConfig(ConfigKey.movieLibraryPath);
        path = StringUtils.replace(path, plexLibraryPath, libraryPath);
        return Paths.get(path.substring(0, path.lastIndexOf("/")));
    }

    public static Path getMovieImportPath() {
        String libraryPath = ConfigUtils.getSysConfig(ConfigKey.movieLibraryPath);
        return Paths.get(libraryPath).resolveSibling(IMPORT);
    }

    public static Path getMovieRecyclePath() {
        String libraryPath = ConfigUtils.getSysConfig(ConfigKey.movieLibraryPath);
        return Paths.get(libraryPath).resolveSibling(RECYCLE);
    }

    public static String getTvshowPath(String path) {
        String plexLibraryPath = ConfigUtils.getSysConfig(ConfigKey.plexTvshowLibraryPath);
        String libraryPath = ConfigUtils.getSysConfig(ConfigKey.tvshowLibraryPath);
        return StringUtils.replace(path, plexLibraryPath, libraryPath);
    }

    public static Path getTvshowImportPath() {
        String libraryPath = ConfigUtils.getSysConfig(ConfigKey.tvshowLibraryPath);
        return Paths.get(libraryPath).resolveSibling(IMPORT);
    }

    public static Path getTvshowRecyclePath() {
        String libraryPath = ConfigUtils.getSysConfig(ConfigKey.tvshowLibraryPath);
        return Paths.get(libraryPath).resolveSibling(RECYCLE);
    }

    public static Path getMusicPath(String path) {
        String plexLibraryPath = ConfigUtils.getSysConfig(ConfigKey.plexMusicLibraryPath);
        String libraryPath = ConfigUtils.getSysConfig(ConfigKey.musicLibraryPath);
        path = StringUtils.replace(path, plexLibraryPath, libraryPath);
        return Paths.get(path);
    }

    public static Path getMusicImportPath() {
        String libraryPath = ConfigUtils.getSysConfig(ConfigKey.musicLibraryPath);
        return Paths.get(libraryPath).resolveSibling(IMPORT);
    }

    public static Path getMusicRecyclePath() {
        String libraryPath = ConfigUtils.getSysConfig(ConfigKey.musicLibraryPath);
        return Paths.get(libraryPath).resolveSibling(RECYCLE);
    }

    public static Path getComicPath(String path) {
        String komgaLibraryPath = ConfigUtils.getSysConfig(ConfigKey.komgaComicLibraryPath);
        String libraryPath = ConfigUtils.getSysConfig(ConfigKey.comicLibraryPath);
        path = StringUtils.replace(path, komgaLibraryPath, libraryPath);
        return Paths.get(path);
    }

    public static Path getComicImportPath() {
        String libraryPath = ConfigUtils.getSysConfig(ConfigKey.comicLibraryPath);
        return Paths.get(libraryPath).resolveSibling(IMPORT);
    }

    public static Path getComicRecyclePath() {
        String libraryPath = ConfigUtils.getSysConfig(ConfigKey.comicLibraryPath);
        return Paths.get(libraryPath).resolveSibling(RECYCLE);
    }

    public static Path inverseComicPath(String path) {
        String komgaLibraryPath = ConfigUtils.getSysConfig(ConfigKey.komgaComicLibraryPath);
        String libraryPath = ConfigUtils.getSysConfig(ConfigKey.comicLibraryPath);
        path = StringUtils.replace(path, libraryPath, komgaLibraryPath);
        return Paths.get(path);
    }

    public static boolean isVideoFile(String filename) {
        return FilenameUtils.isExtension(filename, videoExtensions);
    }

    public static String getSimpleName(String name) {
        if (StringUtils.isEmpty(name)) {
            return name;
        }
        String simpleName = RegExUtils.removePattern(name, "'|’");
        simpleName = RegExUtils.removePattern(simpleName, "\\(.+\\)");
        simpleName = StringUtils.trim(simpleName);
        simpleName = ZhConverterUtil.toSimple(simpleName);
        return simpleName;
    }

    public static String getFormatSize(Long size) {
        if (size == null) {
            return StringUtils.EMPTY;
        }
        double kiloByte = (double) size / 1024;
        if (kiloByte < 1) {
            return "0KB";
        }
        //计算kiloByte
        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, RoundingMode.HALF_UP).toPlainString() + "KB";
        }
        //计算megaByte
        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, RoundingMode.HALF_UP).toPlainString() + "MB";
        }
        //计算gigaByte
        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, RoundingMode.HALF_UP).toPlainString() + "GB";
        }
        //计算TB
        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(2, RoundingMode.HALF_UP).toPlainString() + "TB";
    }

    public static String genComicFolder(String seriesTitle, String writerName, String pencillerName) {
        Set<String> authors = Sets.newLinkedHashSet();
        CollectionUtils.addIgnoreNull(authors, StringUtils.defaultIfEmpty(writerName, null));
        CollectionUtils.addIgnoreNull(authors, StringUtils.defaultIfEmpty(pencillerName, null));
        String authorName = StringUtils.join(authors, "×");
        String folder = String.format("%S [%S]", seriesTitle, authorName);
        folder = folder.replaceAll("[\\\\/:*?\"<>|]", "_");
        return folder;
    }

}
