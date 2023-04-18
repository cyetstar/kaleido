package cc.onelooker.kaleido.convert.business;

import cc.onelooker.kaleido.dto.business.MovieTagDTO;
import cc.onelooker.kaleido.dto.business.exp.MovieTagExp;
import cc.onelooker.kaleido.dto.business.req.MovieTagCreateReq;
import cc.onelooker.kaleido.dto.business.req.MovieTagPageReq;
import cc.onelooker.kaleido.dto.business.req.MovieTagUpdateReq;
import cc.onelooker.kaleido.dto.business.resp.MovieTagCreateResp;
import cc.onelooker.kaleido.dto.business.resp.MovieTagPageResp;
import cc.onelooker.kaleido.dto.business.resp.MovieTagViewResp;
import cc.onelooker.kaleido.entity.business.MovieTagDO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
/**
* 电影标签Convert
*
* @author cyetstar
* @date 2023-04-18 23:04:56
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