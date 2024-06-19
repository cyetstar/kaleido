package cc.onelooker.kaleido.convert.comic;

import cc.onelooker.kaleido.dto.comic.ComicSeriesAuthorDTO;
import cc.onelooker.kaleido.dto.comic.req.ComicSeriesAuthorCreateReq;
import cc.onelooker.kaleido.dto.comic.req.ComicSeriesAuthorPageReq;
import cc.onelooker.kaleido.dto.comic.req.ComicSeriesAuthorUpdateReq;
import cc.onelooker.kaleido.dto.comic.resp.ComicSeriesAuthorCreateResp;
import cc.onelooker.kaleido.dto.comic.resp.ComicSeriesAuthorPageResp;
import cc.onelooker.kaleido.dto.comic.resp.ComicSeriesAuthorViewResp;
import cc.onelooker.kaleido.entity.comic.ComicSeriesAuthorDO;
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