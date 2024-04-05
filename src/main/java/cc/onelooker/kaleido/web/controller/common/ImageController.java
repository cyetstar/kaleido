package cc.onelooker.kaleido.web.controller.common;

import cc.onelooker.kaleido.third.komga.KomgaApiService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/cover")
    public HttpEntity<byte[]> cover(String id, String type) {
        byte[] content = null;
        if (StringUtils.equals(type, "book")) {
            content = komgaApiService.findBookThumbnail(id);
        } else {
            content = komgaApiService.findSeriesThumbnail(id);
        }
        if (content == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).cacheControl(CacheControl.maxAge(30, TimeUnit.DAYS)).body(content);
    }

}
