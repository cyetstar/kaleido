package cc.onelooker.kaleido.service;

import cc.onelooker.kaleido.dto.MusicTrackArtistDTO;
import com.zjjcnt.common.core.service.IBaseService;

import java.util.List;

/**
 * 艺术家歌曲关联表Service
 *
 * @author cyetstar
 * @date 2023-11-25 22:16:58
 */
public interface MusicTrackArtistService extends IBaseService<MusicTrackArtistDTO> {

    List<MusicTrackArtistDTO> listByArtistId(String artistId);

    List<MusicTrackArtistDTO> listByTrackId(String trackId);

    boolean deleteByTrackId(String trackId);

    List<MusicTrackArtistDTO> listByTrackIdList(List<String> trackIdList);
}