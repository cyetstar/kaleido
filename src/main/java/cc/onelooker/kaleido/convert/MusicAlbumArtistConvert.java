package cc.onelooker.kaleido.convert;

import cc.onelooker.kaleido.dto.MusicAlbumArtistDTO;
import cc.onelooker.kaleido.entity.MusicAlbumArtistDO;
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
public interface MusicAlbumArtistConvert {

    MusicAlbumArtistConvert INSTANCE = Mappers.getMapper(MusicAlbumArtistConvert.class);

    MusicAlbumArtistDTO convert(MusicAlbumArtistDO entity);

    @InheritInverseConfiguration(name = "convert")
    MusicAlbumArtistDO convertToDO(MusicAlbumArtistDTO dto);

}