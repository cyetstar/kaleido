package cc.onelooker.kaleido.third.douban;

import cc.onelooker.kaleido.enums.ConfigKey;
import cc.onelooker.kaleido.utils.ConfigUtils;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.google.common.collect.Lists;
import com.zjjcnt.common.util.constant.Constants;
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

import java.util.List;

/**
 * @Author cyetstar
 * @Date 2023-12-03 17:20:00
 * @Description TODO
 */
@Component
public class DoubanApiService {

    private final static String URL = "https://api.douban.com";
    private final static String API_SEARCH_MOVIE = "/v2/movie/search";
    private final static String API_FIND_MOVIE_SUBJECT = "/v2/movie/subject/{doubanId}";
    private final static String API_FIND_MOVIE = "/v2/movie/{doubanId}";
    private final static String API_FIND_MOVIE_BY_IMDB = "/v2/movie/imdb/{imdbId}";
    private final static String API_LIST_MOVIE_WEEKLY = "/v2/movie/weekly";

    @Autowired
    private RestTemplate restTemplate;

    public List<Movie> searchMovie(String keywords) {
        String apikey = ConfigUtils.getSysConfig(ConfigKey.doubanApikey);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("apikey", apikey);
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
        String apikey = ConfigUtils.getSysConfig(ConfigKey.doubanApikey);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("apikey", apikey);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
        ResponseEntity<Movie> response = restTemplate.postForEntity(URL + API_FIND_MOVIE_SUBJECT, request, Movie.class, doubanId);
        return response.getBody();
    }

    public Movie findMovieByImdbId(String imdbId) {
        String apikey = ConfigUtils.getSysConfig(ConfigKey.doubanApikey);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("apikey", apikey);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
        ResponseEntity<JSONObject> response = restTemplate.postForEntity(URL + API_FIND_MOVIE_BY_IMDB, request, JSONObject.class, imdbId);
        JSONObject jsonObject = response.getBody();
        String doubanId = StringUtils.substringAfterLast(jsonObject.getString("id"), Constants.SLASH);
        return findMovieById(doubanId);
    }

    public List<Subject> listMovieWeekly() {
        String apikey = ConfigUtils.getSysConfig(ConfigKey.doubanApikey);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("apikey", apikey);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
        ResponseEntity<JSONObject> response = restTemplate.postForEntity(URL + API_LIST_MOVIE_WEEKLY, request, JSONObject.class);
//        String text = null;
//        try {
//            text = FileUtils.readFileToString(new File("/Users/cyetstar/20231222.json"));
//        } catch (IOException e) {
//        }
//        JSONObject jsonObject = JSONObject.parseObject(text);
        JSONObject jsonObject = response.getBody();
        if (jsonObject != null) {
            JSONArray jsonArray = jsonObject.getJSONArray("subjects");
            return jsonArray.toJavaList(Subject.class);
        }
        return Lists.newArrayList();
    }
}
