package cc.onelooker.kaleido.convert.comic;

import cc.onelooker.kaleido.dto.comic.ComicAuthorDTO;
import cc.onelooker.kaleido.dto.comic.ComicBookDTO;
import cc.onelooker.kaleido.dto.comic.req.ComicBookCreateReq;
import cc.onelooker.kaleido.dto.comic.req.ComicBookPageReq;
import cc.onelooker.kaleido.dto.comic.req.ComicBookUpdateReq;
import cc.onelooker.kaleido.dto.comic.resp.ComicBookCreateResp;
import cc.onelooker.kaleido.dto.comic.resp.ComicBookPageResp;
import cc.onelooker.kaleido.dto.comic.resp.ComicBookViewResp;
import cc.onelooker.kaleido.entity.comic.ComicBookDO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 漫画书籍Convert
 *
 * @author cyetstar
 * @date 2024-03-12 17:47:50
 */
@Mapper
public interface ComicBookConvert {

    ComicBookConvert INSTANCE = Mappers.getMapper(ComicBookConvert.class);

    ComicBookDTO convert(ComicBookDO entity);

    @InheritInverseConfiguration(name = "convert")
    ComicBookDO convertToDO(ComicBookDTO dto);

    ComicBookDTO convertToDTO(ComicBookPageReq req);

    ComicBookDTO convertToDTO(ComicBookCreateReq req);

    ComicBookDTO convertToDTO(ComicBookUpdateReq req);

    ComicBookPageResp convertToPageResp(ComicBookDTO dto);

    ComicBookViewResp convertToViewResp(ComicBookDTO dto);

    ComicBookCreateResp convertToCreateResp(ComicBookDTO dto);

    ComicBookViewResp.Author convertToViewResp(ComicAuthorDTO comicAuthorDTO);
}