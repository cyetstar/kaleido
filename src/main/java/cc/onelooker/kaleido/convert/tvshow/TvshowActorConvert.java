package cc.onelooker.kaleido.convert.tvshow;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.mapstruct.InheritInverseConfiguration;

import cc.onelooker.kaleido.dto.tvshow.TvshowActorDTO;
import cc.onelooker.kaleido.entity.tvshow.TvshowActorDO;
import cc.onelooker.kaleido.dto.tvshow.req.TvshowActorPageReq;
import cc.onelooker.kaleido.dto.tvshow.req.TvshowActorCreateReq;
import cc.onelooker.kaleido.dto.tvshow.req.TvshowActorUpdateReq;
import cc.onelooker.kaleido.dto.tvshow.resp.TvshowActorPageResp;
import cc.onelooker.kaleido.dto.tvshow.resp.TvshowActorViewResp;
import cc.onelooker.kaleido.dto.tvshow.resp.TvshowActorCreateResp;
import cc.onelooker.kaleido.exp.tvshow.TvshowActorExp;

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

    @InheritInverseConfiguration(name="convert")
    TvshowActorDO convertToDO(TvshowActorDTO dto);

    TvshowActorDTO convertToDTO(TvshowActorPageReq req);

    TvshowActorDTO convertToDTO(TvshowActorCreateReq req);

    TvshowActorDTO convertToDTO(TvshowActorUpdateReq req);

    TvshowActorPageResp convertToPageResp(TvshowActorDTO dto);

    TvshowActorViewResp convertToViewResp(TvshowActorDTO dto);

    TvshowActorCreateResp convertToCreateResp(TvshowActorDTO dto);

    TvshowActorExp convertToExp(TvshowActorDTO dto);

}