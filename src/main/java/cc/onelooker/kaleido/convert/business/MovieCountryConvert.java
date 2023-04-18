package cc.onelooker.kaleido.convert.business;

import cc.onelooker.kaleido.dto.business.MovieCountryDTO;
import cc.onelooker.kaleido.dto.business.exp.MovieCountryExp;
import cc.onelooker.kaleido.dto.business.req.MovieCountryCreateReq;
import cc.onelooker.kaleido.dto.business.req.MovieCountryPageReq;
import cc.onelooker.kaleido.dto.business.req.MovieCountryUpdateReq;
import cc.onelooker.kaleido.dto.business.resp.MovieCountryCreateResp;
import cc.onelooker.kaleido.dto.business.resp.MovieCountryPageResp;
import cc.onelooker.kaleido.dto.business.resp.MovieCountryViewResp;
import cc.onelooker.kaleido.entity.business.MovieCountryDO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
/**
* 国家地区Convert
*
* @author cyetstar
* @date 2023-04-18 23:04:56
*/
@Mapper
public interface MovieCountryConvert {

    MovieCountryConvert INSTANCE = Mappers.getMapper(MovieCountryConvert.class);

    MovieCountryDTO convert(MovieCountryDO entity);

    @InheritInverseConfiguration(name="convert")
    MovieCountryDO convertToDO(MovieCountryDTO dto);

    MovieCountryDTO convertToDTO(MovieCountryPageReq req);

    MovieCountryDTO convertToDTO(MovieCountryCreateReq req);

    MovieCountryDTO convertToDTO(MovieCountryUpdateReq req);

    MovieCountryPageResp convertToPageResp(MovieCountryDTO dto);

    MovieCountryViewResp convertToViewResp(MovieCountryDTO dto);

    MovieCountryCreateResp convertToCreateResp(MovieCountryDTO dto);

    MovieCountryExp convertToExp(MovieCountryDTO dto);

}