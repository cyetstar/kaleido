package cc.onelooker.kaleido.convert;

import cc.onelooker.kaleido.dto.MusicArtistAlbumDTO;
import cc.onelooker.kaleido.dto.req.MusicArtistAlbumCreateReq;
import cc.onelooker.kaleido.dto.req.MusicArtistAlbumPageReq;
import cc.onelooker.kaleido.dto.req.MusicArtistAlbumUpdateReq;
import cc.onelooker.kaleido.dto.resp.MusicArtistAlbumCreateResp;
import cc.onelooker.kaleido.dto.resp.MusicArtistAlbumPageResp;
import cc.onelooker.kaleido.dto.resp.MusicArtistAlbumViewResp;
import cc.onelooker.kaleido.entity.MusicArtistAlbumDO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 艺术家专辑关联表Convert
 *
 * @author cyetstar
 * @date 2023-11-25 22:16:58
 */
@Mapper
public interface MusicArtistAlbumConvert {

    MusicArtistAlbumConvert INSTANCE = Mappers.getMapper(MusicArtistAlbumConvert.class);

    MusicArtistAlbumDTO convert(MusicArtistAlbumDO entity);

    @InheritInverseConfiguration(name = "convert")
    MusicArtistAlbumDO convertToDO(MusicArtistAlbumDTO dto);

    MusicArtistAlbumDTO convertToDTO(MusicArtistAlbumPageReq req);

    MusicArtistAlbumDTO convertToDTO(MusicArtistAlbumCreateReq req);

    MusicArtistAlbumDTO convertToDTO(MusicArtistAlbumUpdateReq req);

    MusicArtistAlbumPageResp convertToPageResp(MusicArtistAlbumDTO dto);

    MusicArtistAlbumViewResp convertToViewResp(MusicArtistAlbumDTO dto);

    MusicArtistAlbumCreateResp convertToCreateResp(MusicArtistAlbumDTO dto);

}