package cc.onelooker.kaleido.convert.movie;

import cc.onelooker.kaleido.dto.movie.MovieCountryDTO;
import cc.onelooker.kaleido.dto.movie.req.MovieCountryCreateReq;
import cc.onelooker.kaleido.dto.movie.req.MovieCountryPageReq;
import cc.onelooker.kaleido.dto.movie.req.MovieCountryUpdateReq;
import cc.onelooker.kaleido.dto.movie.resp.MovieCountryCreateResp;
import cc.onelooker.kaleido.dto.movie.resp.MovieCountryPageResp;
import cc.onelooker.kaleido.dto.movie.resp.MovieCountryViewResp;
import cc.onelooker.kaleido.entity.movie.MovieCountryDO;
import cc.onelooker.kaleido.exp.movie.MovieCountryExp;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
* 国家地区Convert
*
* @author cyetstar
* @date 2023-11-26 01:19:02
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