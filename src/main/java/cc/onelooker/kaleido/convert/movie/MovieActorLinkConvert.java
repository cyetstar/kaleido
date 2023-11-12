package cc.onelooker.kaleido.convert.movie;

import cc.onelooker.kaleido.dto.movie.MovieActorLinkDTO;
import cc.onelooker.kaleido.entity.movie.MovieActorLinkDO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
/**
* 电影演职员关联表Convert
*
* @author cyetstar
* @date 2023-04-18 23:04:56
*/
@Mapper
public interface MovieActorLinkConvert {

    MovieActorLinkConvert INSTANCE = Mappers.getMapper(MovieActorLinkConvert.class);

    MovieActorLinkDTO convert(MovieActorLinkDO entity);

    @InheritInverseConfiguration(name="convert")
    MovieActorLinkDO convertToDO(MovieActorLinkDTO dto);

}