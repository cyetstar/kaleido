package cc.onelooker.kaleido.convert.comic;

import cc.onelooker.kaleido.dto.comic.ComicAuthorDTO;
import cc.onelooker.kaleido.dto.comic.req.ComicAuthorCreateReq;
import cc.onelooker.kaleido.dto.comic.req.ComicAuthorPageReq;
import cc.onelooker.kaleido.dto.comic.req.ComicAuthorUpdateReq;
import cc.onelooker.kaleido.dto.comic.resp.ComicAuthorCreateResp;
import cc.onelooker.kaleido.dto.comic.resp.ComicAuthorPageResp;
import cc.onelooker.kaleido.dto.comic.resp.ComicAuthorViewResp;
import cc.onelooker.kaleido.entity.comic.ComicAuthorDO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
* 漫画作者Convert
*
* @author cyetstar
* @date 2024-03-12 17:47:50
*/
@Mapper
public interface ComicAuthorConvert {

    ComicAuthorConvert INSTANCE = Mappers.getMapper(ComicAuthorConvert.class);

    ComicAuthorDTO convert(ComicAuthorDO entity);

    @InheritInverseConfiguration(name="convert")
    ComicAuthorDO convertToDO(ComicAuthorDTO dto);

    ComicAuthorDTO convertToDTO(ComicAuthorPageReq req);

    ComicAuthorDTO convertToDTO(ComicAuthorCreateReq req);

    ComicAuthorDTO convertToDTO(ComicAuthorUpdateReq req);

    ComicAuthorPageResp convertToPageResp(ComicAuthorDTO dto);

    ComicAuthorViewResp convertToViewResp(ComicAuthorDTO dto);

    ComicAuthorCreateResp convertToCreateResp(ComicAuthorDTO dto);

}