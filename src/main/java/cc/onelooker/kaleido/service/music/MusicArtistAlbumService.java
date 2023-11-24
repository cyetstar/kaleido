package cc.onelooker.kaleido.service.music;

import cc.onelooker.kaleido.dto.music.MusicArtistAlbumDTO;
import com.zjjcnt.common.core.service.IBaseService;

import java.util.List;

/**
 * 艺术家专辑关联表Service
 *
 * @author cyetstar
 * @date 2023-11-20 22:35:26
 */
public interface MusicArtistAlbumService extends IBaseService<MusicArtistAlbumDTO> {

    List<MusicArtistAlbumDTO> listByArtistId(Long artistId);

    List<MusicArtistAlbumDTO> listByAlbumId(Long albumId);

    MusicArtistAlbumDTO findByArtistIdAndAlbumId(Long artistId, Long albumId);

    MusicArtistAlbumDTO insertByArtistIdAndAlbumId(Long artistId, Long albumId);
}