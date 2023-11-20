package cc.onelooker.kaleido.service.music;

import cc.onelooker.kaleido.dto.music.MusicArtistDTO;
import cc.onelooker.kaleido.dto.music.MusicAlbumDTO;
import cc.onelooker.kaleido.plex.resp.GetMusicAlbums;
import com.zjjcnt.common.core.service.IBaseService;

import cc.onelooker.kaleido.dto.music.MusicAlbumDTO;

import java.util.List;

/**
 * 专辑Service
 *
 * @author cyetstar
 * @date 2023-11-20 22:35:26
 */
public interface MusicAlbumService extends IBaseService<MusicAlbumDTO> {

    MusicAlbumDTO findByPlexId(String plexId);

    MusicAlbumDTO createIfNotExist(GetMusicAlbums.Metadata metadata, MusicArtistDTO musicArtistDTO);

    List<MusicAlbumDTO> listByArtistId(Long artistId);

}