package cc.onelooker.kaleido.convert.tvshow;

import cc.onelooker.kaleido.dto.movie.resp.MovieBasicSearchInfoResp;
import cc.onelooker.kaleido.dto.tvshow.TvshowActorDTO;
import cc.onelooker.kaleido.dto.tvshow.TvshowShowDTO;
import cc.onelooker.kaleido.dto.tvshow.req.TvshowShowCreateReq;
import cc.onelooker.kaleido.dto.tvshow.req.TvshowShowPageReq;
import cc.onelooker.kaleido.dto.tvshow.req.TvshowShowUpdateReq;
import cc.onelooker.kaleido.dto.tvshow.resp.TvshowShowCreateResp;
import cc.onelooker.kaleido.dto.tvshow.resp.TvshowShowPageResp;
import cc.onelooker.kaleido.dto.tvshow.resp.TvshowShowSearchInfoResp;
import cc.onelooker.kaleido.dto.tvshow.resp.TvshowShowViewResp;
import cc.onelooker.kaleido.entity.tvshow.TvshowShowDO;
import cc.onelooker.kaleido.third.tmm.Movie;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * 剧集Convert
 *
 * @author cyetstar
 * @date 2023-11-26 23:27:03
 */
@Mapper
public interface TvshowShowConvert {

    TvshowShowConvert INSTANCE = Mappers.getMapper(TvshowShowConvert.class);

    TvshowShowDTO convert(TvshowShowDO entity);

    @InheritInverseConfiguration(name = "convert")
    TvshowShowDO convertToDO(TvshowShowDTO dto);

    @Mappings({
            @Mapping(source = "genre", target = "genreId")
    })
    TvshowShowDTO convertToDTO(TvshowShowPageReq req);

    TvshowShowDTO convertToDTO(TvshowShowCreateReq req);

    TvshowShowDTO convertToDTO(TvshowShowUpdateReq req);

    TvshowShowPageResp convertToPageResp(TvshowShowDTO dto);

    TvshowShowViewResp convertToViewResp(TvshowShowDTO dto);

    TvshowShowViewResp.Actor convertToViewResp(TvshowActorDTO dto);

    TvshowShowCreateResp convertToCreateResp(TvshowShowDTO dto);

    TvshowShowSearchInfoResp convertToSearchInfoResp(Movie movie);
}