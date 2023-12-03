package cc.onelooker.kaleido.third.douban;

import cc.onelooker.kaleido.utils.ConfigUtils;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @Author xiadawei
 * @Date 2023-12-03 17:20:00
 * @Description TODO
 */
@Component
public class DoubanApiService {

    private final static String URL = "https://api.douban.com";
    private final static String API_SEARCH_MOVIE = "/v2/movie/search";
    private final static String API_FIND_MOVIE = "/v2/movie/subject/{doubanId}";
    private String apikey = null;

    @Autowired
    private RestTemplate restTemplate;

    @PostConstruct
    public void init() {
        if (StringUtils.isEmpty(this.apikey)) {
            this.apikey = ConfigUtils.getSysConfig("doubanApikey");
        }
    }

    public List<Movie> searchMovie(String keywords) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("apikey", this.apikey);
        map.add("q", keywords);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
        ResponseEntity<JSONObject> response = restTemplate.postForEntity(URL + API_SEARCH_MOVIE, request, JSONObject.class);
        JSONObject jsonObject = response.getBody();
        if (jsonObject != null) {
            return jsonObject.getJSONArray("subjects").toJavaList(Movie.class);
        }
        return Lists.newArrayList();
    }

    public Movie findMovieById(String doubanId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("apikey", this.apikey);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
        ResponseEntity<Movie> response = restTemplate.postForEntity(URL + API_FIND_MOVIE, request, Movie.class, doubanId);
        return response.getBody();
    }
}
