package cc.onelooker.kaleido.plex;

import cc.onelooker.kaleido.plex.resp.GetLibrariesResp;
import cc.onelooker.kaleido.plex.resp.GetMusicAlbums;
import cc.onelooker.kaleido.plex.resp.GetMusicArtists;
import cc.onelooker.kaleido.plex.resp.GetMusicTracks;
import cc.onelooker.kaleido.utils.ConfigUtils;
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

    private final static String API_ALL_LIBRARY = "/library/sections/?X-Plex-Token={plexToken}";
    private final static String API_ALL_MUSIC_ARTISTS = "/library/sections/{libraryId}/all?X-Plex-Token={plexToken}";
    private final static String API_ALL_MUSIC_ALBUMS = "/library/sections/{libraryId}/all?artist.id={artistId}&type=9&X-Plex-Token={plexToken}";
    private final static String API_ALL_MUSIC_TRACKS = "/library/metadata/{albumId}/children?X-Plex-Token={plexToken}";

    @Autowired
    private RestTemplate restTemplate;

    @PostConstruct
    public void init() {
        this.plexUrl = ConfigUtils.getSysConfig("plexUrl");
        this.plexToken = ConfigUtils.getSysConfig("plexToken");
    }

    public void init(String plexUrl, String plexToken) {
        this.plexUrl = plexUrl;
        this.plexToken = plexToken;
    }

    public List<GetLibrariesResp.Directory> getLibraries() {
        GetLibrariesResp resp = restTemplate.getForObject(plexUrl + API_ALL_LIBRARY, GetLibrariesResp.class, plexToken);
        GetLibrariesResp.MediaContainer mediaContainer = resp.getMediaContainer();
        return mediaContainer.getDirectory();
    }

    public List<GetMusicArtists.Metadata> getMusicArtists(String libraryId) {
        GetMusicArtists musicArtists = restTemplate.getForObject(plexUrl + API_ALL_MUSIC_ARTISTS, GetMusicArtists.class, libraryId, plexToken);
        GetMusicArtists.MediaContainer mediaContainer = musicArtists.getMediaContainer();
        return mediaContainer.getMetadata();
    }

    public List<GetMusicAlbums.Metadata> getMusicAlbums(String libraryId, String artistId) {
        GetMusicAlbums musicAlbums = restTemplate.getForObject(plexUrl + API_ALL_MUSIC_ALBUMS, GetMusicAlbums.class, libraryId, artistId, plexToken);
        GetMusicAlbums.MediaContainer mediaContainer = musicAlbums.getMediaContainer();
        return mediaContainer.getMetadata();
    }

    public List<GetMusicTracks.Metadata> getMusicTracks(String albumId) {
        GetMusicTracks musicTracks = restTemplate.getForObject(plexUrl + API_ALL_MUSIC_TRACKS, GetMusicTracks.class, albumId, plexToken);
        GetMusicTracks.MediaContainer mediaContainer = musicTracks.getMediaContainer();
        return mediaContainer.getMetadataList();
    }

}
