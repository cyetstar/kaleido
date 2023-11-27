package cc.onelooker.kaleido.convert.tvshow;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.mapstruct.InheritInverseConfiguration;

import cc.onelooker.kaleido.dto.tvshow.TvshowEpisodeActorDTO;
import cc.onelooker.kaleido.entity.tvshow.TvshowEpisodeActorDO;
import cc.onelooker.kaleido.dto.tvshow.req.TvshowEpisodeActorPageReq;
import cc.onelooker.kaleido.dto.tvshow.req.TvshowEpisodeActorCreateReq;
import cc.onelooker.kaleido.dto.tvshow.req.TvshowEpisodeActorUpdateReq;
import cc.onelooker.kaleido.dto.tvshow.resp.TvshowEpisodeActorPageResp;
import cc.onelooker.kaleido.dto.tvshow.resp.TvshowEpisodeActorViewResp;
import cc.onelooker.kaleido.dto.tvshow.resp.TvshowEpisodeActorCreateResp;
import cc.onelooker.kaleido.exp.tvshow.TvshowEpisodeActorExp;

/**
* 单集演职员关联表Convert
*
* @author cyetstar
* @date 2023-11-26 23:27:03
*/
@Mapper
public interface TvshowEpisodeActorConvert {

    TvshowEpisodeActorConvert INSTANCE = Mappers.getMapper(TvshowEpisodeActorConvert.class);

    TvshowEpisodeActorDTO convert(TvshowEpisodeActorDO entity);

    @InheritInverseConfiguration(name="convert")
    TvshowEpisodeActorDO convertToDO(TvshowEpisodeActorDTO dto);

    TvshowEpisodeActorDTO convertToDTO(TvshowEpisodeActorPageReq req);

    TvshowEpisodeActorDTO convertToDTO(TvshowEpisodeActorCreateReq req);

    TvshowEpisodeActorDTO convertToDTO(TvshowEpisodeActorUpdateReq req);

    TvshowEpisodeActorPageResp convertToPageResp(TvshowEpisodeActorDTO dto);

    TvshowEpisodeActorViewResp convertToViewResp(TvshowEpisodeActorDTO dto);

    TvshowEpisodeActorCreateResp convertToCreateResp(TvshowEpisodeActorDTO dto);

    TvshowEpisodeActorExp convertToExp(TvshowEpisodeActorDTO dto);

}