package cc.onelooker.kaleido.convert.business;

import cc.onelooker.kaleido.dto.business.MovieGenreLinkDTO;
import cc.onelooker.kaleido.entity.business.MovieGenreLinkDO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
/**
* 电影类型关联表Convert
*
* @author cyetstar
* @date 2023-04-18 23:04:56
*/
@Mapper
public interface MovieGenreLinkConvert {

    MovieGenreLinkConvert INSTANCE = Mappers.getMapper(MovieGenreLinkConvert.class);

    MovieGenreLinkDTO convert(MovieGenreLinkDO entity);

    @InheritInverseConfiguration(name="convert")
    MovieGenreLinkDO convertToDO(MovieGenreLinkDTO dto);

}