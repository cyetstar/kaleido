package cc.onelooker.kaleido.utils;

import cc.onelooker.kaleido.dto.*;
import cc.onelooker.kaleido.enums.ConfigKey;
import cc.onelooker.kaleido.nfo.MovieNFO;
import cc.onelooker.kaleido.third.plex.Media;
import cc.onelooker.kaleido.third.tmm.Album;
import cc.onelooker.kaleido.third.tmm.Comic;
import cc.onelooker.kaleido.third.tmm.Movie;
import cc.onelooker.kaleido.third.tmm.Tvshow;
import cn.hutool.extra.pinyin.PinyinUtil;
import com.github.houbb.opencc4j.util.ZhConverterUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.zjjcnt.common.util.constant.Constants;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RegExUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author cyetstar
 * @Date 2023-12-02 21:16:00
 * @Description TODO
 */
@Component
public class KaleidoUtil {

    public static final String VIDEO_EXTENSION = "mkv,mp4,mpeg,mov,avi,wmv,rmvb,ts,m2ts";
    public static final String COMIC_ZIP_EXTENSION = "zip,rar,cbz";
    public static final String AUDIO_ZIP_EXTENSION = "mp3,wav,flac";
    public static String[] lowQualityExtensions = new String[] { "avi", "wmv", "rmvb", "mp4" };

    private static final Pattern seasonIndexPattern = Pattern.compile("S_?(\\d+)E");
    private static final Pattern episodeIndexPattern = Pattern.compile("E[pP_]?(\\d+)");
    private static final Pattern volumeNumberPattern = Pattern.compile("[Vv]ol[._](\\d+)");
    private static final Pattern trackIndexPattern = Pattern.compile("^(\\d{1,3})[\\s.-]*(.*)$");
    private static final String IMPORT = "import";
    private static final String RECYCLE = "#recycle";

    private static String pathMapping = "";

    @Value("${path-mapping:}")
    public void setPathMapping(String value) {
        KaleidoUtil.pathMapping = value;
    }

    // -----------movie--------------//
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

    public static Path getMovieRecyclePath(String path) {
        path = StringUtils.removeStart(path, getMovieLibraryPath().toString());
        if (StringUtils.startsWith(path, Constants.SLASH)) {
            path = StringUtils.removeStart(path, Constants.SLASH);
        }
        return getMovieRecyclePath().resolve(path);
    }

    public static Path getMovieLibraryPath() {
        String libraryPath = ConfigUtils.getSysConfig(ConfigKey.movieLibraryPath);
        libraryPath = pathMapping + libraryPath;
        return Paths.get(libraryPath);
    }

    public static Path getMovieImportPath() {
        return getMovieLibraryPath().resolveSibling(IMPORT);
    }

    private static Path getMovieRecyclePath() {
        return getMovieLibraryPath().resolveSibling(RECYCLE);
    }

    // -----------tvshow--------------//

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

    public static Path getTvshowRecyclePath(String path) {
        path = StringUtils.removeStart(path, getTvshowLibraryPath().toString());
        if (StringUtils.startsWith(path, Constants.SLASH)) {
            path = StringUtils.removeStart(path, Constants.SLASH);
        }
        return getTvshowRecyclePath().resolve(path);
    }

    public static Path getTvshowLibraryPath() {
        String libraryPath = ConfigUtils.getSysConfig(ConfigKey.tvshowLibraryPath);
        libraryPath = pathMapping + libraryPath;
        return Paths.get(libraryPath);
    }

    public static Path getTvshowImportPath() {
        return getTvshowLibraryPath().resolveSibling(IMPORT);
    }

    private static Path getTvshowRecyclePath() {
        return getTvshowLibraryPath().resolveSibling(RECYCLE);
    }

    // -----------music--------------//
    public static Path getMusicFilePath(String path, String filename) {
        if (StringUtils.startsWith(path, Constants.SLASH)) {
            path = StringUtils.removeStart(path, Constants.SLASH);
        }
        path = FilenameUtils.concat(path, filename);
        return getMusicLibraryPath().resolve(path);
    }

    public static Path getMusicPath(String path) {
        if (StringUtils.startsWith(path, Constants.SLASH)) {
            path = StringUtils.removeStart(path, Constants.SLASH);
        }
        return getMusicLibraryPath().resolve(path);
    }

    public static Path getMusicBasicPath(String path) {
        String plexLibraryPath = ConfigUtils.getSysConfig(ConfigKey.plexMusicLibraryPath);
        path = StringUtils.removeStart(path, plexLibraryPath);
        if (StringUtils.startsWith(path, Constants.SLASH)) {
            path = StringUtils.removeStart(path, Constants.SLASH);
        }
        return Paths.get(path);
    }

