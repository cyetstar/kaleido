package cc.onelooker.kaleido.convert.tvshow;

import cc.onelooker.kaleido.dto.tvshow.TvshowSeasonDTO;
import cc.onelooker.kaleido.dto.tvshow.req.TvshowSeasonCreateReq;
import cc.onelooker.kaleido.dto.tvshow.req.TvshowSeasonPageReq;
import cc.onelooker.kaleido.dto.tvshow.req.TvshowSeasonUpdateReq;
import cc.onelooker.kaleido.dto.tvshow.resp.TvshowSeasonCreateResp;
import cc.onelooker.kaleido.dto.tvshow.resp.TvshowSeasonPageResp;
import cc.onelooker.kaleido.dto.tvshow.resp.TvshowSeasonViewResp;
import cc.onelooker.kaleido.entity.tvshow.TvshowSeasonDO;
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