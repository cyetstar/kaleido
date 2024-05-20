package cc.onelooker.kaleido.third.komga;

import cc.onelooker.kaleido.enums.ConfigKey;
import cc.onelooker.kaleido.utils.ConfigUtils;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.zjjcnt.common.core.domain.PageResult;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;

/**
 * @Author xiadawei
 * @Date 2024-03-09 18:11:00
 * @Description TODO
 */
@Component
public class KomgaApiService {

    private final static String API_SERIES = "/api/v1/series/{seriesId}";
    private final static String API_SERIES_THUMBNAIL = "/api/v1/series/{seriesId}/thumbnail";
    private final static String API_SERIES_BOOKS = "/api/v1/series/{seriesId}/books?size=10000";
    private final static String API_BOOKS = "/api/v1/books?page={pageNumber}&size={pageSize}";
    private final static String API_BOOKS_PAGES = "/api/v1/books/{bookId}/pages";
    private final static String API_BOOKS_PAGES_NUMBER = "/api/v1/books/{bookId}/pages/{number}";
    private final static String API_BOOKS_THUMBNAIL = "/api/v1/books/{bookId}/thumbnail";

    @Autowired
    private RestTemplate restTemplate;

    public PageResult<Book> pageBook(Integer pageNumber, Integer pageSize) {
        String url = ConfigUtils.getSysConfig(ConfigKey.komgaUrl, "http://192.168.3.100:25600");
        ResponseEntity<JSONObject> response = restTemplate.exchange(url + API_BOOKS, HttpMethod.GET, new HttpEntity<>(getHeaders()), JSONObject.class, pageNumber, pageSize);
        JSONObject jsonObject = response.getBody();
        JSONArray content = Objects.requireNonNull(jsonObject).getJSONArray("content");
        PageResult<Book> pageResult = new PageResult<>();
        pageResult.setSearchCount(true);
        pageResult.setPageNumber(jsonObject.getLong("number"));
        pageResult.setPageSize(jsonObject.getLong("size"));
        pageResult.setTotal(jsonObject.getLong("totalElements"));
        pageResult.setRecords(content.toJavaList(Book.class));
        return pageResult;
    }

    public Series findSeries(String seriesId) {
        String url = ConfigUtils.getSysConfig(ConfigKey.komgaUrl, "http://192.168.3.100:25600");
        ResponseEntity<Series> response = restTemplate.exchange(url + API_SERIES, HttpMethod.GET, new HttpEntity<>(getHeaders()), Series.class, seriesId);
        return response.getBody();
    }

    public List<Book> listBookBySeries(String seriesId) {
        String url = ConfigUtils.getSysConfig(ConfigKey.komgaUrl, "http://192.168.3.100:25600");
        ResponseEntity<JSONObject> response = restTemplate.exchange(url + API_SERIES_BOOKS, HttpMethod.GET, new HttpEntity<>(getHeaders()), JSONObject.class, seriesId);
        JSONObject jsonObject = response.getBody();
        JSONArray content = Objects.requireNonNull(jsonObject).getJSONArray("content");
        return content.toJavaList(Book.class);
    }

    public byte[] findBookPage(String bookId, Integer number) {
        String url = ConfigUtils.getSysConfig(ConfigKey.komgaUrl, "http://192.168.3.100:25600");
        ResponseEntity<byte[]> response = restTemplate.exchange(url + API_BOOKS_PAGES_NUMBER, HttpMethod.GET, new HttpEntity<>(getHeaders()), byte[].class, bookId, number);
        return response.getBody();
    }

    public byte[] findBookThumbnail(String bookId) {
        String url = ConfigUtils.getSysConfig(ConfigKey.komgaUrl, "http://192.168.3.100:25600");
        ResponseEntity<byte[]> response = restTemplate.exchange(url + API_BOOKS_THUMBNAIL, HttpMethod.GET, new HttpEntity<>(getHeaders()), byte[].class, bookId);
        return response.getBody();
    }

    public byte[] findSeriesThumbnail(String seriesId) {
        String url = ConfigUtils.getSysConfig(ConfigKey.komgaUrl, "http://192.168.3.100:25600");
        ResponseEntity<byte[]> response = restTemplate.exchange(url + API_SERIES_THUMBNAIL, HttpMethod.GET, new HttpEntity<>(getHeaders()), byte[].class, seriesId);
        return response.getBody();
    }

    public List<Page> listPageByBook(String bookId) {
        String url = ConfigUtils.getSysConfig(ConfigKey.komgaUrl, "http://192.168.3.100:25600");
        ResponseEntity<JSONArray> response = restTemplate.exchange(url + API_BOOKS_PAGES, HttpMethod.GET, new HttpEntity<>(getHeaders()), JSONArray.class, bookId);
        JSONArray jsonArray = response.getBody();
        if (jsonArray != null) {
            return jsonArray.toJavaList(Page.class);
        }
        return Lists.newArrayList();
    }

    private HttpHeaders getHeaders() {
        String username = ConfigUtils.getSysConfig(ConfigKey.komgaUsername);
        String password = ConfigUtils.getSysConfig(ConfigKey.komgaPassword);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBasicAuth(username, password);
        return headers;
    }

}
