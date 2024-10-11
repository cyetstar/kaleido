package cc.onelooker.kaleido.convert;

import cc.onelooker.kaleido.dto.ComicSeriesAuthorDTO;
import cc.onelooker.kaleido.entity.ComicSeriesAuthorDO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 漫画书籍作者关联表Convert
 *
 * @author cyetstar
 * @date 2024-03-12 17:47:50
 */
@Mapper
public interface ComicSeriesAuthorConvert {

    ComicSeriesAuthorConvert INSTANCE = Mappers.getMapper(ComicSeriesAuthorConvert.class);

    ComicSeriesAuthorDTO convert(ComicSeriesAuthorDO entity);

    @InheritInverseConfiguration(name = "convert")
    ComicSeriesAuthorDO convertToDO(ComicSeriesAuthorDTO dto);

}