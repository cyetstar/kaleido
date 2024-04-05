package cc.onelooker.kaleido.convert.comic;

import cc.onelooker.kaleido.dto.AttributeDTO;
import cc.onelooker.kaleido.dto.comic.ComicAuthorDTO;
import cc.onelooker.kaleido.dto.comic.ComicSeriesDTO;
import cc.onelooker.kaleido.dto.comic.req.ComicSeriesCreateReq;
import cc.onelooker.kaleido.dto.comic.req.ComicSeriesPageReq;
import cc.onelooker.kaleido.dto.comic.req.ComicSeriesUpdateReq;
import cc.onelooker.kaleido.dto.comic.resp.ComicSeriesCreateResp;
import cc.onelooker.kaleido.dto.comic.resp.ComicSeriesPageResp;
import cc.onelooker.kaleido.dto.comic.resp.ComicSeriesSearchInfoResp;
import cc.onelooker.kaleido.dto.comic.resp.ComicSeriesViewResp;
import cc.onelooker.kaleido.entity.comic.ComicSeriesDO;
import cc.onelooker.kaleido.third.tmm.Comic;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 漫画系列Convert
 *
 * @author cyetstar
 * @date 2024-03-12 17:47:50
 */
@Mapper
public interface ComicSeriesConvert {

    ComicSeriesConvert INSTANCE = Mappers.getMapper(ComicSeriesConvert.class);

    ComicSeriesDTO convert(ComicSeriesDO entity);

    @InheritInverseConfiguration(name = "convert")
    ComicSeriesDO convertToDO(ComicSeriesDTO dto);

    ComicSeriesDTO convertToDTO(ComicSeriesPageReq req);

    ComicSeriesDTO convertToDTO(ComicSeriesCreateReq req);

    ComicSeriesDTO convertToDTO(ComicSeriesUpdateReq req);

    ComicSeriesPageResp convertToPageResp(ComicSeriesDTO dto);

    ComicSeriesViewResp convertToViewResp(ComicSeriesDTO dto);

    ComicSeriesCreateResp convertToCreateResp(ComicSeriesDTO dto);

    ComicSeriesViewResp.Attribute convertToViewResp(AttributeDTO attributeDTO);

    ComicSeriesViewResp.Author convertToViewResp(ComicAuthorDTO comicAuthorDTO);

    ComicSeriesSearchInfoResp convertToSearchInfoResp(Comic comic);
}