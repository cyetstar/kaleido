package cc.onelooker.kaleido.convert.music;

import cc.onelooker.kaleido.dto.music.MusicAlbumDTO;
import cc.onelooker.kaleido.dto.music.req.MusicAlbumCreateReq;
import cc.onelooker.kaleido.dto.music.req.MusicAlbumPageReq;
import cc.onelooker.kaleido.dto.music.req.MusicAlbumUpdateReq;
import cc.onelooker.kaleido.dto.music.resp.*;
import cc.onelooker.kaleido.entity.music.MusicAlbumDO;
import cc.onelooker.kaleido.exp.music.MusicAlbumExp;
import cc.onelooker.kaleido.netease.domain.Album;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
* 专辑Convert
*
* @author cyetstar
* @date 2023-11-25 22:16:58
*/
@Mapper
public interface MusicAlbumConvert {

    MusicAlbumConvert INSTANCE = Mappers.getMapper(MusicAlbumConvert.class);

    MusicAlbumDTO convert(MusicAlbumDO entity);

    @InheritInverseConfiguration(name="convert")
    MusicAlbumDO convertToDO(MusicAlbumDTO dto);

    MusicAlbumDTO convertToDTO(MusicAlbumPageReq req);

    MusicAlbumDTO convertToDTO(MusicAlbumCreateReq req);

    MusicAlbumDTO convertToDTO(MusicAlbumUpdateReq req);

    MusicAlbumPageResp convertToPageResp(MusicAlbumDTO dto);

    MusicAlbumViewResp convertToViewResp(MusicAlbumDTO dto);

    MusicAlbumCreateResp convertToCreateResp(MusicAlbumDTO dto);

    MusicAlbumExp convertToExp(MusicAlbumDTO dto);

    @Mappings({
            @Mapping(source = "id", target = "neteaseId"),
            @Mapping(source = "name", target = "title"),
            @Mapping(source = "artist.name", target = "artist"),
    })
    MusicAlbumSearchNeteaseResp convertToSearchAlbumResp(Album album);

    MusicAlbumListByArtistIdResp convertToListByArtistIdResp(MusicAlbumDTO musicAlbumDTO);

}