package cc.onelooker.kaleido.convert;

import cc.onelooker.kaleido.dto.MovieBasicActorDTO;
import cc.onelooker.kaleido.entity.MovieBasicActorDO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 电影演职员关联表Convert
 *
 * @author cyetstar
 * @date 2023-11-26 01:19:02
 */
@Mapper
public interface MovieBasicActorConvert {

    MovieBasicActorConvert INSTANCE = Mappers.getMapper(MovieBasicActorConvert.class);

    MovieBasicActorDTO convert(MovieBasicActorDO entity);

    @InheritInverseConfiguration(name = "convert")
    MovieBasicActorDO convertToDO(MovieBasicActorDTO dto);

}