package cc.onelooker.kaleido.third.tmm;

import cc.onelooker.kaleido.enums.ConfigKey;
import cc.onelooker.kaleido.utils.ConfigUtils;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.google.common.collect.Lists;
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

    private final static String API_MOVIE_SEARCH = "/v1/movie/search?keyword={keyword}&source={source}";
    private final static String API_MOVIE_VIEW = "/v1/movie/view?douban_id={doubanId}&IMDb_id={imdbId}&tmdb_id={tmdbId}";
    private final static String API_MOVIE_WEEKLY = "/v1/movie/weekly";
    private final static String API_DOULIST_VIEW = "/v1/doulist/view?douban_id={doubanId}";
    private final static String API_DOULIST_MOVIES = "/v1/doulist/movies?douban_id={doubanId}&start={start}";
    private final static String API_SHOW_SEARCH = "/v1/show/search?keyword={keyword}&source={source}";
    private final static String API_SHOW_VIEW = "/v1/show/view?douban_id={doubanId}&imdb_id={imdbId}&tmdb_id={tmdbId}";
    private final static String API_ALBUM_SEARCH = "/v1/album/search?keyword={keyword}&source={source}";
    private final static String API_ALBUM_VIEW = "/v1/album/view?netease_id={neteaseId}&musicbrainz_id={musicbrainzId}";
    private final static String API_LYRIC_VIEW = "/v1/lyric/view?netease_id={neteaseId}";
    private final static String API_ARTIST_SEARCH = "/v1/artist/search?keyword={keyword}";
    private final static String API_COMIC_SERACH = "/v1/comic/search?keyword={keyword}&source={source}";
    private final static String API_COMIC_VIEW = "/v1/comic/view?bgm_id={bgmId}";
    private final static String API_DOUBAN_COOKIE = "/douban/cookie";

    @Autowired
    private RestTemplate restTemplate;

    public List<Movie> searchMovie(String keyword, String source) {
        String url = ConfigUtils.getSysConfig(ConfigKey.tmmUrl);
        JSONObject jsonObject = restTemplate.getForObject(url + API_MOVIE_SEARCH, JSONObject.class, keyword, source);
        JSONArray jsonArray = Objects.requireNonNull(jsonObject).getJSONArray("data");
        return jsonArray != null ? jsonArray.toJavaList(Movie.class) : Lists.newArrayList();
    }

    public Movie findMovie(String doubanId, String imdbId, String tmdbId) {
        if (StringUtils.isAllEmpty(doubanId, imdbId, tmdbId)) {
            return null;
        }
        String url = ConfigUtils.getSysConfig(ConfigKey.tmmUrl);
        JSONObject jsonObject = restTemplate.getForObject(url + API_MOVIE_VIEW, JSONObject.class, doubanId, imdbId, tmdbId);
        return Objects.requireNonNull(jsonObject).getObject("data", Movie.class);
    }

    public List<Movie> listMovieWeekly() {
        String url = ConfigUtils.getSysConfig(ConfigKey.tmmUrl);
        JSONObject jsonObject = restTemplate.getForObject(url + API_MOVIE_WEEKLY, JSONObject.class);
        JSONArray jsonArray = Objects.requireNonNull(jsonObject).getJSONArray("data");
        return jsonArray != null ? jsonArray.toJavaList(Movie.class) : Lists.newArrayList();
    }

    public Doulist findDoulist(String doubanId) {
        String url = ConfigUtils.getSysConfig(ConfigKey.tmmUrl);
        JSONObject jsonObject = restTemplate.getForObject(url + API_DOULIST_VIEW, JSONObject.class, doubanId);
        return Objects.requireNonNull(jsonObject).getObject("data", Doulist.class);
    }

    public List<Movie> listMovieByDoulist(String doubanId, Integer start) {
        String url = ConfigUtils.getSysConfig(ConfigKey.tmmUrl);
        JSONObject jsonObject = restTemplate.getForObject(url + API_DOULIST_MOVIES, JSONObject.class, doubanId, start);
        JSONArray jsonArray = Objects.requireNonNull(jsonObject).getJSONArray("data");
        return jsonArray != null ? jsonArray.toJavaList(Movie.class) : Lists.newArrayList();
    }

    public List<Tvshow> searchShow(String keyword, String source) {
        String url = ConfigUtils.getSysConfig(ConfigKey.tmmUrl);
        JSONObject jsonObject = restTemplate.getForObject(url + API_SHOW_SEARCH, JSONObject.class, keyword, source);
        JSONArray jsonArray = Objects.requireNonNull(jsonObject).getJSONArray("data");
        return jsonArray != null ? jsonArray.toJavaList(Tvshow.class) : Lists.newArrayList();
    }

    public Tvshow findShow(String doubanId, String imdbId, String tmdbId) {
        if (StringUtils.isAllEmpty(doubanId, imdbId, tmdbId)) {
            return null;
        }
        String url = ConfigUtils.getSysConfig(ConfigKey.tmmUrl);
        JSONObject jsonObject = restTemplate.getForObject(url + API_SHOW_VIEW, JSONObject.class, doubanId, imdbId, tmdbId);
        return Objects.requireNonNull(jsonObject).getObject("data", Tvshow.class);
    }

    public List<Album> searchAlbum(String keyword, String source) {
        String url = ConfigUtils.getSysConfig(ConfigKey.tmmUrl);
        JSONObject jsonObject = restTemplate.getForObject(url + API_ALBUM_SEARCH, JSONObject.class, keyword, source);
        JSONArray jsonArray = Objects.requireNonNull(jsonObject).getJSONArray("data");
        return jsonArray != null ? jsonArray.toJavaList(Album.class) : Lists.newArrayList();
    }

    public Album findAlbum(String neteaseId, String musicbrainzId) {
        if (StringUtils.isAllEmpty(neteaseId, musicbrainzId)) {
            return null;
        }
        String url = ConfigUtils.getSysConfig(ConfigKey.tmmUrl);
        JSONObject jsonObject = restTemplate.getForObject(url + API_ALBUM_VIEW, JSONObject.class, neteaseId, musicbrainzId);
        return Objects.requireNonNull(jsonObject).getObject("data", Album.class);
    }

    public String findLyric(String neteaseId) {
        if (StringUtils.isEmpty(neteaseId)) {
            return null;
        }
        String url = ConfigUtils.getSysConfig(ConfigKey.tmmUrl);
        JSONObject jsonObject = restTemplate.getForObject(url + API_LYRIC_VIEW, JSONObject.class, neteaseId);
        return Objects.requireNonNull(jsonObject).getObject("data", String.class);
    }

    public List<Artist> searchArtist(String keyword, String source) {
        String url = ConfigUtils.getSysConfig(ConfigKey.tmmUrl);
        JSONObject jsonObject = restTemplate.getForObject(url + API_ARTIST_SEARCH, JSONObject.class, keyword, source);
        JSONArray jsonArray = Objects.requireNonNull(jsonObject).getJSONArray("data");
        return jsonArray != null ? jsonArray.toJavaList(Artist.class) : Lists.newArrayList();
    }

    public List<Comic> searchComic(String keyword, String source) {
        String url = ConfigUtils.getSysConfig(ConfigKey.tmmUrl);
        JSONObject jsonObject = restTemplate.getForObject(url + API_COMIC_SERACH, JSONObject.class, keyword, source);
        JSONArray jsonArray = Objects.requireNonNull(jsonObject).getJSONArray("data");
        return jsonArray != null ? jsonArray.toJavaList(Comic.class) : Lists.newArrayList();
    }

    public Comic findComic(String bgmId) {
        if (StringUtils.isEmpty(bgmId)) {
            return null;
        }
        String url = ConfigUtils.getSysConfig(ConfigKey.tmmUrl);
        JSONObject jsonObject = restTemplate.getForObject(url + API_COMIC_VIEW, JSONObject.class, bgmId);
        return Objects.requireNonNull(jsonObject).getObject("data", Comic.class);
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

