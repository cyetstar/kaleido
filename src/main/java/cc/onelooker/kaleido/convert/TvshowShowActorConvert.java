package cc.onelooker.kaleido.convert;

import cc.onelooker.kaleido.dto.TvshowShowActorDTO;
import cc.onelooker.kaleido.dto.req.TvshowShowActorCreateReq;
import cc.onelooker.kaleido.dto.req.TvshowShowActorPageReq;
import cc.onelooker.kaleido.dto.req.TvshowShowActorUpdateReq;
import cc.onelooker.kaleido.dto.resp.TvshowShowActorCreateResp;
import cc.onelooker.kaleido.dto.resp.TvshowShowActorPageResp;
import cc.onelooker.kaleido.dto.resp.TvshowShowActorViewResp;
import cc.onelooker.kaleido.entity.TvshowShowActorDO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 剧集演职员关联表Convert
 *
 * @author cyetstar
 * @date 2023-11-26 23:27:03
 */
@Mapper
public interface TvshowShowActorConvert {

    TvshowShowActorConvert INSTANCE = Mappers.getMapper(TvshowShowActorConvert.class);

    TvshowShowActorDTO convert(TvshowShowActorDO entity);

    @InheritInverseConfiguration(name = "convert")
    TvshowShowActorDO convertToDO(TvshowShowActorDTO dto);

    TvshowShowActorDTO convertToDTO(TvshowShowActorPageReq req);

    TvshowShowActorDTO convertToDTO(TvshowShowActorCreateReq req);

    TvshowShowActorDTO convertToDTO(TvshowShowActorUpdateReq req);

    TvshowShowActorPageResp convertToPageResp(TvshowShowActorDTO dto);

    TvshowShowActorViewResp convertToViewResp(TvshowShowActorDTO dto);

    TvshowShowActorCreateResp convertToCreateResp(TvshowShowActorDTO dto);

}