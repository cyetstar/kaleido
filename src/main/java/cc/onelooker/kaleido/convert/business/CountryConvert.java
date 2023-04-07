package cc.onelooker.kaleido.convert.business;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.mapstruct.InheritInverseConfiguration;

import cc.onelooker.kaleido.dto.business.CountryDTO;
import cc.onelooker.kaleido.entity.business.CountryDO;
import cc.onelooker.kaleido.dto.business.req.CountryPageReq;
import cc.onelooker.kaleido.dto.business.req.CountryCreateReq;
import cc.onelooker.kaleido.dto.business.req.CountryUpdateReq;
import cc.onelooker.kaleido.dto.business.resp.CountryPageResp;
import cc.onelooker.kaleido.dto.business.resp.CountryViewResp;
import cc.onelooker.kaleido.dto.business.resp.CountryCreateResp;
import cc.onelooker.kaleido.dto.business.exp.CountryExp;

/**
* Convert
*
* @author cyetstar
* @date 2023-04-06 13:32:12
*/
@Mapper
public interface CountryConvert {

    CountryConvert INSTANCE = Mappers.getMapper(CountryConvert.class);

    CountryDTO convert(CountryDO entity);

    @InheritInverseConfiguration(name="convert")
    CountryDO convertToDO(CountryDTO dto);

    CountryDTO convertToDTO(CountryPageReq req);

    CountryDTO convertToDTO(CountryCreateReq req);

    CountryDTO convertToDTO(CountryUpdateReq req);

    CountryPageResp convertToPageResp(CountryDTO dto);

    CountryViewResp convertToViewResp(CountryDTO dto);

    CountryCreateResp convertToCreateResp(CountryDTO dto);

    CountryExp convertToExp(CountryDTO dto);

}