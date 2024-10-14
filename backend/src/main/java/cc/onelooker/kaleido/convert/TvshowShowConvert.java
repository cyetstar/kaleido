package cc.onelooker.kaleido.convert;

import cc.onelooker.kaleido.dto.TvshowShowDTO;
import cc.onelooker.kaleido.dto.req.TvshowShowCreateReq;
import cc.onelooker.kaleido.dto.req.TvshowShowPageReq;
import cc.onelooker.kaleido.dto.req.TvshowShowUpdateReq;
import cc.onelooker.kaleido.dto.resp.TvshowShowCreateResp;
import cc.onelooker.kaleido.dto.resp.TvshowShowPageResp;
import cc.onelooker.kaleido.dto.resp.TvshowShowSearchInfoResp;
import cc.onelooker.kaleido.dto.resp.TvshowShowViewResp;
import cc.onelooker.kaleido.entity.TvshowShowDO;
import cc.onelooker.kaleido.third.tmm.Tvshow;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * 剧集Convert
 *
 * @author cyetstar
 * @date 2023-11-26 23:27:03
 */
@Mapper
public interface TvshowShowConvert {

    TvshowShowConvert INSTANCE = Mappers.getMapper(TvshowShowConvert.class);

    TvshowShowDTO convert(TvshowShowDO entity);

    @InheritInverseConfiguration(name = "convert")
    TvshowShowDO convertToDO(TvshowShowDTO dto);

    @Mappings({@Mapping(source = "genre", target = "genreId")})
    TvshowShowDTO convertToDTO(TvshowShowPageReq req);

    TvshowShowDTO convertToDTO(TvshowShowCreateReq req);

    TvshowShowDTO convertToDTO(TvshowShowUpdateReq req);

    TvshowShowPageResp convertToPageResp(TvshowShowDTO dto);

    TvshowShowViewResp convertToViewResp(TvshowShowDTO dto);

    TvshowShowCreateResp convertToCreateResp(TvshowShowDTO dto);

    TvshowShowSearchInfoResp convertToSearchInfoResp(Tvshow tvshow);
}