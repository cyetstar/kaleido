package cc.onelooker.kaleido.convert.movie;

import cc.onelooker.kaleido.dto.movie.MovieSetLinkDTO;
import cc.onelooker.kaleido.entity.movie.MovieSetLinkDO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
/**
* 电影集合关联表Convert
*
* @author cyetstar
* @date 2023-04-18 23:04:56
*/
@Mapper
public interface MovieSetLinkConvert {

    MovieSetLinkConvert INSTANCE = Mappers.getMapper(MovieSetLinkConvert.class);

    MovieSetLinkDTO convert(MovieSetLinkDO entity);

    @InheritInverseConfiguration(name="convert")
    MovieSetLinkDO convertToDO(MovieSetLinkDTO dto);

}