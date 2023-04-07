package cc.onelooker.kaleido.convert.business;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.mapstruct.InheritInverseConfiguration;

import cc.onelooker.kaleido.dto.business.GenreDTO;
import cc.onelooker.kaleido.entity.business.GenreDO;
import cc.onelooker.kaleido.dto.business.req.GenrePageReq;
import cc.onelooker.kaleido.dto.business.req.GenreCreateReq;
import cc.onelooker.kaleido.dto.business.req.GenreUpdateReq;
import cc.onelooker.kaleido.dto.business.resp.GenrePageResp;
import cc.onelooker.kaleido.dto.business.resp.GenreViewResp;
import cc.onelooker.kaleido.dto.business.resp.GenreCreateResp;
import cc.onelooker.kaleido.dto.business.exp.GenreExp;

/**
* Convert
*
* @author cyetstar
* @date 2023-04-06 13:32:12
*/
@Mapper
public interface GenreConvert {

    GenreConvert INSTANCE = Mappers.getMapper(GenreConvert.class);

    GenreDTO convert(GenreDO entity);

    @InheritInverseConfiguration(name="convert")
    GenreDO convertToDO(GenreDTO dto);

    GenreDTO convertToDTO(GenrePageReq req);

    GenreDTO convertToDTO(GenreCreateReq req);

    GenreDTO convertToDTO(GenreUpdateReq req);

    GenrePageResp convertToPageResp(GenreDTO dto);

    GenreViewResp convertToViewResp(GenreDTO dto);

    GenreCreateResp convertToCreateResp(GenreDTO dto);

    GenreExp convertToExp(GenreDTO dto);

}