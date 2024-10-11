package cc.onelooker.kaleido.convert;

import cc.onelooker.kaleido.dto.TvshowSeasonActorDTO;
import cc.onelooker.kaleido.entity.TvshowSeasonActorDO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 剧集演职员关联表Convert
 *
 * @author cyetstar
 * @date 2023-11-26 23:27:03
 */
@Mapper
public interface TvshowSeasonActorConvert {

    TvshowSeasonActorConvert INSTANCE = Mappers.getMapper(TvshowSeasonActorConvert.class);

    TvshowSeasonActorDTO convert(TvshowSeasonActorDO entity);

    @InheritInverseConfiguration(name = "convert")
    TvshowSeasonActorDO convertToDO(TvshowSeasonActorDTO dto);

}