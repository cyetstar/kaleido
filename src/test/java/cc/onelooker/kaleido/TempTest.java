package cc.onelooker.kaleido;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.ZipUtil;
import cn.hutool.extra.compress.CompressUtil;
import cn.hutool.extra.compress.archiver.Archiver;
import cn.hutool.extra.compress.extractor.Extractor;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RegExUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @Author xiadawei
 * @Date 2023-12-19 13:25:00
 * @Description TODO
 */
public class TempTest {

    @Test
    public void removePattern() {
        String title = "Creepin' In";
        String simpleTitle = RegExUtils.removePattern(title, "'|’");
        assertEquals("Creepin In", simpleTitle);
        title = "Loretta (live, 2004)";
        simpleTitle = RegExUtils.removePattern(title, "\\(.+\\)");
        assertEquals("Loretta ", simpleTitle);
    }

    @Test
    public void getSeasonEpisodeNumber() {
        Pattern pattern = Pattern.compile("([sS]_?(\\d+))?[eE][pP]?_?(\\d+)");
        Matcher matcher = pattern.matcher("[不毛地带].[TVBT]Fumo.Chitai_EP15_ChineseSubbed.rmvb");
        if (matcher.find()) {
            System.out.println(matcher.group(0));
            System.out.println(matcher.group(1));
            System.out.println(matcher.group(2));
            System.out.println(matcher.group(3));
        }
    }

    @Test
    public void getVolumeNumber() {
        Pattern pattern = Pattern.compile("[V|v]ol[.|_](\\d+)");
        Matcher matcher = pattern.matcher("[Comic][17青春遁走][古谷實][HK]vol_03.zip");
        if (matcher.find()) {
            System.out.println(matcher.group(0));
            System.out.println(Integer.parseInt(matcher.group(1)));
        }
    }

    @Test
    public void compressZip() throws IOException {
        Path path = Paths.get("/Users/cyetstar/dev/kaleido/zip");
        Archiver archiver = CompressUtil.createArchiver(CharsetUtil.CHARSET_UTF_8, ArchiveStreamFactory.ZIP, path.resolveSibling("压缩.cbz").toFile());
        Files.list(path).forEach(s -> {
            archiver.add(s.toFile());
        });
        archiver.close();

    }

    @Test
    public void compressZip2() throws IOException {
        Path path = Paths.get("/Users/cyetstar/dev/kaleido/zip");
        ZipUtil.zip(path.toFile());
    }

    @Test
    public void fileCount() throws IOException {
        Path path = Paths.get("/Volumes/comic/download/20世紀少年 [浦澤直樹]");
        System.out.println(Files.list(path).count());
    }

    @Test
    public void unzip() {
        try {
            Path path = Paths.get("/Users/cyetstar/dev/kaleido/Vol_01.cbz");
            String fileName = path.getFileName().toString();
            String extension = FilenameUtils.getExtension(fileName);
            if (!StringUtils.equalsAnyIgnoreCase(extension, "zip", "cbz")) {
                return;
            }
            String baseName = FilenameUtils.getBaseName(fileName);
            Path folderPath = path.getParent().resolve(baseName);
            Extractor extractor = CompressUtil.createExtractor(CharsetUtil.defaultCharset(), path.toFile());
            extractor.extract(folderPath.toFile(), archiveEntry -> StringUtils.equalsAnyIgnoreCase(FilenameUtils.getExtension(archiveEntry.getName()), "jpg", "png", "xml"));
            extractor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}