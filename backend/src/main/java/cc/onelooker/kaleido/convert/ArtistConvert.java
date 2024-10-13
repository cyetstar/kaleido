package cc.onelooker.kaleido.convert;

import cc.onelooker.kaleido.dto.ArtistDTO;
import cc.onelooker.kaleido.dto.req.ArtistCreateReq;
import cc.onelooker.kaleido.dto.req.ArtistPageReq;
import cc.onelooker.kaleido.dto.req.ArtistUpdateReq;
import cc.onelooker.kaleido.dto.resp.ArtistCreateResp;
import cc.onelooker.kaleido.dto.resp.ArtistPageResp;
import cc.onelooker.kaleido.dto.resp.ArtistSearchNeteaseResp;
import cc.onelooker.kaleido.dto.resp.ArtistViewResp;
import cc.onelooker.kaleido.entity.ArtistDO;
import cc.onelooker.kaleido.third.tmm.Artist;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * 艺术家Convert
 *
 * @author cyetstar
 * @date 2023-11-25 22:16:58
 */
@Mapper
public interface ArtistConvert {

    ArtistConvert INSTANCE = Mappers.getMapper(ArtistConvert.class);

    ArtistDTO convert(ArtistDO entity);

    @InheritInverseConfiguration(name = "convert")
    ArtistDO convertToDO(ArtistDTO dto);

    ArtistDTO convertToDTO(ArtistPageReq req);

    ArtistDTO convertToDTO(ArtistCreateReq req);

    ArtistDTO convertToDTO(ArtistUpdateReq req);

    ArtistPageResp convertToPageResp(ArtistDTO dto);

    ArtistViewResp convertToViewResp(ArtistDTO dto);

    ArtistCreateResp convertToCreateResp(ArtistDTO dto);

    ArtistSearchNeteaseResp convertToSearchNeteaseResp(Artist artist);
}