    public static Path getMusicLibraryPath() {
        String libraryPath = ConfigUtils.getSysConfig(ConfigKey.musicLibraryPath);
        libraryPath = pathMapping + libraryPath;
        return Paths.get(libraryPath);
    }

    public static Path getMusicImportPath() {
        return getMusicLibraryPath().resolveSibling(IMPORT);
    }

    public static Path getMusicRecyclePath(String path) {
        path = StringUtils.removeStart(path, getMusicLibraryPath().toString());
        if (StringUtils.startsWith(path, Constants.SLASH)) {
            path = StringUtils.removeStart(path, Constants.SLASH);
        }
        return getMusicRecyclePath().resolve(path);
    }

    private static Path getMusicRecyclePath() {
        return getMusicLibraryPath().resolveSibling(RECYCLE);
    }

    // -----------comic--------------//
    public static Path getComicFilePath(String path, String filename) {
        if (StringUtils.startsWith(path, Constants.SLASH)) {
            path = StringUtils.removeStart(path, Constants.SLASH);
        }
        path = FilenameUtils.concat(path, filename);
        return getComicLibraryPath().resolve(path);
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
        if (StringUtils.startsWith(path, Constants.SLASH)) {
            path = StringUtils.removeStart(path, Constants.SLASH);
        }
        return getComicLibraryPath().resolve(path);
    }

    public static Path getComicRecyclePath(String path) {
        path = StringUtils.removeStart(path, getComicLibraryPath().toString());
        if (StringUtils.startsWith(path, Constants.SLASH)) {
            path = StringUtils.removeStart(path, Constants.SLASH);
        }
        return getComicRecyclePath().resolve(path);
    }

    public static Path getComicLibraryPath() {
        String libraryPath = ConfigUtils.getSysConfig(ConfigKey.comicLibraryPath);
        libraryPath = pathMapping + libraryPath;
        return Paths.get(libraryPath);
    }

    public static Path getComicImportPath() {
        return getComicLibraryPath().resolveSibling(IMPORT);
    }

    private static Path getComicRecyclePath() {
        return getComicLibraryPath().resolveSibling(RECYCLE);
    }

