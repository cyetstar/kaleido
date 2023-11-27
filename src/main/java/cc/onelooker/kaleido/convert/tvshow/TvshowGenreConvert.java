package cc.onelooker.kaleido.convert.tvshow;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.mapstruct.InheritInverseConfiguration;

import cc.onelooker.kaleido.dto.tvshow.TvshowGenreDTO;
import cc.onelooker.kaleido.entity.tvshow.TvshowGenreDO;
import cc.onelooker.kaleido.dto.tvshow.req.TvshowGenrePageReq;
import cc.onelooker.kaleido.dto.tvshow.req.TvshowGenreCreateReq;
import cc.onelooker.kaleido.dto.tvshow.req.TvshowGenreUpdateReq;
import cc.onelooker.kaleido.dto.tvshow.resp.TvshowGenrePageResp;
import cc.onelooker.kaleido.dto.tvshow.resp.TvshowGenreViewResp;
import cc.onelooker.kaleido.dto.tvshow.resp.TvshowGenreCreateResp;
import cc.onelooker.kaleido.exp.tvshow.TvshowGenreExp;

/**
* 剧集类型Convert
*
* @author cyetstar
* @date 2023-11-26 23:27:03
*/
@Mapper
public interface TvshowGenreConvert {

    TvshowGenreConvert INSTANCE = Mappers.getMapper(TvshowGenreConvert.class);

    TvshowGenreDTO convert(TvshowGenreDO entity);

    @InheritInverseConfiguration(name="convert")
    TvshowGenreDO convertToDO(TvshowGenreDTO dto);

    TvshowGenreDTO convertToDTO(TvshowGenrePageReq req);

    TvshowGenreDTO convertToDTO(TvshowGenreCreateReq req);

    TvshowGenreDTO convertToDTO(TvshowGenreUpdateReq req);

    TvshowGenrePageResp convertToPageResp(TvshowGenreDTO dto);

    TvshowGenreViewResp convertToViewResp(TvshowGenreDTO dto);

    TvshowGenreCreateResp convertToCreateResp(TvshowGenreDTO dto);

    TvshowGenreExp convertToExp(TvshowGenreDTO dto);

}