package cc.onelooker.kaleido.service.music;

import com.zjjcnt.common.core.service.IBaseService;

import java.lang.Long;
import java.util.List;

import cc.onelooker.kaleido.dto.music.MusicArtistReleaseDTO;

/**
 * 艺术家发行品关联表Service
 *
 * @author cyetstar
 * @date 2023-09-29 22:32:53
 */
public interface MusicArtistReleaseService extends IBaseService<MusicArtistReleaseDTO> {

    List<MusicArtistReleaseDTO> listByReleaseId(Long releaseId);

    List<MusicArtistReleaseDTO> listByArtistId(Long artistId);
}