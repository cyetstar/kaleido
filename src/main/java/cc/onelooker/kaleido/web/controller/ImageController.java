package cc.onelooker.kaleido.web.controller;

import cc.onelooker.kaleido.dto.ComicBookDTO;
import cc.onelooker.kaleido.dto.ComicSeriesDTO;
import cc.onelooker.kaleido.service.ComicBookService;
import cc.onelooker.kaleido.service.ComicSeriesService;
import cc.onelooker.kaleido.third.komga.KomgaApiService;
import cc.onelooker.kaleido.utils.KaleidoUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.TimeUnit;

/**
 * @Author xiadawei
 * @Date 2024-03-17 23:03:00
 * @Description TODO
 */
@RestController
@RequestMapping("/image")
public class ImageController {

    @Autowired
    private KomgaApiService komgaApiService;

    @Autowired
    private ComicSeriesService comicSeriesService;

    @Autowired
    private ComicBookService comicBookService;

    @GetMapping("/cover")
    public HttpEntity<byte[]> cover(String id, String type) throws IOException {
        byte[] content = null;
        Path coverPath = null;
        if (StringUtils.equals(type, "book")) {
            ComicBookDTO comicBookDTO = comicBookService.findById(id);
            Path path = KaleidoUtils.getComicPath(comicBookDTO.getPath());
            String fileName = FilenameUtils.getBaseName(path.getFileName().toString());
            coverPath = path.resolveSibling(fileName + ".jpg");
        } else {
            ComicSeriesDTO comicSeriesDTO = comicSeriesService.findById(id);
            Path path = KaleidoUtils.getComicPath(comicSeriesDTO.getPath());
            coverPath = path.resolve("cover.jpg");
        }
        if (Files.exists(coverPath)) {
            content = Files.readAllBytes(coverPath);
        }
        if (content == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).eTag(String.valueOf(Files.getLastModifiedTime(coverPath).toMillis())).body(content);
    }

    @GetMapping("/page")
    public HttpEntity<byte[]> page(String id, Integer number) {
        byte[] content = komgaApiService.findBookPage(id, number);
        return ResponseEntity.ok().lastModified(System.currentTimeMillis()).contentType(MediaType.IMAGE_JPEG).cacheControl(CacheControl.maxAge(1, TimeUnit.HOURS)).body(content);
    }

}
