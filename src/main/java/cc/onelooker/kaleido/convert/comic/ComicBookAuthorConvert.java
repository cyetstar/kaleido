package cc.onelooker.kaleido.convert.comic;

import cc.onelooker.kaleido.dto.comic.ComicBookAuthorDTO;
import cc.onelooker.kaleido.dto.comic.req.ComicBookAuthorCreateReq;
import cc.onelooker.kaleido.dto.comic.req.ComicBookAuthorPageReq;
import cc.onelooker.kaleido.dto.comic.req.ComicBookAuthorUpdateReq;
import cc.onelooker.kaleido.dto.comic.resp.ComicBookAuthorCreateResp;
import cc.onelooker.kaleido.dto.comic.resp.ComicBookAuthorPageResp;
import cc.onelooker.kaleido.dto.comic.resp.ComicBookAuthorViewResp;
import cc.onelooker.kaleido.entity.comic.ComicBookAuthorDO;
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
public interface ComicBookAuthorConvert {

    ComicBookAuthorConvert INSTANCE = Mappers.getMapper(ComicBookAuthorConvert.class);

    ComicBookAuthorDTO convert(ComicBookAuthorDO entity);

    @InheritInverseConfiguration(name="convert")
    ComicBookAuthorDO convertToDO(ComicBookAuthorDTO dto);

    ComicBookAuthorDTO convertToDTO(ComicBookAuthorPageReq req);

    ComicBookAuthorDTO convertToDTO(ComicBookAuthorCreateReq req);

    ComicBookAuthorDTO convertToDTO(ComicBookAuthorUpdateReq req);

    ComicBookAuthorPageResp convertToPageResp(ComicBookAuthorDTO dto);

    ComicBookAuthorViewResp convertToViewResp(ComicBookAuthorDTO dto);

    ComicBookAuthorCreateResp convertToCreateResp(ComicBookAuthorDTO dto);

}