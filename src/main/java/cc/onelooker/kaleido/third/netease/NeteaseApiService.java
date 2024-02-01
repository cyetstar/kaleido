package cc.onelooker.kaleido.third.netease;

import cc.onelooker.kaleido.enums.ConfigKey;
import cc.onelooker.kaleido.utils.ConfigUtils;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @Author cyetstar
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

    public void getConfigInfo() {
        this.url = ConfigUtils.getSysConfig(ConfigKey.neteaseUrl);
    }

    public List<Album> searchAlbum(String keywords, Integer limit) {
        getConfigInfo();
        JSONObject jsonObject = restTemplate.getForObject(url + API_CLOUDSEARCH, JSONObject.class, keywords, "10", limit);
        JSONObject resultJsonObject = jsonObject.getJSONObject("result");
        Integer albumCount = resultJsonObject.getInteger("albumCount");
        if (albumCount != null && albumCount > 0) {
            JSONArray albumJSONArray = resultJsonObject.getJSONArray("albums");
            return albumJSONArray.toJavaList(Album.class);
        }
        return Lists.newArrayList();
    }

    public List<Artist> searchArtist(String keywords, Integer limit) {
        getConfigInfo();
        JSONObject jsonObject = restTemplate.getForObject(url + API_CLOUDSEARCH, JSONObject.class, keywords, "100", limit);
        JSONObject resultJsonObject = jsonObject.getJSONObject("result");
        Integer artistCount = resultJsonObject.getInteger("artistCount");
        if (artistCount > 0) {
            JSONArray artistJSONArray = resultJsonObject.getJSONArray("artists");
            return artistJSONArray.toJavaList(Artist.class);
        }
        return Lists.newArrayList();
    }

    public Album getAlbum(String id) {
        getConfigInfo();
        JSONObject jsonObject = restTemplate.getForObject(url + API_ALBUM, JSONObject.class, id);
        JSONObject albumJsonObject = jsonObject.getJSONObject("album");
        JSONArray songJSONArray = jsonObject.getJSONArray("songs");
        Album album = albumJsonObject.toJavaObject(Album.class);
        List<Song> songList = songJSONArray.toJavaList(Song.class);
        album.setSongs(songList);
        return album;
    }

    public String getLyric(String id) {
        getConfigInfo();
        JSONObject jsonObject = restTemplate.getForObject(url + API_LYRIC, JSONObject.class, id);
        JSONObject lyricJsonObject = jsonObject.getJSONObject("lrc");
        return lyricJsonObject.getString("lyric");
    }

}
