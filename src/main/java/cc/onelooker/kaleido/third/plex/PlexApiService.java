package cc.onelooker.kaleido.third.plex;

import cc.onelooker.kaleido.utils.ConfigUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
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

    private final static String API_LIBRARY_LIST = "/library/sections/?X-Plex-Token={plexToken}";
    private final static String API_LIBRARY_LIST_SECONDARY = "/library/sections/{libraryId}/{secondary}?X-Plex-Token={plexToken}";
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

    @Autowired
    private RestTemplate restTemplate;

    @PostConstruct
    public void init() {
        this.plexUrl = ConfigUtils.getSysConfig("plexUrl");
        this.plexToken = ConfigUtils.getSysConfig("plexToken");
    }

    @Deprecated
    public void init(String plexUrl, String plexToken) {
        Validate.notEmpty(plexUrl);
        Validate.notEmpty(plexToken);
        this.plexUrl = plexUrl;
        this.plexToken = plexToken;
    }

    public List<Directory> listLibrary() {
        init();
        PlexResult plexResult = restTemplate.getForObject(plexUrl + API_LIBRARY_LIST, PlexResult.class, plexToken);
        MediaContainer mediaContainer = plexResult.getMediaContainer();
        return mediaContainer.getDirectoryList();
    }

    public List<Metadata> listArtist(String libraryId) {
        init();
        PlexResult plexResult = restTemplate.getForObject(plexUrl + API_ARTIST_LIST, PlexResult.class, libraryId, plexToken);
        MediaContainer mediaContainer = plexResult.getMediaContainer();
        return mediaContainer.getMetadataList();
    }

    public Metadata findArtistById(Long artistId) {
        init();
        PlexResult plexResult = restTemplate.getForObject(plexUrl + API_ARTIST_FIND, PlexResult.class, artistId, plexToken);
        MediaContainer mediaContainer = plexResult.getMediaContainer();
        return mediaContainer.getMetadata();
    }

    public List<Metadata> listAlbum(String libraryId) {
        init();
        PlexResult plexResult = restTemplate.getForObject(plexUrl + API_ALBUM_LIST, PlexResult.class, libraryId, plexToken);
        MediaContainer mediaContainer = plexResult.getMediaContainer();
        return mediaContainer.getMetadataList();
    }

    public List<Metadata> listAlbumByUpdatedAt(String libraryId, Long updatedAt) {
        init();
        PlexResult plexResult = restTemplate.getForObject(plexUrl + API_ALBUM_LIST_BY_UPDATED_AT, PlexResult.class, libraryId, updatedAt, plexToken);
        MediaContainer mediaContainer = plexResult.getMediaContainer();
        return mediaContainer.getMetadataList();
    }

    public Metadata findAlbumById(Long albumId) {
        init();
        PlexResult plexResult = restTemplate.getForObject(plexUrl + API_ALBUM_FIND, PlexResult.class, albumId, plexToken);
        MediaContainer mediaContainer = plexResult.getMediaContainer();
        return mediaContainer.getMetadata();
    }

    public List<Metadata> listAlbumByArtist(String libraryId, Long artistId) {
        PlexResult plexResult = restTemplate.getForObject(plexUrl + API_ALBUM_LIST_BY_ARTIST, PlexResult.class, libraryId, artistId, plexToken);
        MediaContainer mediaContainer = plexResult.getMediaContainer();
        return mediaContainer.getMetadataList();
    }

    public List<Metadata> listTrackByAlbumId(Long albumId) {
        init();
        PlexResult plexResult = restTemplate.getForObject(plexUrl + API_TRACK_LIST_BY_ALBUM, PlexResult.class, albumId, plexToken);
        MediaContainer mediaContainer = plexResult.getMediaContainer();
        return mediaContainer.getMetadataList();
    }

    public String getLibraryPath(String libraryId) {
        Directory directory = listLibrary().stream().filter(s -> StringUtils.equals(s.getKey(), libraryId)).findFirst().get();
        return directory.getLocation().getPath();
    }

    public Metadata findMovieById(Long movieId) {
        init();
        PlexResult plexResult = restTemplate.getForObject(plexUrl + API_MOVIE_FIND, PlexResult.class, movieId, plexToken);
        MediaContainer mediaContainer = plexResult.getMediaContainer();
        return mediaContainer.getMetadata();
    }

    public List<Metadata> listMovie(String libraryId) {
        init();
        PlexResult plexResult = restTemplate.getForObject(plexUrl + API_MOVIE_LIST, PlexResult.class, libraryId, plexToken);
        MediaContainer mediaContainer = plexResult.getMediaContainer();
        return mediaContainer.getMetadataList();
    }

    public List<Metadata> listMovieByUpdatedAt(String libraryId, Long updatedAt) {
        init();
        PlexResult plexResult = restTemplate.getForObject(plexUrl + API_MOVIE_LIST_BY_UPDATED_AT, PlexResult.class, libraryId, updatedAt, plexToken);
        MediaContainer mediaContainer = plexResult.getMediaContainer();
        return mediaContainer.getMetadataList();
    }

    public List<Metadata> listTvshow(String libraryId) {
        init();
        PlexResult plexResult = restTemplate.getForObject(plexUrl + API_TVSHOW_LIST, PlexResult.class, libraryId, plexToken);
        MediaContainer mediaContainer = plexResult.getMediaContainer();
        return mediaContainer.getMetadataList();
    }

    public List<Metadata> listTvshowByUpdatedAt(String libraryId, Long updatedAt) {
        init();
        PlexResult plexResult = restTemplate.getForObject(plexUrl + API_TVSHOW_LIST_BY_UPDATED_AT, PlexResult.class, libraryId, updatedAt, plexToken);
        MediaContainer mediaContainer = plexResult.getMediaContainer();
        return mediaContainer.getMetadataList();
    }

    public List<Metadata> listEpsiode(String libraryId) {
        init();
        PlexResult episodes = restTemplate.getForObject(plexUrl + API_EPISODE_LIST, PlexResult.class, libraryId, plexToken);
        MediaContainer mediaContainer = episodes.getMediaContainer();
        return mediaContainer.getMetadataList();
    }

    public List<Metadata> listEpsiodeByUpdatedAt(String libraryId, Long updatedAt) {
        init();
        PlexResult plexResult = restTemplate.getForObject(plexUrl + API_EPISODE_LIST_BY_UPDATED_AT, PlexResult.class, libraryId, updatedAt, plexToken);
        MediaContainer mediaContainer = plexResult.getMediaContainer();
        return mediaContainer.getMetadataList();
    }

    public Metadata findEpisodeById(Long episodeId) {
        init();
        PlexResult plexResult = restTemplate.getForObject(plexUrl + API_EPISODE_FIND, PlexResult.class, episodeId, plexToken);
        MediaContainer mediaContainer = plexResult.getMediaContainer();
        return mediaContainer.getMetadata();
    }

    public Metadata findSeasonById(Long seasonId) {
        init();
        PlexResult plexResult = restTemplate.getForObject(plexUrl + API_SEASON_FIND, PlexResult.class, seasonId, plexToken);
        MediaContainer mediaContainer = plexResult.getMediaContainer();
        return mediaContainer.getMetadata();
    }

    public Metadata findTvshowById(Long tvshowId) {
        init();
        PlexResult plexResult = restTemplate.getForObject(plexUrl + API_TVSHOW_FIND, PlexResult.class, tvshowId, plexToken);
        MediaContainer mediaContainer = plexResult.getMediaContainer();
        return mediaContainer.getMetadata();
    }

    public void refreshMovieById(Long movieId) {
        init();
        restTemplate.put(plexUrl + API_MOVIE_REFRESH, String.class, movieId, plexToken);
    }

    public void refresAlbumById(Long albumId) {
        init();
        restTemplate.put(plexUrl + API_ALBUM_REFRESH, String.class, albumId, plexToken);
    }

    public List<Directory> listDirectory(String libraryId, String secondary) {
        init();
        PlexResult plexResult = restTemplate.getForObject(plexUrl + API_LIBRARY_LIST_SECONDARY, PlexResult.class, libraryId, secondary, plexToken);
        MediaContainer mediaContainer = plexResult.getMediaContainer();
        return mediaContainer.getDirectoryList();
    }

    public void deleteCollection(String collectionId) {
        init();
        restTemplate.delete(plexUrl + API_COLLECTION, String.class, collectionId, plexToken);
    }

    public Metadata findCollectionById(Long collectionId) {
        init();
        PlexResult plexResult = restTemplate.getForObject(plexUrl + API_COLLECTION, PlexResult.class, collectionId, plexToken);
        MediaContainer mediaContainer = plexResult.getMediaContainer();
        return mediaContainer.getMetadata();
    }

    public List<Metadata> listMovieByCollectionId(Long collectionId) {
        init();
        PlexResult plexResult = restTemplate.getForObject(plexUrl + API_COLLECTION_CHILDREN, PlexResult.class, collectionId, plexToken);
        MediaContainer mediaContainer = plexResult.getMediaContainer();
        return mediaContainer.getMetadataList();
    }

    public List<Metadata> listCollection(String libraryId) {
        init();
        PlexResult plexResult = restTemplate.getForObject(plexUrl + API_COLLECTIONS, PlexResult.class, libraryId, plexToken);
        MediaContainer mediaContainer = plexResult.getMediaContainer();
        return mediaContainer.getMetadataList();
    }
}
