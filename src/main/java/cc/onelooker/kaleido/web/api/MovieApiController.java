package cc.onelooker.kaleido.web.api;

import cc.onelooker.kaleido.dto.movie.MovieBasicDTO;
import cc.onelooker.kaleido.service.movie.MovieBasicService;
import cc.onelooker.kaleido.third.plex.Metadata;
import cc.onelooker.kaleido.third.plex.PlexApiService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author xiadawei
 * @Date 2024-01-10 15:13:00
 * @Description TODO
 */
@RestController
@RequestMapping("/api/movie")
public class MovieApiController {

    @Autowired
    private MovieBasicService movieBasicService;

    @Autowired
    private PlexApiService plexApiService;

    @GetMapping("view")
    public String view(@RequestParam String doubanId, @RequestParam String imdbId) {
        MovieBasicDTO movieBasicDTO = null;
        if (StringUtils.isNotEmpty(doubanId)) {
            movieBasicDTO = movieBasicService.findByDoubanId(doubanId);
        }
        if (movieBasicDTO == null && StringUtils.isNotEmpty(imdbId)) {
            movieBasicDTO = movieBasicService.findByImdb(imdbId);
        }
        if (movieBasicDTO != null) {
            Metadata metadata = plexApiService.findMovieById(movieBasicDTO.getId());
            return metadata.getMedia().getPart().getFile();
        }
        return StringUtils.EMPTY;
    }
}
