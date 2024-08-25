package cc.onelooker.kaleido.convert;

import cc.onelooker.kaleido.dto.TvshowEpisodeDTO;
import cc.onelooker.kaleido.dto.req.TvshowEpisodeCreateReq;
import cc.onelooker.kaleido.dto.req.TvshowEpisodePageReq;
import cc.onelooker.kaleido.dto.req.TvshowEpisodeUpdateReq;
import cc.onelooker.kaleido.dto.resp.TvshowEpisodeCreateResp;
import cc.onelooker.kaleido.dto.resp.TvshowEpisodePageResp;
import cc.onelooker.kaleido.dto.resp.TvshowEpisodeViewResp;
import cc.onelooker.kaleido.entity.TvshowEpisodeDO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 单集Convert
 *
 * @author cyetstar
 * @date 2023-11-26 23:27:03
 */
@Mapper
public interface TvshowEpisodeConvert {

    TvshowEpisodeConvert INSTANCE = Mappers.getMapper(TvshowEpisodeConvert.class);

    TvshowEpisodeDTO convert(TvshowEpisodeDO entity);

    @InheritInverseConfiguration(name = "convert")
    TvshowEpisodeDO convertToDO(TvshowEpisodeDTO dto);

    TvshowEpisodeDTO convertToDTO(TvshowEpisodePageReq req);

    TvshowEpisodeDTO convertToDTO(TvshowEpisodeCreateReq req);

    TvshowEpisodeDTO convertToDTO(TvshowEpisodeUpdateReq req);

    TvshowEpisodePageResp convertToPageResp(TvshowEpisodeDTO dto);

    TvshowEpisodeViewResp convertToViewResp(TvshowEpisodeDTO dto);

    TvshowEpisodeCreateResp convertToCreateResp(TvshowEpisodeDTO dto);

}