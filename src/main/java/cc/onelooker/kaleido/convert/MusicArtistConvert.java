package cc.onelooker.kaleido.convert;

import cc.onelooker.kaleido.dto.MusicArtistDTO;
import cc.onelooker.kaleido.dto.req.MusicArtistCreateReq;
import cc.onelooker.kaleido.dto.req.MusicArtistPageReq;
import cc.onelooker.kaleido.dto.req.MusicArtistUpdateReq;
import cc.onelooker.kaleido.dto.resp.MusicArtistCreateResp;
import cc.onelooker.kaleido.dto.resp.MusicArtistPageResp;
import cc.onelooker.kaleido.dto.resp.MusicArtistSearchNeteaseResp;
import cc.onelooker.kaleido.dto.resp.MusicArtistViewResp;
import cc.onelooker.kaleido.entity.MusicArtistDO;
import cc.onelooker.kaleido.third.netease.Artist;
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
public interface MusicArtistConvert {

    MusicArtistConvert INSTANCE = Mappers.getMapper(MusicArtistConvert.class);

    MusicArtistDTO convert(MusicArtistDO entity);

    @InheritInverseConfiguration(name = "convert")
    MusicArtistDO convertToDO(MusicArtistDTO dto);

    MusicArtistDTO convertToDTO(MusicArtistPageReq req);

    MusicArtistDTO convertToDTO(MusicArtistCreateReq req);

    MusicArtistDTO convertToDTO(MusicArtistUpdateReq req);

    MusicArtistPageResp convertToPageResp(MusicArtistDTO dto);

    MusicArtistViewResp convertToViewResp(MusicArtistDTO dto);

    MusicArtistCreateResp convertToCreateResp(MusicArtistDTO dto);

    @Mappings({
            @Mapping(source = "id", target = "neteaseId"),
            @Mapping(source = "name", target = "title"),
    })
    MusicArtistSearchNeteaseResp convertToSearchNeteaseResp(Artist artist);
}