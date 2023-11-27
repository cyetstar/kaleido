package cc.onelooker.kaleido.convert.tvshow;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.mapstruct.InheritInverseConfiguration;

import cc.onelooker.kaleido.dto.tvshow.TvshowShowDTO;
import cc.onelooker.kaleido.entity.tvshow.TvshowShowDO;
import cc.onelooker.kaleido.dto.tvshow.req.TvshowShowPageReq;
import cc.onelooker.kaleido.dto.tvshow.req.TvshowShowCreateReq;
import cc.onelooker.kaleido.dto.tvshow.req.TvshowShowUpdateReq;
import cc.onelooker.kaleido.dto.tvshow.resp.TvshowShowPageResp;
import cc.onelooker.kaleido.dto.tvshow.resp.TvshowShowViewResp;
import cc.onelooker.kaleido.dto.tvshow.resp.TvshowShowCreateResp;
import cc.onelooker.kaleido.exp.tvshow.TvshowShowExp;

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

    @InheritInverseConfiguration(name="convert")
    TvshowShowDO convertToDO(TvshowShowDTO dto);

    TvshowShowDTO convertToDTO(TvshowShowPageReq req);

    TvshowShowDTO convertToDTO(TvshowShowCreateReq req);

    TvshowShowDTO convertToDTO(TvshowShowUpdateReq req);

    TvshowShowPageResp convertToPageResp(TvshowShowDTO dto);

    TvshowShowViewResp convertToViewResp(TvshowShowDTO dto);

    TvshowShowCreateResp convertToCreateResp(TvshowShowDTO dto);

    TvshowShowExp convertToExp(TvshowShowDTO dto);

}