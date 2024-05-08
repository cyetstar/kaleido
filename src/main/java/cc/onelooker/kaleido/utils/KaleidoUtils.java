package cc.onelooker.kaleido.utils;

import cc.onelooker.kaleido.enums.ConfigKey;
import cc.onelooker.kaleido.nfo.ComicInfoNFO;
import cc.onelooker.kaleido.third.tmm.Comic;
import com.github.houbb.opencc4j.util.ZhConverterUtil;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RegExUtils;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;

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

    public static String getComicFolder(String path) {
        String libraryPath = ConfigUtils.getSysConfig(ConfigKey.comicLibraryPath);
        return StringUtils.replace(path, "/comic", libraryPath);
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

    public static String genComicFolder(ComicInfoNFO comicInfoNFO) {
        String folder = String.format("%S [%S]", comicInfoNFO.getSeries(), comicInfoNFO.getAuthors());
        folder = folder.replaceAll("[\\\\/:*?\"<>|]", "_");
        return folder;
    }

    public static String genComicFolder(Comic comic) {
        String folder = String.format("%S [%S]", comic.getSeries(), comic.getAuthors());
        folder = folder.replaceAll("[\\\\/:*?\"<>|]", "_");
        return folder;
    }

}
