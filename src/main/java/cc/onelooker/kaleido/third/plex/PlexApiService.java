package cc.onelooker.kaleido.third.plex;

import cc.onelooker.kaleido.enums.ConfigKey;
import cc.onelooker.kaleido.utils.ConfigUtils;
import cn.hutool.core.thread.ThreadUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @Author cyetstar
 * @Date 2023-09-25 22:25:00
 * @Description TODO
 */
@Component
public class PlexApiService {

    private String plexToken;
    private String plexUrl;
    private Integer plexRetries;

    private final static String API_LIBRARY_LIST = "/library/sections/?X-Plex-Token={plexToken}";
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
    private final static String API_MOVIE_LIST = "/library/sections/{libraryId}/all?X-Plex-Token={plexToken}";
    private final static String API_MOVIE_LIST_BY_UPDATED_AT = "/library/sections/{libraryId}/all?updatedAt>={updatedAt}&X-Plex-Token={plexToken}";
    private final static String API_MOVIE_FIND = "/library/metadata/{movieId}?X-Plex-Token={plexToken}";
    private final static String API_MOVIE_REFRESH = "/library/metadata/{movieId}/refresh?force=1&X-Plex-Token={plexToken}";
    private final static String API_EPISODE_LIST = "/library/sections/{libraryId}/all?type=4&X-Plex-Token={plexToken}";
    private final static String API_EPISODE_LIST_BY_UPDATED_AT = "/library/sections/{libraryId}/all?type=4&updatedAt>={updatedAt}&X-Plex-Token={plexToken}";
    private final static String API_EPISODE_FIND = "/library/metadata/{episodeId}?X-Plex-Token={plexToken}";
    private final static String API_SEASON_FIND = "/library/metadata/{seasonId}?X-Plex-Token={plexToken}";
    private final static String API_TVSHOW_LIST = "/library/sections/{libraryId}/all?X-Plex-Token={plexToken}";
    private final static String API_TVSHOW_LIST_BY_UPDATED_AT = "/library/sections/{libraryId}/all?updatedAt>={updatedAt}&X-Plex-Token={plexToken}";
    private final static String API_TVSHOW_FIND = "/library/metadata/{tvshowId}?X-Plex-Token={plexToken}";
    private final static String API_COLLECTIONS = "/library/sections/{libraryId}/collections?X-Plex-Token={plexToken}";
    private final static String API_COLLECTION = "/library/collections/{collectionId}?X-Plex-Token={plexToken}";
    private final static String API_COLLECTION_CHILDREN = "/library/collections/{collectionId}/children?X-Plex-Token={plexToken}";

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
            PlexResult plexResult = restTemplate.getForObject(plexUrl + API_LIBRARY_LIST, PlexResult.class, plexToken);
            MediaContainer mediaContainer = plexResult.getMediaContainer();
            return mediaContainer.getDirectoryList();
        });
    }

    public List<Metadata> listArtist(String libraryId) {
        return doRequest(() -> {
            PlexResult plexResult = restTemplate.getForObject(plexUrl + API_ARTIST_LIST, PlexResult.class, libraryId, plexToken);
            MediaContainer mediaContainer = plexResult.getMediaContainer();
            return mediaContainer.getMetadataList();
        });
    }

    public Metadata findArtistById(Long artistId) {
        return doRequest(() -> {
            PlexResult plexResult = restTemplate.getForObject(plexUrl + API_ARTIST_FIND, PlexResult.class, artistId, plexToken);
            MediaContainer mediaContainer = plexResult.getMediaContainer();
            return mediaContainer.getMetadata();
        });
    }

    public List<Metadata> listAlbum(String libraryId) {
        return doRequest(() -> {
            PlexResult plexResult = restTemplate.getForObject(plexUrl + API_ALBUM_LIST, PlexResult.class, libraryId, plexToken);
            MediaContainer mediaContainer = plexResult.getMediaContainer();
            return mediaContainer.getMetadataList();
        });
    }

    public List<Metadata> listAlbumByUpdatedAt(String libraryId, Long updatedAt) {
        return doRequest(() -> {
            PlexResult plexResult = restTemplate.getForObject(plexUrl + API_ALBUM_LIST_BY_UPDATED_AT, PlexResult.class, libraryId, updatedAt, plexToken);
            MediaContainer mediaContainer = plexResult.getMediaContainer();
            return mediaContainer.getMetadataList();
        });
    }

    public Metadata findAlbumById(Long albumId) {
        return doRequest(() -> {
            PlexResult plexResult = restTemplate.getForObject(plexUrl + API_ALBUM_FIND, PlexResult.class, albumId, plexToken);
            MediaContainer mediaContainer = plexResult.getMediaContainer();
            return mediaContainer.getMetadata();
        });
    }

    public List<Metadata> listAlbumByArtist(String libraryId, Long artistId) {
        return doRequest(() -> {
            PlexResult plexResult = restTemplate.getForObject(plexUrl + API_ALBUM_LIST_BY_ARTIST, PlexResult.class, libraryId, artistId, plexToken);
            MediaContainer mediaContainer = plexResult.getMediaContainer();
            return mediaContainer.getMetadataList();
        });
    }

    public List<Metadata> listTrackByAlbumId(Long albumId) {
        return doRequest(() -> {
            PlexResult plexResult = restTemplate.getForObject(plexUrl + API_TRACK_LIST_BY_ALBUM, PlexResult.class, albumId, plexToken);
            MediaContainer mediaContainer = plexResult.getMediaContainer();
            return mediaContainer.getMetadataList();
        });
    }

    public String getLibraryPath(String libraryId) {
        return doRequest(() -> {
            Directory directory = listLibrary().stream().filter(s -> StringUtils.equals(s.getKey(), libraryId)).findFirst().get();
            return directory.getLocation().getPath();
        });
    }

    public Metadata findMovieById(Long movieId) {
        return doRequest(() -> {
            PlexResult plexResult = restTemplate.getForObject(plexUrl + API_MOVIE_FIND, PlexResult.class, movieId, plexToken);
            MediaContainer mediaContainer = plexResult.getMediaContainer();
            return mediaContainer.getMetadata();
        });
    }

    public List<Metadata> listMovie(String libraryId) {
        return doRequest(() -> {
            PlexResult plexResult = restTemplate.getForObject(plexUrl + API_MOVIE_LIST, PlexResult.class, libraryId, plexToken);
            MediaContainer mediaContainer = plexResult.getMediaContainer();
            return mediaContainer.getMetadataList();
        });
    }

    public List<Metadata> listMovieByUpdatedAt(String libraryId, Long updatedAt) {
        return doRequest(() -> {
            PlexResult plexResult = restTemplate.getForObject(plexUrl + API_MOVIE_LIST_BY_UPDATED_AT, PlexResult.class, libraryId, updatedAt, plexToken);
            MediaContainer mediaContainer = plexResult.getMediaContainer();
            return mediaContainer.getMetadataList();
        });
    }

    public List<Metadata> listTvshow(String libraryId) {
        return doRequest(() -> {
            PlexResult plexResult = restTemplate.getForObject(plexUrl + API_TVSHOW_LIST, PlexResult.class, libraryId, plexToken);
            MediaContainer mediaContainer = plexResult.getMediaContainer();
            return mediaContainer.getMetadataList();
        });
    }

    public List<Metadata> listTvshowByUpdatedAt(String libraryId, Long updatedAt) {
        return doRequest(() -> {
            PlexResult plexResult = restTemplate.getForObject(plexUrl + API_TVSHOW_LIST_BY_UPDATED_AT, PlexResult.class, libraryId, updatedAt, plexToken);
            MediaContainer mediaContainer = plexResult.getMediaContainer();
            return mediaContainer.getMetadataList();
        });
    }

    public List<Metadata> listEpsiode(String libraryId) {
        return doRequest(() -> {
            PlexResult episodes = restTemplate.getForObject(plexUrl + API_EPISODE_LIST, PlexResult.class, libraryId, plexToken);
            MediaContainer mediaContainer = episodes.getMediaContainer();
            return mediaContainer.getMetadataList();
        });
    }

    public List<Metadata> listEpsiodeByUpdatedAt(String libraryId, Long updatedAt) {
        return doRequest(() -> {
            PlexResult plexResult = restTemplate.getForObject(plexUrl + API_EPISODE_LIST_BY_UPDATED_AT, PlexResult.class, libraryId, updatedAt, plexToken);
            MediaContainer mediaContainer = plexResult.getMediaContainer();
            return mediaContainer.getMetadataList();
        });
    }

    public Metadata findEpisodeById(Long episodeId) {
        return doRequest(() -> {
            PlexResult plexResult = restTemplate.getForObject(plexUrl + API_EPISODE_FIND, PlexResult.class, episodeId, plexToken);
            MediaContainer mediaContainer = plexResult.getMediaContainer();
            return mediaContainer.getMetadata();
        });
    }

    public Metadata findSeasonById(Long seasonId) {
        return doRequest(() -> {
            PlexResult plexResult = restTemplate.getForObject(plexUrl + API_SEASON_FIND, PlexResult.class, seasonId, plexToken);
            MediaContainer mediaContainer = plexResult.getMediaContainer();
            return mediaContainer.getMetadata();
        });
    }

    public Metadata findTvshowById(Long tvshowId) {
        return doRequest(() -> {
            PlexResult plexResult = restTemplate.getForObject(plexUrl + API_TVSHOW_FIND, PlexResult.class, tvshowId, plexToken);
            MediaContainer mediaContainer = plexResult.getMediaContainer();
            return mediaContainer.getMetadata();
        });
    }

    public void refreshMovieById(Long movieId) {
        doRequest(() -> {
            restTemplate.put(plexUrl + API_MOVIE_REFRESH, String.class, movieId, plexToken);
            return true;
        });
    }

    public void refresAlbumById(Long albumId) {
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

    public void deleteCollection(Long collectionId) {
        doRequest(() -> {
            restTemplate.delete(plexUrl + API_COLLECTION, collectionId, plexToken);
            return true;
        });
    }

    public Metadata findCollectionById(Long collectionId) {
        return doRequest(() -> {
            PlexResult plexResult = restTemplate.getForObject(plexUrl + API_COLLECTION, PlexResult.class, collectionId, plexToken);
            MediaContainer mediaContainer = plexResult.getMediaContainer();
            return mediaContainer.getMetadata();
        });
    }

    public List<Metadata> listMovieByCollectionId(Long collectionId) {
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

    public void deleteMetadata(Long metadataId) {
        doRequest(() -> {
            restTemplate.delete(plexUrl + API_METADATA, metadataId, plexToken);
            return true;
        });
    }

    public List<Metadata> listMetadataChildren(Long metadataId) {
        return doRequest(() -> {
            PlexResult plexResult = restTemplate.getForObject(plexUrl + API_METADATA_CHILDREN, PlexResult.class, metadataId, plexToken);
            return plexResult.getMediaContainer().getMetadataList();
        });
    }

    public Metadata findMetadata(Long metadataId) {
        return doRequest(() -> {
            PlexResult plexResult = restTemplate.getForObject(plexUrl + API_METADATA, PlexResult.class, metadataId, plexToken);
            MediaContainer mediaContainer = plexResult.getMediaContainer();
            return mediaContainer.getMetadata();
        });
    }

    public void refreshMetadata(Long metadataId) {
         doRequest(() -> {
            restTemplate.put(plexUrl + API_METADATA_REFRESH, String.class, metadataId, plexToken);
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
