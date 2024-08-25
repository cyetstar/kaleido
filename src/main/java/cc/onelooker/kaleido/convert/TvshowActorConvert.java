package cc.onelooker.kaleido.convert;

import cc.onelooker.kaleido.dto.TvshowActorDTO;
import cc.onelooker.kaleido.dto.req.TvshowActorCreateReq;
import cc.onelooker.kaleido.dto.req.TvshowActorPageReq;
import cc.onelooker.kaleido.dto.req.TvshowActorUpdateReq;
import cc.onelooker.kaleido.dto.resp.TvshowActorCreateResp;
import cc.onelooker.kaleido.dto.resp.TvshowActorPageResp;
import cc.onelooker.kaleido.dto.resp.TvshowActorViewResp;
import cc.onelooker.kaleido.entity.TvshowActorDO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 剧集演职员Convert
 *
 * @author cyetstar
 * @date 2023-11-26 23:27:03
 */
@Mapper
public interface TvshowActorConvert {

    TvshowActorConvert INSTANCE = Mappers.getMapper(TvshowActorConvert.class);

    TvshowActorDTO convert(TvshowActorDO entity);

    @InheritInverseConfiguration(name = "convert")
    TvshowActorDO convertToDO(TvshowActorDTO dto);

    TvshowActorDTO convertToDTO(TvshowActorPageReq req);

    TvshowActorDTO convertToDTO(TvshowActorCreateReq req);

    TvshowActorDTO convertToDTO(TvshowActorUpdateReq req);

    TvshowActorPageResp convertToPageResp(TvshowActorDTO dto);

    TvshowActorViewResp convertToViewResp(TvshowActorDTO dto);

    TvshowActorCreateResp convertToCreateResp(TvshowActorDTO dto);

}