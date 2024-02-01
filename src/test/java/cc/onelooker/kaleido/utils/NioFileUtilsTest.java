package cc.onelooker.kaleido.utils;

import cc.onelooker.kaleido.dto.movie.MovieThreadDTO;
import cc.onelooker.kaleido.dto.movie.MovieThreadFilenameDTO;
import cc.onelooker.kaleido.enums.ThreadStatus;
import cc.onelooker.kaleido.service.movie.MovieThreadFilenameService;
import cc.onelooker.kaleido.service.movie.MovieThreadService;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Author xiadawei
 * @Date 2023-12-25 19:38:00
 * @Description TODO
 */
@Slf4j
@ActiveProfiles("dev")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class NioFileUtilsTest {

    @Autowired
    private MovieThreadFilenameService movieThreadFilenameService;

    @Autowired
    private MovieThreadService movieThreadService;

    @Test
    public void deleteEmptyFolder() {
        String movieLibraryPath = "/Volumes/movie";
        String[] decadeArr = {"1900s", "1910s", "1920s", "1930s", "1940s", "1950s", "1960s", "1970s", "1980s", "1990s", "2000s", "2010s", "2020s"};
        for (String decade : decadeArr) {
            try (Stream<Path> paths = Files.list(Paths.get(movieLibraryPath, decade))) {
                List<Path> pathList = paths.collect(Collectors.toList());
                for (Path p : pathList) {
                    if (!Files.isDirectory(p)) {
                        continue;
                    }
                    try (Stream<Path> subPaths = Files.list(p)) {
                        if (subPaths.noneMatch(s -> KaleidoUtils.isVideoFile(s.getFileName().toString()))) {
                            log.info("发现空文目录：{}", p);
                        }
                    } catch (Exception e) {
                        log.error("发现空文目录出现错误：{}", p);
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Test
    public void deleteEmptyOtherFolder() {
        String movieLibraryPath = "/Volumes/movie";
        try (Stream<Path> paths = Files.list(Paths.get(movieLibraryPath))) {
            List<Path> pathList = paths.collect(Collectors.toList());
            int count = 0;
            for (Path p : pathList) {
                count++;
                if (!Files.isDirectory(p) || StringUtils.equals(p.toString(), "/Volumes/movie/#recycle")) {
                    continue;
                }
                Path otherPath = p.resolve("Other");
                if (!Files.exists(otherPath)) {
                    continue;
                }
                List<Path> otherPathList = Files.list(otherPath).collect(Collectors.toList());
                if (CollectionUtils.isEmpty(otherPathList)) {
                    Files.deleteIfExists(otherPath);
                    log.info("删除空Other文件夹：{}", p);
                }
                if (count % 1000 == 0) {
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
                if (count % 1000 == 0) {
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
        String[] decadeArr = {"1900s", "1910s", "1920s", "1930s", "1940s", "1950s", "1960s", "1970s", "1980s", "1990s", "2000s", "2010s", "2020s"};
        for (String decade : decadeArr) {
            try (Stream<Path> paths = Files.list(Paths.get(movieLibraryPath, decade))) {
                List<Path> pathList = paths.collect(Collectors.toList());
                for (Path p : pathList) {
                    if (!Files.isDirectory(p)) {
                        continue;
                    }
                    try (Stream<Path> subPaths = Files.list(p)) {
                        List<String> videoList = subPaths.map(s -> s.getFileName().toString()).filter(s -> KaleidoUtils.isVideoFile(s) && !StringUtils.containsAny(s, "-CD", ".part")).collect(Collectors.toList());
                        if (videoList.size() > 1) {
                            log.info("发现多个视频文件：{} - {}", p, StringUtils.join(videoList, "；"));
                        }
                    } catch (Exception e) {
                        log.error("发现多个视频文件发生错误：{}", p);
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }

    @Test
    public void findOtherVideo() {
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
                    List<Path> videoList = subPaths.filter(s -> KaleidoUtils.isVideoFile(s.getFileName().toString()) && StringUtils.contains(s.getFileName().toString(), "-other.")).collect(Collectors.toList());
                    if (CollectionUtils.isEmpty(videoList)) {
                        continue;
                    }
                    log.info("发现Other文件：{}", p);
                    Path otherPath = p.resolve("Other");
                    if (!Files.exists(otherPath)) {
                        Files.createDirectories(otherPath);
                    }
                    for (Path videoPath : videoList) {
                        String filename = videoPath.getFileName().toString();
                        log.info("移动Other文件：{}", filename);
                        Files.move(videoPath, otherPath.resolve(filename.replace("-other.", ".")));
                    }
                } catch (Exception e) {
                    log.error("发现Other文件发生错误：{}", p);
                }
                if (count % 1000 == 0) {
                    log.info("已经处理了 {} 个文件夹", count);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void moveMovieFolder() {
        String movieLibraryPath = "/Volumes/movie";
        Pattern pattern = Pattern.compile("\\((\\d+)\\)");
        try (Stream<Path> paths = Files.list(Paths.get(movieLibraryPath))) {
            List<Path> pathList = paths.collect(Collectors.toList());
            int count = 0;
            for (Path p : pathList) {
                count++;
                if (!Files.isDirectory(p) || StringUtils.equals(p.toString(), "/Volumes/movie/#recycle")) {
                    continue;
                }
                String fileName = p.getFileName().toString();
                Matcher matcher = pattern.matcher(fileName);
                String decade = null;
                if (matcher.find()) {
                    String year = matcher.group(1);
                    decade = year.substring(0, 3) + "0s";
                }
                Path decadePath = Paths.get(movieLibraryPath, decade);
                if (!Files.exists(decadePath)) {
                    Files.createDirectory(decadePath);
                }
                NioFileUtils.moveDir(p, decadePath, StandardCopyOption.REPLACE_EXISTING);
                if (count % 1000 == 0) {
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

    @org.junit.jupiter.api.Test
    public void checkFile() throws IOException {
        String movieLibraryPath = "/Volumes/movie";
        List<Path> pathList = Lists.newArrayList();
        addPathList(pathList, Paths.get(movieLibraryPath));
        FileOutputStream fos = new FileOutputStream("/Users/cyetstar/dev/kaleido/test.txt");
        IOUtils.writeLines(pathList.stream().map(s -> s.getFileName().toString()).collect(Collectors.toList()), "\n", fos);
        for (Path path : pathList) {
            MovieThreadFilenameDTO movieThreadFilenameDTO = movieThreadFilenameService.findByValue(path.getFileName().toString());
            if (movieThreadFilenameDTO == null) {
                continue;
            }
            MovieThreadDTO movieThreadDTO = movieThreadService.findById(movieThreadFilenameDTO.getThreadId());
            if (movieThreadDTO != null && movieThreadDTO.getStatus() == ThreadStatus.todo.ordinal()) {
                movieThreadDTO.setStatus(ThreadStatus.done.ordinal());
                movieThreadService.update(movieThreadDTO);
                log.info("完成待收：{}", movieThreadDTO.getTitle());
            }
        }
    }

    private void addPathList(List<Path> pathList, Path path) {
        if (Files.isDirectory(path) && !StringUtils.equalsAny(path.getFileName().toString(), "#recycle", "Other")) {
            try (Stream<Path> paths = Files.list(path)) {
                paths.forEach(p -> addPathList(pathList, p));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else if (KaleidoUtils.isVideoFile(path.getFileName().toString())) {
            pathList.add(path);

        }
    }

}