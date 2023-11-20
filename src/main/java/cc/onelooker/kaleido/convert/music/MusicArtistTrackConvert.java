package cc.onelooker.kaleido.convert.music;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.mapstruct.InheritInverseConfiguration;

import cc.onelooker.kaleido.dto.music.MusicArtistTrackDTO;
import cc.onelooker.kaleido.entity.music.MusicArtistTrackDO;
import cc.onelooker.kaleido.dto.music.req.MusicArtistTrackPageReq;
import cc.onelooker.kaleido.dto.music.req.MusicArtistTrackCreateReq;
import cc.onelooker.kaleido.dto.music.req.MusicArtistTrackUpdateReq;
import cc.onelooker.kaleido.dto.music.resp.MusicArtistTrackPageResp;
import cc.onelooker.kaleido.dto.music.resp.MusicArtistTrackViewResp;
import cc.onelooker.kaleido.dto.music.resp.MusicArtistTrackCreateResp;
import cc.onelooker.kaleido.exp.music.MusicArtistTrackExp;

/**
* 艺术家曲目关联表Convert
*
* @author cyetstar
* @date 2023-11-20 22:35:26
*/
@Mapper
public interface MusicArtistTrackConvert {

    MusicArtistTrackConvert INSTANCE = Mappers.getMapper(MusicArtistTrackConvert.class);

    MusicArtistTrackDTO convert(MusicArtistTrackDO entity);

    @InheritInverseConfiguration(name="convert")
    MusicArtistTrackDO convertToDO(MusicArtistTrackDTO dto);

    MusicArtistTrackDTO convertToDTO(MusicArtistTrackPageReq req);

    MusicArtistTrackDTO convertToDTO(MusicArtistTrackCreateReq req);

    MusicArtistTrackDTO convertToDTO(MusicArtistTrackUpdateReq req);

    MusicArtistTrackPageResp convertToPageResp(MusicArtistTrackDTO dto);

    MusicArtistTrackViewResp convertToViewResp(MusicArtistTrackDTO dto);

    MusicArtistTrackCreateResp convertToCreateResp(MusicArtistTrackDTO dto);

    MusicArtistTrackExp convertToExp(MusicArtistTrackDTO dto);

}