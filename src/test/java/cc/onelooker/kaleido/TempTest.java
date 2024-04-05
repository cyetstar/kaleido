package cc.onelooker.kaleido;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.ZipUtil;
import cn.hutool.extra.compress.CompressUtil;
import cn.hutool.extra.compress.archiver.Archiver;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.lang3.RegExUtils;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
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

}