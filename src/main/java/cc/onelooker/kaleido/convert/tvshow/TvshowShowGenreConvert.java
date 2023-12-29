package cc.onelooker.kaleido.convert.tvshow;

import cc.onelooker.kaleido.dto.tvshow.TvshowShowGenreDTO;
import cc.onelooker.kaleido.dto.tvshow.req.TvshowShowGenreCreateReq;
import cc.onelooker.kaleido.dto.tvshow.req.TvshowShowGenrePageReq;
import cc.onelooker.kaleido.dto.tvshow.req.TvshowShowGenreUpdateReq;
import cc.onelooker.kaleido.dto.tvshow.resp.TvshowShowGenreCreateResp;
import cc.onelooker.kaleido.dto.tvshow.resp.TvshowShowGenrePageResp;
import cc.onelooker.kaleido.dto.tvshow.resp.TvshowShowGenreViewResp;
import cc.onelooker.kaleido.entity.tvshow.TvshowShowGenreDO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 剧集类型关联表Convert
 *
 * @author cyetstar
 * @date 2023-11-26 23:27:03
 */
@Mapper
public interface TvshowShowGenreConvert {

    TvshowShowGenreConvert INSTANCE = Mappers.getMapper(TvshowShowGenreConvert.class);

    TvshowShowGenreDTO convert(TvshowShowGenreDO entity);

    @InheritInverseConfiguration(name = "convert")
    TvshowShowGenreDO convertToDO(TvshowShowGenreDTO dto);

    TvshowShowGenreDTO convertToDTO(TvshowShowGenrePageReq req);

    TvshowShowGenreDTO convertToDTO(TvshowShowGenreCreateReq req);

    TvshowShowGenreDTO convertToDTO(TvshowShowGenreUpdateReq req);

    TvshowShowGenrePageResp convertToPageResp(TvshowShowGenreDTO dto);

    TvshowShowGenreViewResp convertToViewResp(TvshowShowGenreDTO dto);

    TvshowShowGenreCreateResp convertToCreateResp(TvshowShowGenreDTO dto);

}