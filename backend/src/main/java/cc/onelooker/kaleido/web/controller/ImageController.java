package cc.onelooker.kaleido.web.controller;

import cc.onelooker.kaleido.dto.*;
import cc.onelooker.kaleido.enums.SubjectType;
import cc.onelooker.kaleido.service.*;
import cc.onelooker.kaleido.third.komga.KomgaApiService;
import cc.onelooker.kaleido.utils.KaleidoConstants;
import cc.onelooker.kaleido.utils.KaleidoUtil;
import cn.hutool.http.HttpUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
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
    private MovieBasicService movieBasicService;

    @Autowired
    private TvshowShowService tvshowShowService;

    @Autowired
    private TvshowSeasonService tvshowSeasonService;

    @Autowired
    private TvshowEpisodeService tvshowEpisodeService;

    @Autowired
    private MusicAlbumService musicAlbumService;

    @Autowired
    private ComicSeriesService comicSeriesService;

    @Autowired
    private ComicBookService comicBookService;

    @GetMapping("/thumb")
    public HttpEntity<byte[]> thumb(@RequestParam(required = false) String id, @RequestParam(required = false) SubjectType type, @RequestParam(required = false) String url) throws IOException {
        byte[] content = null;
        Path path = null;
        if (Objects.equals(type, SubjectType.MovieBasic)) {
            MovieBasicDTO movieBasicDTO = movieBasicService.findById(id);
            path = KaleidoUtil.getMoviePath(movieBasicDTO.getPath()).resolve(KaleidoConstants.POSTER);
        } else if (Objects.equals(type, SubjectType.TvshowShow)) {
            TvshowShowDTO tvshowShowDTO = tvshowShowService.findById(id);
            path = KaleidoUtil.getTvshowPath(tvshowShowDTO.getPath()).resolve(KaleidoConstants.POSTER);
        } else if (Objects.equals(type, SubjectType.TvshowSeason)) {
            TvshowSeasonDTO tvshowSeasonDTO = tvshowSeasonService.findById(id);
            TvshowShowDTO tvshowShowDTO = tvshowShowService.findById(tvshowSeasonDTO.getShowId());
            path = KaleidoUtil.getTvshowPath(tvshowShowDTO.getPath()).resolve(KaleidoUtil.genSeasonPosterFilename(tvshowSeasonDTO.getSeasonIndex()));
        } else if (Objects.equals(type, SubjectType.TvshowEpisode)) {
            TvshowEpisodeDTO tvshowEpisodeDTO = tvshowEpisodeService.findById(id);
            TvshowSeasonDTO tvshowSeasonDTO = tvshowSeasonService.findById(tvshowEpisodeDTO.getSeasonId());
            TvshowShowDTO tvshowShowDTO = tvshowShowService.findById(tvshowSeasonDTO.getShowId());
            String seasonFolder = KaleidoUtil.genSeasonFolder(tvshowSeasonDTO.getSeasonIndex());
            path = KaleidoUtil.getTvshowPath(tvshowShowDTO.getPath()).resolve(seasonFolder).resolve(KaleidoUtil.genEpisodeThumbFilename(tvshowEpisodeDTO.getFilename()));
        } else if (Objects.equals(type, SubjectType.MusicAlbum)) {
            MusicAlbumDTO musicAlbumDTO = musicAlbumService.findById(id);
            path = KaleidoUtil.getMusicPath(musicAlbumDTO.getPath()).resolve(KaleidoConstants.COVER);
        } else if (Objects.equals(type, SubjectType.ComicBook)) {
            ComicBookDTO comicBookDTO = comicBookService.findById(id);
            ComicSeriesDTO comicSeriesDTO = comicSeriesService.findById(comicBookDTO.getSeriesId());
            path = KaleidoUtil.getComicPath(comicSeriesDTO.getPath()).resolve(KaleidoUtil.genCoverFilename(comicBookDTO.getFilename()));
        } else if (Objects.equals(type, SubjectType.ComicSeries)) {
            ComicSeriesDTO comicSeriesDTO = comicSeriesService.findById(id);
            path = KaleidoUtil.getComicPath(comicSeriesDTO.getPath()).resolve(KaleidoConstants.COVER);
        }
        if (path != null && Files.exists(path)) {
            content = Files.readAllBytes(path);
        } else if (StringUtils.isNotEmpty(url)) {
            content = HttpUtil.downloadBytes(url);
        }
        if (content == null) {
            return ResponseEntity.notFound().build();
        }
        CacheControl cacheControl = CacheControl.maxAge(365, TimeUnit.DAYS).cachePublic();
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).cacheControl(cacheControl).body(content);
    }

    @GetMapping("/page")
    public HttpEntity<byte[]> page(String id, Integer number) {
        byte[] content = komgaApiService.findBookPage(id, number);
        CacheControl cacheControl = CacheControl.maxAge(1, TimeUnit.HOURS).cachePublic();
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).cacheControl(cacheControl).body(content);
    }

}
