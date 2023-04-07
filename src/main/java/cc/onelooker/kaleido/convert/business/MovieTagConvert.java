package cc.onelooker.kaleido.convert.business;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.mapstruct.InheritInverseConfiguration;

import cc.onelooker.kaleido.dto.business.MovieTagDTO;
import cc.onelooker.kaleido.entity.business.MovieTagDO;
import cc.onelooker.kaleido.dto.business.req.MovieTagPageReq;
import cc.onelooker.kaleido.dto.business.req.MovieTagCreateReq;
import cc.onelooker.kaleido.dto.business.req.MovieTagUpdateReq;
import cc.onelooker.kaleido.dto.business.resp.MovieTagPageResp;
import cc.onelooker.kaleido.dto.business.resp.MovieTagViewResp;
import cc.onelooker.kaleido.dto.business.resp.MovieTagCreateResp;
import cc.onelooker.kaleido.dto.business.exp.MovieTagExp;

/**
* Convert
*
* @author cyetstar
* @date 2023-04-06 13:32:12
*/
@Mapper
public interface MovieTagConvert {

    MovieTagConvert INSTANCE = Mappers.getMapper(MovieTagConvert.class);

    MovieTagDTO convert(MovieTagDO entity);

    @InheritInverseConfiguration(name="convert")
    MovieTagDO convertToDO(MovieTagDTO dto);

    MovieTagDTO convertToDTO(MovieTagPageReq req);

    MovieTagDTO convertToDTO(MovieTagCreateReq req);

    MovieTagDTO convertToDTO(MovieTagUpdateReq req);

    MovieTagPageResp convertToPageResp(MovieTagDTO dto);

    MovieTagViewResp convertToViewResp(MovieTagDTO dto);

    MovieTagCreateResp convertToCreateResp(MovieTagDTO dto);

    MovieTagExp convertToExp(MovieTagDTO dto);

}