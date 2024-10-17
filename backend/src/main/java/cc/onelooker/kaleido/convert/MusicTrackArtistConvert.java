package cc.onelooker.kaleido.convert;

import cc.onelooker.kaleido.dto.MusicTrackArtistDTO;
import cc.onelooker.kaleido.entity.MusicTrackArtistDO;
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
public interface MusicTrackArtistConvert {

    MusicTrackArtistConvert INSTANCE = Mappers.getMapper(MusicTrackArtistConvert.class);

    MusicTrackArtistDTO convert(MusicTrackArtistDO entity);

    @InheritInverseConfiguration(name = "convert")
    MusicTrackArtistDO convertToDO(MusicTrackArtistDTO dto);

}