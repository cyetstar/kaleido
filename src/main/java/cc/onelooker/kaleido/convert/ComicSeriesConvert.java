package cc.onelooker.kaleido.convert;

import cc.onelooker.kaleido.dto.AuthorDTO;
import cc.onelooker.kaleido.dto.ComicSeriesDTO;
import cc.onelooker.kaleido.dto.req.ComicSeriesCreateReq;
import cc.onelooker.kaleido.dto.req.ComicSeriesPageReq;
import cc.onelooker.kaleido.dto.req.ComicSeriesUpdateReq;
import cc.onelooker.kaleido.dto.resp.ComicSeriesCreateResp;
import cc.onelooker.kaleido.dto.resp.ComicSeriesPageResp;
import cc.onelooker.kaleido.dto.resp.ComicSeriesSearchInfoResp;
import cc.onelooker.kaleido.dto.resp.ComicSeriesViewResp;
import cc.onelooker.kaleido.entity.ComicSeriesDO;
import cc.onelooker.kaleido.third.tmm.Comic;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.Map;

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

    @Mappings({
            @Mapping(target = "writerList", ignore = true),
            @Mapping(target = "pencillerList", ignore = true),
            @Mapping(target = "tagList", ignore = true),
            @Mapping(target = "alternateTitleList", ignore = true),
    })
    ComicSeriesDTO convertToDTO(ComicSeriesUpdateReq req);

    ComicSeriesPageResp convertToPageResp(ComicSeriesDTO dto);

    ComicSeriesViewResp convertToViewResp(ComicSeriesDTO dto);

    ComicSeriesCreateResp convertToCreateResp(ComicSeriesDTO dto);

    ComicSeriesViewResp.Author convertToViewResp(AuthorDTO authorDTO);

    ComicSeriesSearchInfoResp convertToSearchInfoResp(Comic comic);

    @Mappings({
            @Mapping(target = "tagList", ignore = true),
            @Mapping(target = "alternateTitleList", ignore = true),
            @Mapping(target = "writerList", ignore = true),
            @Mapping(target = "pencillerList", ignore = true),
            @Mapping(target = "bookList", ignore = true)
    })
    ComicSeriesDTO convertToDTO(Map<String, String> params);
}