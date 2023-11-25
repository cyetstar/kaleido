package cc.onelooker.kaleido.plex;

import cc.onelooker.kaleido.plex.resp.*;
import cc.onelooker.kaleido.utils.ConfigUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @Author xiadawei
 * @Date 2023-09-25 22:25:00
 * @Description TODO
 */
@Component
public class PlexApiService {

    private String plexToken;
    private String plexUrl;

    private final static String API_LIST_LIBRARY = "/library/sections/?X-Plex-Token={plexToken}";
    private final static String API_LIST_ARTIST = "/library/sections/{libraryId}/all?X-Plex-Token={plexToken}";
    private final static String API_FIND_ARTIST = "/library/metadata/{artistId}?X-Plex-Token={plexToken}";
    private final static String API_LIST_ALBUM = "/library/sections/{libraryId}/all?type=9&X-Plex-Token={plexToken}";
    private final static String API_LIST_ALBUM_BY_UPDATED_AT = "/library/sections/{libraryId}/all?type=9&updatedAt>={updatedAt}&X-Plex-Token={plexToken}";
    private final static String API_FIND_ALBUM = "/library/metadata/{albumId}?X-Plex-Token={plexToken}";
    private final static String API_LIST_ALBUM_BY_ARTIST = "/library/sections/{libraryId}/all?artist.id={artistId}&type=9&X-Plex-Token={plexToken}";
    private final static String API_LIST_TRACK_BY_ALBUM = "/library/metadata/{albumId}/children?X-Plex-Token={plexToken}";
    private final static String API_LIST_MOVIE = "/library/sections/{libraryId}/all?X-Plex-Token={plexToken}";
    private final static String API_LIST_MOVIE_BY_UPDATED_AT = "/library/sections/{libraryId}/all?updatedAt>={updatedAt}&X-Plex-Token={plexToken}";
    private final static String API_FIND_MOVIE = "/library/metadata/{movieId}?X-Plex-Token={plexToken}";

    @Autowired
    private RestTemplate restTemplate;

    @PostConstruct
    public void init() {
        if (StringUtils.isEmpty(this.plexUrl) || StringUtils.isEmpty(this.plexToken)) {
            this.plexUrl = ConfigUtils.getSysConfig("plexUrl");
            this.plexToken = ConfigUtils.getSysConfig("plexToken");
        }
    }

    @Deprecated
    public void init(String plexUrl, String plexToken) {
        Validate.notEmpty(plexUrl);
        Validate.notEmpty(plexToken);
        this.plexUrl = plexUrl;
        this.plexToken = plexToken;
    }

    public List<GetLibraries.Directory> listLibrary() {
        init();
        GetLibraries resp = restTemplate.getForObject(plexUrl + API_LIST_LIBRARY, GetLibraries.class, plexToken);
        GetLibraries.MediaContainer mediaContainer = resp.getMediaContainer();
        return mediaContainer.getDirectoryList();
    }

    public List<GetMusicArtists.Metadata> listArtist(String libraryId) {
        init();
        GetMusicArtists musicArtists = restTemplate.getForObject(plexUrl + API_LIST_ARTIST, GetMusicArtists.class, libraryId, plexToken);
        GetMusicArtists.MediaContainer mediaContainer = musicArtists.getMediaContainer();
        return mediaContainer.getMetadataList();
    }

    public GetMusicArtists.Metadata findArtistById(Long artistId) {
        init();
        GetMusicArtists musicArtists = restTemplate.getForObject(plexUrl + API_FIND_ARTIST, GetMusicArtists.class, artistId, plexToken);
        GetMusicArtists.MediaContainer mediaContainer = musicArtists.getMediaContainer();
        return mediaContainer.getMetadata();
    }

    public List<GetMusicAlbums.Metadata> listAlbum(String libraryId) {
        init();
        GetMusicAlbums musicAlbums = restTemplate.getForObject(plexUrl + API_LIST_ALBUM, GetMusicAlbums.class, libraryId, plexToken);
        GetMusicAlbums.MediaContainer mediaContainer = musicAlbums.getMediaContainer();
        return mediaContainer.getMetadataList();
    }

    public List<GetMusicAlbums.Metadata> listAlbumByUpdatedAt(String libraryId, Long updatedAt) {
        init();
        GetMusicAlbums musicAlbums = restTemplate.getForObject(plexUrl + API_LIST_ALBUM_BY_UPDATED_AT, GetMusicAlbums.class, libraryId, updatedAt, plexToken);
        GetMusicAlbums.MediaContainer mediaContainer = musicAlbums.getMediaContainer();
        return mediaContainer.getMetadataList();
    }

    public GetMusicAlbums.Metadata findAlbumById(Long albumId) {
        init();
        GetMusicAlbums musicAlbums = restTemplate.getForObject(plexUrl + API_FIND_ALBUM, GetMusicAlbums.class, albumId, plexToken);
        GetMusicAlbums.MediaContainer mediaContainer = musicAlbums.getMediaContainer();
        return mediaContainer.getMetadata();
    }

    public List<GetMusicAlbums.Metadata> listAlbumByArtist(String libraryId, Long artistId) {
        GetMusicAlbums musicAlbums = restTemplate.getForObject(plexUrl + API_LIST_ALBUM_BY_ARTIST, GetMusicAlbums.class, libraryId, artistId, plexToken);
        GetMusicAlbums.MediaContainer mediaContainer = musicAlbums.getMediaContainer();
        return mediaContainer.getMetadataList();
    }

    public List<GetMusicTracks.Metadata> listTrackByAlbumId(Long albumId) {
        init();
        GetMusicTracks musicTracks = restTemplate.getForObject(plexUrl + API_LIST_TRACK_BY_ALBUM, GetMusicTracks.class, albumId, plexToken);
        GetMusicTracks.MediaContainer mediaContainer = musicTracks.getMediaContainer();
        return mediaContainer.getMetadataList();
    }

    public String getLibraryPath(String libraryId) {
        GetLibraries.Directory directory = listLibrary().stream().filter(s -> StringUtils.equals(s.getKey(), libraryId)).findFirst().get();
        return directory.getLocation().getPath();
    }

    public GetMovies.Metadata findMovieById(Long movieId) {
        init();
        GetMovies movies = restTemplate.getForObject(plexUrl + API_FIND_MOVIE, GetMovies.class, movieId, plexToken);
        GetMovies.MediaContainer mediaContainer = movies.getMediaContainer();
        return mediaContainer.getMetadata();
    }

    public List<GetMovies.Metadata> listMovie(String libraryId) {
        init();
        GetMovies movies = restTemplate.getForObject(plexUrl + API_LIST_MOVIE, GetMovies.class, libraryId, plexToken);
        GetMovies.MediaContainer mediaContainer = movies.getMediaContainer();
        return mediaContainer.getMetadataList();
    }

    public List<GetMovies.Metadata> listMovieByUpdatedAt(String libraryId, Long updatedAt) {
        init();
        GetMovies movies = restTemplate.getForObject(plexUrl + API_LIST_MOVIE_BY_UPDATED_AT, GetMovies.class, libraryId, updatedAt, plexToken);
        GetMovies.MediaContainer mediaContainer = movies.getMediaContainer();
        return mediaContainer.getMetadataList();
    }
}
