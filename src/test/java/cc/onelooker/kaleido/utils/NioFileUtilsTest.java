package cc.onelooker.kaleido.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Author xiadawei
 * @Date 2023-12-25 19:38:00
 * @Description TODO
 */
@Slf4j
public class NioFileUtilsTest {

    @Test
    public void deleteEmptyFolder() {
        String movieLibraryPath = "/Volumes/movie";
        try (Stream<Path> paths = Files.list(Paths.get(movieLibraryPath))) {
            List<Path> pathList = paths.collect(Collectors.toList());
            int count = 0;
            for (Path p : pathList) {
                count++;
                if (!Files.isDirectory(p) || StringUtils.equals(p.toString(), "/Volumes/movie/#recycle")) {
                    continue;
                }
                try (Stream<Path> subPaths = Files.list(p)) {
                    if (subPaths.noneMatch(s -> KaleidoUtils.isVideoFile(s.getFileName().toString()))) {
                        Files.deleteIfExists(p);
                        log.info("删除空文件夹：{}", p);
                    }
                } catch (Exception e) {
                    log.error("删除空文件夹发生错误：{}", p);
                }
                if (count % 100 == 0) {
                    log.info("已经处理了 {} 个文件夹", count);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void findNoPoster() {
        String movieLibraryPath = "/Volumes/movie";
        try (Stream<Path> paths = Files.list(Paths.get(movieLibraryPath))) {
            List<Path> pathList = paths.collect(Collectors.toList());
            int count = 0;
            for (Path p : pathList) {
                count++;
                if (!Files.isDirectory(p) || StringUtils.equals(p.toString(), "/Volumes/movie/#recycle")) {
                    continue;
                }
                try (Stream<Path> subPaths = Files.list(p)) {
                    if (subPaths.noneMatch(s -> StringUtils.equalsAny(s.getFileName().toString(), "poster.jpg", "poster.png"))) {
                        log.info("没找到海报：{}", p);
                    }
                } catch (Exception e) {
                    log.error("没找到海报发生错误：{}", p);
                }
                if (count % 100 == 0) {
                    log.info("已经处理了 {} 个文件夹", count);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void findMultiVideo() {
        String movieLibraryPath = "/Volumes/movie";
        try (Stream<Path> paths = Files.list(Paths.get(movieLibraryPath))) {
            List<Path> pathList = paths.collect(Collectors.toList());
            int count = 0;
            for (Path p : pathList) {
                count++;
                if (!Files.isDirectory(p) || StringUtils.equals(p.toString(), "/Volumes/movie/#recycle")) {
                    continue;
                }
                try (Stream<Path> subPaths = Files.list(p)) {
                    List<String> videoList = subPaths.map(s -> s.getFileName().toString()).filter(KaleidoUtils::isVideoFile).collect(Collectors.toList());
                    if (videoList.size() > 1) {
                        log.info("发现多个视频文件：{} - {}", p, StringUtils.join(videoList, "；"));
                    }
                } catch (Exception e) {
                    log.error("发现多个视频文件发生错误：{}", p);
                }
                if (count % 100 == 0) {
                    log.info("已经处理了 {} 个文件夹", count);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void operateFolder() throws IOException {
        Path path = Paths.get("/Users/cyetstar/dev/kaleido/source/ABC (1900)");
        Path sourcePath = Paths.get("/Users/cyetstar/dev/kaleido/source");
        Path targetPath = Paths.get("/Users/cyetstar/dev/kaleido/target");
        String folderName = "ABC (1900)";
        Path folderPath = targetPath.resolve(folderName);
        if (Files.notExists(folderPath)) {
            Files.createDirectory(folderPath);
        }
        //将主文件移动到文件夹
        Files.move(path, folderPath.resolve(path.getFileName()));
        //如果存在额外文件夹也移动
        Path extraPath = path.getParent().resolve("Other");
        if (Files.exists(extraPath)) {
            NioFileUtils.moveDir(extraPath, folderPath, StandardCopyOption.REPLACE_EXISTING);
        }
        //删除父文件夹
        Path parent = path.getParent();
        if (!parent.equals(sourcePath) && Objects.requireNonNull(parent.toFile().list()).length == 0) {
            NioFileUtils.deleteIfExists(parent);
        }
    }

}