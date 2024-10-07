package cc.onelooker.kaleido.third.plex;

import cc.onelooker.kaleido.enums.ConfigKey;
import cc.onelooker.kaleido.utils.ConfigUtils;
import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.thread.ThreadUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zjjcnt.common.core.domain.PageResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

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

    private String plexToken;
    private String plexUrl;
    private Integer plexRetries;

    private final static String API_LIBRARY_LIST_SECONDARY = "/library/sections/{libraryId}?X-Plex-Token={plexToken}";
    private final static String API_LIBRARY_VIEW_SECONDARY = "/library/sections/{libraryId}/{secondary}?X-Plex-Token={plexToken}";
    private final static String API_ARTIST_LIST = "/library/sections/{libraryId}/all?X-Plex-Token={plexToken}";
    private final static String API_ARTIST_FIND = "/library/metadata/{artistId}?X-Plex-Token={plexToken}";
    private final static String API_ALBUM_LIST = "/library/sections/{libraryId}/all?type=9&X-Plex-Token={plexToken}";
    private final static String API_ALBUM_LIST_BY_UPDATED_AT = "/library/sections/{libraryId}/all?type=9&updatedAt>={updatedAt}&X-Plex-Token={plexToken}";
    private final static String API_ALBUM_LIST_BY_ARTIST = "/library/sections/{libraryId}/all?artist.id={artistId}&type=9&X-Plex-Token={plexToken}";
    private final static String API_ALBUM_FIND = "/library/metadata/{albumId}?X-Plex-Token={plexToken}";
    private final static String API_ALBUM_REFRESH = "/library/metadata/{albumId}/refresh?force=1&X-Plex-Token={plexToken}";
    private final static String API_TRACK_LIST_BY_ALBUM = "/library/metadata/{albumId}/children?X-Plex-Token={plexToken}";
    private final static String API_MOVIE_LIST = "/library/sections/{libraryId}/all?X-Plex-Token={plexToken}&X-Plex-Container-Start={start}&X-Plex-Container-Size={size}";
    private final static String API_MOVIE_LIST_BY_UPDATED_AT = "/library/sections/{libraryId}/all?updatedAt>={updatedAt}&X-Plex-Token={plexToken}";
    private final static String API_MOVIE_FIND = "/library/metadata/{movieId}?X-Plex-Token={plexToken}";
    private final static String API_MOVIE_REFRESH = "/library/metadata/{movieId}/refresh?force=1&X-Plex-Token={plexToken}";
    private final static String API_EPISODE_LIST = "/library/sections/{libraryId}/all?type=4&X-Plex-Token={plexToken}&X-Plex-Container-Start={start}&X-Plex-Container-Size={size}";
    private final static String API_EPISODE_LIST_BY_UPDATED_AT = "/library/sections/{libraryId}/all?type=4&updatedAt>={updatedAt}&X-Plex-Token={plexToken}";
    private final static String API_EPISODE_FIND = "/library/metadata/{episodeId}?X-Plex-Token={plexToken}";
    private final static String API_SEASON_FIND = "/library/metadata/{seasonId}?X-Plex-Token={plexToken}";
    private final static String API_TVSHOW_LIST = "/library/sections/{libraryId}/all?X-Plex-Token={plexToken}";
    private final static String API_TVSHOW_LIST_BY_UPDATED_AT = "/library/sections/{libraryId}/all?updatedAt>={updatedAt}&X-Plex-Token={plexToken}";
    private final static String API_TVSHOW_FIND = "/library/metadata/{tvshowId}?X-Plex-Token={plexToken}";
    private final static String API_COLLECTIONS = "/library/sections/{libraryId}/collections?X-Plex-Token={plexToken}";
    private final static String API_COLLECTION = "/library/collections/{collectionId}?X-Plex-Token={plexToken}";
    private final static String API_COLLECTION_CHILDREN = "/library/collections/{collectionId}/children?X-Plex-Token={plexToken}";

    private final static String API_SECTIONS = "/library/sections/?X-Plex-Token={plexToken}";
    private final static String API_SECTIONS_ALL = "/library/sections/{libraryId}/all?type={type}&X-Plex-Token={token}&X-Plex-Container-Start={start}&X-Plex-Container-Size={size}";
    private final static String API_SECTIONS_REFRESH = "/library/sections/{libraryId}/refresh?force=0&X-Plex-Token={token}";
    private final static String API_METADATA = "/library/metadata/{metadataId}?X-Plex-Token={plexToken}";
    private final static String API_METADATA_CHILDREN = "/library/metadata/{metadataId}/children?X-Plex-Token={plexToken}";
    private final static String API_METADATA_REFRESH = "/library/metadata/{metadataId}/refresh?force=1&X-Plex-Token={plexToken}";

    @Autowired
    private RestTemplate restTemplate;

    private void getPlexConfig() {
        this.plexUrl = ConfigUtils.getSysConfig(ConfigKey.plexUrl);
        this.plexToken = ConfigUtils.getSysConfig(ConfigKey.plexToken);
        this.plexRetries = Integer.valueOf(ConfigUtils.getSysConfig(ConfigKey.plexRetries, "3"));
    }

    public List<Directory> listLibrary() {
        return doRequest(() -> {
            try {
                String plexUrl = ConfigUtils.getSysConfig(ConfigKey.plexUrl);
                String plexToken = ConfigUtils.getSysConfig(ConfigKey.plexToken);
                if (StringUtils.isEmpty(plexUrl) || StringUtils.isEmpty(plexToken)) {
                    return Lists.newArrayList();
                }
                PlexResult plexResult = restTemplate.getForObject(plexUrl + API_SECTIONS, PlexResult.class, plexToken);
                Validate.notNull(plexResult);
                MediaContainer mediaContainer = plexResult.getMediaContainer();
                return mediaContainer.getDirectoryList();
            } catch (Exception e) {
                log.error("获取Plex库列表失败:{}", ExceptionUtil.getMessage(e));
            }
            return Lists.newArrayList();
        });
    }

    public PageResult<Metadata> pageMetadata(String libraryId, String type, Integer pageNumber, Integer pageSize) {
        return doRequest(() -> {
            Map<String, Object> urlParams = Maps.newHashMap();
            urlParams.put("libraryId", libraryId);
            urlParams.put("type", type);
            urlParams.put("token", plexToken);
            urlParams.put("start", (pageNumber - 1) * pageSize);
            urlParams.put("size", pageSize);
            String uri = UriComponentsBuilder.fromUriString(plexUrl + API_SECTIONS_ALL).buildAndExpand(urlParams).toUriString();
            PlexResult plexResult = restTemplate.getForObject(uri, PlexResult.class);
            Validate.notNull(plexResult);
            MediaContainer mediaContainer = plexResult.getMediaContainer();
            PageResult<Metadata> pageResult = new PageResult<>();
            pageResult.setPageNumber(pageNumber.longValue());
            pageResult.setPageSize(pageSize.longValue());
            pageResult.setSearchCount(true);
            pageResult.setTotal(mediaContainer.getTotalSize().longValue());
            pageResult.setRecords(mediaContainer.getMetadataList() == null ? Lists.newArrayList() : mediaContainer.getMetadataList());
            return pageResult;
        });
    }

    public List<Metadata> listTrackByAlbumId(String albumId) {
        return doRequest(() -> {
            PlexResult plexResult = restTemplate.getForObject(plexUrl + API_TRACK_LIST_BY_ALBUM, PlexResult.class, albumId, plexToken);
            MediaContainer mediaContainer = plexResult.getMediaContainer();
            return mediaContainer.getMetadataList();
        });
    }

    public Metadata findMovieById(String movieId) {
        return doRequest(() -> {
            PlexResult plexResult = restTemplate.getForObject(plexUrl + API_MOVIE_FIND, PlexResult.class, movieId, plexToken);
            MediaContainer mediaContainer = plexResult.getMediaContainer();
            return mediaContainer.getMetadata();
        });
    }

    public PageResult<Metadata> pageMovie(String libraryId, Integer pageNumber, Integer pageSize) {
        return doRequest(() -> {
            PlexResult plexResult = restTemplate.getForObject(plexUrl + API_MOVIE_LIST, PlexResult.class, libraryId, plexToken, (pageNumber - 1) * pageSize, pageSize);
            MediaContainer mediaContainer = Objects.requireNonNull(plexResult).getMediaContainer();
            PageResult<Metadata> pageResult = new PageResult<>();
            pageResult.setPageNumber(pageNumber.longValue());
            pageResult.setPageSize(pageSize.longValue());
            pageResult.setSearchCount(true);
            pageResult.setTotal(mediaContainer.getTotalSize().longValue());
            pageResult.setRecords(mediaContainer.getMetadataList() == null ? Lists.newArrayList() : mediaContainer.getMetadataList());
            return pageResult;
        });
    }

    public void refreshMovieById(String movieId) {
        doRequest(() -> {
            restTemplate.put(plexUrl + API_MOVIE_REFRESH, String.class, movieId, plexToken);
            return true;
        });
    }

    public void refresAlbumById(String albumId) {
        doRequest(() -> {
            restTemplate.put(plexUrl + API_ALBUM_REFRESH, String.class, albumId, plexToken);
            return true;
        });
    }

    public List<Directory> listSecondary(String libraryId) {
        return doRequest(() -> {
            PlexResult plexResult = restTemplate.getForObject(plexUrl + API_LIBRARY_LIST_SECONDARY, PlexResult.class, libraryId, plexToken);
            MediaContainer mediaContainer = plexResult.getMediaContainer();
            return mediaContainer.getDirectoryList();
        });
    }

    public List<Directory> listDirectoryBySecondary(String libraryId, String secondary) {
        return doRequest(() -> {
            PlexResult plexResult = restTemplate.getForObject(plexUrl + API_LIBRARY_VIEW_SECONDARY, PlexResult.class, libraryId, secondary, plexToken);
            MediaContainer mediaContainer = plexResult.getMediaContainer();
            return mediaContainer.getDirectoryList();
        });
    }

    public void deleteCollection(String collectionId) {
        doRequest(() -> {
            restTemplate.delete(plexUrl + API_COLLECTION, collectionId, plexToken);
            return true;
        });
    }

    public Metadata findCollectionById(String collectionId) {
        return doRequest(() -> {
            PlexResult plexResult = restTemplate.getForObject(plexUrl + API_COLLECTION, PlexResult.class, collectionId, plexToken);
            MediaContainer mediaContainer = plexResult.getMediaContainer();
            return mediaContainer.getMetadata();
        });
    }

    public List<Metadata> listMovieByCollectionId(String collectionId) {
        return doRequest(() -> {
            PlexResult plexResult = restTemplate.getForObject(plexUrl + API_COLLECTION_CHILDREN, PlexResult.class, collectionId, plexToken);
            MediaContainer mediaContainer = plexResult.getMediaContainer();
            return mediaContainer.getMetadataList();
        });
    }

    public List<Metadata> listCollection(String libraryId) {
        return doRequest(() -> {
            PlexResult plexResult = restTemplate.getForObject(plexUrl + API_COLLECTIONS, PlexResult.class, libraryId, plexToken);
            MediaContainer mediaContainer = plexResult.getMediaContainer();
            return mediaContainer.getMetadataList();
        });
    }

    public void deleteMetadata(String metadataId) {
        doRequest(() -> {
            restTemplate.delete(plexUrl + API_METADATA, metadataId, plexToken);
            return true;
        });
    }

    public List<Metadata> listMetadataChildren(String metadataId) {
        return doRequest(() -> {
            PlexResult plexResult = restTemplate.getForObject(plexUrl + API_METADATA_CHILDREN, PlexResult.class, metadataId, plexToken);
            return plexResult.getMediaContainer().getMetadataList();
        });
    }

    public Metadata findMetadata(String metadataId) {
        return doRequest(() -> {
            PlexResult plexResult = restTemplate.getForObject(plexUrl + API_METADATA, PlexResult.class, metadataId, plexToken);
            MediaContainer mediaContainer = plexResult.getMediaContainer();
            return mediaContainer.getMetadata();
        });
    }

    public void refreshMetadata(String metadataId) {
        doRequest(() -> {
            restTemplate.put(plexUrl + API_METADATA_REFRESH, String.class, metadataId, plexToken);
            return true;
        });
    }

    public void refreshLibrary(String libraryId) {
        doRequest(() -> {
            restTemplate.getForObject(plexUrl + API_SECTIONS_REFRESH, String.class, libraryId, plexToken);
            return true;
        });
    }

    private interface PlexRequest<T> {
        T execute();
    }

    private <T> T doRequest(PlexRequest<T> plexRequest) {
        getPlexConfig();
        int times = 0;
        while (times < this.plexRetries) {
            try {
                return plexRequest.execute();
            } catch (HttpClientErrorException.BadRequest e) {
                times++;
                ThreadUtil.sleep(1000L);
            } catch (HttpClientErrorException.NotFound e) {
                return null;
            }
        }
        return null;
    }

}
