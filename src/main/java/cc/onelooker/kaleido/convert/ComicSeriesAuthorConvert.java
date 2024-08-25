package cc.onelooker.kaleido.convert;

import cc.onelooker.kaleido.dto.ComicSeriesAuthorDTO;
import cc.onelooker.kaleido.dto.req.ComicSeriesAuthorCreateReq;
import cc.onelooker.kaleido.dto.req.ComicSeriesAuthorPageReq;
import cc.onelooker.kaleido.dto.req.ComicSeriesAuthorUpdateReq;
import cc.onelooker.kaleido.dto.resp.ComicSeriesAuthorCreateResp;
import cc.onelooker.kaleido.dto.resp.ComicSeriesAuthorPageResp;
import cc.onelooker.kaleido.dto.resp.ComicSeriesAuthorViewResp;
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

    @InheritInverseConfiguration(name="convert")
    ComicSeriesAuthorDO convertToDO(ComicSeriesAuthorDTO dto);

    ComicSeriesAuthorDTO convertToDTO(ComicSeriesAuthorPageReq req);

    ComicSeriesAuthorDTO convertToDTO(ComicSeriesAuthorCreateReq req);

    ComicSeriesAuthorDTO convertToDTO(ComicSeriesAuthorUpdateReq req);

    ComicSeriesAuthorPageResp convertToPageResp(ComicSeriesAuthorDTO dto);

    ComicSeriesAuthorViewResp convertToViewResp(ComicSeriesAuthorDTO dto);

    ComicSeriesAuthorCreateResp convertToCreateResp(ComicSeriesAuthorDTO dto);

}