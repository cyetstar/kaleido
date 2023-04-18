package cc.onelooker.kaleido.convert.business;

import cc.onelooker.kaleido.dto.business.MovieCountryLinkDTO;
import cc.onelooker.kaleido.entity.business.MovieCountryLinkDO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
/**
* 电影国家地区关联表Convert
*
* @author cyetstar
* @date 2023-04-18 23:04:56
*/
@Mapper
public interface MovieCountryLinkConvert {

    MovieCountryLinkConvert INSTANCE = Mappers.getMapper(MovieCountryLinkConvert.class);

    MovieCountryLinkDTO convert(MovieCountryLinkDO entity);

    @InheritInverseConfiguration(name="convert")
    MovieCountryLinkDO convertToDO(MovieCountryLinkDTO dto);

}