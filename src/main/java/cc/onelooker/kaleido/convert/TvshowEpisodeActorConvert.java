package cc.onelooker.kaleido.convert;

import cc.onelooker.kaleido.dto.TvshowEpisodeActorDTO;
import cc.onelooker.kaleido.dto.req.TvshowEpisodeActorCreateReq;
import cc.onelooker.kaleido.dto.req.TvshowEpisodeActorPageReq;
import cc.onelooker.kaleido.dto.req.TvshowEpisodeActorUpdateReq;
import cc.onelooker.kaleido.dto.resp.TvshowEpisodeActorCreateResp;
import cc.onelooker.kaleido.dto.resp.TvshowEpisodeActorPageResp;
import cc.onelooker.kaleido.dto.resp.TvshowEpisodeActorViewResp;
import cc.onelooker.kaleido.entity.TvshowEpisodeActorDO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

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

    @InheritInverseConfiguration(name = "convert")
    TvshowEpisodeActorDO convertToDO(TvshowEpisodeActorDTO dto);

    TvshowEpisodeActorDTO convertToDTO(TvshowEpisodeActorPageReq req);

    TvshowEpisodeActorDTO convertToDTO(TvshowEpisodeActorCreateReq req);

    TvshowEpisodeActorDTO convertToDTO(TvshowEpisodeActorUpdateReq req);

    TvshowEpisodeActorPageResp convertToPageResp(TvshowEpisodeActorDTO dto);

    TvshowEpisodeActorViewResp convertToViewResp(TvshowEpisodeActorDTO dto);

    TvshowEpisodeActorCreateResp convertToCreateResp(TvshowEpisodeActorDTO dto);

}