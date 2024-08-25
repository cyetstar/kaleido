package cc.onelooker.kaleido.service;

import cc.onelooker.kaleido.dto.MusicArtistAlbumDTO;
import com.zjjcnt.common.core.service.IBaseService;

import java.util.List;

/**
 * 艺术家专辑关联表Service
 *
 * @author cyetstar
 * @date 2023-11-25 22:16:58
 */
public interface MusicArtistAlbumService extends IBaseService<MusicArtistAlbumDTO> {

    List<MusicArtistAlbumDTO> listByArtistId(String artistId);

    List<MusicArtistAlbumDTO> listByAlbumId(String albumId);

    MusicArtistAlbumDTO findByArtistIdAndAlbumId(String artistId, String albumId);

    MusicArtistAlbumDTO insertByArtistIdAndAlbumId(String artistId, String albumId);

    boolean deleteByAlbumId(String albumId);
}