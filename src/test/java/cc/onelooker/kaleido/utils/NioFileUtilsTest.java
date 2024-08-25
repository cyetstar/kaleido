package cc.onelooker.kaleido.utils;

import cc.onelooker.kaleido.dto.MovieThreadDTO;
import cc.onelooker.kaleido.dto.MovieThreadFilenameDTO;
import cc.onelooker.kaleido.dto.PTThreadDTO;
import cc.onelooker.kaleido.enums.ThreadStatus;
import cc.onelooker.kaleido.service.MovieThreadFilenameService;
import cc.onelooker.kaleido.service.MovieThreadService;
import cc.onelooker.kaleido.service.PTThreadService;
import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.URLUtil;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
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

    @Autowired
    private PTThreadService ptThreadService;

    @Autowired
    private RestTemplate restTemplate;

    private Pattern pattern = Pattern.compile("filename=\"(.+?)\"");

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

    @org.junit.jupiter.api.Test
    public void downloadTorrent() {
        System.setProperty("https.protocols", "TLSv1,TLSv1.1,TLSv1.2");
        PTThreadDTO param = new PTThreadDTO();
        param.setTitle("iNT-TLF");
        List<PTThreadDTO> ptThreadDTOList = ptThreadService.list(param);
        for (PTThreadDTO ptThreadDTO : ptThreadDTOList) {
            String link = "https://pt.eastgame.org/download.php?id=" + ptThreadDTO.getId();
            downloadTorrent(link, ptThreadDTO.getTitle());
        }
    }

    private void downloadTorrent(String url, String title) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.COOKIE, "c_secure_uid=Mjk2OTQ%3D; c_secure_pass=158ee141f59b0c48b3311bcc198f7f55; c_secure_ssl=eWVhaA%3D%3D; c_secure_tracker_ssl=eWVhaA%3D%3D; c_secure_login=bm9wZQ%3D%3D; OUTFOX_SEARCH_USER_ID_NCOO=147191347.17357275; __utmz=160627708.1706756768.105.22.utmcsr=192.168.3.100:8000|utmccn=(referral)|utmcmd=referral|utmcct=/; __utma=160627708.7976078.1693316768.1707215490.1707490535.114; __utmc=160627708; __utmb=160627708.6.10.1707490535");
            headers.add(HttpHeaders.REFERER, "https://pt.eastgame.org");
            headers.add(HttpHeaders.USER_AGENT, "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36");
            headers.add(HttpHeaders.ACCEPT, "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7");
            RequestEntity<byte[]> requestEntity = new RequestEntity<>(headers, HttpMethod.GET, URI.create(url));
            ResponseEntity<byte[]> responseEntity = restTemplate.exchange(requestEntity, byte[].class);
            HttpHeaders responseEntityHeaders = responseEntity.getHeaders();
            String contentDisposition = responseEntityHeaders.getFirst("Content-Disposition");
            //attachment; filename=%5BTLF%5D.%E7%BD%97%E5%AF%86%E6%AC%A7%E4%B8%8E%E6%9C%B1%E4%B8%BD%E5%8F%B6%E5%90%8E%E7%8E%B0%E4%BB%A3%E6%BF%80%E6%83%85%E7%89%88.Romeo%2BJuliet.1996.BD.2Audio.MiniSD-TLF.mkv.torrent
            String decodeContentDisposition = URLUtil.decode(contentDisposition);
            Matcher matcher = pattern.matcher(decodeContentDisposition);
            String fileName = title + ".torrent";
            if (matcher.find()) {
                fileName = matcher.group(1);
            }
            FileUtils.writeByteArrayToFile(Paths.get("/Users/cyetstar/Downloads/eastgame", fileName).toFile(), responseEntity.getBody());
        } catch (Exception e) {
            log.error("【{}】下载出错, {} :{}", title, url, ExceptionUtil.getMessage(e));
        } finally {
            ThreadUtil.sleep(RandomUtil.randomInt(500));
        }
    }

}