package cc.onelooker.kaleido.convert.music;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.mapstruct.InheritInverseConfiguration;

import cc.onelooker.kaleido.dto.music.MusicArtistReleaseDTO;
import cc.onelooker.kaleido.entity.music.MusicArtistReleaseDO;
import cc.onelooker.kaleido.dto.music.req.MusicArtistReleasePageReq;
import cc.onelooker.kaleido.dto.music.req.MusicArtistReleaseCreateReq;
import cc.onelooker.kaleido.dto.music.req.MusicArtistReleaseUpdateReq;
import cc.onelooker.kaleido.dto.music.resp.MusicArtistReleasePageResp;
import cc.onelooker.kaleido.dto.music.resp.MusicArtistReleaseViewResp;
import cc.onelooker.kaleido.dto.music.resp.MusicArtistReleaseCreateResp;
import cc.onelooker.kaleido.exp.music.MusicArtistReleaseExp;

import java.lang.Long;
/**
* 艺术家发行品关联表Convert
*
* @author cyetstar
* @date 2023-09-29 22:32:53
*/
@Mapper
public interface MusicArtistReleaseConvert {

    MusicArtistReleaseConvert INSTANCE = Mappers.getMapper(MusicArtistReleaseConvert.class);

    MusicArtistReleaseDTO convert(MusicArtistReleaseDO entity);

    @InheritInverseConfiguration(name="convert")
    MusicArtistReleaseDO convertToDO(MusicArtistReleaseDTO dto);

    MusicArtistReleaseDTO convertToDTO(MusicArtistReleasePageReq req);

    MusicArtistReleaseDTO convertToDTO(MusicArtistReleaseCreateReq req);

    MusicArtistReleaseDTO convertToDTO(MusicArtistReleaseUpdateReq req);

    MusicArtistReleasePageResp convertToPageResp(MusicArtistReleaseDTO dto);

    MusicArtistReleaseViewResp convertToViewResp(MusicArtistReleaseDTO dto);

    MusicArtistReleaseCreateResp convertToCreateResp(MusicArtistReleaseDTO dto);

    MusicArtistReleaseExp convertToExp(MusicArtistReleaseDTO dto);

}