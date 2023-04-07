package cc.onelooker.kaleido.convert.business;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.mapstruct.InheritInverseConfiguration;

import cc.onelooker.kaleido.dto.business.MovieSetsDTO;
import cc.onelooker.kaleido.entity.business.MovieSetsDO;
import cc.onelooker.kaleido.dto.business.req.MovieSetsPageReq;
import cc.onelooker.kaleido.dto.business.req.MovieSetsCreateReq;
import cc.onelooker.kaleido.dto.business.req.MovieSetsUpdateReq;
import cc.onelooker.kaleido.dto.business.resp.MovieSetsPageResp;
import cc.onelooker.kaleido.dto.business.resp.MovieSetsViewResp;
import cc.onelooker.kaleido.dto.business.resp.MovieSetsCreateResp;
import cc.onelooker.kaleido.dto.business.exp.MovieSetsExp;

/**
* Convert
*
* @author cyetstar
* @date 2023-04-06 13:32:12
*/
@Mapper
public interface MovieSetsConvert {

    MovieSetsConvert INSTANCE = Mappers.getMapper(MovieSetsConvert.class);

    MovieSetsDTO convert(MovieSetsDO entity);

    @InheritInverseConfiguration(name="convert")
    MovieSetsDO convertToDO(MovieSetsDTO dto);

    MovieSetsDTO convertToDTO(MovieSetsPageReq req);

    MovieSetsDTO convertToDTO(MovieSetsCreateReq req);

    MovieSetsDTO convertToDTO(MovieSetsUpdateReq req);

    MovieSetsPageResp convertToPageResp(MovieSetsDTO dto);

    MovieSetsViewResp convertToViewResp(MovieSetsDTO dto);

    MovieSetsCreateResp convertToCreateResp(MovieSetsDTO dto);

    MovieSetsExp convertToExp(MovieSetsDTO dto);

}