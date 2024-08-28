package cc.onelooker.kaleido.convert;

import cc.onelooker.kaleido.dto.TvshowSeasonDTO;
import cc.onelooker.kaleido.dto.req.TvshowSeasonCreateReq;
import cc.onelooker.kaleido.dto.req.TvshowSeasonPageReq;
import cc.onelooker.kaleido.dto.req.TvshowSeasonUpdateReq;
import cc.onelooker.kaleido.dto.resp.TvshowSeasonCreateResp;
import cc.onelooker.kaleido.dto.resp.TvshowSeasonPageResp;
import cc.onelooker.kaleido.dto.resp.TvshowSeasonViewResp;
import cc.onelooker.kaleido.entity.TvshowSeasonDO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 单季Convert
 *
 * @author cyetstar
 * @date 2023-11-26 23:27:03
 */
@Mapper
public interface TvshowSeasonConvert {

    TvshowSeasonConvert INSTANCE = Mappers.getMapper(TvshowSeasonConvert.class);

    TvshowSeasonDTO convert(TvshowSeasonDO entity);

    @InheritInverseConfiguration(name = "convert")
    TvshowSeasonDO convertToDO(TvshowSeasonDTO dto);

    TvshowSeasonDTO convertToDTO(TvshowSeasonPageReq req);

    TvshowSeasonDTO convertToDTO(TvshowSeasonCreateReq req);

    TvshowSeasonDTO convertToDTO(TvshowSeasonUpdateReq req);

    TvshowSeasonPageResp convertToPageResp(TvshowSeasonDTO dto);

    TvshowSeasonViewResp convertToViewResp(TvshowSeasonDTO dto);

    TvshowSeasonCreateResp convertToCreateResp(TvshowSeasonDTO dto);

}