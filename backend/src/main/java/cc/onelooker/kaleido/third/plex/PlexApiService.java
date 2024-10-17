package cc.onelooker.kaleido.third.plex;

import cc.onelooker.kaleido.enums.ConfigKey;
import cc.onelooker.kaleido.utils.ConfigUtils;
import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.http.Method;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zjjcnt.common.core.domain.PageResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.UriTemplate;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @Author cyetstar
 * @Date 2023-09-25 22:25:00
 * @Description TODO
 */
@Slf4j
@Component
public class PlexApiService {

    public static final String TYPE_MOVIE = "1";
    public static final String TYPE_SHOW = "2";
    public static final String TYPE_SEASON = "3";
    public static final String TYPE_EPISODE = "4";
    public static final String TYPE_ARTIST = "8";
    public static final String TYPE_ALBUM = "9";
    public static final String TYPE_TRACK = "10";

    private final static String API_LIBRARY_LIST_SECONDARY = "/library/sections/{libraryId}?X-Plex-Token={plexToken}";
    private final static String API_LIBRARY_VIEW_SECONDARY = "/library/sections/{libraryId}/{secondary}?X-Plex-Token={plexToken}";

    private final static String API_SECTIONS = "/library/sections/?X-Plex-Token={plexToken}";
    private final static String API_SECTIONS_ALL = "/library/sections/{libraryId}/all?type={type}&X-Plex-Token={plexToken}&X-Plex-Container-Start={start}&X-Plex-Container-Size={size}";
    private final static String API_SECTIONS_REFRESH = "/library/sections/{libraryId}/refresh?force=0&X-Plex-Token={token}";
    private final static String API_METADATA = "/library/metadata/{metadataId}?X-Plex-Token={plexToken}";
    private final static String API_METADATA_CHILDREN = "/library/metadata/{metadataId}/children?X-Plex-Token={plexToken}";
    private final static String API_METADATA_REFRESH = "/library/metadata/{metadataId}/refresh?force=1&X-Plex-Token={plexToken}";

    public List<Directory> listLibrary() {
        PlexResult plexResult = get(API_SECTIONS, null);
        if (plexResult == null) {
            return Lists.newArrayList();
        }
        MediaContainer mediaContainer = plexResult.getMediaContainer();
        return mediaContainer.getDirectoryList();
    }

    public Metadata findMetadata(String metadataId) {
        Map<String, Object> uriVariables = Maps.newHashMap();
        uriVariables.put("metadataId", metadataId);
        PlexResult plexResult = get(API_METADATA, uriVariables);
        if (plexResult == null) {
            return null;
        }
        MediaContainer mediaContainer = plexResult.getMediaContainer();
        return mediaContainer.getMetadata();
    }

    public PageResult<Metadata> pageMetadata(String libraryId, String type, Integer pageNumber, Integer pageSize) {
        Map<String, Object> uriVariables = Maps.newHashMap();
        uriVariables.put("libraryId", libraryId);
        uriVariables.put("type", type);
        uriVariables.put("start", (pageNumber - 1) * pageSize);
        uriVariables.put("size", pageSize);
        PlexResult plexResult = get(API_SECTIONS_ALL, uriVariables);
        PageResult<Metadata> pageResult = new PageResult<>();
        pageResult.setPageNumber(pageNumber.longValue());
        pageResult.setPageSize(pageSize.longValue());
        pageResult.setSearchCount(true);
        List<Metadata> metadataList = null;
        if (plexResult != null) {
            MediaContainer mediaContainer = plexResult.getMediaContainer();
            metadataList = mediaContainer.getMetadataList();
            pageResult.setTotal(mediaContainer.getTotalSize().longValue());
        }
        pageResult.setRecords(Lists.newArrayList(CollectionUtils.emptyIfNull(metadataList)));
        return pageResult;
    }

    public List<Metadata> listMetadataChildren(String metadataId) {
        Map<String, Object> uriVariables = Maps.newHashMap();
        uriVariables.put("metadataId", metadataId);
        PlexResult plexResult = get(API_METADATA_CHILDREN, uriVariables);
        if (plexResult == null) {
            return Lists.newArrayList();
        }
        return plexResult.getMediaContainer().getMetadataList();
    }

    public void deleteMetadata(String metadataId) {
        Map<String, Object> uriVariables = Maps.newHashMap();
        uriVariables.put("metadataId", metadataId);
        URI uri = generateUri(API_METADATA_CHILDREN, uriVariables);
        HttpRequest request = HttpUtil.createRequest(Method.DELETE, uri.toString());
        request.execute();
    }

    public void refreshMetadata(String metadataId) {
        Map<String, Object> uriVariables = Maps.newHashMap();
        uriVariables.put("metadataId", metadataId);
        URI uri = generateUri(API_METADATA_REFRESH, uriVariables);
        HttpRequest request = HttpUtil.createRequest(Method.PUT, uri.toString());
        request.execute();
    }

    public List<Directory> listSecondary(String libraryId) {
        Map<String, Object> uriVariables = Maps.newHashMap();
        uriVariables.put("libraryId", libraryId);
        PlexResult plexResult = get(API_LIBRARY_LIST_SECONDARY, uriVariables);
        if (plexResult == null) {
            return Lists.newArrayList();
        }
        MediaContainer mediaContainer = plexResult.getMediaContainer();
        return mediaContainer.getDirectoryList();
    }

    public List<Directory> listDirectoryBySecondary(String libraryId, String secondary) {
        Map<String, Object> uriVariables = Maps.newHashMap();
        uriVariables.put("libraryId", libraryId);
        uriVariables.put("secondary", secondary);
        PlexResult plexResult = get(API_LIBRARY_VIEW_SECONDARY, uriVariables);
        if (plexResult == null) {
            return Lists.newArrayList();
        }
        MediaContainer mediaContainer = plexResult.getMediaContainer();
        return mediaContainer.getDirectoryList();
    }

    public void refreshLibrary(String libraryId) {
        Map<String, Object> uriVariables = Maps.newHashMap();
        uriVariables.put("libraryId", libraryId);
        get(API_SECTIONS_REFRESH, uriVariables);
    }

    private PlexResult get(String url, Map<String, Object> uriVariables) {
        URI uri = generateUri(url, uriVariables);
        HttpRequest request = HttpUtil.createRequest(Method.GET, uri.toString());
        HttpResponse response = request.execute();
        if (response.isOk()) {
            return JSON.parseObject(response.body(), PlexResult.class);
        } else {
            return null;
        }
    }

    private URI generateUri(String url, Map<String, Object> uriVariables) {
        if (uriVariables == null) {
            uriVariables = Maps.newHashMap();
        }
        String plexUrl = ConfigUtils.getSysConfig(ConfigKey.plexUrl);
        String plexToken = ConfigUtils.getSysConfig(ConfigKey.plexToken);
        uriVariables.put("plexToken", plexToken);
        UriTemplate uriTemplate = new UriTemplate(plexUrl + url);
        return uriTemplate.expand(uriVariables);
    }

}
