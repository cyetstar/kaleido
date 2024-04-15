package cc.onelooker.kaleido.web.controller.common;

import cc.onelooker.kaleido.dto.comic.ComicBookDTO;
import cc.onelooker.kaleido.dto.comic.ComicSeriesDTO;
import cc.onelooker.kaleido.service.comic.ComicBookService;
import cc.onelooker.kaleido.service.comic.ComicSeriesService;
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
import java.nio.file.Paths;
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
        if (StringUtils.equals(type, "book")) {
            ComicBookDTO comicBookDTO = comicBookService.findById(id);
            Path path = Paths.get(KaleidoUtils.getComicFolder(comicBookDTO.getPath()));
            String fileName = FilenameUtils.getBaseName(path.getFileName().toString());
            Path coverPath = path.getParent().resolve(fileName + ".jpg");
            if (Files.exists(coverPath)) {
                content = Files.readAllBytes(coverPath);
            } else {
                content = komgaApiService.findBookThumbnail(id);
            }
        } else {
            ComicSeriesDTO comicSeriesDTO = comicSeriesService.findById(id);
            Path path = Paths.get(KaleidoUtils.getComicFolder(comicSeriesDTO.getPath()));
            if (Files.exists(path.resolve("cover.jpg"))) {
                content = Files.readAllBytes(path.resolve("cover.jpg"));
            } else {
                content = komgaApiService.findSeriesThumbnail(id);
            }
        }
        if (content == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).cacheControl(CacheControl.maxAge(1, TimeUnit.DAYS)).body(content);
    }

    @GetMapping("/page")
    public HttpEntity<byte[]> page(String id, Integer number) {
        byte[] content = komgaApiService.findBookPage(id, number);
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).cacheControl(CacheControl.maxAge(1, TimeUnit.HOURS)).body(content);
    }

}
