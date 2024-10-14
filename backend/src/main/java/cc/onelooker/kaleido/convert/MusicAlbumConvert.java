package cc.onelooker.kaleido.convert;

import cc.onelooker.kaleido.dto.MusicAlbumDTO;
import cc.onelooker.kaleido.dto.req.MusicAlbumCreateReq;
import cc.onelooker.kaleido.dto.req.MusicAlbumPageReq;
import cc.onelooker.kaleido.dto.req.MusicAlbumUpdateReq;
import cc.onelooker.kaleido.dto.resp.*;
import cc.onelooker.kaleido.entity.MusicAlbumDO;
import cc.onelooker.kaleido.third.tmm.Album;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.Map;

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

    @InheritInverseConfiguration(name = "convert")
    MusicAlbumDO convertToDO(MusicAlbumDTO dto);

    MusicAlbumDTO convertToDTO(MusicAlbumPageReq req);

    MusicAlbumDTO convertToDTO(MusicAlbumCreateReq req);

    MusicAlbumDTO convertToDTO(MusicAlbumUpdateReq req);

    MusicAlbumPageResp convertToPageResp(MusicAlbumDTO dto);

    MusicAlbumViewResp convertToViewResp(MusicAlbumDTO dto);

    MusicAlbumCreateResp convertToCreateResp(MusicAlbumDTO dto);

    MusicAlbumSearchInfoResp convertToSearchInfoResp(Album album);

    MusicAlbumListByArtistIdResp convertToListByArtistIdResp(MusicAlbumDTO musicAlbumDTO);

    @Mappings({
            @Mapping(target = "styleList", ignore = true),
            @Mapping(target = "genreList", ignore = true),
            @Mapping(target = "moodList", ignore = true),
            @Mapping(target = "artistList", ignore = true),
            @Mapping(target = "trackList", ignore = true),
    })
    MusicAlbumDTO convertToDTO(Map<String, String> params);

}