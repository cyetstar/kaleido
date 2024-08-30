package cc.onelooker.kaleido.utils;

import cc.onelooker.kaleido.dto.AuthorDTO;
import cc.onelooker.kaleido.dto.ComicSeriesDTO;
import cc.onelooker.kaleido.dto.MovieBasicDTO;
import cc.onelooker.kaleido.dto.TvshowShowDTO;
import cc.onelooker.kaleido.enums.ConfigKey;
import cc.onelooker.kaleido.third.plex.Media;
import cn.hutool.extra.pinyin.PinyinUtil;
import com.github.houbb.opencc4j.util.ZhConverterUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.zjjcnt.common.util.constant.Constants;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RegExUtils;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
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

    public static Path getMovieBasicPath(String path) {
        String plexLibraryPath = ConfigUtils.getSysConfig(ConfigKey.plexMovieLibraryPath);
        path = StringUtils.removeStart(path, plexLibraryPath);
        if (StringUtils.startsWith(path, Constants.SLASH)) {
            path = StringUtils.removeStart(path, Constants.SLASH);
        }
        return Paths.get(path);
    }

    public static Path getMoviePath(String path) {
        if (StringUtils.startsWith(path, Constants.SLASH)) {
            path = StringUtils.removeStart(path, Constants.SLASH);
        }
        return getMovieLibraryPath().resolve(path);
    }

    public static Path getMovieLibraryPath() {
        String libraryPath = ConfigUtils.getSysConfig(ConfigKey.movieLibraryPath);
        return Paths.get(libraryPath);
    }

    public static Path getMovieImportPath() {
        return getMovieLibraryPath().resolveSibling(IMPORT);
    }

    public static Path getMovieRecyclePath() {
        return getMovieLibraryPath().resolveSibling(RECYCLE);
    }

    public static Path getTvshowBasicPath(String path) {
        String plexLibraryPath = ConfigUtils.getSysConfig(ConfigKey.plexTvshowLibraryPath);
        path = StringUtils.removeStart(path, plexLibraryPath);
        if (StringUtils.startsWith(path, Constants.SLASH)) {
            path = StringUtils.removeStart(path, Constants.SLASH);
        }
        return Paths.get(path);
    }

    public static Path getTvshowPath(String path) {
        if (StringUtils.startsWith(path, Constants.SLASH)) {
            path = StringUtils.removeStart(path, Constants.SLASH);
        }
        return getTvshowLibraryPath().resolve(path);
    }

    public static Path getTvshowLibraryPath() {
        String libraryPath = ConfigUtils.getSysConfig(ConfigKey.tvshowLibraryPath);
        return Paths.get(libraryPath);
    }

    public static Path getTvshowImportPath() {
        return getTvshowLibraryPath().resolveSibling(IMPORT);
    }

    public static Path getTvshowRecyclePath() {
        return getTvshowLibraryPath().resolveSibling(RECYCLE);
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

    public static Path getComicBasicPath(String path) {
        String komgaLibraryPath = ConfigUtils.getSysConfig(ConfigKey.komgaComicLibraryPath);
        path = StringUtils.removeStart(path, komgaLibraryPath);
        if (StringUtils.startsWith(path, Constants.SLASH)) {
            path = StringUtils.removeStart(path, Constants.SLASH);
        }
        return Paths.get(path);
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

    public static Path inverseMoviePath(String path) {
        String plexLibraryPath = ConfigUtils.getSysConfig(ConfigKey.plexMovieLibraryPath);
        String libraryPath = ConfigUtils.getSysConfig(ConfigKey.movieLibraryPath);
        path = StringUtils.replace(path, libraryPath, plexLibraryPath);
        return Paths.get(path);
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

    public static String genTitleSort(String title) {
        if (StringUtils.isNotEmpty(title)) {
            String titleSort = PinyinUtil.getFirstLetter(title, StringUtils.EMPTY);
            titleSort = StringUtils.lowerCase(titleSort);
            titleSort = titleSort.replaceAll("[^a-zA-Z0-9]", StringUtils.EMPTY);
            return titleSort;
        }
        return StringUtils.EMPTY;
    }

    public static String genComicFolder(ComicSeriesDTO comicSeriesDTO) {
        Optional<String> writerOptional = comicSeriesDTO.getWriterList().stream().map(AuthorDTO::getName).findFirst();
        Optional<String> pencillerOptional = comicSeriesDTO.getPencillerList().stream().map(AuthorDTO::getName).findFirst();
        Set<String> authors = Sets.newLinkedHashSet();
        CollectionUtils.addIgnoreNull(authors, writerOptional.orElse(null));
        CollectionUtils.addIgnoreNull(authors, pencillerOptional.orElse(null));
        String authorName = StringUtils.join(authors, "×");
        return String.format("%s [%s]", sanitizeFileName(comicSeriesDTO.getTitle()), sanitizeFileName(authorName));
    }

    public static String genMovieFolder(MovieBasicDTO movieBasicDTO) {
        String decade = movieBasicDTO.getDecade();
        if (StringUtils.isEmpty(decade)) {
            decade = StringUtils.substring(movieBasicDTO.getYear(), 0, 3) + "0s";
        }
        return String.format("%s/%s (%s)", decade, sanitizeFileName(movieBasicDTO.getTitle()), movieBasicDTO.getYear());
    }

    public static String genShowFolder(TvshowShowDTO tvshowShowDTO) {
        return String.format("%s (%s)", sanitizeFileName(tvshowShowDTO.getTitle()), tvshowShowDTO.getYear());
    }

    public static String genSeasonFolder(Integer seasonIndex) {
        return "Season " + StringUtils.leftPad(String.valueOf(seasonIndex), 2, "0");
    }

    public static boolean isChineseStream(Media.Stream stream) {
        List<String> values = Lists.newArrayList();
        CollectionUtils.addIgnoreNull(values, stream.getLanguage());
        CollectionUtils.addIgnoreNull(values, stream.getLanguageTag());
        CollectionUtils.addIgnoreNull(values, stream.getLanguageCode());
        CollectionUtils.addIgnoreNull(values, stream.getTitle());
        return values.stream().anyMatch(s -> StringUtils.equalsAnyIgnoreCase(s, "zh", "chs", "cht", "ch", "中文", "中字", "简中", "繁中", "简体中文", "简体中字", "繁体中文", "繁体中字", "普通话", "国语"));
    }

    private static String sanitizeFileName(String fileName) {
        // 将字符串中的非法字符替换为下划线
        fileName = fileName.replaceAll("[\\\\/:*?\"<>|\\s]", Constants.UNDER_LINE);
        fileName = fileName.replaceAll("_+", Constants.UNDER_LINE);
        fileName = fileName.replaceAll("^_+|_+$", StringUtils.EMPTY);
        return fileName;
    }

}