    // -----------utils--------------//
    public static String genSongSimpleName(String name) {
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
        // 计算kiloByte
        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, RoundingMode.HALF_UP).toPlainString() + "KB";
        }
        // 计算megaByte
        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, RoundingMode.HALF_UP).toPlainString() + "MB";
        }
        // 计算gigaByte
        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, RoundingMode.HALF_UP).toPlainString() + "GB";
        }
        // 计算TB
        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(2, RoundingMode.HALF_UP).toPlainString() + "TB";
    }

    public static String genSortTitle(String title) {
        if (StringUtils.isNotEmpty(title)) {
            String titleSort = PinyinUtil.getFirstLetter(title, StringUtils.EMPTY);
            titleSort = StringUtils.lowerCase(titleSort);
            titleSort = titleSort.replaceAll("[^a-zA-Z0-9]", StringUtils.EMPTY);
            return titleSort;
        }
        return StringUtils.EMPTY;
    }

    public static String genMovieFolder(MovieBasicDTO movieBasicDTO) {
        String decade = movieBasicDTO.getDecade();
        if (StringUtils.isEmpty(decade) && StringUtils.isNotEmpty(movieBasicDTO.getYear())) {
            decade = StringUtils.substring(movieBasicDTO.getYear(), 0, 3) + "0s";
        }
        decade = StringUtils.defaultIfEmpty(decade, "0000s");
        String title = sanitizeFileName(movieBasicDTO.getTitle());
        String year = StringUtils.defaultString(movieBasicDTO.getYear(), "0000");
        return String.format("%s/%s (%s)", decade, title, year);
    }

    public static String genTvshowFolder(TvshowShowDTO tvshowShowDTO) {
        return String.format("%s (%s)", sanitizeFileName(tvshowShowDTO.getTitle()), tvshowShowDTO.getYear());
    }

    public static String genSeasonFolder(Integer seasonIndex) {
        return "Season " + StringUtils.leftPad(String.valueOf(seasonIndex), 2, "0");
    }

    public static String genMusicFolder(MusicAlbumDTO musicAlbumDTO) {
        String year = StringUtils.defaultString(musicAlbumDTO.getYear(), "0000");
        String artist = sanitizeFileName(musicAlbumDTO.getArtists());
        String title = sanitizeFileName(musicAlbumDTO.getTitle());
        // 如果最后一个字符是. 在群晖中会出现乱码
        return String.format("%s/%s - %s (%s)", StringUtils.removeEnd(artist, Constants.DOT), artist, title, year);
    }

    public static String genComicFolder(ComicSeriesDTO comicSeriesDTO) {
        Optional<String> writerOptional = comicSeriesDTO.getWriterList().stream().map(AuthorDTO::getName).findFirst();
        Optional<String> pencillerOptional = comicSeriesDTO.getPencillerList().stream().map(AuthorDTO::getName)
                .findFirst();
        Set<String> authors = Sets.newLinkedHashSet();
        CollectionUtils.addIgnoreNull(authors, writerOptional.orElse(null));
        CollectionUtils.addIgnoreNull(authors, pencillerOptional.orElse(null));
        String authorName = StringUtils.join(authors, "×");
        if (StringUtils.isEmpty(authorName)) {
            return String.format("%s", sanitizeFileName(comicSeriesDTO.getTitle()));
        }
        return String.format("%s [%s]", sanitizeFileName(comicSeriesDTO.getTitle()), sanitizeFileName(authorName));
    }

    public static String genMusicTrackFilename(MusicTrackDTO musicTrackDTO) {
        String index = StringUtils.leftPad(String.valueOf(musicTrackDTO.getTrackIndex()), 2, "0");
        String title = sanitizeFileName(musicTrackDTO.getTitle());
        String extension = FilenameUtils.getExtension(musicTrackDTO.getFilename());
        extension = StringUtils.defaultIfEmpty(extension, musicTrackDTO.getFormat());
        return String.format("%s - %s.%s", index, title, extension);
    }

    public static String genEpisodeNfoFilename(String filename) {
        return FilenameUtils.getBaseName(filename) + ".nfo";
    }

    public static String genCoverFilename(String filename) {
        return FilenameUtils.getBaseName(filename) + ".jpg";
    }

    public static String genSeasonPosterFilename(Integer seasonIndex) {
        return "season-" + StringUtils.leftPad(String.valueOf(seasonIndex), 2, "0") + '-' + KaleidoConstants.POSTER;
    }

    public static String genEpisodeThumbFilename(String filename) {
        return FilenameUtils.getBaseName(filename) + "-thumb.jpg";
    }

    public static boolean isVideoFile(String filename) {
        String extension = ConfigUtils.getSysConfig(ConfigKey.videoExtension, VIDEO_EXTENSION);
        String[] extensions = StringUtils.split(extension, Constants.COMMA);
        String fileExtension = FilenameUtils.getExtension(filename);
        return StringUtils.equalsAnyIgnoreCase(fileExtension, extensions);
    }

    public static boolean isComicZipFile(String filename) {
        String extension = ConfigUtils.getSysConfig(ConfigKey.comicZipExtension, COMIC_ZIP_EXTENSION);
        String[] extensions = StringUtils.split(extension, Constants.COMMA);
        String fileExtension = FilenameUtils.getExtension(filename);
        return StringUtils.equalsAnyIgnoreCase(fileExtension, extensions);
    }

    public static boolean isAudioFile(String filename) {
        String extension = ConfigUtils.getSysConfig(ConfigKey.audioExtension, AUDIO_ZIP_EXTENSION);
        String[] extensions = StringUtils.split(extension, Constants.COMMA);
        String fileExtension = FilenameUtils.getExtension(filename);
        return StringUtils.equalsAnyIgnoreCase(fileExtension, extensions);
    }

    public static boolean isChineseStream(Media.Stream stream) {
        List<String> values = Lists.newArrayList();
        CollectionUtils.addIgnoreNull(values, stream.getLanguage());
        CollectionUtils.addIgnoreNull(values, stream.getLanguageTag());
        CollectionUtils.addIgnoreNull(values, stream.getLanguageCode());
        CollectionUtils.addIgnoreNull(values, stream.getTitle());
        return values.stream().anyMatch(s -> StringUtils.equalsAnyIgnoreCase(s, "zh", "chs", "cht", "ch", "中文", "中字",
                "简中", "繁中", "简体中文", "简体中字", "繁体中文", "繁体中字", "普通话", "国语"));
    }

    private static String sanitizeFileName(String fileName) {
        // 将字符串中的非法字符替换为下划线
        fileName = fileName.replaceAll("[\\\\/:*?\"<>|]", Constants.UNDER_LINE);
        fileName = fileName.replaceAll("_+", Constants.UNDER_LINE);
        fileName = fileName.replaceAll("^_+|_+$", StringUtils.EMPTY);
        return fileName;
    }

    public static boolean isSame(MovieBasicDTO movieBasicDTO, MovieNFO movieNFO) {
        if (movieBasicDTO == null || movieNFO == null) {
            return false;
        }
        if (movieBasicDTO.getDoubanId() != null && movieNFO.getDoubanId() != null
                && StringUtils.equals(movieBasicDTO.getDoubanId(), movieNFO.getDoubanId())) {
            return true;
        }
        if (movieBasicDTO.getImdbId() != null && movieNFO.getImdbId() != null
                && StringUtils.equals(movieBasicDTO.getImdbId(), movieNFO.getImdbId())) {
            return true;
        }
        return movieBasicDTO.getTmdbId() != null && movieNFO.getTmdbId() != null
                && StringUtils.equals(movieBasicDTO.getTmdbId(), movieNFO.getTmdbId());
    }

    public static boolean isSame(MovieBasicDTO movieBasicDTO, Movie movie) {
        if (movieBasicDTO == null || movie == null) {
            return false;
        }
        if (movieBasicDTO.getDoubanId() != null && movie.getDoubanId() != null
                && StringUtils.equals(movieBasicDTO.getDoubanId(), movie.getDoubanId())) {
            return true;
        }
        if (movieBasicDTO.getImdbId() != null && movie.getImdbId() != null
                && StringUtils.equals(movieBasicDTO.getImdbId(), movie.getImdbId())) {
            return true;
        }
        return movieBasicDTO.getTmdbId() != null && movie.getTmdbId() != null
                && StringUtils.equals(movieBasicDTO.getTmdbId(), movie.getTmdbId());
    }

    public static boolean isSame(TvshowShowDTO tvshowShowDTO, Tvshow tvshow) {
        if (tvshowShowDTO == null || tvshow == null) {
            return false;
        }
        if (tvshowShowDTO.getDoubanId() != null && tvshow.getDoubanId() != null
                && StringUtils.equals(tvshowShowDTO.getDoubanId(), tvshow.getDoubanId())) {
            return true;
        }
        if (tvshowShowDTO.getImdbId() != null && tvshow.getImdbId() != null
                && StringUtils.equals(tvshowShowDTO.getImdbId(), tvshow.getImdbId())) {
            return true;
        }
        return tvshowShowDTO.getTmdbId() != null && tvshow.getTmdbId() != null
                && StringUtils.equals(tvshowShowDTO.getTmdbId(), tvshow.getTmdbId());
    }

    public static boolean isSame(MusicAlbumDTO musicAlbumDTO, Album album) {
        if (musicAlbumDTO == null || album == null) {
            return false;
        }
        if (musicAlbumDTO.getNeteaseId() != null && album.getNeteaseId() != null
                && StringUtils.equals(musicAlbumDTO.getNeteaseId(), album.getNeteaseId())) {
            return true;
        }
        return musicAlbumDTO.getMusicbrainzId() != null && album.getMusicbrainzId() != null
                && StringUtils.equals(musicAlbumDTO.getMusicbrainzId(), album.getMusicbrainzId());
    }

    public static boolean isSame(ComicSeriesDTO comicSeriesDTO, Comic comic) {
        if (comicSeriesDTO == null || comic == null) {
            return false;
        }
        return comicSeriesDTO.getBgmId() != null && comic.getBgmId() != null
                && StringUtils.equals(comicSeriesDTO.getBgmId(), comic.getBgmId());
    }

    public static Integer parseSeasonIndex(String text, Integer defaultValue) {
        Matcher matcher = seasonIndexPattern.matcher(text);
        if (matcher.find()) {
            return Integer.parseInt(matcher.group(1));
        }
        return defaultValue;
    }

    public static Integer parseEpisodeIndex(String text) {
        Matcher matcher = episodeIndexPattern.matcher(text);
        if (matcher.find()) {
            return Integer.parseInt(matcher.group(1));
        }
        return null;
    }

    public static Integer parseVolumeNumber(String text, Integer defaultValue) {
        Matcher matcher = volumeNumberPattern.matcher(text);
        if (matcher.find()) {
            return Integer.valueOf(matcher.group(1));
        }
        return defaultValue;
    }

    public static Integer parseTrackIndex(String text) {
        Matcher matcher = trackIndexPattern.matcher(text);
        if (matcher.find()) {
            return Integer.parseInt(matcher.group(1));
        }
        return null;
    }

    public static String parseTrackTitle(String text) {
        text = FilenameUtils.removeExtension(text);
        Matcher matcher = trackIndexPattern.matcher(text);
        if (matcher.find()) {
            return matcher.group(2);
        }
        return null;
    }

    public static boolean createFolderPath(Path folderPath) throws IOException {
        if (Files.notExists(folderPath)) {
            Files.createDirectories(folderPath);
            return true;
        } else {
            return false;
        }
    }

    public static boolean isOverride(String param1, String param2) {
        // 如果 param1 为 null 且 param2 非 null，则返回 true
        if (param1 == null && param2 != null) {
            return true;
        }

        // 如果 param1 非 null 且 param2 非 null，且两者不相等，则返回 true
        return param1 != null && param2 != null && !param1.equals(param2);

        // 其他情况返回 false
    }

    public static void main(String[] args) {
        System.out.println(StringUtils.equals(null, null));
    }

}
