package cc.onelooker.kaleido.convert;

import cc.onelooker.kaleido.dto.ComicAuthorDTO;
import cc.onelooker.kaleido.dto.ComicBookDTO;
import cc.onelooker.kaleido.dto.req.ComicBookCreateReq;
import cc.onelooker.kaleido.dto.req.ComicBookPageReq;
import cc.onelooker.kaleido.dto.req.ComicBookUpdateReq;
import cc.onelooker.kaleido.dto.resp.ComicBookCreateResp;
import cc.onelooker.kaleido.dto.resp.ComicBookListPageResp;
import cc.onelooker.kaleido.dto.resp.ComicBookPageResp;
import cc.onelooker.kaleido.dto.resp.ComicBookViewResp;
import cc.onelooker.kaleido.entity.ComicBookDO;
import cc.onelooker.kaleido.third.komga.Page;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.Map;

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

    ComicBookListPageResp convertToListPageResp(Page page);

    ComicBookDTO convertToDTO(Map<String, String> params);
}