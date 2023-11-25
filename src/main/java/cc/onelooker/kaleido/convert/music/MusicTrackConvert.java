package cc.onelooker.kaleido.convert.music;

import cc.onelooker.kaleido.dto.music.resp.MusicTrackListByAlbumIdResp;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.mapstruct.InheritInverseConfiguration;

import cc.onelooker.kaleido.dto.music.MusicTrackDTO;
import cc.onelooker.kaleido.entity.music.MusicTrackDO;
import cc.onelooker.kaleido.dto.music.req.MusicTrackPageReq;
import cc.onelooker.kaleido.dto.music.req.MusicTrackCreateReq;
import cc.onelooker.kaleido.dto.music.req.MusicTrackUpdateReq;
import cc.onelooker.kaleido.dto.music.resp.MusicTrackPageResp;
import cc.onelooker.kaleido.dto.music.resp.MusicTrackViewResp;
import cc.onelooker.kaleido.dto.music.resp.MusicTrackCreateResp;
import cc.onelooker.kaleido.exp.music.MusicTrackExp;

/**
* 曲目Convert
*
* @author cyetstar
* @date 2023-11-25 22:16:58
*/
@Mapper
public interface MusicTrackConvert {

    MusicTrackConvert INSTANCE = Mappers.getMapper(MusicTrackConvert.class);

    MusicTrackDTO convert(MusicTrackDO entity);

    @InheritInverseConfiguration(name="convert")
    MusicTrackDO convertToDO(MusicTrackDTO dto);

    MusicTrackDTO convertToDTO(MusicTrackPageReq req);

    MusicTrackDTO convertToDTO(MusicTrackCreateReq req);

    MusicTrackDTO convertToDTO(MusicTrackUpdateReq req);

    MusicTrackPageResp convertToPageResp(MusicTrackDTO dto);

    MusicTrackViewResp convertToViewResp(MusicTrackDTO dto);

    MusicTrackCreateResp convertToCreateResp(MusicTrackDTO dto);

    MusicTrackExp convertToExp(MusicTrackDTO dto);

    MusicTrackListByAlbumIdResp convertToListByAlbumIdResp(MusicTrackDTO dto);
}