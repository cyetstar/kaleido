package cc.onelooker.kaleido.convert.business;

import cc.onelooker.kaleido.dto.business.MovieLanguageLinkDTO;
import cc.onelooker.kaleido.entity.business.MovieLanguageLinkDO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
/**
* 电影语言关联表Convert
*
* @author cyetstar
* @date 2023-04-18 23:04:56
*/
@Mapper
public interface MovieLanguageLinkConvert {

    MovieLanguageLinkConvert INSTANCE = Mappers.getMapper(MovieLanguageLinkConvert.class);

    MovieLanguageLinkDTO convert(MovieLanguageLinkDO entity);

    @InheritInverseConfiguration(name="convert")
    MovieLanguageLinkDO convertToDO(MovieLanguageLinkDTO dto);

}