package cc.onelooker.kaleido.service;

import cc.onelooker.kaleido.dto.ArtistDTO;
import com.zjjcnt.common.core.service.IBaseService;

import java.util.List;
import java.util.Map;

/**
 * 艺术家Service
 *
 * @author cyetstar
 * @date 2023-11-25 22:16:58
 */
public interface ArtistService extends IBaseService<ArtistDTO> {

    List<ArtistDTO> listByAlbumId(String albumId);

    List<ArtistDTO> listByTrackId(String trackId);

    Map<String, List<ArtistDTO>> mapByTrackIdList(List<String> trackIdList);

    Boolean updateNeteaseId(String id, String neteaseId);

    ArtistDTO findByNeteaseId(String neteaseId);

    ArtistDTO findByMusicbrainzId(String musicbrainzId);

    ArtistDTO findByTitle(String title);

    void updateAlbumArtists(List<ArtistDTO> artistList, String albumId);

    void updateTrackArtists(List<ArtistDTO> artistList, String trackId);
}