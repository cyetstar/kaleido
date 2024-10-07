package cc.onelooker.kaleido;

import cc.onelooker.kaleido.nfo.ComicInfoNFO;
import cc.onelooker.kaleido.nfo.NFOUtil;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        Pattern pattern = Pattern.compile("(S_?(\\d+))?EP?_?(\\d+)");
        Matcher matcher = pattern.matcher("Sense8.S01E01.2015.1080p.WEB-DL.x265.10bit.AC3￡cXcY@FRDS.");
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

    @Test
    public void containsAnyIgnoreCase() {
        String title = "作者";
        boolean result = StringUtils.containsAny(title, "原作");
        assertTrue(result);
    }

    @Test
    public void readComicInfo() {
        Path path = Paths.get("/Users/cyetstar/dev/kaleido/ComicInfo.xml");
        ComicInfoNFO comicInfoNFO = NFOUtil.read(ComicInfoNFO.class, path);
    }

    @Test
    public void testPath() throws IOException {
        String folder = "乱马1/2";
        Paths.get("/Users/cyetstar/dev/kaleido/", folder).toFile().mkdirs();
    }

    @Test
    public void testPath2() {
        String folder = "/1990s/阿里 (1994)";
        Path path = Paths.get("/Users/cyetstar/dev/kaleido/");
        path = path.resolve(folder);
        System.out.println(path.toString());
    }

    @Test
    public void replaceColor() throws IOException {
        /**
         * 要处理的图片目录
         */
        File file = new File("/Users/cyetstar/dev/kaleido/1.jpg");
        /**
         * 定义一个RGB的数组，因为图片的RGB模式是由三个 0-255来表示的 比如白色就是(255,255,255)
         */
        int[] rgb = new int[3];
        /**
         * 用来处理图片的缓冲流
         */
        BufferedImage bi = null;
        try {
            /**
             * 用ImageIO将图片读入到缓冲中
             */
            bi = ImageIO.read(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        /**
         * 得到图片的长宽
         */
        int width = bi.getWidth();
        int height = bi.getHeight();
        int minx = bi.getMinX();
        int miny = bi.getMinY();
        /**
         * 这里是遍历图片的像素，因为要处理图片的背色，所以要把指定像素上的颜色换成目标颜色
         * 这里 是一个二层循环，遍历长和宽上的每个像素
         */
        for (int i = minx; i < width; i++) {
            for (int j = miny; j < height; j++) {
                // System.out.print(bi.getRGB(jw, ih));
                /**
                 * 得到指定像素（i,j)上的RGB值，
                 */
                int pixel = bi.getRGB(i, j);
                /**
                 * 分别进行位操作得到 r g b上的值
                 */
                rgb[0] = (pixel & 0xff0000) >> 16;
                rgb[1] = (pixel & 0xff00) >> 8;
                rgb[2] = (pixel & 0xff);
                /**
                 * 进行换色操作，我这里是要把蓝底换成白底，那么就判断图片中rgb值是否在蓝色范围的像素
                 */
                // if(rgb[0]<140&&rgb[0]>100 && rgb[1]<80&&rgb[1]>50 && rgb[2]<170&&rgb[2]>150 ){
                if (rgb[0] < 256 && rgb[0] > 230 && rgb[1] < 256 && rgb[1] > 230 && rgb[2] < 256 && rgb[2] > 230) {
                    /**
                     * 这里是判断通过，则把该像素换成白色
                     */
                    bi.setRGB(i, j, 0x000000);
                }
                if (rgb[0] < 140 && rgb[0] > 100 && rgb[1] < 80 && rgb[1] > 50 && rgb[2] < 170 && rgb[2] > 150) {
                    /**
                     * 这里是判断通过，则把该像素换成白色
                     */
                    bi.setRGB(i, j, 0xffffff);
                }

            }
        }
        /**
         * 将缓冲对象保存到新文件中
         */
        FileOutputStream ops = new FileOutputStream(new File("/Users/cyetstar/dev/kaleido/2.jpg"));
        ImageIO.write(bi, "jpg", ops);
        ops.flush();
        ops.close();
    }

    @Test
    public void listDir() throws IOException {
        Files.list(Paths.get("/Volumes/comic/import/")).forEach(System.out::println);
    }

    @Test
    public void encodePassword() {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        System.out.println(passwordEncoder.encode("lenovot430"));
    }

    @Test
    public void renameFiles() throws IOException {
        Files.list(Paths.get("/Volumes/comic/import/[烈焰先鋒 救國的橘衣消防員][曽田正人×冨山玖呂][Vol.01-Vol.09][东立][电子版]")).forEach(s -> {
            try {
                String filename = s.getFileName().toString();
                filename = renameFile(filename);
                Files.move(s, s.resolveSibling(filename));
            } catch (Exception e) {

            }
        });
    }

    private String renameFile(String fileName) {
//        String nameWithoutExtension = fileName.substring(0, fileName.lastIndexOf("."));
//        String extension = fileName.substring(fileName.lastIndexOf("."));

        Pattern pattern = Pattern.compile("(\\d+)");
        Matcher matcher = pattern.matcher(fileName);
        if (matcher.find()) {
            Integer vol = Integer.parseInt(matcher.group(1));
            String volStr = StringUtils.leftPad(vol.toString(), 2, '0');
            String newName = "[曽田正人×冨山玖呂][烈焰先鋒 救國的橘衣消防員][東立電子版]Vol." + volStr + ".zip";
            return newName;
        }
        // 3. 返回新的文件名
        return fileName;
    }

    public static String renameFileWithAddition(String fileName, int addition) {
        // 定义正则表达式来匹配 "Vol.数字"
        Pattern pattern = Pattern.compile("Vol\\.(\\d+)");
        Matcher matcher = pattern.matcher(fileName);

        // 如果找到匹配的 "Vol.数字"
        if (matcher.find()) {
            // 获取当前的 Vol 后的数字
            int currentVol = Integer.parseInt(matcher.group(1));
            // 进行加法运算
            int newVol = currentVol + addition;
            // 格式化成三位数的Vol号
            String newVolString = String.format("Vol.%03d", newVol);
            // 返回替换后的新文件名
            return matcher.replaceFirst(newVolString);
        }

        // 如果没有匹配到 "Vol." 的情况，返回原始文件名
        return fileName;
    }

}