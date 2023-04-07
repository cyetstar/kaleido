package cc.onelooker.kaleido.convert.business;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.mapstruct.InheritInverseConfiguration;

import cc.onelooker.kaleido.dto.business.MovieDTO;
import cc.onelooker.kaleido.entity.business.MovieDO;
import cc.onelooker.kaleido.dto.business.req.MoviePageReq;
import cc.onelooker.kaleido.dto.business.req.MovieCreateReq;
import cc.onelooker.kaleido.dto.business.req.MovieUpdateReq;
import cc.onelooker.kaleido.dto.business.resp.MoviePageResp;
import cc.onelooker.kaleido.dto.business.resp.MovieViewResp;
import cc.onelooker.kaleido.dto.business.resp.MovieCreateResp;
import cc.onelooker.kaleido.dto.business.exp.MovieExp;

/**
* Convert
*
* @author cyetstar
* @date 2023-04-06 13:32:12
*/
@Mapper
public interface MovieConvert {

    MovieConvert INSTANCE = Mappers.getMapper(MovieConvert.class);

    MovieDTO convert(MovieDO entity);

    @InheritInverseConfiguration(name="convert")
    MovieDO convertToDO(MovieDTO dto);

    MovieDTO convertToDTO(MoviePageReq req);

    MovieDTO convertToDTO(MovieCreateReq req);

    MovieDTO convertToDTO(MovieUpdateReq req);

    MoviePageResp convertToPageResp(MovieDTO dto);

    MovieViewResp convertToViewResp(MovieDTO dto);

    MovieCreateResp convertToCreateResp(MovieDTO dto);

    MovieExp convertToExp(MovieDTO dto);

}