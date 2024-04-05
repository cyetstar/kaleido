package cc.onelooker.kaleido.third.tmm;

import cc.onelooker.kaleido.enums.ConfigKey;
import cc.onelooker.kaleido.utils.ConfigUtils;
import com.alibaba.fastjson2.JSONArray;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;

/**
 * @Author xiadawei
 * @Date 2024-01-04 13:54:00
 * @Description TODO
 */
@Component
public class TmmApiService {

    private final static String API_SEARCH_MOVIE = "/movie/search?keyword={keyword}&type={type}";
    private final static String API_FIND_MOVIE = "/movie/detail?douban_id={doubanId}&imdb_id={imdbId}&tmdb_id={tmdbId}";
    private final static String API_FIND_DOULIST = "/doulist/detail?douban_id={doubanId}";
    private final static String API_LIST_DOULIST_MOVIE = "/doulist/movies?douban_id={doubanId}&start={start}";
    private final static String API_FIND_TVSHOW = "/tvshow/detail?douban_id={doubanId}&imdb_id={imdbId}&tmdb_id={tmdbId}";
    private final static String API_SERACH_COMIC = "/comic/search?keyword={keyword}";
    private final static String API_FIND_COMIC = "/comic/detail?bgm_id={bgmId}";
    private final static String API_DOUBAN_COOKIE = "/douban/cookie";

    @Autowired
    private RestTemplate restTemplate;

    public List<Movie> searchMovie(String keyword, String type) {
        String url = ConfigUtils.getSysConfig(ConfigKey.tmmUrl);
        JSONArray jsonArray = restTemplate.getForObject(url + API_SEARCH_MOVIE, JSONArray.class, keyword, type);
        return Objects.requireNonNull(jsonArray).toJavaList(Movie.class);
    }

    public Movie findMovie(String doubanId, String imdbId, String tmdbId) {
        if (StringUtils.isAllEmpty(doubanId, imdbId, tmdbId)) {
            return null;
        }
        String url = ConfigUtils.getSysConfig(ConfigKey.tmmUrl);
        return restTemplate.getForObject(url + API_FIND_MOVIE, Movie.class, doubanId, imdbId, tmdbId);
    }

    public Doulist findDoulist(String doubanId) {
        String url = ConfigUtils.getSysConfig(ConfigKey.tmmUrl);
        return restTemplate.getForObject(url + API_FIND_DOULIST, Doulist.class, doubanId);
    }

    public List<Movie> listDoulistMovie(String doubanId, Integer start) {
        String url = ConfigUtils.getSysConfig(ConfigKey.tmmUrl);
        JSONArray jsonArray = restTemplate.getForObject(url + API_LIST_DOULIST_MOVIE, JSONArray.class, doubanId, start);
        return Objects.requireNonNull(jsonArray).toJavaList(Movie.class);
    }

    public Tvshow findTvshow(String doubanId, String imdbId, String tmdbId) {
        if (StringUtils.isAllEmpty(doubanId, imdbId, tmdbId)) {
            return null;
        }
        String url = ConfigUtils.getSysConfig(ConfigKey.tmmUrl);
        return restTemplate.getForObject(url + API_FIND_TVSHOW, Tvshow.class, doubanId, imdbId, tmdbId);
    }

    public List<Comic> searchComic(String keyword) {
        String url = ConfigUtils.getSysConfig(ConfigKey.tmmUrl);
        JSONArray jsonArray = restTemplate.getForObject(url + API_SERACH_COMIC, JSONArray.class, keyword);
        return Objects.requireNonNull(jsonArray).toJavaList(Comic.class);
    }

    public Comic findComic(String bgmId) {
        String url = ConfigUtils.getSysConfig(ConfigKey.tmmUrl);
        return restTemplate.getForObject(url + API_FIND_COMIC, Comic.class, bgmId);
    }

    public void setDoubanCookie(String cookie) {
        String url = ConfigUtils.getSysConfig(ConfigKey.tmmUrl);
        HttpHeaders headers = new HttpHeaders();
        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("cookie", cookie);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, Object>> param = new HttpEntity<>(map, headers);
        restTemplate.postForEntity(url + API_DOUBAN_COOKIE, param, String.class);
    }
}
