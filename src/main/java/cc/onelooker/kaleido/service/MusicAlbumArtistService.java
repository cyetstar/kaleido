package cc.onelooker.kaleido.service;

import cc.onelooker.kaleido.dto.MusicAlbumArtistDTO;
import com.zjjcnt.common.core.service.IBaseService;

import java.util.List;

/**
 * 艺术家专辑关联表Service
 *
 * @author cyetstar
 * @date 2023-11-25 22:16:58
 */
public interface MusicAlbumArtistService extends IBaseService<MusicAlbumArtistDTO> {

    List<MusicAlbumArtistDTO> listByArtistId(String artistId);

    List<MusicAlbumArtistDTO> listByAlbumId(String albumId);

    MusicAlbumArtistDTO findByArtistIdAndAlbumId(String artistId, String albumId);

    MusicAlbumArtistDTO insertByArtistIdAndAlbumId(String artistId, String albumId);

    boolean deleteByAlbumId(String albumId);
}