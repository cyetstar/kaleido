package cc.onelooker.kaleido.third.tmm;

import cc.onelooker.kaleido.enums.ConfigKey;
import cc.onelooker.kaleido.utils.ConfigUtils;
import com.alibaba.fastjson2.JSONArray;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

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

    @Autowired
    private RestTemplate restTemplate;

    public List<Movie> searchMovie(String keyword, String type) {
        String url = ConfigUtils.getSysConfig(ConfigKey.tmmUrl);
        JSONArray jsonArray = restTemplate.getForObject(url + API_SEARCH_MOVIE, JSONArray.class, keyword, type);
        if (jsonArray != null) {
            return jsonArray.toJavaList(Movie.class);
        }
        return Lists.newArrayList();
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
        if (jsonArray != null) {
            return jsonArray.toJavaList(Movie.class);
        }
        return Lists.newArrayList();
    }

    public Tvshow findTvshow(String doubanId, String imdbId, String tmdbId) {
        if (StringUtils.isAllEmpty(doubanId, imdbId, tmdbId)) {
            return null;
        }
        String url = ConfigUtils.getSysConfig(ConfigKey.tmmUrl);
        return restTemplate.getForObject(url + API_FIND_TVSHOW, Tvshow.class, doubanId, imdbId, tmdbId);
    }
}
