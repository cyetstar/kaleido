package cc.onelooker.kaleido.third.tmm;

import cc.onelooker.kaleido.enums.ConfigKey;
import cc.onelooker.kaleido.utils.ConfigUtils;
import com.alibaba.fastjson2.JSONArray;
import com.google.common.collect.Lists;
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

    private String url;

    private final static String API_SEARCH_MOVIE = "/movie/search?keyword={keyword}";
    private final static String API_FIND_MOVIE = "/movie/detail?douban_id={doubanId}";
    private final static String API_FIND_DOULIST = "/doulist/detail?douban_id={doubanId}";
    private final static String API_LIST_DOULIST_MOVIE = "/doulist/movies?douban_id={doubanId}&start={start}";

    @Autowired
    private RestTemplate restTemplate;

    public void setUrl(String url) {
        this.url = url;
    }

    private void getTmmConfig() {
        this.url = ConfigUtils.getSysConfig(ConfigKey.tmmUrl, url);
    }

    public List<Movie> searchMovie(String keyword) {
        getTmmConfig();
        JSONArray jsonArray = restTemplate.getForObject(url + API_SEARCH_MOVIE, JSONArray.class, keyword);
        if (jsonArray != null) {
            return jsonArray.toJavaList(Movie.class);
        }
        return Lists.newArrayList();
    }

    public Movie findMovie(String doubanId) {
        getTmmConfig();
        return restTemplate.getForObject(url + API_FIND_MOVIE, Movie.class, doubanId);
    }

    public Doulist findDoulist(String doubanId) {
        getTmmConfig();
        return restTemplate.getForObject(url + API_FIND_DOULIST, Doulist.class, doubanId);
    }

    public List<Movie> listDoulistMovie(String doubanId, Integer start) {
        getTmmConfig();
        JSONArray jsonArray = restTemplate.getForObject(url + API_LIST_DOULIST_MOVIE, JSONArray.class, doubanId, start);
        if (jsonArray != null) {
            return jsonArray.toJavaList(Movie.class);
        }
        return Lists.newArrayList();
    }
}
