package cc.onelooker.kaleido.netease;

import cc.onelooker.kaleido.netease.domain.Album;
import cc.onelooker.kaleido.netease.domain.Song;
import cc.onelooker.kaleido.utils.ConfigUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @Author xiadawei
 * @Date 2023-11-15 17:35:00
 * @Description TODO
 */
@Component
public class NeteaseApiService {

    private String url;

    private final static String API_CLOUDSEARCH = "/cloudsearch?keywords={keywords}&type={type}&limit={limit}";
    private final static String API_ALBUM = "/album?id={id}";
    private final static String API_LYRIC = "/lyric?id={id}";

    @Autowired
    private RestTemplate restTemplate;

    @PostConstruct
    public void init() {
        if (StringUtils.isEmpty(this.url)) {
            this.url = ConfigUtils.getSysConfig("neteaseUrl");
        }
    }

    public List<Album> searchAlbum(String keywords, Integer limit) {
        init();
        JSONObject jsonObject = restTemplate.getForObject(url + API_CLOUDSEARCH, JSONObject.class, keywords, "10", limit);
        JSONObject resultJsonObject = jsonObject.getJSONObject("result");
        Integer albumCount = resultJsonObject.getInteger("albumCount");
        if (albumCount > 0) {
            JSONArray albumJSONArray = resultJsonObject.getJSONArray("albums");
            return albumJSONArray.toJavaList(Album.class);
        }
        return Lists.newArrayList();

    }

    public Album getAlbum(String id) {
        init();
        JSONObject jsonObject = restTemplate.getForObject(url + API_ALBUM, JSONObject.class, id);
        JSONObject albumJsonObject = jsonObject.getJSONObject("album");
        JSONArray songJSONArray = jsonObject.getJSONArray("songs");
        Album album = albumJsonObject.toJavaObject(Album.class);
        List<Song> songList = songJSONArray.toJavaList(Song.class);
        album.setSongs(songList);
        return album;
    }

    public String getLyric(String id) {
        init();
        JSONObject jsonObject = restTemplate.getForObject(url + API_LYRIC, JSONObject.class, id);
        JSONObject lyricJsonObject = jsonObject.getJSONObject("lrc");
        return lyricJsonObject.getString("lyric");
    }
}
